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

import net.vvakame.util.jsonpullparser.JsonPullParser.State;

/**
 * Type of JSON value.
 * @author vvakame
 * @since 1.4.12
 */
public enum Type {
	/** String value. */
	STRING,
	/** Long value. */
	LONG,
	/** Double value. */
	DOUBLE,
	/** Boolean value. */
	BOOLEAN,
	/** {@link JsonHash} value. */
	HASH,
	/** {@link JsonArray} value. */
	ARRAY,
	/** Null value. */
	NULL;

	static Type from(State state) {
		switch (state) {
			case VALUE_STRING:
				return Type.STRING;
			case VALUE_LONG:
				return Type.LONG;
			case VALUE_DOUBLE:
				return Type.DOUBLE;
			case VALUE_BOOLEAN:
				return Type.BOOLEAN;
			case START_HASH:
				return Type.HASH;
			case START_ARRAY:
				return Type.ARRAY;
			case VALUE_NULL:
				return Type.NULL;
			default:
				throw new IllegalArgumentException(state + " is invalid");
		}
	}

	static State to(Type type) {
		switch (type) {
			case STRING:
				return State.VALUE_STRING;
			case LONG:
				return State.VALUE_LONG;
			case DOUBLE:
				return State.VALUE_DOUBLE;
			case BOOLEAN:
				return State.VALUE_BOOLEAN;
			case HASH:
				return State.START_HASH;
			case ARRAY:
				return State.START_ARRAY;
			case NULL:
				return State.VALUE_NULL;
			default:
				throw new IllegalArgumentException(type + " is invalid");
		}
	}

	static Type getType(Object obj) {
		if (obj == null) {
			return Type.NULL;
		} else if (obj instanceof String) {
			return Type.STRING;
		} else if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long) {
			return Type.LONG;
		} else if (obj instanceof Double || obj instanceof Float) {
			return Type.DOUBLE;
		} else if (obj instanceof Boolean) {
			return Type.BOOLEAN;
		} else if (obj instanceof JsonHash) {
			return Type.HASH;
		} else if (obj instanceof JsonArray) {
			return Type.ARRAY;
		} else {
			throw new IllegalArgumentException(obj.getClass().getCanonicalName()
					+ " is not supported");
		}
	}
}
