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

package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

/**
 * A JSON array ([]).
 * @author vvakame
 */
public class JsonArray extends ArrayList<Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	ArrayList<Type> stateList = new ArrayList<Type>();


	/**
	 * Parses the given JSON data as an array.
	 * @param json JSON-formatted data
	 * @return {@link JsonArray}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonArray fromString(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return fromParser(parser);
	}

	/**
	 * Parses the given JSON data as an array.
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @return {@link JsonArray}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonArray fromParser(JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.getEventType();

		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("unexpected token. token=" + state, parser);
		}

		JsonArray jsonArray = new JsonArray();
		while ((state = parser.lookAhead()) != State.END_ARRAY) {
			jsonArray.add(getValue(parser), state);
		}
		parser.getEventType();

		return jsonArray;
	}

	static Object getValue(JsonPullParser parser) throws IOException, JsonFormatException {
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
				throw new JsonFormatException("unexpected token. token=" + state, parser);
		}
	}

	/**
	 * Encodes into the JSON format.
	 * @param writer {@link Writer} to be used for writing
	 * @throws IOException
	 * @author vvakame
	 */
	public void toJson(Writer writer) throws IOException {
		startArray(writer);

		int size = size();
		for (int i = 0; i < size; i++) {
			put(writer, get(i));
			if (i + 1 < size) {
				addSeparator(writer);
			}
		}

		endArray(writer);
	}

	/**
	 * Retrieves the nth element in the array as boolean.<br>
	 * If it is neither {@code null} nor a {@link Boolean}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Boolean}.
	 * @author vvakame
	 */
	public Boolean getBooleanOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case BOOLEAN:
				return (Boolean) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the nth element in the array as string.<br>
	 * If it is neither {@code null} nor a {@link String}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link String}.
	 * @author vvakame
	 */
	public String getStringOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case STRING:
				return (String) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the nth element in the array as integer.<br>
	 * If it is neither {@code null} nor a {@link Long}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Long}.
	 * @author vvakame
	 */
	public Long getLongOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case LONG:
				Object obj = get(index);
				if (obj instanceof Integer) {
					return (long) (Integer) obj;
				} else if (obj instanceof Long) {
					return (Long) obj;
				} else if (obj instanceof Byte) {
					return (long) (Byte) obj;
				} else if (obj instanceof Short) {
					return (long) (Short) obj;
				} else {
					throw new IllegalStateException("unexpected class. class="
							+ obj.getClass().getCanonicalName());
				}
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the nth element in the array as double-precision floating point number.<br>
	 * If it is neither {@code null} nor a {@link Double}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Double}.
	 * @author vvakame
	 */
	public Double getDoubleOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case DOUBLE:
				Object obj = get(index);
				if (obj instanceof Double) {
					return (Double) obj;
				} else if (obj instanceof Float) {
					return (double) (Float) obj;
				} else {
					throw new IllegalStateException("unexpected class. class="
							+ obj.getClass().getCanonicalName());
				}
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the nth element in the array as {@link JsonArray}.<br>
	 * If it is neither {@code null} nor a {@link JsonArray}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link JsonArray}.
	 * @author vvakame
	 */
	public JsonArray getJsonArrayOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case ARRAY:
				return (JsonArray) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the nth element in the array as {@link JsonHash}.<br>
	 * If it is neither {@code null} nor a {@link JsonHash}, {@link IllegalStateException} will be thrown.
	 * @param index
	 * @return The element
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link JsonHash}.
	 * @author vvakame
	 */
	public JsonHash getJsonHashOrNull(int index) throws IllegalStateException {
		Type type = stateList.get(index);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case HASH:
				return (JsonHash) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the type of the nth element in the array as {@link State}.<br>
	 * NB. this method returns {@link State#START_HASH} for a {@link JsonHash}, {@link State#START_ARRAY} for a {@link JsonArray}.
	 * @param index
	 * @return The element type
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonArray}. since 1.4.12.
	 * @author vvakame
	 */
	@Deprecated
	public State getState(int index) {
		return Type.to(stateList.get(index));
	}

	/**
	 * Retrieves the type of the nth element in the array as {@link Type}.<br>
	 * @param index 
	 * @return The type code (see {@link Type})
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Type getType(int index) {
		return stateList.get(index);
	}

	/**
	 * Returns the type code (as in {@link State}) the given object has.
	 * 
	 * @param obj
	 *            The value
	 * @return The type code (see {@link State})
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonArray}. since 1.4.12.
	 */
	@Deprecated
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
		} else if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long) {
			state = State.VALUE_LONG;
		} else if (obj instanceof JsonArray) {
			state = State.START_ARRAY;
		} else if (obj instanceof JsonHash) {
			state = State.START_HASH;
		} else {
			throw new IllegalArgumentException(obj.getClass().getCanonicalName()
					+ " is not supported");
		}
		return state;
	}

	/**
	 * Inserts the given object and State at the given index in the array.
	 * 
	 * @param index
	 *            The index to insert at.
	 * @param obj
	 *            The value to insert.
	 * @param state
	 *            The type code of the value (see {@link State})
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonArray}. since 1.4.12.
	 */
	@Deprecated
	public void add(int index, Object obj, State state) {
		stateList.add(index, Type.from(state));
		super.add(index, obj);
	}

	/**
	 * Inserts the given object and State at the given index in the array.
	 * 
	 * @param index
	 *            The index to insert at.
	 * @param obj
	 *            The value to insert.
	 * @param type
	 *            The type code of the value (see {@link Type})
	 */
	public void add(int index, Object obj, Type type) {
		stateList.add(index, type);
		super.add(index, obj);
	}

	/**
	 * Inserts the given object at the given index in the array.<br>
	 * WARNING: While we attempt to guess the value type with {@link #isState(Object)}, it is error-prone, thus it is not advisable to use this method.
	 * 
	 * @param index
	 *            The index to insert at.
	 * @param obj
	 *            The value to insert.
	 */
	@Override
	public void add(int index, Object obj) {
		Type type = Type.getType(obj);
		add(index, obj, type);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#STRING}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, String value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.STRING);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#BOOLEAN}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Boolean value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.BOOLEAN);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#DOUBLE}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Double value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.DOUBLE);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#DOUBLE}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Float value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.DOUBLE);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#LONG}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Byte value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.LONG);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#LONG}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Short value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.LONG);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#LONG}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Integer value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.LONG);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#LONG}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, Long value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.LONG);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#ARRAY}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, JsonArray value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.ARRAY);
		}

		super.add(index, value);
	}

	/**
	 * see {@link List#add(int, Object)}
	 * this method is alternative of {@link #add(int, Object, Type)} call with {@link Type#HASH}.
	 * @param index 
	 * @param value
	 * @since 1.4.12
	 * @author vvakame
	 */
	public void add(int index, JsonHash value) {
		if (value == null) {
			stateList.add(index, Type.NULL);
		} else {
			stateList.add(index, Type.HASH);
		}

		super.add(index, value);
	}

	/**
	 * Appends the given object and State to the array.
	 * 
	 * @param obj
	 *            The value to append.
	 * @param state
	 *            The type code of the value (see {@link State})
	 * @return {@link Collection#add(Object)} reference
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonArray}. since 1.4.12.
	 */
	@Deprecated
	public boolean add(Object obj, State state) {
		stateList.add(Type.from(state));
		return super.add(obj);
	}

	/**
	 * Appends the given object and State to the array.
	 * 
	 * @param obj
	 *            The value to append.
	 * @param type
	 *            The type code of the value (see {@link Type})
	 * @return {@link Collection#add(Object)} reference
	 */
	public boolean add(Object obj, Type type) {
		stateList.add(type);
		return super.add(obj);
	}

	/**
	 * Appends the given object and State to the array.
	 * WARNING: While we attempt to guess the value type with {@link #isState(Object)}, it is error-prone, thus it is not advisable to use this method.
	 * 
	 * @param obj
	 *            The value to append.
	 * @return {@link Collection#add(Object)} reference
	 */
	@Override
	public boolean add(Object obj) {
		Type type = Type.getType(obj);
		return add(obj, type);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#STRING}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(String value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.STRING);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#BOOLEAN}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Boolean value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.BOOLEAN);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#DOUBLE}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Double value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.DOUBLE);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#DOUBLE}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Float value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.DOUBLE);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#LONG}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Byte value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.LONG);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#LONG}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Short value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.LONG);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#LONG}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Integer value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.LONG);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#LONG}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(Long value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.LONG);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#ARRAY}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(JsonArray value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.ARRAY);
		}

		return super.add(value);
	}

	/**
	 * see {@link List#add(Object)}.
	 * this method is alternative of {@link #add(Object, Type)} call with {@link Type#HASH}.
	 * @param value
	 * @return see {@link List#add(Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public boolean add(JsonHash value) {
		if (value == null) {
			stateList.add(Type.NULL);
		} else {
			stateList.add(Type.HASH);
		}

		return super.add(value);
	}

	/**
	 * Appends the given objects to the array.
	 * WARNING: While we attempt to guess the value type with {@link #isState(Object)}, it is error-prone, thus it is not advisable to use this method.
	 * 
	 * @param args
	 *            The values to append.
	 * @return {@link Collection#add(Object)} reference
	 */
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
	 * Inserts the given objects at the given index in the array.
	 * WARNING: While we attempt to guess the value type with {@link #isState(Object)}, it is error-prone, thus it is not advisable to use this method.
	 * 
	 * @oaram start The index to insert at.
	 * @param args
	 *            The values to insert.
	 */
	@Override
	public boolean addAll(int start, Collection<? extends Object> args) {
		boolean result = false;
		for (Object obj : args) {
			result = true;
			try {
				add(start++, obj);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("index " + start + " is invalid type", e);
			}
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
	 * Currently undefined; merely throws UnsupportedOperationException.
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
	 * TODO
	 * @param index 
	 * @param obj 
	 * @param state 
	 * @return The instance at the given index in the array
	 */
	public Object set(int index, Object obj, State state) {
		stateList.set(index, Type.from(state));
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
	public <T>T[] toArray(T[] arg0) {
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
			JsonArray ary = (JsonArray) obj;
			int size = size();
			if (size != ary.size()) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (!stateList.get(i).equals(ary.stateList.get(i))) {
					return false;
				} else if (get(i) == null && ary.get(i) != null) {
					return false;
				} else if (get(i) == null && ary.get(i) == null) {
					continue;
				} else if (!get(i).equals(ary.get(i))) {
					return false;
				}
			}
			return true;
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

	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		try {
			toJson(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
