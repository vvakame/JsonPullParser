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

public class JsonUtil {

	public static void startHash(StringBuilder builder) {
		builder.append("{");
	}

	public static void endHash(StringBuilder builder) {
		builder.append("}");
	}

	public static void startArray(StringBuilder builder) {
		builder.append("[");
	}

	public static void endArray(StringBuilder builder) {
		builder.append("]");
	}

	public static void addSeparator(StringBuilder builder) {
		builder.append(",");
	}

	public static void putKey(StringBuilder builder, String key) {
		builder.append("\"").append(sanitize(key)).append("\":");
	}

	public static void put(StringBuilder builder, String value) {
		if (value == null) {
			builder.append("null");
		} else {
			builder.append("\"").append(sanitize(value)).append("\"");
		}
	}

	public static void put(StringBuilder builder, char value) {
		builder.append("\"").append(sanitize(value)).append("\"");
	}

	public static void put(StringBuilder builder, byte value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, short value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, int value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, long value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, float value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, double value) {
		builder.append(value);
	}

	public static void put(StringBuilder builder, boolean value) {
		builder.append(value);
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
