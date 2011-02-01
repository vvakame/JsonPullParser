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

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonArray} のテスト.
 * @author vvakame
 */
public class JsonArrayTest {

	/**
	 * 要素0の配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmpty() throws IOException, JsonFormatException {
		String json = "[]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(0));
	}

	/**
	 * 簡単な配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimple1() throws IOException, JsonFormatException {
		String json = "[1]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.getLongOrNull(0), is(Long.valueOf(1)));
		assertThat(jsonArray.getState(0), is(State.VALUE_LONG));
	}

	/**
	 * 簡単な配列の解釈. 値がnullの場合.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleNull() throws IOException, JsonFormatException {
		String json = "[null]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.get(0), is(nullValue()));
		assertThat(jsonArray.getState(0), is(State.VALUE_NULL));
	}

	/**
	 * 配列の中に配列がネストしている場合の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseNested() throws IOException, JsonFormatException {
		String json = "[[1,2],[3,4],5,[6,7,8]]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(4));
		jsonArray = (JsonArray) jsonArray.get(1);
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getLongOrNull(1), is(Long.valueOf(4)));
	}

	/**
	 * {@link JsonPullParser}を途中まで手で操作したのちに {@link JsonArray#fromParser(JsonPullParser)} を利用したときのテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseAppendable() throws IOException, JsonFormatException {
		String json = "[[1,2],[3,4]]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		// 読み捨て
		parser.getEventType();
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(2));

		assertThat(jsonArray.getLongOrNull(0), is(1L));
		assertThat(jsonArray.getLongOrNull(1), is(2L));
	}

	/**
	 * {@link State} で定義されているトークンを全部使ってテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseAllTypes() throws IOException, JsonFormatException {
		String json = "[null, true, \"fuga\", 2.3, 1, [], {}]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(7));

		int i = 0;
		assertThat(jsonArray.getStringOrNull(i), is(nullValue()));
		assertThat(jsonArray.getState(i), is(State.VALUE_NULL));

		i++;
		assertThat(jsonArray.getBooleanOrNull(i), is(true));
		assertThat(jsonArray.getState(i), is(State.VALUE_BOOLEAN));

		i++;
		assertThat(jsonArray.getStringOrNull(i), is("fuga"));
		assertThat(jsonArray.getState(i), is(State.VALUE_STRING));

		i++;
		assertThat(jsonArray.getDoubleOrNull(i), is(2.3));
		assertThat(jsonArray.getState(i), is(State.VALUE_DOUBLE));

		i++;
		assertThat(jsonArray.getLongOrNull(i), is(1L));
		assertThat(jsonArray.getState(i), is(State.VALUE_LONG));

		i++;
		assertThat(jsonArray.getJsonArrayOrNull(i).size(), is(0));
		assertThat(jsonArray.getState(i), is(State.START_ARRAY));

		i++;
		assertThat(jsonArray.getJsonHashOrNull(i).size(), is(0));
		assertThat(jsonArray.getState(i), is(State.START_HASH));
	}

	/**
	 * {@link JsonArray#toJson(java.io.Writer)} のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson() throws IOException, JsonFormatException {
		String json = "[null, true, \"fuga\", 2.3, 1, [], {}]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		StringWriter writer = new StringWriter();
		jsonArray.toJson(writer);
		JsonArray jsonArray2 = JsonArray.fromString(writer.toString());

		assertThat(jsonArray, equalTo(jsonArray2));
	}

	// TODO テストを書いてない操作用関数が多数ある…
}
