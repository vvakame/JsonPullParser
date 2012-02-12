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
import java.util.Arrays;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for {@link JsonUtil}.
 * 
 * @author vvakame
 */
public class JsonUtilTest {

	/**
	 * Writing a JSON array for test.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void toJsonArray() throws IOException {
		StringWriter writer = new StringWriter();

		startArray(writer);

		put(writer, (byte) 1);

		addSeparator(writer);

		put(writer, (short) 2);

		addSeparator(writer);

		put(writer, 3);

		addSeparator(writer);

		put(writer, 4L);

		addSeparator(writer);

		put(writer, 5.1f);

		addSeparator(writer);

		put(writer, 6.2);

		endArray(writer);

		JSONArray jsonArray = JSONArray.fromObject(writer.toString());

		assertThat(jsonArray.getLong(0), is(1L));
		assertThat(jsonArray.getLong(1), is(2L));
		assertThat(jsonArray.getLong(2), is(3L));
		assertThat(jsonArray.getLong(3), is(4L));
		assertThat(jsonArray.getDouble(4), is(5.1));
		assertThat(jsonArray.getDouble(5), is(6.2));
	}

	/**
	 * Writing a JSON hash for test.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void toJsonHash() throws IOException {
		StringWriter writer = new StringWriter();

		startHash(writer);

		putKey(writer, "hoge");
		put(writer, (Object) null);

		addSeparator(writer);

		putKey(writer, "\"");
		put(writer, "\\");

		addSeparator(writer);

		putKey(writer, "海藻系ぬこ");
		put(writer, "にゃー!!");

		addSeparator(writer);

		putKey(writer, "char");
		put(writer, 'c');

		endHash(writer);

		JSONObject json = JSONObject.fromObject(writer.toString());

		assertThat(json.getJSONObject("hoge").isNullObject(), is(true));
		assertThat(json.getString("\""), is("\\"));
		assertThat(json.getString("海藻系ぬこ"), is("にゃー!!"));
		assertThat(json.getString("char"), is("c"));
	}

	/**
	 * Tests for the behavior of {@link JsonUtil#sanitize(String)}.
	 */
	@Test
	public void sanitize() {
		assertThat(JsonUtil.sanitize(null), is(nullValue()));

		assertThat(JsonUtil.sanitize("\\ \" / \b \f \n \r \t"),
				is("\\\\ \\\" \\/ \\b \\f \\n \\r \\t"));
	}

