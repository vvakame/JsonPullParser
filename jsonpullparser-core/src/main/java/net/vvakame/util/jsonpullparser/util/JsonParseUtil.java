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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

/**
 * JSON parse facility.<br>
 * Generated codes make use of it too.
 * @author vvakame
 */
public class JsonParseUtil {

	/**
	 * Parses the current token as an integer.
	 * @param parser
	 * @return {@link Integer}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Integer parserInteger(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_LONG) {
			return (int) parser.getValueLong();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a long.
	 * @param parser
	 * @return {@link Long}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Long parserLong(JsonPullParser parser) throws IOException, JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_LONG) {
			return parser.getValueLong();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a byte.
	 * @param parser
	 * @return {@link Byte}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Byte parserByte(JsonPullParser parser) throws IOException, JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_LONG) {
			return (byte) parser.getValueLong();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a short.
	 * @param parser
	 * @return {@link Short}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Short parserShort(JsonPullParser parser) throws IOException, JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_LONG) {
			return (short) parser.getValueLong();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a boolean.
	 * @param parser
	 * @return {@link Boolean}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Boolean parserBoolean(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_BOOLEAN) {
			return parser.getValueBoolean();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_BOOLEAN, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a character.
	 * @param parser
	 * @return {@link Character}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Character parserCharacter(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_STRING) {
			String str = parser.getValueString();
			if (str.length() != 1) {
				throw new IllegalStateException(
						"unexpected value. expecte string size is 1. but get=" + str);
			}
			return str.charAt(0);
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_STRING, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a double-precision floating point value.
	 * @param parser
	 * @return {@link Double}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Double parserDouble(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_DOUBLE) {
			return parser.getValueDouble();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_DOUBLE, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a single-precision floating point value.
	 * @param parser
	 * @return {@link Float}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static Float parserFloat(JsonPullParser parser) throws IOException, JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		} else if (eventType == State.VALUE_DOUBLE) {
			return (float) parser.getValueDouble();
		} else {
			throw new IllegalStateException("unexpected state. expected=VALUE_DOUBLE, but get="
					+ eventType.toString());
		}
	}

	/**
	 * Parses the current token as a list of integers.
	 * @param parser
	 * @return List of {@link Integer}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Integer> parserIntegerList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Integer> list = new ArrayList<Integer>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_LONG) {
				list.add((int) parser.getValueLong());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of longs.
	 * @param parser
	 * @return List of {@link Long}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Long> parserLongList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Long> list = new ArrayList<Long>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_LONG) {
				list.add(parser.getValueLong());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of bytes.
	 * @param parser
	 * @return List of {@link Byte}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Byte> parserByteList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Byte> list = new ArrayList<Byte>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_LONG) {
				list.add((byte) parser.getValueLong());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of shorts.
	 * @param parser
	 * @return List of {@link Short}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Short> parserShortList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Short> list = new ArrayList<Short>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_LONG) {
				list.add((short) parser.getValueLong());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of booleans.
	 * @param parser
	 * @return List of {@link Boolean}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Boolean> parserBooleanList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Boolean> list = new ArrayList<Boolean>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_BOOLEAN) {
				list.add(parser.getValueBoolean());
			} else {
				throw new IllegalStateException(
						"unexpected state. expected=VALUE_BOOLEAN, but get=" + eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of characters.
	 * @param parser
	 * @return List of {@link Character}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Character> parserCharacterList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Character> list = new ArrayList<Character>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_STRING) {
				String str = parser.getValueString();
				if (str.length() != 1) {
					throw new IllegalStateException(
							"unexpected value. expecte string size is 1. but get=" + str);
				}
				list.add(str.charAt(0));
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_STRING, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of double-precision floating point numbers.
	 * @param parser
	 * @return List of {@link Double}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Double> parserDoubleList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Double> list = new ArrayList<Double>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_DOUBLE) {
				list.add(parser.getValueDouble());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_DOUBLE, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of single-precision floating point numbers.
	 * @param parser
	 * @return List of {@link Float}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Float> parserFloatList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Float> list = new ArrayList<Float>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_DOUBLE) {
				list.add((float) parser.getValueDouble());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_DOUBLE, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of strings.
	 * @param parser
	 * @return List of {@link String}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<String> parserStringList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<String> list = new ArrayList<String>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_STRING) {
				list.add(parser.getValueString());
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_STRING, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * JSONを解釈し、Dateのリストを返します.
	 * Parses the current token as a list of Dates.
	 * @param parser
	 * @return List of {@link Date}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<Date> parserDateList(JsonPullParser parser) throws IOException,
			JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<Date> list = new ArrayList<Date>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_LONG) {
				list.add(new Date(parser.getValueLong()));
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_LONG, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}

	/**
	 * Parses the current token as a list of Enums.
	 * @param <T> 
	 * @param parser
	 * @param clazz 
	 * @return List of {@link Enum}s
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static <T extends Enum<T>>List<T> parserEnumList(JsonPullParser parser, Class<T> clazz)
			throws IOException, JsonFormatException {

		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			throw new IllegalStateException("not started brace!");
		}
		List<T> list = new ArrayList<T>();
		while (parser.lookAhead() != State.END_ARRAY) {
			eventType = parser.getEventType();
			if (eventType == State.VALUE_NULL) {
				list.add(null);
			} else if (eventType == State.VALUE_STRING) {
				T obj = Enum.valueOf(clazz, parser.getValueString());
				list.add(obj);
			} else {
				throw new IllegalStateException("unexpected state. expected=VALUE_STRING, but get="
						+ eventType.toString());
			}
		}
		parser.getEventType();
		return list;
	}
}
