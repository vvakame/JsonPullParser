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
 * Test class for {@link JsonHash}.
 * @author vvakame
 */
public class JsonHashTest {

	/**
	 * Tests the parsing of hash with 0-element (i.e. empty.)
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmpty() throws IOException, JsonFormatException {
		String json = "{}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);

		assertThat(jsonHash.size(), is(0));
	}

	/**
	 * Tests the parsing of a simple hash.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimple1() throws IOException, JsonFormatException {
		String json = "{\"key\":1}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);

		assertThat(jsonHash.size(), is(1));
		assertThat(jsonHash.getLongOrNull("key"), is(Long.valueOf(1)));
		assertThat(jsonHash.getState("key"), is(State.VALUE_LONG));
	}

	/**
	 * Tests the parsing of a simple hash with null value.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleNull() throws IOException, JsonFormatException {
		String json = "{\"key\":null}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonArray = JsonHash.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.get("key"), is(nullValue()));
		assertThat(jsonArray.getState("key"), is(State.VALUE_NULL));
	}

	/**
	 * Tests the parsing of a nested hash.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseNested() throws IOException, JsonFormatException {
		String json = "{\"key1\":{}, \"key2\":{\"key\":true}}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);
		JsonHash jsonHash2;

		assertThat(jsonHash.size(), is(2));
		jsonHash2 = jsonHash.getJsonHashOrNull("key1");
		assertThat(jsonHash2.size(), is(0));
		jsonHash2 = jsonHash.getJsonHashOrNull("key2");
		assertThat(jsonHash2.size(), is(1));
	}

	/**
	 * Tests the parser handling of {@link JsonHash#fromParser(JsonPullParser)} with pre-manipulated one.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseAppendable() throws IOException, JsonFormatException {
		String json = "{{\"key\":2},{\"key\":4}}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		// 読み捨て
		parser.getEventType();
		JsonHash jsonHash = JsonHash.fromParser(parser);

		assertThat(jsonHash.size(), is(1));

		assertThat(jsonHash.getLongOrNull("key"), is(2L));
	}

	/**
	 * Tests the parsing of a hash with all the types.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseAllTypes() throws IOException, JsonFormatException {
		String json =
				"{\"key1\":{\"key\":null}, \"key2\":{\"key\":true}, \"key3\":{\"key\":\"fuga\"}, \"key4\":{\"key\":2.3}, \"key5\":{\"key\":1}, \"key6\":{\"key\":[]}, \"key7\":{\"key\":{}}}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);
		JsonHash jsonHash2;

		assertThat(jsonHash.size(), is(7));

		jsonHash2 = jsonHash.getJsonHashOrNull("key1");
		assertThat(jsonHash2.getStringOrNull("key"), is(nullValue()));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_NULL));

		jsonHash2 = jsonHash.getJsonHashOrNull("key2");
		assertThat(jsonHash2.getBooleanOrNull("key"), is(true));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_BOOLEAN));

		jsonHash2 = jsonHash.getJsonHashOrNull("key3");
		assertThat(jsonHash2.getStringOrNull("key"), is("fuga"));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_STRING));

		jsonHash2 = jsonHash.getJsonHashOrNull("key4");
		assertThat(jsonHash2.getDoubleOrNull("key"), is(2.3));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_DOUBLE));

		jsonHash2 = jsonHash.getJsonHashOrNull("key5");
		assertThat(jsonHash2.getLongOrNull("key"), is(1L));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_LONG));

		jsonHash2 = jsonHash.getJsonHashOrNull("key6");
		assertThat(jsonHash2.getJsonArrayOrNull("key"), instanceOf(JsonArray.class));
		assertThat(jsonHash2.getState("key"), is(State.START_ARRAY));

		jsonHash2 = jsonHash.getJsonHashOrNull("key7");
		assertThat(jsonHash2.getJsonHashOrNull("key"), instanceOf(JsonHash.class));
		assertThat(jsonHash2.getState("key"), is(State.START_HASH));
	}

	/**
	 * Tests the behavior of {@link JsonHash#toJson(java.io.Writer)}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson() throws IOException, JsonFormatException {
		String json =
				"   {\"key1\":{\"key\":null}, \"key2\":{\"key\":true}, \"key3\":{\"key\":\"fuga\"}, \"key4\":{\"key\":2.3}, \"key5\":{\"key\":1}, \"key6\":{\"key\":[]}, \"key7\":{\"key\":{}}}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);

		StringWriter writer = new StringWriter();
		jsonHash.toJson(writer);
		JsonHash jsonHash2 = JsonHash.fromString(writer.toString());

		assertThat(jsonHash, equalTo(jsonHash2));
	}

	// TODO テストを書いてない操作用関数が多数ある…
}
