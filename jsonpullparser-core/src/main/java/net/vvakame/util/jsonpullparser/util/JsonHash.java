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

import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

public class JsonHash extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	LinkedHashMap<String, State> stateMap = new LinkedHashMap<String, State>();


	public static JsonHash fromString(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return fromParser(parser);
	}

	public static JsonHash fromParser(JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.getEventType();

		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_HASH) {
			throw new JsonFormatException("unexpected token. token=" + state);
		}

		JsonHash jsonHash = new JsonHash();
		while ((state = parser.lookAhead()) != State.END_HASH) {
			state = parser.getEventType();
			if (state != State.KEY) {
				throw new JsonFormatException("unexpected token. token=" + state);
			}
			String key = parser.getValueString();
			state = parser.lookAhead();
			jsonHash.put(key, getValue(parser), state);
		}
		parser.getEventType();

		return jsonHash;
	}

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

	public Boolean getBooleanOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_BOOLEAN:
				return (Boolean) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public String getStringOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_STRING:
				return (String) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public Long getLongOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_LONG:
				return (Long) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public Double getDoubleOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_DOUBLE:
				return (Double) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public JsonHash getJsonHashOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case START_HASH:
				return (JsonHash) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public JsonArray getJsonArrayOrNull(String key) throws JsonFormatException {
		State state = stateMap.get(key);
		switch (state) {
			case VALUE_NULL:
				return null;
			case START_ARRAY:
				return (JsonArray) get(key);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public State getState(String key) {
		return stateMap.get(key);
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
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	public Object put(String key, Object value, State state) {
		stateMap.put(key, state);
		return super.put(key, value);
	}

	@Deprecated
	@Override
	public Object put(String key, Object value) {
		State state = isState(value);
		return put(key, value, state);
	}

	@Deprecated
	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		Set<? extends String> keySet = map.keySet();
		for (String key : keySet) {
			put(key, map.get(key));
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
