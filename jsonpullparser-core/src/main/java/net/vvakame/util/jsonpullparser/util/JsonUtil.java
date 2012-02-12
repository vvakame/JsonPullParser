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
import java.io.Writer;
import java.util.Date;
import java.util.List;

/**
 * {@link TokenConverter} supporting facility.<br>
 * Generated codes make use of it too.
 * @author vvakame
 */
public class JsonUtil {

	/**
	 * Writes "{".
	 * @param writer {@link Writer} to be used for writing value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void startHash(Writer writer) throws IOException {
		writer.write("{");
	}

	/**
	 * Writes "}".
	 * @param writer {@link Writer} to be used for writing value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void endHash(Writer writer) throws IOException {
		writer.write("}");
	}

	/**
	 * Writes "[".
	 * @param writer {@link Writer} to be used for writing value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void startArray(Writer writer) throws IOException {
		writer.write("[");
	}

	/**
	 * Writes "]".
	 * @param writer {@link Writer} to be used for writing value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void endArray(Writer writer) throws IOException {
		writer.write("]");
	}

	/**
	 * Writes ",".
	 * @param writer {@link Writer} to be used for writing value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void addSeparator(Writer writer) throws IOException {
		writer.write(",");
	}

	/**
	 * Writes the given key as a JSON hash key, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer {@link Writer} to be used for writing value
	 * @param key Keyの文字列表現
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putKey(Writer writer, String key) throws IOException {
		writer.write("\"");
		writer.write(sanitize(key));
		writer.write("\":");
	}

	/**
	 * Writes the given object.<br>
	 * Supported types are: {@link String} {@link Boolean} {@link Long} {@link Double} {@link JsonHash} {@link JsonArray}.<br>
	 * An {@link IllegalStateException} will be thrown if the given object has an unsupported type.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Object value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else if (value instanceof String) {
			put(writer, (String) value);
		} else if (value instanceof Boolean) {
			put(writer, (boolean) (Boolean) value);
		} else if (value instanceof Long) {
			put(writer, (long) (Long) value);
		} else if (value instanceof Double) {
			put(writer, (double) (Double) value);
		} else if (value instanceof Date) {
			put(writer, (Date) value);
		} else if (value instanceof JsonHash) {
			JsonHash jsonHash = (JsonHash) value;
			jsonHash.toJson(writer);
		} else if (value instanceof JsonArray) {
			JsonArray jsonArray = (JsonArray) value;
			jsonArray.toJson(writer);
		} else {
			throw new IllegalStateException("unknown class. class="
					+ value.getClass().getCanonicalName());
		}
	}

	/**
	 * Writes the given value with the given writer, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, String value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write("\"");
			writer.write(sanitize(value));
			writer.write("\"");
		}
	}

	/**
	 * Writes the given value with the given writer, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putStringList(Writer writer, List<String> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, char value) throws IOException {
		writer.write("\"");
		writer.write(sanitize(value));
		writer.write("\"");
	}

	/**
	 * Writes the given value with the given writer, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Character value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write("\"");
			writer.write(sanitize(value));
			writer.write("\"");
		}
	}

	/**
	 * Writes the given value with the given writer, sanitizing with {@link #sanitize(String)} as a valid JSON-formatted data.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putCharacterList(Writer writer, List<Character> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, byte value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Byte value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putByteList(Writer writer, List<Byte> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, short value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Short value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putShortList(Writer writer, List<Short> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, int value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Integer value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putIntegerList(Writer writer, List<Integer> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, long value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Long value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putLongList(Writer writer, List<Long> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, float value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Float value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putFloatList(Writer writer, List<Float> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, double value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Double value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putDoubleList(Writer writer, List<Double> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, boolean value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Boolean value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(value.toString());
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putBooleanList(Writer writer, List<Boolean> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Date value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write(String.valueOf(value.getTime()));
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, Enum<?> value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write("\"");
			writer.write(sanitize(value.name()));
			writer.write("\"");
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putEnumList(Writer writer, List<? extends Enum<?>> values)
			throws IOException {

		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	/**
	 * Writes the given value with the given writer.
	 * @param writer
	 * @param values
	 * @throws IOException
	 * @author vvakame
	 */
	public static void putDateList(Writer writer, List<Date> values) throws IOException {
		if (values == null) {
			writer.write("null");
		} else {
			startArray(writer);
			for (int i = 0; i < values.size(); i++) {
				put(writer, values.get(i));
				if (i != values.size() - 1) {
					addSeparator(writer);
				}
			}
			endArray(writer);
		}
	}

	static String sanitize(char orig) {
		return sanitize(String.valueOf(orig));
	}

	static String sanitize(String orig) {
		if (orig == null) {
			return null;
		}
		// TODO Increase Process Speed
		return orig.replace("\\", "\\\\").replace("\"", "\\\"").replace("/", "\\/")
			.replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r")
			.replace("\t", "\\t");
	}
}
