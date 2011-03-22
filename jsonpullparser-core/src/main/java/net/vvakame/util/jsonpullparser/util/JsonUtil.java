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

/**
 * {@link TokenConverter} 記述支援用ユーティリティ.<br>
 * 本ライブラリ生成ソースからも利用されます.
 * @author vvakame
 */
public class JsonUtil {

	/**
	 * { を書き込みます
	 * @param writer 出力先
	 * @throws IOException
	 * @author vvakame
	 */
	public static void startHash(Writer writer) throws IOException {
		writer.write("{");
	}

	/**
	 * } を書き込みます
	 * @param writer 出力先
	 * @throws IOException
	 * @author vvakame
	 */
	public static void endHash(Writer writer) throws IOException {
		writer.write("}");
	}

	/**
	 * [ を書き込みます
	 * @param writer 出力先
	 * @throws IOException
	 * @author vvakame
	 */
	public static void startArray(Writer writer) throws IOException {
		writer.write("[");
	}

	/**
	 * ] を書き込みます
	 * @param writer 出力先
	 * @throws IOException
	 * @author vvakame
	 */
	public static void endArray(Writer writer) throws IOException {
		writer.write("]");
	}

	/**
	 * , を書き込みます
	 * @param writer 出力先
	 * @throws IOException
	 * @author vvakame
	 */
	public static void addSeparator(Writer writer) throws IOException {
		writer.write(",");
	}

	/**
	 * JSONの連想配列の Key を書き込みます.<br>
	 * 出力する際、 {@link #sanitize(String)} を利用してJSONとしてvalidな文字列に変換されます.
	 * @param writer 出力先
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
	 * obj を writer に出力します.<br>
	 * {@link String} {@link Boolean} {@link Long} {@link Double} {@link JsonHash} {@link JsonArray} を書きこむことが可能です.<br>
	 * 未知のインスタンスだった場合、 {@link IllegalStateException} が投げられます.
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
	 * writer に value を書き込みます.<br>
	 * 書き込む際、 {@link #sanitize(String)} で処理されます.
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
	 * writer に value を書き込みます.<br>
	 * 書き込む際、 {@link #sanitize(String)} で処理されます.
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
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, byte value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, short value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, int value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, long value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, float value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, double value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
	 * @param writer
	 * @param value
	 * @throws IOException
	 * @author vvakame
	 */
	public static void put(Writer writer, boolean value) throws IOException {
		writer.write(String.valueOf(value));
	}

	/**
	 * writer に value を書き込みます.
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
