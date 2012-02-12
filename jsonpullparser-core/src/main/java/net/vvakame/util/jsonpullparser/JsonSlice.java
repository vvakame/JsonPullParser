package net.vvakame.util.jsonpullparser;

import net.vvakame.util.jsonpullparser.JsonPullParser.State;

/**
 * A {@link State} and related stuff.
 * @author vvakame
 */
public class JsonSlice {

	State state;

	String valueStr;

	long valueLong;

	double valueDouble;

	boolean valueBoolean;


	/**
	 * the constructor.
	 * @param state
	 * @category constructor
	 */
	public JsonSlice(State state) {
		switch (state) {
			case START_ARRAY:
			case START_HASH:
			case END_ARRAY:
			case END_HASH:
			case VALUE_NULL:
				break;

			default:
				throw new UnsupportedOperationException(state + " is not found.");
		}
		this.state = state;
	}

	/**
	 * the constructor.
	 * @param state
	 * @param value
	 * @category constructor
	 */
	public JsonSlice(State state, String value) {
		switch (state) {
			case KEY:
			case VALUE_STRING:
				break;

			default:
				throw new UnsupportedOperationException(state + " is not found.");
		}
		this.state = state;
		this.valueStr = value;
	}

	/**
	 * the constructor.
	 * @param state
	 * @param value
	 * @category constructor
	 */
	public JsonSlice(State state, long value) {
		switch (state) {
			case VALUE_LONG:
				break;

			default:
				throw new UnsupportedOperationException(state + " is not found.");
		}
		this.state = state;
		this.valueLong = value;
	}

	/**
	 * the constructor.
	 * @param state
	 * @param value
	 * @category constructor
	 */
	public JsonSlice(State state, double value) {
		switch (state) {
			case VALUE_DOUBLE:
				break;

			default:
				throw new UnsupportedOperationException(state + " is not found.");
		}
		this.state = state;
		this.valueDouble = value;
	}

	/**
	 * the constructor.
	 * @param state
	 * @param value
	 * @category constructor
	 */
	public JsonSlice(State state, boolean value) {
		switch (state) {
			case VALUE_BOOLEAN:
				break;

			default:
				throw new UnsupportedOperationException(state + " is not found.");
		}
		this.state = state;
		this.valueBoolean = value;
	}

	/**
	 * @return the state
	 * @category accessor
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the valueStr
	 * @category accessor
	 */
	public String getValueStr() {
		return valueStr;
	}

	/**
	 * @return the valueLong
	 * @category accessor
	 */
	public long getValueLong() {
		return valueLong;
	}

	/**
	 * @return the valueDouble
	 * @category accessor
	 */
	public double getValueDouble() {
		return valueDouble;
	}

	/**
	 * @return the valueBoolean
	 * @category accessor
	 */
	public boolean getValueBoolean() {
		return valueBoolean;
	}
}
