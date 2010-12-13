package net.vvakame.util.jsonpullparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * JSONPullParserを提供します.<br>
 * 現在処理中の場所までのJSONとして正しい形式かチェックされます.<br>
 * ただし、最後まで処理したときにJSONとして正しい形式になっているかはわかりません.<br>
 * 途中でJSONとして正しくない形式だった場合は例外が発生します.<br>
 * ライブラリ利用者は、その場合も正しくプログラムが動作するようにコーディングしなければならないです.
 * 
 * @author vvakame
 * 
 */
public class JsonPullParser {
	/**
	 * 現在処理中のトークン.
	 * 
	 * @author vvakame
	 */
	public static enum State {
		/**
		 * 初期状態.
		 */
		ORIGIN,
		/**
		 * キー.<br>
		 * keyの文字列 → {"key":"value"}
		 */
		KEY,
		/**
		 * 文字列の値.<br>
		 * valueの値 → {"key":"value"}
		 */
		VALUE_STRING,
		/**
		 * 数字の値.<br>
		 * valueの値 → {"key":0123}
		 */
		VALUE_LONG,
		/**
		 * 数字の値.<br>
		 * valueの値 → {"key":0123.11}
		 */
		VALUE_DOUBLE,
		/**
		 * 真偽値の値.<br>
		 * valueの値 → {"key":true}
		 */
		VALUE_BOOLEAN,
		/**
		 * nullの値.<br>
		 * valueの値 → {"key":null}
		 */
		VALUE_NULL,
		/**
		 * ハッシュのスタート.<br>
		 * これ → {
		 */
		START_HASH,
		/**
		 * ハッシュのエンド.<br>
		 * これ → }
		 */
		END_HASH,
		/**
		 * 配列のスタート.<br>
		 * これ → [
		 */
		START_ARRAY,
		/**
		 * 配列のエンド.<br>
		 * これ → ]
		 */
		END_ARRAY,
	}

	BufferedReader br;
	Deque<State> stack;

	// 値保持用
	String valueStr;
	long valueLong;
	double valueDouble;
	boolean valueBoolean;

	State lookAhead = null;

	/**
	 * 入力ストリームを設定します.<br>
	 * このメソッドはインスタンス生成後、一番最初に呼ぶべきです.
	 * 
	 * @param is
	 * @throws IOException
	 */
	public void setInput(InputStream is) throws IOException {
		br = new BufferedReader(new InputStreamReader(is));
		stack = new ArrayDeque<JsonPullParser.State>();
		stack.push(State.ORIGIN);
	}

	/**
	 * 一つ先の要素を先読みます.<br>
	 * このメソッドは真のlookAheadではありません.呼び出した途端、getValueXxx()の返り値は壊れてしまいます.<br>
	 * なるべく、このメソッドは使わないほうがよいでしょう<br>
	 * lookAheadは何回呼び出しても、 getEventType() を呼び出すまで、同じ値を返します.<br>
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonFormatException
	 */
	public State lookAhead() throws IOException, JsonFormatException {
		if (lookAhead == null) {
			lookAhead = getEventType();
		}
		return lookAhead;
	}

