/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A JSON pull parser implementation.<br>
 * The parser guarantees format correctness till the point it stands at any given moment.<br>
 * (however, a successful parse doesn't guarantee for overall concreteness of the document.)<br>
 * As it throws an exception anytime it encounters an unexpected format defect, it is imperative that the caller handles that situation gracefully. 
 *
 * @author vvakame
 * 
 */
public class JsonPullParser {

	/**
	 * The token in the hand.
	 * 
	 * @author vvakame
	 */
	public static enum State {
		/**
		 * The initial state.
		 */
		ORIGIN,
		/**
		 * Key.<br>
		 * (e.g. {"key":...})
		 */
		KEY,
		/**
		 * String value.<br>
		 * (e.g. {...:"value"})
		 */
		VALUE_STRING,
		/**
		 *
		 * Long value.<br>
		 * (e.g. {...:0123})
		 */
		VALUE_LONG,
		/**
		 * Double precision real value.<br>
		 * (e.g. {...:0123.11})
		 */
		VALUE_DOUBLE,
		/**
		 * Boolean value.<br>
		 * (e.g. {...:true})
		 */
		VALUE_BOOLEAN,
		/**
		 * The null value.<br>
		 * (e.g. {...:null})
		 */
		VALUE_NULL,
		/**
		 * Hash start marker.<br>
		 * (i.e. {)
		 */
		START_HASH,
		/**
		 * Hash end marker.<br>
		 * (e.g. })
		 */
		END_HASH,
		/**
		 * Array start marker.<br>
		 * (e.g. [)
		 */
		START_ARRAY,
		/**
		 * Array end marker.<br>
		 * (e.g. ])
		 */
		END_ARRAY,
	}


	/**
	 * The fallback encoding for naive streams.
	 */
	public static final String DEFAULT_CHARSET_NAME = "UTF-8";

	/**
	 * The fallback encoding if naive streams.
	 */
	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

	BufferedReader br;

	final Stack<State> stack = new Stack<State>();

	// 値保持用
	State current;

	String valueStr;

	long valueLong;

	double valueDouble;

	boolean valueBoolean;

	State lookAhead = null;

	// ログ作成用
	boolean logEnabled = false;

	List<JsonSlice> slices = null;


	/**
	 * Creates a new parser, using the given InputStream as its {@code JSON} feed.
	 * 
	 * <p>
	 * Assumes the characters in the stream are encoded in {@link #DEFAULT_CHARSET_NAME}.
	 * </p>
	 * 
	 * @param is
	 *            An InputStream serves as {@code JSON} feed (should be in the default encoding.)  Cannot be null.
	 *
	 * @return {@link JsonPullParser}
	 * @throws IllegalArgumentException
	 *             {@code null} has been passed in where not applicable.
	 */
	public static JsonPullParser newParser(InputStream is) {
		return newParser(is, DEFAULT_CHARSET);
	}

	/**
	 * Creates a new parser, using the given InputStream as its {@code JSON} feed.
	 * 
	 * <p>
	 * Please call one of the {@code setSource(...)}'s before calling other methods.
	 * </p>
	 * 
	 * @param is
	 *            An InputStream serves as {@code JSON} feed. Cannot be null.
	 * @param charsetName
	 *            The character set should be assumed in which in the stream are encoded. {@link #DEFAULT_CHARSET_NAME} is assumed if null is passed.
	 * @return {@link JsonPullParser}
	 * @throws UnsupportedEncodingException
	 *             An unknown character set is specified.
	 * @throws IllegalArgumentException
	 *             {@code null} has been passed in where not applicable.
	 */
	public static JsonPullParser newParser(InputStream is, String charsetName)
			throws UnsupportedEncodingException {
		if (is == null) {
			throw new IllegalArgumentException("'is' must not be null.");
		}

		try {
			final Charset charset = (charsetName == null) ? null : Charset.forName(charsetName);
			return newParser(is, charset);
		} catch (UnsupportedCharsetException e) {
			throw new UnsupportedEncodingException(e.getCharsetName());
		}
	}

	/**
	 * Creates a new parser, using the given InputStream as its {@code JSON} feed.
	 * 
	 * <p>
	 * Please call one of the {@code setSource(...)}'s before calling other methods.
	 * </p>
	 * 
	 * @param is
	 *            An InputStream serves as {@code JSON} feed.  Cannot be null.
	 * @param charset
	 *            The character set should be assumed in which in the stream are encoded. {@link #DEFAULT_CHARSET_NAME} is assumed if null is passed.
	 * @return {@link JsonPullParser}
	 * @throws IllegalArgumentException
	 *             {@code null} has been passed in where not applicable.
	 */
	public static JsonPullParser newParser(InputStream is, Charset charset) {
		if (is == null) {
			throw new IllegalArgumentException("'is' must not be null.");
		}

		final Reader reader =
				new InputStreamReader(is, (charset == null) ? DEFAULT_CHARSET : charset);
		return newParser(reader);
	}

	/**
	 * Creates a new parser, using the given InputStream as its {@code JSON} feed.
	 * 
	 * <p>
	 * Please call one of the {@code setSource(...)}'s before calling other methods.
	 * </p>
	 * 
	 * @param json
	 *            An InputStream serves as {@code JSON} feed.  Cannot be null.
	 * @return {@link JsonPullParser}
	 * @throws IllegalArgumentException
	 *             {@code null} has been passed in where not applicable.
	 */
	public static JsonPullParser newParser(String json) {
		if (json == null) {
			throw new IllegalArgumentException("'json' must not be null.");
		}

		return newParser(new StringReader(json));
	}

	/**
	 * Creates a new parser, using the given InputStream as its {@code JSON} feed.
	 * 
	 * <p>
	 * Please call one of the {@code setSource(...)}'s before calling other methods.
	 * </p>
	 * 
	 * @param reader
	 *            A Reader. Cannot be null.
	 * @return {@link JsonPullParser}
	 * @throws IllegalArgumentException
	 *             {@code null} has been passed in where not applicable.
	 */
	public static JsonPullParser newParser(Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("'reader' must not be null.");
		}

		BufferedReader br =
				(reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(
						reader);
		JsonPullParser parser = new JsonPullParser();
		parser.setSource(br);
		return parser;
	}

	JsonPullParser() {
		stack.push(State.ORIGIN);
	}

	/**
	 * Sets an Reader as the {@code JSON} feed.
	 * @param reader
	 *            A Reader. Cannot be null.
	 */
	void setSource(Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("'reader' must not be null.");
		}

		br =
				(reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(
						reader);
	}

	/**
	 * Sets if original-equivalent data should be saved as parsing goes.
	 * @return this ({@link JsonPullParser})
	 * @author vvakame
	 */
	public JsonPullParser setLogEnable() {
		logEnabled = true;
		slices = new ArrayList<JsonSlice>();
		return this;
	}

	/**
	 * Advances by one element.
	 * 
	 * <p>
	 * This method is not a true lookahead operation, and has adverse effects on the return value of getValue* family.  You have been warned.
	 * </p>
	 * <p>
	 * Its return value does not mutate until you call {@link #getEventType()}.
	 * </p>
	 * 
	 * @return The next token type.
	 * @throws IOException
	 *             {@code JSON} data cannot read successfully.
	 * @throws JsonFormatException
	 *             The data is malformed
	 */
	public State lookAhead() throws IOException, JsonFormatException {
		if (lookAhead == null) {
			lookAhead = getEventType();
		}
		return lookAhead;
	}

	/**
	 * Returns the current token type.
	 * 
	 * @return The token type in the hand.
	 * @throws IOException
	 *             {@code JSON} data cannot read successfully.
	 * @throws JsonFormatException
	 *             The data is malformed
	 */
	public State getEventType() throws IOException, JsonFormatException {
		if (lookAhead != null) {
			State tmp = lookAhead;
			lookAhead = null;
			return tmp;
		}

		boolean saveSlice = true;
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
						throw new JsonFormatException("unexpected token. token=" + c, this);
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
							throw new JsonFormatException(e, this);
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
							throw new JsonFormatException("unexpected token. token=" + c, this);
						}
						break;
					default:
						throw new JsonFormatException("unexpected token. token=" + c, this);
				}
				break;

			case END_ARRAY:
				if (!State.START_ARRAY.equals(stack.pop())) {
					throw new JsonFormatException("unexpected token.", this);
				}
				switch (c) {
					case ',':
						getEventType();
						saveSlice = false;
						break;
					case ']':
						stack.push(State.END_ARRAY);
						break;
					case '}':
						stack.push(State.END_HASH);
						break;
					default:
						throw new JsonFormatException("unexpected token. token=" + c, this);
				}
				break;
			case END_HASH:
				if (!State.START_HASH.equals(stack.pop())) {
					throw new JsonFormatException("unexpected token.", this);
				}
				switch (c) {
					case ',':
						getEventType();
						saveSlice = false;
						break;
					case ']':
						stack.push(State.END_ARRAY);
						break;
					case '}':
						stack.push(State.END_HASH);
						break;
					default:
						throw new JsonFormatException("unexpected token. token=" + c, this);
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
							throw new JsonFormatException(e, this);
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
						saveSlice = false;
						break;
					case '}':
						stack.push(State.END_HASH);
						break;
					case ']':
						stack.push(State.END_ARRAY);
						break;
					default:
						throw new JsonFormatException("unexpected token. token=" + c, this);
				}
				break;
			default:
				throw new JsonFormatException("unexpected token.", this);
		}

		current = stack.peek();

		if (logEnabled && saveSlice) {
			saveSlices();
		}

		return current;
	}

	void saveSlices() throws JsonFormatException {
		switch (current) {
			case START_ARRAY:
			case END_ARRAY:
			case START_HASH:
			case END_HASH:
			case VALUE_NULL:
				slices.add(new JsonSlice(current));
				break;
			case KEY:
			case VALUE_STRING:
				slices.add(new JsonSlice(current, valueStr));
				break;
			case VALUE_BOOLEAN:
				slices.add(new JsonSlice(current, valueBoolean));
				break;
			case VALUE_DOUBLE:
				slices.add(new JsonSlice(current, valueDouble));
				break;
			case VALUE_LONG:
				slices.add(new JsonSlice(current, valueLong));
				break;
			default:
				throw new JsonFormatException("unknown State=" + current, this);
		}
	}

	/**
	 * Returns the size of the {@link JsonSlice} in the hand.<br>
	 * Consider and compensates the effect of {@link #lookAhead()} (i.e. it consumes the slice by 1.)
	 * @return The current slice size.
	 * @author vvakame
	 */
	public int getSliceSize() {
		if (slices == null) {
			return -1;
		} else {
			return lookAhead == null ? slices.size() : slices.size() - 1;
		}
	}

	/**
	 * Reads and discards the next value.<br>
	 * We:
	 * a) discard the whole element if we're standing on an array ({@link State#START_HASH}) or a hash ({@link State#START_HASH},) or
	 * b) discard the key and corresponding value if we're standing on a key ({@link State#KEY},) or
	 * c) discard the value itself if we're standing on a value, or
	 * d) throw {@link IllegalStateException} as we're in an unexpected situation.
	 *
	 * @throws IOException
	 *             {@code JSON} data cannot read successfully.
	 * @throws JsonFormatException
	 *             The data is malformed
	 * @throws IllegalStateException when it stumbles upon an unexpected {@link State}.
	 */
	public void discardValue() throws IOException, JsonFormatException, IllegalStateException {
		State state = lookAhead();
		switch (state) {
			case START_ARRAY:
				discardArrayToken();
				break;
			case START_HASH:
				discardHashToken();
				break;
			case KEY:
				getEventType();
				discardValue();
				break;
			case VALUE_NULL:
			case VALUE_STRING:
			case VALUE_BOOLEAN:
			case VALUE_LONG:
			case VALUE_DOUBLE:
				getEventType();
				break;
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * Reads and discards the next array/hash or null.<br>
	 * Throws {@link IllegalStateException} otherwise.
	 * @throws IOException
	 *             {@code JSON} data cannot read successfully.
	 * @throws JsonFormatException
	 *             The data is malformed
	 * @throws IllegalStateException when it stumbles upon an unexpected {@link State}.
	 */
	public void discardArrayToken() throws IOException, JsonFormatException, IllegalStateException {
		State state = lookAhead();
		switch (state) {
			case START_ARRAY:
				getEventType();
				while ((state = lookAhead()) != State.END_ARRAY) {
					switch (state) {
						case START_ARRAY:
							discardArrayToken();
							break;
						case START_HASH:
							discardHashToken();
							break;
						case VALUE_NULL:
						case VALUE_STRING:
						case VALUE_BOOLEAN:
						case VALUE_LONG:
						case VALUE_DOUBLE:
							getEventType();
							break;
						default:
							throw new IllegalStateException("unexpected token. token=" + state);
					}
				}
				getEventType();

				break;
			case VALUE_NULL:
				getEventType();
				break;
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * Reads and discards the next hash or null.<br>
	 * Throws {@link IllegalStateException} otherwise.
	 * @throws IOException
	 *             {@code JSON} data cannot read successfully.
	 * @throws JsonFormatException
	 *             The data is malformed
	 * @throws IllegalStateException when it stumbles upon an unexpected {@link State}.
	 */
	public void discardHashToken() throws IOException, JsonFormatException, IllegalStateException {
		State state = lookAhead();
		switch (state) {
			case START_HASH:
				getEventType();
				while ((state = lookAhead()) != State.END_HASH) {
					switch (state) {
						case START_ARRAY:
							discardArrayToken();
							break;
						case START_HASH:
							discardHashToken();
							break;
						case KEY:
						case VALUE_NULL:
						case VALUE_STRING:
						case VALUE_BOOLEAN:
						case VALUE_LONG:
						case VALUE_DOUBLE:
							getEventType();
							break;
						default:
							throw new IllegalStateException("unexpected token. token=" + state);
					}
				}
				getEventType();

				break;
			case VALUE_NULL:
				getEventType();
				break;
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * Get the current value as string.<br>
	 * 
	 * <p>
	 * Please call if {@link JsonPullParser#getEventType()} returns {@link State#KEY} or
	 * {@link State#VALUE_STRING}.
	 * </p>
	 * 
	 * @return The current value read as string.
	 */
	public String getValueString() {
		if (current == State.KEY) {
			return valueStr;
		} else if (current == State.VALUE_STRING) {
			return valueStr;
		} else if (current == State.VALUE_NULL) {
			return null;
		} else {
			throw new IllegalStateException("unexpected state. state=" + current);
		}
	}

	/**
	 * Get the current value as integer.<br>
	 * 
	 * <p>
	 * Please call if {@link JsonPullParser#getEventType()} returns {@link State#VALUE_LONG}.
	 * </p>
	 * 
	 * @return The current value read as integer.
	 * @throws NullPointerException The current value is null ({@link #getEventType()} returns {@link State#VALUE_NULL}.)
	 */
	public long getValueLong() throws NullPointerException {
		if (current == State.VALUE_LONG) {
			return valueLong;
		} else if (current == State.VALUE_NULL) {
			return -1;
		} else {
			throw new IllegalStateException("unexpected state. state=" + current);
		}
	}

	/**
	 * Get the current value as double-precison floating point number.<br>
	 * 
	 * <p>
	 * Please call if {@link JsonPullParser#getEventType()} returns {@link State#VALUE_DOUBLE}.
	 * </p>
	 * 
	 * @return The current value read as double-precison floating point number.
	 * @throws NullPointerException The current value is null ({@link #getEventType()} returns {@link State#VALUE_NULL}.)
	 */
	public double getValueDouble() throws NullPointerException {
		if (current == State.VALUE_DOUBLE) {
			return valueDouble;
		} else if (current == State.VALUE_NULL) {
			return -1;
		} else if (current == State.VALUE_LONG) {
			return valueLong;
		} else {
			throw new IllegalStateException("unexpected state. state=" + current);
		}
	}

	/**
	 * Get the current value as boolean.<br>
	 * 
	 * <p>
	 * Please call if {@link JsonPullParser#getEventType()} returns {@link State#VALUE_BOOLEAN}.
	 * </p>
	 * 
	 * @return The current value read as boolean.
	 * @throws NullPointerException The current value is null ({@link #getEventType()} returns {@link State#VALUE_NULL}.)
	 */
	public boolean getValueBoolean() throws NullPointerException {
		if (current == State.VALUE_BOOLEAN) {
			return valueBoolean;
		} else if (current == State.VALUE_NULL) {
			return false;
		} else {
			throw new IllegalStateException("unexpected state. state=" + current);
		}
	}

	private char getNextChar() throws IOException {
		br.mark(1);
		char c = (char) br.read();
		while (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
			br.mark(1);
			c = (char) br.read();
		}
		return c;
	}

	private void expectNextChar(char expect) throws IOException, JsonFormatException {
		char c = getNextChar();
		if (c != expect) {
			throw new JsonFormatException("unexpected char. expected=" + expect + ", char=" + c,
					this);
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
							for (int i = 0; i < 4; i++) {
								r = getNextStringHelper(br.read());
								if (r == -1) {
									c = '\\';
									br.reset();
									break;
								}
								c <<= 4;
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
		} else if ('A' <= c && c <= 'F') {
			return c - 'A' + 10;
		} else {
			return -1;
		}
	}

	/**
	 * @return the logEnable
	 * @category accessor
	 */
	public boolean isLogEnabled() {
		return logEnabled;
	}

	/**
	 * @return the slices
	 * @category accessor
	 */
	public List<JsonSlice> getSlices() {
		return slices;
	}
}