	/**
	 * Writing an array with primitive values for test.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void primitive() throws IOException {
		StringWriter writer = new StringWriter();

		startArray(writer);

		put(writer, (byte) 1);

		addSeparator(writer);

		put(writer, (short) 2);

		addSeparator(writer);

		put(writer, 3);

		addSeparator(writer);

		put(writer, 4L);

		addSeparator(writer);

		put(writer, 5.1f);

		addSeparator(writer);

		put(writer, 6.2);

		endArray(writer);

		JSONArray jsonArray = JSONArray.fromObject(writer.toString());

		assertThat(jsonArray.getLong(0), is(1L));
		assertThat(jsonArray.getLong(1), is(2L));
		assertThat(jsonArray.getLong(2), is(3L));
		assertThat(jsonArray.getLong(3), is(4L));
		assertThat(jsonArray.getDouble(4), is(5.1));
		assertThat(jsonArray.getDouble(5), is(6.2));
	}

	/**
	 * Writing an array with primitive values (with wrapper) for test.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void primitiveWrapper() throws IOException {
		StringWriter writer = new StringWriter();

		startArray(writer);

		put(writer, Byte.valueOf((byte) 1));

		addSeparator(writer);

		put(writer, Short.valueOf((short) 2));

		addSeparator(writer);

		put(writer, Integer.valueOf(3));

		addSeparator(writer);

		put(writer, Long.valueOf(4L));

		addSeparator(writer);

		put(writer, Float.valueOf(5.1f));

		addSeparator(writer);

		put(writer, Double.valueOf(6.2));

		endArray(writer);

		JSONArray jsonArray = JSONArray.fromObject(writer.toString());

		assertThat(jsonArray.getLong(0), is(1L));
		assertThat(jsonArray.getLong(1), is(2L));
		assertThat(jsonArray.getLong(2), is(3L));
		assertThat(jsonArray.getLong(3), is(4L));
		assertThat(jsonArray.getDouble(4), is(5.1));
		assertThat(jsonArray.getDouble(5), is(6.2));
	}

	/**
	 * Writing an array with primitive values (with wrapper) for test.
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void primitiveWrapperNull() throws IOException, JsonFormatException {
		StringWriter writer = new StringWriter();

		startArray(writer);

		put(writer, (Byte) null);

		addSeparator(writer);

		put(writer, (Short) null);

		addSeparator(writer);

		put(writer, (Integer) null);

		addSeparator(writer);

		put(writer, (Long) null);

		addSeparator(writer);

		put(writer, (Float) null);

		addSeparator(writer);

		put(writer, (Double) null);

		endArray(writer);

		JsonArray jsonArray = JsonArray.fromString(writer.toString());

		assertThat(jsonArray.getLongOrNull(0), nullValue());
		assertThat(jsonArray.getLongOrNull(1), nullValue());
		assertThat(jsonArray.getLongOrNull(2), nullValue());
		assertThat(jsonArray.getLongOrNull(3), nullValue());
		assertThat(jsonArray.getDoubleOrNull(4), nullValue());
		assertThat(jsonArray.getDoubleOrNull(5), nullValue());
	}

	/**
	 * Writing an array with primitive values (with wrapper) for test.
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void putList() throws IOException, JsonFormatException {
		StringWriter writer = new StringWriter();

		startArray(writer);

		putByteList(writer, Arrays.asList(new Byte[] {
			1,
			2,
			3
		}));

		addSeparator(writer);

		putShortList(writer, Arrays.asList(new Short[] {
			1,
			2,
			3
		}));

		addSeparator(writer);

		putIntegerList(writer, Arrays.asList(new Integer[] {
			1,
			2,
			3
		}));

		addSeparator(writer);

		putLongList(writer, Arrays.asList(new Long[] {
			1L,
			2L,
			3L
		}));

		addSeparator(writer);

		putFloatList(writer, Arrays.asList(new Float[] {
			1.1f,
			2.2f,
			3.3f
		}));

		addSeparator(writer);

		putDoubleList(writer, Arrays.asList(new Double[] {
			1.1,
			2.2,
			3.3
		}));

		addSeparator(writer);

		putDateList(writer, Arrays.asList(new Date[] {
			new Date(111111),
			new Date(222222),
			new Date(333333)
		}));

		endArray(writer);

		JsonArray jsonArray = JsonArray.fromString(writer.toString());

		assertThat(jsonArray.getJsonArrayOrNull(0).getLongOrNull(0), is(1L));
		assertThat(jsonArray.getJsonArrayOrNull(0).getLongOrNull(1), is(2L));
		assertThat(jsonArray.getJsonArrayOrNull(0).getLongOrNull(2), is(3L));

		assertThat(jsonArray.getJsonArrayOrNull(1).getLongOrNull(0), is(1L));
		assertThat(jsonArray.getJsonArrayOrNull(1).getLongOrNull(1), is(2L));
		assertThat(jsonArray.getJsonArrayOrNull(1).getLongOrNull(2), is(3L));

		assertThat(jsonArray.getJsonArrayOrNull(2).getLongOrNull(0), is(1L));
		assertThat(jsonArray.getJsonArrayOrNull(2).getLongOrNull(1), is(2L));
		assertThat(jsonArray.getJsonArrayOrNull(2).getLongOrNull(2), is(3L));

		assertThat(jsonArray.getJsonArrayOrNull(3).getLongOrNull(0), is(1L));
		assertThat(jsonArray.getJsonArrayOrNull(3).getLongOrNull(1), is(2L));
		assertThat(jsonArray.getJsonArrayOrNull(3).getLongOrNull(2), is(3L));

		assertThat(jsonArray.getJsonArrayOrNull(4).getDoubleOrNull(0), is(1.1));
		assertThat(jsonArray.getJsonArrayOrNull(4).getDoubleOrNull(1), is(2.2));
		assertThat(jsonArray.getJsonArrayOrNull(4).getDoubleOrNull(2), is(3.3));

		assertThat(jsonArray.getJsonArrayOrNull(5).getDoubleOrNull(0), is(1.1));
		assertThat(jsonArray.getJsonArrayOrNull(5).getDoubleOrNull(1), is(2.2));
		assertThat(jsonArray.getJsonArrayOrNull(5).getDoubleOrNull(2), is(3.3));

		assertThat(jsonArray.getJsonArrayOrNull(6).getLongOrNull(0), is(111111L));
		assertThat(jsonArray.getJsonArrayOrNull(6).getLongOrNull(1), is(222222L));
		assertThat(jsonArray.getJsonArrayOrNull(6).getLongOrNull(2), is(333333L));
	}
}