	/**
	 * 現在の状態を取得します.<br>
	 * このメソッドを使う前に、{@link JsonPullParser#setInput(InputStream)}を呼ぶべきです.
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonFormatException
	 */
	public State getEventType() throws IOException, JsonFormatException {
		if (lookAhead != null) {
			State tmp = lookAhead;
			lookAhead = null;
			return tmp;
		}

		char c = getNextChar();
		switch (stack.pop()) {
		case ORIGIN:
			switch (c) {
			case '{':
				stack.push(State.START_HASH);
				break;
			case '[':
				stack.push(State.START_ARRAY);
				break;
			default:
				throw new JsonFormatException();
			}
			break;
		case START_ARRAY:
			stack.push(State.START_ARRAY);
			switch (c) {
			case '{':
				stack.push(State.START_HASH);
				break;
			case '[':
				stack.push(State.START_ARRAY);
				break;
			case '"':
				stack.push(State.VALUE_STRING);
				valueStr = getNextString();
				break;
			case ']':
				stack.push(State.END_ARRAY);
				break;
			case 't':
				expectNextChar('r');
				expectNextChar('u');
				expectNextChar('e');

				stack.push(State.VALUE_BOOLEAN);
				valueBoolean = true;
				break;
			case 'f':
				expectNextChar('a');
				expectNextChar('l');
				expectNextChar('s');
				expectNextChar('e');

				stack.push(State.VALUE_BOOLEAN);
				valueBoolean = false;
				break;
			case 'n':
				expectNextChar('u');
				expectNextChar('l');
				expectNextChar('l');

				stack.push(State.VALUE_NULL);
				break;
			default:
				// 数字
				try {
					fetchNextNumeric();
					break;
				} catch (NumberFormatException e) {
					throw new JsonFormatException(e);
				}
			}
			break;

		case START_HASH:
			stack.push(State.START_HASH);
			switch (c) {
			case '{':
				stack.push(State.START_HASH);
				break;
			case '[':
				stack.push(State.START_ARRAY);
				break;
			case '}':
				stack.push(State.END_HASH);
				break;
			case '"':
				stack.push(State.KEY);
				valueStr = getNextString();
				c = getNextChar();
				if (c != ':') {
					throw new JsonFormatException();
				}
				break;
			default:
				throw new JsonFormatException();
			}
			break;

		case END_ARRAY:
			if (!State.START_ARRAY.equals(stack.pop())) {
				throw new JsonFormatException();
			}
			switch (c) {
			case ',':
				getEventType();
				break;
			case ']':
				stack.push(State.END_ARRAY);
				break;
			case '}':
				stack.push(State.END_HASH);
				break;
			default:
				throw new JsonFormatException();
			}
			break;
		case END_HASH:
			if (!State.START_HASH.equals(stack.pop())) {
				throw new JsonFormatException();
			}
			switch (c) {
			case ',':
				getEventType();
				break;
			case ']':
				stack.push(State.END_ARRAY);
				break;
			case '}':
				stack.push(State.END_HASH);
				break;
			default:
				throw new JsonFormatException();
			}
			break;
		case KEY:
			switch (c) {
			case '"':
				stack.push(State.VALUE_STRING);
				valueStr = getNextString();
				break;
			case '[':
				stack.push(State.START_ARRAY);
				break;
			case '{':
				stack.push(State.START_HASH);
				break;
			case 't':
				expectNextChar('r');
				expectNextChar('u');
				expectNextChar('e');

				stack.push(State.VALUE_BOOLEAN);
				valueBoolean = true;
				break;
			case 'f':
				expectNextChar('a');
				expectNextChar('l');
				expectNextChar('s');
				expectNextChar('e');

				stack.push(State.VALUE_BOOLEAN);
				valueBoolean = false;
				break;
			case 'n':
				expectNextChar('u');
				expectNextChar('l');
				expectNextChar('l');

				stack.push(State.VALUE_NULL);
				break;
			default:
				// 数字
				try {
					fetchNextNumeric();
					break;
				} catch (NumberFormatException e) {
					throw new JsonFormatException(e);
				}
			}
			break;
		case VALUE_STRING:
		case VALUE_LONG:
		case VALUE_DOUBLE:
		case VALUE_NULL:
		case VALUE_BOOLEAN:
			switch (c) {
			case ',':
				getEventType();
				break;
			case '}':
				stack.push(State.END_HASH);
				break;
			case ']':
				stack.push(State.END_ARRAY);
				break;
			default:
				throw new JsonFormatException();
			}
			break;
		default:
			throw new JsonFormatException();
		}

		return stack.getFirst();
	}

