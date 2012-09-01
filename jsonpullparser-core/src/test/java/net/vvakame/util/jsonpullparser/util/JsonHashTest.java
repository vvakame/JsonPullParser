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
	@SuppressWarnings("deprecation")
	public void parseSimple1() throws IOException, JsonFormatException {
		String json = "{\"key\":1}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);

		assertThat(jsonHash.size(), is(1));
		assertThat(jsonHash.getLongOrNull("key"), is(Long.valueOf(1)));
		assertThat(jsonHash.getState("key"), is(State.VALUE_LONG));
		assertThat(jsonHash.getType("key"), is(Type.LONG));
	}

	/**
	 * Tests the parsing of a simple hash with null value.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void parseSimpleNull() throws IOException, JsonFormatException {
		String json = "{\"key\":null}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonArray = JsonHash.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.get("key"), is(nullValue()));
		assertThat(jsonArray.getState("key"), is(State.VALUE_NULL));
		assertThat(jsonArray.getType("key"), is(Type.NULL));
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
	@SuppressWarnings("deprecation")
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
		assertThat(jsonHash2.getType("key"), is(Type.NULL));

		jsonHash2 = jsonHash.getJsonHashOrNull("key2");
		assertThat(jsonHash2.getBooleanOrNull("key"), is(true));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_BOOLEAN));
		assertThat(jsonHash2.getType("key"), is(Type.BOOLEAN));

		jsonHash2 = jsonHash.getJsonHashOrNull("key3");
		assertThat(jsonHash2.getStringOrNull("key"), is("fuga"));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_STRING));
		assertThat(jsonHash2.getType("key"), is(Type.STRING));

		jsonHash2 = jsonHash.getJsonHashOrNull("key4");
		assertThat(jsonHash2.getDoubleOrNull("key"), is(2.3));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_DOUBLE));
		assertThat(jsonHash2.getType("key"), is(Type.DOUBLE));

		jsonHash2 = jsonHash.getJsonHashOrNull("key5");
		assertThat(jsonHash2.getLongOrNull("key"), is(1L));
		assertThat(jsonHash2.getState("key"), is(State.VALUE_LONG));
		assertThat(jsonHash2.getType("key"), is(Type.LONG));

		jsonHash2 = jsonHash.getJsonHashOrNull("key6");
		assertThat(jsonHash2.getJsonArrayOrNull("key"), instanceOf(JsonArray.class));
		assertThat(jsonHash2.getState("key"), is(State.START_ARRAY));
		assertThat(jsonHash2.getType("key"), is(Type.ARRAY));

		jsonHash2 = jsonHash.getJsonHashOrNull("key7");
		assertThat(jsonHash2.getJsonHashOrNull("key"), instanceOf(JsonHash.class));
		assertThat(jsonHash2.getState("key"), is(State.START_HASH));
		assertThat(jsonHash2.getType("key"), is(Type.HASH));
	}

	/**
	 * Tests the behavior of {@link JsonHash#toJson(java.io.Writer)}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson1() throws IOException, JsonFormatException {
		String json =
				"   {\"key1\":{\"key\":null}, \"key2\":{\"key\":true}, \"key3\":{\"key\":\"fuga\"}, \"key4\":{\"key\":2.3}, \"key5\":{\"key\":1}, \"key6\":{\"key\":[]}, \"key7\":{\"key\":{}}}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonHash jsonHash = JsonHash.fromParser(parser);

		StringWriter writer = new StringWriter();
		jsonHash.toJson(writer);
		JsonHash jsonHash2 = JsonHash.fromString(writer.toString());

		assertThat(jsonHash, equalTo(jsonHash2));
	}

	/**
	 * Tests the behavior of {@link JsonHash#toJson(java.io.Writer)}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson2() throws IOException, JsonFormatException {
		JsonHash jsonHash = new JsonHash();
		jsonHash.put("null", (Object) null);
		jsonHash.put("string", "str");
		jsonHash.put("boolean", true);
		jsonHash.put("double", 1.125);
		jsonHash.put("float", 1.125f);
		jsonHash.put("byte", (byte) 1);
		jsonHash.put("short", (short) 1);
		jsonHash.put("int", 1);
		jsonHash.put("long", 1L);
		jsonHash.put("array", new JsonArray());
		jsonHash.put("hash", new JsonHash());

		StringWriter writer = new StringWriter();
		jsonHash.toJson(writer);
		JsonHash.fromString(writer.toString());
	}

	/**
	 * Tests the behavior of {@link JsonHash#clear()}.
	 * @author vvakame
	 */
	@Test
	public void clear() {
		JsonHash jsonHash = new JsonHash();
		jsonHash.put("hoge", true);

		jsonHash.clear();

		assertThat(jsonHash.size(), is(0));
	}

	/**
	 * Tests the behavior of {@link JsonHash#clone()}.
	 * @author vvakame
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void cloneTest() {
		new JsonHash().clone();
	}

	/**
	 * Tests the behavior of {@link JsonHash#get(Object)}. and get method of all.
	 * @author vvakame
	 */
	@Test
	public void get() {
		JsonHash jsonHash = new JsonHash();

		jsonHash.put("null", (Object) null);
		jsonHash.put("string", "str");
		jsonHash.put("boolean", true);
		jsonHash.put("double", 1.125);
		jsonHash.put("float", 1.125f);
		jsonHash.put("byte", (byte) 1);
		jsonHash.put("short", (short) 1);
		jsonHash.put("int", 1);
		jsonHash.put("long", 1L);
		jsonHash.put("array", new JsonArray());
		jsonHash.put("hash", new JsonHash());

		assertThat(jsonHash.getBooleanOrNull("null"), nullValue());
		assertThat(jsonHash.getStringOrNull("string"), is("str"));
		assertThat(jsonHash.getBooleanOrNull("boolean"), is(true));
		assertThat(jsonHash.getDoubleOrNull("double"), is(1.125));
		assertThat(jsonHash.getDoubleOrNull("float"), is(1.125));
		assertThat(jsonHash.getLongOrNull("byte"), is(1L));
		assertThat(jsonHash.getLongOrNull("short"), is(1L));
		assertThat(jsonHash.getLongOrNull("int"), is(1L));
		assertThat(jsonHash.getLongOrNull("long"), is(1L));
		assertThat(jsonHash.getJsonArrayOrNull("array"), is(new JsonArray()));
		assertThat(jsonHash.getJsonHashOrNull("hash"), is(new JsonHash()));

		jsonHash.clear();

		jsonHash.put("null", (Object) null);
		jsonHash.put("string", (String) null);
		jsonHash.put("boolean", (Boolean) null);
		jsonHash.put("double", (Double) null);
		jsonHash.put("float", (Float) null);
		jsonHash.put("byte", (Byte) null);
		jsonHash.put("short", (Short) null);
		jsonHash.put("int", (Integer) null);
		jsonHash.put("long", (Long) null);
		jsonHash.put("array", (JsonArray) null);
		jsonHash.put("hash", (JsonHash) null);

		assertThat(jsonHash.getBooleanOrNull("null"), nullValue());
		assertThat(jsonHash.getStringOrNull("string"), nullValue());
		assertThat(jsonHash.getBooleanOrNull("boolean"), nullValue());
		assertThat(jsonHash.getDoubleOrNull("double"), nullValue());
		assertThat(jsonHash.getDoubleOrNull("float"), nullValue());
		assertThat(jsonHash.getLongOrNull("byte"), nullValue());
		assertThat(jsonHash.getLongOrNull("short"), nullValue());
		assertThat(jsonHash.getLongOrNull("int"), nullValue());
		assertThat(jsonHash.getLongOrNull("long"), nullValue());
		assertThat(jsonHash.getJsonArrayOrNull("array"), nullValue());
		assertThat(jsonHash.getJsonHashOrNull("hash"), nullValue());
	}

	/**
	 * Tests the behavior of {@link JsonHash#put(String, Object)}. and put method of all.
	 * @author vvakame
	 */
	@Test
	public void put() {
		JsonHash jsonHash = new JsonHash();

		jsonHash.put("null", (Object) null);
		jsonHash.put("string", "str");
		jsonHash.put("boolean", true);
		jsonHash.put("double", 1.1);
		jsonHash.put("float", 1.1f);
		jsonHash.put("byte", (byte) 1);
		jsonHash.put("short", (short) 1);
		jsonHash.put("int", 1);
		jsonHash.put("long", 1L);
		jsonHash.put("array", new JsonArray());
		jsonHash.put("hash", new JsonHash());

		assertThat(jsonHash.getType("null"), is(Type.NULL));
		assertThat(jsonHash.getType("string"), is(Type.STRING));
		assertThat(jsonHash.getType("boolean"), is(Type.BOOLEAN));
		assertThat(jsonHash.getType("double"), is(Type.DOUBLE));
		assertThat(jsonHash.getType("float"), is(Type.DOUBLE));
		assertThat(jsonHash.getType("byte"), is(Type.LONG));
		assertThat(jsonHash.getType("short"), is(Type.LONG));
		assertThat(jsonHash.getType("int"), is(Type.LONG));
		assertThat(jsonHash.getType("long"), is(Type.LONG));
		assertThat(jsonHash.getType("array"), is(Type.ARRAY));
		assertThat(jsonHash.getType("hash"), is(Type.HASH));

		jsonHash.clear();

		jsonHash.put("null", (Object) null);
		jsonHash.put("string", (String) null);
		jsonHash.put("boolean", (Boolean) null);
		jsonHash.put("double", (Double) null);
		jsonHash.put("float", (Float) null);
		jsonHash.put("byte", (Byte) null);
		jsonHash.put("short", (Short) null);
		jsonHash.put("int", (Integer) null);
		jsonHash.put("long", (Long) null);
		jsonHash.put("array", (JsonArray) null);
		jsonHash.put("hash", (JsonHash) null);

		assertThat(jsonHash.getType("null"), is(Type.NULL));
		assertThat(jsonHash.getType("string"), is(Type.NULL));
		assertThat(jsonHash.getType("boolean"), is(Type.NULL));
		assertThat(jsonHash.getType("double"), is(Type.NULL));
		assertThat(jsonHash.getType("float"), is(Type.NULL));
		assertThat(jsonHash.getType("byte"), is(Type.NULL));
		assertThat(jsonHash.getType("short"), is(Type.NULL));
		assertThat(jsonHash.getType("int"), is(Type.NULL));
		assertThat(jsonHash.getType("long"), is(Type.NULL));
		assertThat(jsonHash.getType("array"), is(Type.NULL));
		assertThat(jsonHash.getType("hash"), is(Type.NULL));
	}

	/**
	 * Tests the behavior of {@link JsonHash#remove(Object)}. and put method of all.
	 * @author vvakame
	 */
	@Test
	public void remove() {
		JsonHash jsonHash = new JsonHash();
		jsonHash.put("key", "hoge");
		assertThat(jsonHash.getType("key"), is(Type.STRING));

		jsonHash.remove("key");
		assertThat(jsonHash.getType("key"), nullValue());
	}

	/**
	 * Tests the behavior of {@link JsonHash#equals(Object)}. and put method of all.
	 * @author vvakame
	 */
	@Test
	public void equalsTest() {
		JsonHash jsonHash1 = new JsonHash();
		JsonHash jsonHash2 = new JsonHash();

		assertThat(jsonHash1.equals(jsonHash2), is(true));

		jsonHash1.put("str", "hoge");
		assertThat(jsonHash1.equals(jsonHash2), is(false));

		jsonHash2.put("str", "hoge");
		assertThat(jsonHash1.equals(jsonHash2), is(true));
	}
}
