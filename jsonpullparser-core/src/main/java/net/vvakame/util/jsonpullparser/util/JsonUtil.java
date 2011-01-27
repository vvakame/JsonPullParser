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

public class JsonUtil {

	public static void startHash(Writer writer) throws IOException {
		writer.write("{");
	}

	public static void endHash(Writer writer) throws IOException {
		writer.write("}");
	}

	public static void startArray(Writer writer) throws IOException {
		writer.write("[");
	}

	public static void endArray(Writer writer) throws IOException {
		writer.write("]");
	}

	public static void addSeparator(Writer writer) throws IOException {
		writer.write(",");
	}

	public static void putKey(Writer writer, String key) throws IOException {
		writer.write("\"");
		writer.write(sanitize(key));
		writer.write("\":");
	}

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

	public static void put(Writer writer, String value) throws IOException {
		if (value == null) {
			writer.write("null");
		} else {
			writer.write("\"");
			writer.write(sanitize(value));
			writer.write("\"");
		}
	}

	public static void put(Writer writer, char value) throws IOException {
		writer.write("\"");
		writer.write(sanitize(value));
		writer.write("\"");
	}

	public static void put(Writer writer, byte value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, short value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, int value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, long value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, float value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, double value) throws IOException {
		writer.write(String.valueOf(value));
	}

	public static void put(Writer writer, boolean value) throws IOException {
		writer.write(String.valueOf(value));
	}

	static String sanitize(char orig) {
		return sanitize(String.valueOf(orig));
	}

	static String sanitize(String orig) {
		if (orig == null) {
			return null;
		}
		// TODO Increase Process Speed
		return orig.replace("\\", "\\\\").replace("\"", "\\\"")
				.replace("/", "\\/").replace("\b", "\\b").replace("\f", "\\f")
				.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}
