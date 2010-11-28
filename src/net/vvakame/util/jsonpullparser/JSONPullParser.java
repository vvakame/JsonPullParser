package net.vvakame.util.jsonpullparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * JSONPullParserを提供します.<br>
 * [予定] 現在処理中の場所までのJSONとして正しい形式かチェックされます.<br>
 * ただし、最後まで処理したときにJSONとして正しい形式になっているかはわかりません.<br>
 * 途中でJSONとして正しくない形式だった場合は例外が発生します.<br>
 * ライブラリ利用者は、その場合も正しくプログラムが動作するようにコーディングしなければならないです.
 * 
 * @author vvakame
 * 
 */
public class JSONPullParser {
	/**
	 * 現在処理中のトークン.
	 * 
	 * @author vvakame
	 */
	static enum Current {
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
		VALUE_NUMERIC,
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
	Stack<Current> stack;
	String valueStr;
	int valueInt;
	double valueDouble;
	boolean valueBoolean;

	public void setInput(InputStream is) throws IOException {
		br = new BufferedReader(new InputStreamReader(is));
		stack = new Stack<JSONPullParser.Current>();
		stack.push(Current.ORIGIN);
	}

	public Current getEventType() throws IOException {
		char c = getNextChar();
		switch (stack.pop()) {
		case ORIGIN:
			switch (c) {
			case '{':
				stack.push(Current.START_HASH);
				break;
			case '[':
				stack.push(Current.START_ARRAY);
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
		case START_ARRAY:
			switch (c) {
			case '{':
				stack.push(Current.START_HASH);
				break;
			case '[':
				stack.push(Current.START_ARRAY);
				break;
			case '"':
				stack.push(Current.KEY);
				// TODO
				break;
			case ']':
				stack.push(Current.END_ARRAY);
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
			break;

		case START_HASH:
			switch (c) {
			case '{':
				stack.push(Current.START_HASH);
				break;
			case '[':
				stack.push(Current.START_ARRAY);
				break;
			case '}':
				stack.push(Current.END_HASH);
				break;
			case '"':
				stack.push(Current.KEY);
				valueStr = getNextString();
				c = getNextChar();
				if (c != ':') {
					// TODO 多分独自Exceptionにしたほうがいい
					throw new IllegalStateException();
				}
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
			break;

		case END_ARRAY:
			switch (c) {
			case ',':
				// TODO
				break;
			case ']':
				stack.push(Current.END_ARRAY);
				break;
			case '}':
				stack.push(Current.END_HASH);
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}

		case END_HASH:
			switch (c) {
			case ',':
				// TODO
				break;
			case ']':
				stack.push(Current.END_ARRAY);
				break;
			case '}':
				stack.push(Current.END_HASH);
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
			break;
		case KEY:
			switch (c) {
			case '"':
				stack.push(Current.VALUE_STRING);
				valueStr = getNextString();
				break;
			case 't':
				expectNextChar('r');
				expectNextChar('u');
				expectNextChar('e');

				stack.push(Current.VALUE_BOOLEAN);
				valueBoolean = true;
				break;
			case 'f':
				expectNextChar('a');
				expectNextChar('l');
				expectNextChar('s');
				expectNextChar('e');

				stack.push(Current.VALUE_BOOLEAN);
				valueBoolean = false;
				break;
			case 'n':
				expectNextChar('u');
				expectNextChar('l');
				expectNextChar('l');

				stack.push(Current.VALUE_NULL);
				break;
			default:
				// 数字
				// true
				// false
				// null

				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
			break;
		case VALUE_STRING:
		case VALUE_NUMERIC:
		case VALUE_NULL:
		case VALUE_BOOLEAN:
			switch (c) {
			case ',':
				// TODO
				break;
			case '}':
				stack.push(Current.END_HASH);
				break;
			case ']':
				stack.push(Current.END_ARRAY);
				break;
			default:
				// TODO 多分独自Exceptionにしたほうがいい
				throw new IllegalStateException();
			}
			break;
		default:
			// TODO 多分独自Exceptionにしたほうがいい
			throw new IllegalStateException();
		}

		return stack.lastElement();
	}

	private void expectNextChar(char expect) throws IOException {
		char c = getNextChar();
		if (c != expect) {
			// TODO 多分独自Exceptionにしたほうがいい
			throw new IllegalStateException();
		}
	}

	public Object getValue() {
		return null;
	}

	public String getValueString() {
		return valueStr;
	}

	public int getValueInt() {
		return valueInt;
	}

	public double getValueDouble() {
		return valueDouble;
	}

	public boolean getValueBoolean() {
		return valueBoolean;
	}

	private char getNextChar() throws IOException {
		char c = (char) br.read();
		while (c == ' ' || c == '\r' || c == '\n') {
			c = (char) br.read();
		}
		return c;
	}

	StringBuilder stb = new StringBuilder();

	private String getNextString() throws IOException {
		stb.setLength(0);
		char c;
		while (true) {
			c = (char) br.read();
			if (c == '"') {
				break;
			}
			stb.append(c);
		}
		return stb.toString();
	}
}
