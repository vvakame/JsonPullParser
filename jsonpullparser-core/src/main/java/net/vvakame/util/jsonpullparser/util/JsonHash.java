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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

/**
 * A JSON hash ({}).
 * @author vvakame
 */
public class JsonHash extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	LinkedHashMap<String, Type> stateMap = new LinkedHashMap<String, Type>();


	/**
	 * Parses the given JSON data as a hash.
	 * @param json JSON-formatted data
	 * @return {@link JsonHash}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonHash fromString(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return fromParser(parser);
	}

	/**
	 * Parses the given JSON data as a hash.
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @return {@link JsonHash}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonHash fromParser(JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.getEventType();

		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_HASH) {
			throw new JsonFormatException("unexpected token. token=" + state, parser);
		}

		JsonHash jsonHash = new JsonHash();
		while ((state = parser.lookAhead()) != State.END_HASH) {
			state = parser.getEventType();
			if (state != State.KEY) {
				throw new JsonFormatException("unexpected token. token=" + state, parser);
			}
			String key = parser.getValueString();
			state = parser.lookAhead();
			jsonHash.put(key, getValue(parser), state);
		}
		parser.getEventType();

		return jsonHash;
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
				return JsonArray.fromParser(parser);
			case START_HASH:
				return fromParser(parser);
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
		startHash(writer);

		int size = size();
		Set<String> set = keySet();
		int i = 0;
		for (String key : set) {

			putKey(writer, key);
			JsonUtil.put(writer, get(key));

			if (i + 1 < size) {
				addSeparator(writer);
			}
			i++;
		}

		endHash(writer);
	}

	@Override
	public void clear() {
		stateMap.clear();
		super.clear();
	}

	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the type code (as in {@link State}) the given object has.
	 * 
	 * @param obj
	 *            The value
	 * @return The type code (see {@link State})
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonHash}. since 1.4.12.
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
	 * Returns the type code (as in {@link State}) the corresponding value of the given key has.<br>
	 * NB. this method returns {@link State#START_HASH} for a {@link JsonHash}, {@link State#START_ARRAY} for a {@link JsonArray}.
	 *
	 * @param key The key
	 * @return The type code (see {@link State})
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonHash}. since 1.4.12.
	 */
	@Deprecated
	public State getState(String key) {
		return Type.to(stateMap.get(key));
	}

	/**
	 * Returns the type code (as in {@link Type}) the corresponding value of the given key has.<br>
	 * @param key The key
	 * @return The type code (see {@link Type})
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Type getType(String key) {
		return stateMap.get(key);
	}

	/**
	 * Retrieves the corresponding value of the given key as boolean.<br>
	 * If it is neither {@code null} nor a {@link Boolean}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Boolean}.
	 * @author vvakame
	 */
	public Boolean getBooleanOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case BOOLEAN:
				return (Boolean) get(key);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the corresponding value of the given key as string.<br>
	 * If it is neither {@code null} nor a {@link String}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link String}.
	 * @author vvakame
	 */
	public String getStringOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case STRING:
				return (String) get(key);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the corresponding value of the given key as integer.<br>
	 * If it is neither {@code null} nor a {@link Long}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Long}.
	 * @author vvakame
	 */
	public Long getLongOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case LONG:
				Object obj = get(key);
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
	 * Retrieves the corresponding value of the given key as double-precision floating point number.<br>
	 * If it is neither {@code null} nor a {@link Double}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link Double}.
	 * @author vvakame
	 */
	public Double getDoubleOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case DOUBLE:
				Object obj = get(key);
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
	 * Retrieves the corresponding value of the given key as {@link JsonHash}.<br>
	 * If it is neither {@code null} nor a {@link JsonHash}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link JsonHash}.
	 * @author vvakame
	 */
	public JsonHash getJsonHashOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case HASH:
				return (JsonHash) get(key);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * Retrieves the corresponding value of the given key as {@link JsonArray}.<br>
	 * If it is neither {@code null} nor a {@link JsonArray}, {@link IllegalStateException} will be thrown.
	 * @param key
	 * @return The value
	 * @throws IllegalStateException The given index points neither {@code null} nor a {@link JsonArray}.
	 * @author vvakame
	 */
	public JsonArray getJsonArrayOrNull(String key) throws IllegalStateException {
		Type type = stateMap.get(key);
		if (type == null) {
			return null;
		}
		switch (type) {
			case NULL:
				return null;
			case ARRAY:
				return (JsonArray) get(key);
			default:
				throw new IllegalStateException("unexpected token. token=" + type);
		}
	}

	/**
	 * put with State.
	 * @param key 
	 * @param value 
	 * @param state 
	 * @return The instance of the value for the given key
	 * @deprecated {@link State} is confuse the users. replace to {@link Type} in about {@link JsonHash}. since 1.4.12.
	 */
	@Deprecated
	public Object put(String key, Object value, State state) {
		return put(key, value, Type.from(state));
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * {@link Type} is hint for every get* method.
	 * @param key
	 * @param value
	 * @param type see {@link Type#getType(Object)}
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Object value, Type type) {
		stateMap.put(key, type);
		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#STRING}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, String value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.STRING);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#BOOLEAN}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Boolean value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.BOOLEAN);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#DOUBLE}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Double value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.DOUBLE);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#DOUBLE}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Float value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.DOUBLE);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#LONG}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Byte value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.LONG);
		}
		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#LONG}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Short value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.LONG);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#LONG}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Integer value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.LONG);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#LONG}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, Long value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.LONG);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#LONG}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, JsonArray value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.ARRAY);
		}

		return super.put(key, value);
	}

	/**
	 * see {@link Map#put(Object, Object)}.
	 * this method is alternative of {@link #put(String, Object, Type)} call with {@link Type#HASH}.
	 * @param key
	 * @param value
	 * @return see {@link Map#put(Object, Object)}
	 * @since 1.4.12
	 * @author vvakame
	 */
	public Object put(String key, JsonHash value) {
		if (value == null) {
			stateMap.put(key, Type.NULL);
		} else {
			stateMap.put(key, Type.HASH);
		}

		return super.put(key, value);
	}

	@Override
	public Object put(String key, Object value) throws IllegalArgumentException {
		Type type = Type.getType(value);
		return put(key, value, type);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		Set<? extends String> keySet = map.keySet();
		for (String key : keySet) {
			try {
				put(key, map.get(key));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(key + " is invalid type", e);
			}
		}
	}

	@Override
	public Object remove(Object obj) {
		stateMap.remove(obj);
		return super.remove(obj);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JsonHash) {
			JsonHash hash = (JsonHash) obj;
			int size = size();
			if (size != hash.size()) {
				return false;
			}
			Set<String> set = keySet();
			for (String key : set) {
				if (!hash.containsKey(key)) {
					return false;
				} else if (!stateMap.get(key).equals(hash.stateMap.get(key))) {
					return false;
				} else if (get(key) == null && hash.get(key) != null) {
					return false;
				} else if (get(key) == null && hash.get(key) == null) {
					continue;
				} else if (!get(key).equals(hash.get(key))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
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
