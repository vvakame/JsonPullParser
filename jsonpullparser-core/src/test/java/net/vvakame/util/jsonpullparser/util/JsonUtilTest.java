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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonUtil}のテスト.
 * 
 * @author vvakame
 */
public class JsonUtilTest {

	/**
	 * JSON配列を出力するテスト.
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
	 * JSON連想配列を出力するテスト.
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
	 * {@link JsonUtil#sanitize(String)}のテスト
	 */
	@Test
	public void sanitize() {
		assertThat(JsonUtil.sanitize(null), is(nullValue()));

		assertThat(JsonUtil.sanitize("\\ \" / \b \f \n \r \t"),
				is("\\\\ \\\" \\/ \\b \\f \\n \\r \\t"));
	}
}
