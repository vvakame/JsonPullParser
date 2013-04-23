package net.vvakame.util.jsonpullparser.googleapiclient;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;

/**
 * Implementation for {@link JsonParser} by JsonPullParser.
 * @author vvakame
 */
public class JppParser extends JsonParser {

	final JppFactory factory;

	final JsonPullParser parser;

	JsonToken current;

	JsonFormatException ex;

	Deque<State> stateStack = new ArrayDeque<State>();

	boolean readLast = false;

	boolean finished = false;


	/**
	 * the constructor.
	 * @param factory
	 * @param parser
	 * @category constructor
	 */
	public JppParser(JppFactory factory, JsonPullParser parser) {
		this.factory = factory;
		this.parser = parser;
	}

	@Override
	public JsonFactory getFactory() {
		return factory;
	}

	@Override
	public JsonToken nextToken() throws IOException {
		try {
			if (readLast) {
				finished = true;
				return null;
			}
			State state = parser.getEventType();
			if (state == State.START_ARRAY || state == State.START_HASH) {
				stateStack.push(state);
			} else if (state == State.END_ARRAY || state == State.END_HASH) {
				stateStack.pop();
				if (stateStack.isEmpty()) {
					readLast = true;
				}
			}
			current = JppFactory.convert(state, parser);
			return current;
		} catch (JsonFormatException e) {
			ex = e;
		}
		return null;
	}

	@Override
	public JsonToken getCurrentToken() {
		if (ex != null) {
			throw new IllegalArgumentException(ex);
		}
		if (finished) {
			return null;
		} else {
			return current;
		}
	}

	@Override
	public String getCurrentName() {
		switch (current) {
			case START_ARRAY:
			case END_ARRAY:
			case START_OBJECT:
			case END_OBJECT:
			case VALUE_NUMBER_FLOAT:
			case VALUE_NUMBER_INT:
			case VALUE_FALSE:
			case VALUE_TRUE:
				return null;
			default:
				return parser.getValueString();
		}
	}

	@Override
	public JsonParser skipChildren() throws IOException {
		try {
			JsonToken current = getCurrentToken();
			if (current != JsonToken.START_OBJECT && current != JsonToken.START_ARRAY) {
				return this;
			}
			JsonToken next;
			do {
				next = nextToken();
				if (current == JsonToken.START_OBJECT || current == JsonToken.START_ARRAY) {
					skipChildren();
				}
			} while (next != JsonToken.END_OBJECT && next != JsonToken.END_ARRAY);
		} catch (IllegalStateException e) {
			throw new IOException(e);
		}
		return this;
	}

	@Override
	public String getText() {
		return parser.getValueString();
	}

	@Override
	public byte getByteValue() {
		return (byte) parser.getValueLong();
	}

	@Override
	public short getShortValue() {
		return (short) parser.getValueLong();
	}

	@Override
	public int getIntValue() {
		return (int) parser.getValueLong();
	}

	@Override
	public long getLongValue() {
		return parser.getValueLong();
	}

	@Override
	public float getFloatValue() {
		return (float) parser.getValueDouble();
	}

	@Override
	public double getDoubleValue() {
		return parser.getValueDouble();
	}

	// TODO support BigInteger and BigDecimal

	@Override
	public BigInteger getBigIntegerValue() {
		return BigInteger.valueOf(parser.getValueLong());
	}

	@Override
	public BigDecimal getDecimalValue() {
		if (current == JsonToken.VALUE_NUMBER_INT) {
			return BigDecimal.valueOf(parser.getValueLong());
		} else {
			return BigDecimal.valueOf(parser.getValueDouble());
		}
	}

	@Override
	public void close() {
	}
}
