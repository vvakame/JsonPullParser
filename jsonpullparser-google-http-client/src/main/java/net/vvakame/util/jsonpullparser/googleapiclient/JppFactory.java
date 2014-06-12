package net.vvakame.util.jsonpullparser.googleapiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;

/**
 * Implementation about {@link JsonFactory} by JsonPullParser.
 * @author vvakame
 */
public class JppFactory extends JsonFactory {

	@Override
	public JsonParser createJsonParser(InputStream in) {
		if (in == null) {
			throw new NullPointerException("in is required");
		}
		JsonPullParser parser = JsonPullParser.newParser(in);
		return new JppParser(this, parser);
	}

	@Override
	public JsonParser createJsonParser(InputStream in, Charset charset) {
		if (in == null) {
			throw new NullPointerException("in is required");
		}
		JsonPullParser parser = JsonPullParser.newParser(in, charset);
		return new JppParser(this, parser);
	}

	@Override
	public JsonParser createJsonParser(String value) {
		if (value == null) {
			throw new NullPointerException("value is required");
		}
		// vs invalid format JSON data.
		if (value.indexOf("{") == -1) {
			StubParser parser = adhocSolutions(value);
			if (parser != null) {
				return parser;
			}
		}

		JsonPullParser parser = JsonPullParser.newParser(value);
		return new JppParser(this, parser);
	}

	// adhoc solutions...
	// Why support for invalid JSON data at JsonFactory!? It's crazy!! >:(
	StubParser adhocSolutions(String value) {
		if (Boolean.toString(true).equals(value)) {
			return new StubParser(this) {

				@Override
				public JsonToken nextToken() {
					return JsonToken.VALUE_TRUE;
				}

				@Override
				public JsonToken getCurrentToken() {
					return JsonToken.VALUE_TRUE;
				}
			};
		} else if (Boolean.toString(false).equals(value)) {
			return new StubParser(this) {

				@Override
				public JsonToken nextToken() {
					return JsonToken.VALUE_FALSE;
				}

				@Override
				public JsonToken getCurrentToken() {
					return JsonToken.VALUE_FALSE;
				}
			};
		} else if ("null".equals(value)) {
			return new StubParser(this) {

				@Override
				public JsonToken nextToken() {
					return JsonToken.VALUE_NULL;
				}

				@Override
				public JsonToken getCurrentToken() {
					return JsonToken.VALUE_NULL;
				}
			};
		} else if (value.startsWith("\"") && value.endsWith("\"")) {
			final StringBuilder builder = new StringBuilder(value);
			builder.deleteCharAt(0);
			builder.setLength(builder.length() - 1);
			return new StubParser(this) {

				@Override
				public JsonToken nextToken() {
					return JsonToken.VALUE_STRING;
				}

				@Override
				public JsonToken getCurrentToken() {
					return JsonToken.VALUE_STRING;
				}

				@Override
				public String getText() {
					return builder.toString();
				}
			};
		} else {
			try {
				final BigDecimal decimal = new BigDecimal(value);
				return new StubParser(this) {

					@Override
					public JsonToken nextToken() {
						try {
							decimal.longValueExact();
							return JsonToken.VALUE_NUMBER_INT;
						} catch (ArithmeticException e) {
							return JsonToken.VALUE_NUMBER_FLOAT;
						}
					}

					@Override
					public JsonToken getCurrentToken() {
						try {
							decimal.longValueExact();
							return JsonToken.VALUE_NUMBER_INT;
						} catch (ArithmeticException e) {
							return JsonToken.VALUE_NUMBER_FLOAT;
						}
					}

					@Override
					public short getShortValue() {
						return decimal.shortValue();
					}

					@Override
					public int getIntValue() {
						return decimal.intValue();
					}

					@Override
					public float getFloatValue() {
						return decimal.floatValue();
					}

					@Override
					public long getLongValue() {
						return decimal.longValue();
					}

					@Override
					public double getDoubleValue() {
						return decimal.doubleValue();
					}

					@Override
					public BigInteger getBigIntegerValue() {
						return decimal.toBigInteger();
					}

					@Override
					public BigDecimal getDecimalValue() {
						return decimal;
					}
				};
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	@Override
	public JsonParser createJsonParser(Reader reader) {
		if (reader == null) {
			throw new NullPointerException("reader is required");
		}
		JsonPullParser parser = JsonPullParser.newParser(reader);
		return new JppParser(this, parser);
	}

	@Override
	public JsonGenerator createJsonGenerator(OutputStream out, Charset enc) {
		return new JppGenerator(this, out, enc);
	}

	@Override
	public JsonGenerator createJsonGenerator(Writer writer) {
		return new JppGenerator(this, writer);
	}

	static JsonToken convert(State state, JsonPullParser parser) {
		if (state == null) {
			return null;
		}
		switch (state) {
			case END_ARRAY:
				return JsonToken.END_ARRAY;
			case START_ARRAY:
				return JsonToken.START_ARRAY;
			case END_HASH:
				return JsonToken.END_OBJECT;
			case START_HASH:
				return JsonToken.START_OBJECT;
			case VALUE_BOOLEAN:
				if (parser.getValueBoolean()) {
					return JsonToken.VALUE_TRUE;
				} else {
					return JsonToken.VALUE_FALSE;
				}
			case VALUE_NULL:
				return JsonToken.VALUE_NULL;
			case VALUE_STRING:
				return JsonToken.VALUE_STRING;
			case VALUE_DOUBLE:
				return JsonToken.VALUE_NUMBER_FLOAT;
			case VALUE_LONG:
				return JsonToken.VALUE_NUMBER_INT;
			case KEY:
				return JsonToken.FIELD_NAME;
			default:
				return JsonToken.NOT_AVAILABLE;
		}
	}


	@SuppressWarnings("unused")
	static class StubParser extends JsonParser {

		JsonFactory factory;


		StubParser(JsonFactory factory) {
			this.factory = factory;
		}

		@Override
		public JsonFactory getFactory() {
			return factory;
		}

		@Override
		public void close() throws IOException {
		}

		@Override
		public JsonToken nextToken() throws IOException {
			return null;
		}

		@Override
		public JsonToken getCurrentToken() {
			return null;
		}

		@Override
		public String getCurrentName() throws IOException {
			return null;
		}

		@Override
		public JsonParser skipChildren() throws IOException {
			return null;
		}

		@Override
		public String getText() throws IOException {
			return null;
		}

		@Override
		public byte getByteValue() throws IOException {
			return 0;
		}

		@Override
		public short getShortValue() throws IOException {
			return 0;
		}

		@Override
		public int getIntValue() throws IOException {
			return 0;
		}

		@Override
		public float getFloatValue() throws IOException {
			return 0;
		}

		@Override
		public long getLongValue() throws IOException {
			return 0;
		}

		@Override
		public double getDoubleValue() throws IOException {
			return 0;
		}

		@Override
		public BigInteger getBigIntegerValue() throws IOException {
			return null;
		}

		@Override
		public BigDecimal getDecimalValue() throws IOException {
			return null;
		}
	}
}