	/**
	 * 値を文字列として取得します.<br> {@link JsonPullParser#getEventType()}を読んだ時に
	 * {@link State#KEY}もしくは{@link State#VALUE_STRING}が返ってきたときに呼び出してください.
	 * 
	 * @return 読み込んだ文字列
	 */
	public String getValueString() {
		return valueStr;
	}

	/**
	 * 値を整数値として取得します.<br> {@link JsonPullParser#getEventType()}を読んだ時に
	 * {@link State#VALUE_LONG}が返ってきたときに呼び出してください.
	 * 
	 * @return 読み込んだ整数値
	 */
	public long getValueLong() {
		return valueLong;
	}

	/**
	 * 値を整数値として取得します.<br> {@link JsonPullParser#getEventType()}を読んだ時に
	 * {@link State#VALUE_DOUBLE}が返ってきたときに呼び出してください.
	 * 
	 * @return 読み込んだ浮動小数点の値
	 */
	public double getValueDouble() {
		return valueDouble;
	}

	/**
	 * 値を整数値として取得します.<br> {@link JsonPullParser#getEventType()}を読んだ時に
	 * {@link State#VALUE_BOOLEAN}が返ってきたときに呼び出してください.
	 * 
	 * @return 読み込んだ真偽値の値
	 */
	public boolean getValueBoolean() {
		return valueBoolean;
	}

	private char getNextChar() throws IOException {
		br.mark(1);
		char c = (char) br.read();
		while (c == ' ' || c == '\r' || c == '\n') {
			br.mark(1);
			c = (char) br.read();
		}
		return c;
	}

	private void expectNextChar(char expect) throws IOException,
			JsonFormatException {
		char c = getNextChar();
		if (c != expect) {
			throw new JsonFormatException();
		}
	}

	StringBuilder stb = new StringBuilder();

	private void fetchNextNumeric() throws IOException {
		stb.setLength(0);
		br.reset();
		char c;
		boolean d = false;
		loop: while (true) {
			c = (char) br.read();
			switch (c) {
			case '.':
			case 'e':
			case 'E':
				d = true;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '-':
				break;
			default:
				br.reset();
				break loop;
			}
			br.mark(1);
			stb.append(c);
		}
		if (d) {
			valueDouble = Double.parseDouble(stb.toString());
			stack.push(State.VALUE_DOUBLE);
		} else {
			valueLong = Long.parseLong(stb.toString());
			stack.push(State.VALUE_LONG);
		}
	}

	private String getNextString() throws IOException {
		stb.setLength(0);
		char c;
		loop: while (true) {
			c = (char) br.read();
			switch (c) {
			case '\\':
				br.mark(5);
				c = (char) br.read();
				switch (c) {
				case '/':
				case '"':
				case '\\':
					break;
				case 'n':
					c = '\n';
					break;
				case 'r':
					c = '\r';
					break;
				case 't':
					c = '\t';
					break;
				case 'b':
					c = '\b';
					break;
				case 'f':
					c = '\f';
					break;
				case 'u':
					c = 0;
					int r;
					r = getNextStringHelper(br.read());
					if (r == -1) {
						c = '\\';
						br.reset();
						break;
					} else {
						c += r * 4096;
					}
					r = getNextStringHelper(br.read());
					if (r == -1) {
						c = '\\';
						br.reset();
						break;
					} else {
						c += r * 256;
					}
					r = getNextStringHelper(br.read());
					if (r == -1) {
						c = '\\';
						br.reset();
						break;
					} else {
						c += r * 16;
					}
					r = getNextStringHelper(br.read());
					if (r == -1) {
						c = '\\';
						br.reset();
						break;
					} else {
						c += r;
					}
					break;
				default:
					c = '\\';
					br.reset();
					break;
				}
				break;
			case '"':
				break loop;
			default:
				break;
			}
			stb.append(c);
		}
		return stb.toString();
	}

	private int getNextStringHelper(int c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'f') {
			return c - 'a' + 10;
		} else {
			return -1;
		}
	}
}
