package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

public class JsonArray extends ArrayList<Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	ArrayList<State> stateList = new ArrayList<State>();

	public static JsonArray fromString(String json) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return fromParser(parser);
	}

	public static JsonArray fromParser(JsonPullParser parser)
			throws IOException, JsonFormatException {
		State state = parser.getEventType();

		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("unexpected token. token=" + state);
		}

		JsonArray jsonArray = new JsonArray();
		while ((state = parser.lookAhead()) != State.END_ARRAY) {
			jsonArray.add(getValue(parser), state);
		}
		parser.getEventType();

		return jsonArray;
	}

	public Boolean getBooleanOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case VALUE_BOOLEAN:
			return (Boolean) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public String getStringOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case VALUE_STRING:
			return (String) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public Long getLongOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case VALUE_LONG:
			return (Long) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public Double getDoubleOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case VALUE_DOUBLE:
			return (Double) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public JsonArray getJsonArrayOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case START_ARRAY:
			return (JsonArray) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public JsonHash getJsonHashOrNull(int index) throws JsonFormatException {
		State state = stateList.get(index);
		switch (state) {
		case VALUE_NULL:
			return null;
		case START_HASH:
			return (JsonHash) get(index);
		default:
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public State getState(int index) {
		return stateList.get(index);
	}

	static Object getValue(JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.lookAhead();
		switch (state) {
		case VALUE_BOOLEAN:
			parser.getEventType();
			return parser.getValueBoolean();
		case VALUE_STRING:
			parser.getEventType();
			return parser.getValueString();
		case VALUE_DOUBLE:
			parser.getEventType();
			return parser.getValueDouble();
		case VALUE_LONG:
			parser.getEventType();
			return parser.getValueLong();
		case VALUE_NULL:
			parser.getEventType();
			return null;
		case START_ARRAY:
			return fromParser(parser);
		case START_HASH:
			return JsonHash.fromParser(parser);
		default:
			// TODO JsonHash的な何かへの対応
			throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	/**
	 * 渡された引数が {@link State} の何にあたるかを判定し返します.<br>
	 * 
	 * @param obj
	 *            判定したいオブジェクト
	 * @return {@link State} の何にあたるか.
	 */
	State isState(Object obj) {
		State state = null;
		if (obj == null) {
			state = State.VALUE_NULL;
		} else if (obj instanceof String) {
			state = State.VALUE_STRING;
		} else if (obj instanceof Boolean) {
			state = State.VALUE_BOOLEAN;
		} else if (obj instanceof Double || obj instanceof Float) {
			state = State.VALUE_DOUBLE;
		} else if (obj instanceof Byte || obj instanceof Short
				|| obj instanceof Integer || obj instanceof Long) {
			state = State.VALUE_LONG;
		} else if (obj instanceof JsonArray) {
			state = State.START_ARRAY;
		} else if (obj instanceof JsonHash) {
			state = State.START_HASH;
		} else {
			throw new IllegalArgumentException(obj.getClass()
					.getCanonicalName() + " is not supported");
		}
		return state;
	}

	/**
	 * 指定された位置に、渡されたオブジェクト、 {@link State} をセットします.
	 * 
	 * @param index
	 *            挿入位置
	 * @param obj
	 *            保持するオブジェクト
	 * @param state
	 *            保持するオブジェクトの {@link State} 表現
	 */
	public void add(int index, Object obj, State state) {
		stateList.add(index, state);
		super.add(index, obj);
	}

	/**
	 * 指定された位置に、渡されたオブジェクトをセットします.<br> {@link #isState(Object)} を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param index
	 *            挿入位置
	 * @param obj
	 *            保持するオブジェクト
	 */
	@Deprecated
	@Override
	public void add(int index, Object obj) {
		State state = isState(obj);
		add(index, obj, state);
	}

	/**
	 * リストの末尾に、渡されたオブジェクト、 {@link State} をセットします.
	 * 
	 * @param obj
	 *            保持するオブジェクト
	 * @param state
	 *            保持するオブジェクトの {@link State} 表現
	 */
	public boolean add(Object obj, State state) {
		stateList.add(state);
		return super.add(obj);
	}

	/**
	 * リストの末尾に、渡されたオブジェクトをセットします.<br> {@link #isState(Object)} を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param obj
	 *            保持するオブジェクト
	 */
	@Deprecated
	@Override
	public boolean add(Object obj) {
		State state = isState(obj);
		return add(obj, state);
	}

	/**
	 * 指定された要素全てをリストの末尾に追加します.<br> {@link #isState(Object)}を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param args
	 *            追加するオブジェクト群
	 */
	@Deprecated
	@Override
	public boolean addAll(Collection<? extends Object> args) {
		boolean result = false;
		for (Object obj : args) {
			result = true;
			add(obj);
		}
		return result;
	}

	/**
	 * 指定された要素全てをリストの指定された位置に追加します.<br> {@link #isState(Object)}を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @oaram start 挿入開始位置
	 * @param args
	 *            追加するオブジェクト群
	 */
	@Deprecated
	@Override
	public boolean addAll(int start, Collection<? extends Object> args) {
		boolean result = false;
		for (Object obj : args) {
			result = true;
			add(start++, obj);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		stateList.clear();
		super.clear();
	}

	/**
	 * 動作を定義していないため動作しません.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object remove(int index) {
		stateList.remove(index);
		return super.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object obj) {
		if (super.contains(obj)) {
			int index = super.indexOf(obj);
			stateList.remove(index);
			super.remove(index);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Object set(int index, Object obj, State state) {
		stateList.set(index, state);
		return super.set(index, obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object set(int index, Object obj) {
		State state = isState(obj);
		return set(index, obj, state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return super.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T[] toArray(T[] arg0) {
		return super.toArray(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void trimToSize() {
		stateList.trimToSize();
		super.trimToSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JsonArray) {
			return equals(obj);
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> args) {
		throw new UnsupportedOperationException();
	}
}
