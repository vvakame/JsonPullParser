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
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

/**
 * {@link JsonUtil}のテスト.
 * 
 * @author vvakame
 */
public class JsonUtilTest {

	@Test
	public void toJsonArray() {
		StringBuilder builder = new StringBuilder();

		startArray(builder);

		put(builder, (byte) 1);

		addSeparator(builder);

		put(builder, (short) 2);

		addSeparator(builder);

		put(builder, 3);

		addSeparator(builder);

		put(builder, 4L);

		addSeparator(builder);

		put(builder, 5.1f);

		addSeparator(builder);

		put(builder, 6.2);

		endArray(builder);

		JSONArray jsonArray = JSONArray.fromObject(builder.toString());

		assertThat(jsonArray.getLong(0), is(1L));
		assertThat(jsonArray.getLong(1), is(2L));
		assertThat(jsonArray.getLong(2), is(3L));
		assertThat(jsonArray.getLong(3), is(4L));
		assertThat(jsonArray.getDouble(4), is(5.1));
		assertThat(jsonArray.getDouble(5), is(6.2));
	}

	@Test
	public void toJsonHash() {
		StringBuilder builder = new StringBuilder();

		startHash(builder);

		putKey(builder, "hoge");
		put(builder, null);

		addSeparator(builder);

		putKey(builder, "\"");
		put(builder, "\\");

		addSeparator(builder);

		putKey(builder, "海藻系ぬこ");
		put(builder, "にゃー!!");

		addSeparator(builder);

		putKey(builder, "char");
		put(builder, 'c');

		endHash(builder);

		JSONObject json = JSONObject.fromObject(builder.toString());

		assertThat(json.getJSONObject("hoge").isNullObject(), is(true));
		assertThat(json.getString("\""), is("\\"));
		assertThat(json.getString("海藻系ぬこ"), is("にゃー!!"));
		assertThat(json.getString("char"), is("c"));
	}

	/**
	 * {@link JsonUtil#sanitizeJson(String)}のテスト
	 */
	@Test
	public void sanitize() {
		assertThat(JsonUtil.sanitize(null), is(nullValue()));

		assertThat(JsonUtil.sanitize("\\ \" / \b \f \n \r \t"),
				is("\\\\ \\\" \\/ \\b \\f \\n \\r \\t"));
	}
}
