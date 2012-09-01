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
 * Test class for {@link JsonArray}.
 * @author vvakame
 */
public class JsonArrayTest {

	/**
	 * Tests the parsing of array with 0-element (i.e. empty.)
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
	 * Tests the parsing of a simple array.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void parseSimple1() throws IOException, JsonFormatException {
		String json = "[1]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.getLongOrNull(0), is(Long.valueOf(1)));
		assertThat(jsonArray.getState(0), is(State.VALUE_LONG));
		assertThat(jsonArray.getType(0), is(Type.LONG));
	}

	/**
	 * Tests the parsing of a simple array with null value.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void parseSimpleNull() throws IOException, JsonFormatException {
		String json = "[null]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.get(0), is(nullValue()));
		assertThat(jsonArray.getState(0), is(State.VALUE_NULL));
		assertThat(jsonArray.getType(0), is(Type.NULL));
	}

	/**
	 * Tests the parsing of a nested array.
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
	 * Tests the parser handling of {@link JsonArray#fromParser(JsonPullParser)} with pre-manipulated one.
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
	 * Tests the parsing of an array with all the types.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void parseAllTypes() throws IOException, JsonFormatException {
		String json = "[null, true, \"fuga\", 2.3, 1, [], {}]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(7));

		int i = 0;
		assertThat(jsonArray.getStringOrNull(i), is(nullValue()));
		assertThat(jsonArray.getState(i), is(State.VALUE_NULL));
		assertThat(jsonArray.getType(i), is(Type.NULL));

		i++;
		assertThat(jsonArray.getBooleanOrNull(i), is(true));
		assertThat(jsonArray.getState(i), is(State.VALUE_BOOLEAN));
		assertThat(jsonArray.getType(i), is(Type.BOOLEAN));

		i++;
		assertThat(jsonArray.getStringOrNull(i), is("fuga"));
		assertThat(jsonArray.getState(i), is(State.VALUE_STRING));
		assertThat(jsonArray.getType(i), is(Type.STRING));

		i++;
		assertThat(jsonArray.getDoubleOrNull(i), is(2.3));
		assertThat(jsonArray.getState(i), is(State.VALUE_DOUBLE));
		assertThat(jsonArray.getType(i), is(Type.DOUBLE));

		i++;
		assertThat(jsonArray.getLongOrNull(i), is(1L));
		assertThat(jsonArray.getState(i), is(State.VALUE_LONG));
		assertThat(jsonArray.getType(i), is(Type.LONG));

		i++;
		assertThat(jsonArray.getJsonArrayOrNull(i).size(), is(0));
		assertThat(jsonArray.getState(i), is(State.START_ARRAY));
		assertThat(jsonArray.getType(i), is(Type.ARRAY));

		i++;
		assertThat(jsonArray.getJsonHashOrNull(i).size(), is(0));
		assertThat(jsonArray.getState(i), is(State.START_HASH));
		assertThat(jsonArray.getType(i), is(Type.HASH));
	}

	/**
	 * Tests the behavior of {@link JsonArray#toJson(java.io.Writer)}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson1() throws IOException, JsonFormatException {
		String json = "[null, true, \"fuga\", 2.3, 1, [], {}]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		StringWriter writer = new StringWriter();
		jsonArray.toJson(writer);
		JsonArray jsonArray2 = JsonArray.fromString(writer.toString());

		assertThat(jsonArray, equalTo(jsonArray2));
	}

	/**
	 * Tests the behavior of {@link JsonArray#toJson(java.io.Writer)}.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void toJson2() throws IOException, JsonFormatException {
		JsonArray jsonArray = new JsonArray();
		jsonArray.add((Object) null);
		jsonArray.add("str");
		jsonArray.add(true);
		jsonArray.add(1.125);
		jsonArray.add(1.125f);
		jsonArray.add((byte) 1);
		jsonArray.add((short) 1);
		jsonArray.add(1);
		jsonArray.add(1L);
		jsonArray.add(new JsonArray());
		jsonArray.add(new JsonHash());

		StringWriter writer = new StringWriter();
		jsonArray.toJson(writer);

		JsonArray.fromString(writer.toString());
	}

	/**
	 * Tests the behavior of {@link JsonArray#clear()}.
	 * @author vvakame
	 */
	@Test
	public void clear() {
		JsonArray jsonArray = new JsonArray();
		jsonArray.add("hoge");

		jsonArray.clear();

		assertThat(jsonArray.size(), is(0));
	}

	/**
	 * Tests the behavior of {@link JsonArray#clone()}.
	 * @author vvakame
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void cloneTest() {
		new JsonArray().clone();
	}

	/**
	 * Tests the behavior of {@link JsonArray#add(Object)}. and get method of all.
	 * @author vvakame
	 */
	@Test
	public void get() {
		JsonArray jsonArray = new JsonArray();

		jsonArray.add((Object) null);
		jsonArray.add("str");
		jsonArray.add(true);
		jsonArray.add(1.125);
		jsonArray.add(1.125f);
		jsonArray.add((byte) 1);
		jsonArray.add((short) 1);
		jsonArray.add(1);
		jsonArray.add(1L);
		jsonArray.add(new JsonArray());
		jsonArray.add(new JsonHash());

		assertThat(jsonArray.getBooleanOrNull(0), nullValue());
		assertThat(jsonArray.getStringOrNull(1), is("str"));
		assertThat(jsonArray.getBooleanOrNull(2), is(true));
		assertThat(jsonArray.getDoubleOrNull(3), is(1.125));
		assertThat(jsonArray.getDoubleOrNull(4), is(1.125));
		assertThat(jsonArray.getLongOrNull(5), is(1L));
		assertThat(jsonArray.getLongOrNull(6), is(1L));
		assertThat(jsonArray.getLongOrNull(7), is(1L));
		assertThat(jsonArray.getLongOrNull(8), is(1L));
		assertThat(jsonArray.getJsonArrayOrNull(9), is(new JsonArray()));
		assertThat(jsonArray.getJsonHashOrNull(10), is(new JsonHash()));

		jsonArray.clear();

		jsonArray.add((Object) null);
		jsonArray.add((String) null);
		jsonArray.add((Boolean) null);
		jsonArray.add((Double) null);
		jsonArray.add((Float) null);
		jsonArray.add((Byte) null);
		jsonArray.add((Short) null);
		jsonArray.add((Integer) null);
		jsonArray.add((Long) null);
		jsonArray.add((JsonArray) null);
		jsonArray.add((JsonHash) null);

		assertThat(jsonArray.getBooleanOrNull(0), nullValue());
		assertThat(jsonArray.getStringOrNull(1), nullValue());
		assertThat(jsonArray.getBooleanOrNull(2), nullValue());
		assertThat(jsonArray.getDoubleOrNull(3), nullValue());
		assertThat(jsonArray.getDoubleOrNull(4), nullValue());
		assertThat(jsonArray.getLongOrNull(5), nullValue());
		assertThat(jsonArray.getLongOrNull(6), nullValue());
		assertThat(jsonArray.getLongOrNull(7), nullValue());
		assertThat(jsonArray.getLongOrNull(8), nullValue());
		assertThat(jsonArray.getJsonArrayOrNull(9), nullValue());
		assertThat(jsonArray.getJsonHashOrNull(10), nullValue());
	}

	/**
	 * Tests the behavior of {@link JsonArray#add(Object)}. and add method of all.
	 * @author vvakame
	 */
	@Test
	public void addWithoutIndex() {
		JsonArray jsonArray = new JsonArray();

		jsonArray.add((Object) null);
		jsonArray.add("str");
		jsonArray.add(true);
		jsonArray.add(1.125);
		jsonArray.add(1.125f);
		jsonArray.add((byte) 1);
		jsonArray.add((short) 1);
		jsonArray.add(1);
		jsonArray.add(1L);
		jsonArray.add(new JsonArray());
		jsonArray.add(new JsonHash());

		assertThat(jsonArray.getType(0), is(Type.NULL));
		assertThat(jsonArray.getType(1), is(Type.STRING));
		assertThat(jsonArray.getType(2), is(Type.BOOLEAN));
		assertThat(jsonArray.getType(3), is(Type.DOUBLE));
		assertThat(jsonArray.getType(4), is(Type.DOUBLE));
		assertThat(jsonArray.getType(5), is(Type.LONG));
		assertThat(jsonArray.getType(6), is(Type.LONG));
		assertThat(jsonArray.getType(7), is(Type.LONG));
		assertThat(jsonArray.getType(8), is(Type.LONG));
		assertThat(jsonArray.getType(9), is(Type.ARRAY));
		assertThat(jsonArray.getType(10), is(Type.HASH));

		jsonArray.clear();

		jsonArray.add((Object) null);
		jsonArray.add((String) null);
		jsonArray.add((Boolean) null);
		jsonArray.add((Double) null);
		jsonArray.add((Float) null);
		jsonArray.add((Byte) null);
		jsonArray.add((Short) null);
		jsonArray.add((Integer) null);
		jsonArray.add((Long) null);
		jsonArray.add((JsonArray) null);
		jsonArray.add((JsonHash) null);

		assertThat(jsonArray.getType(0), is(Type.NULL));
		assertThat(jsonArray.getType(1), is(Type.NULL));
		assertThat(jsonArray.getType(2), is(Type.NULL));
		assertThat(jsonArray.getType(3), is(Type.NULL));
		assertThat(jsonArray.getType(4), is(Type.NULL));
		assertThat(jsonArray.getType(5), is(Type.NULL));
		assertThat(jsonArray.getType(6), is(Type.NULL));
		assertThat(jsonArray.getType(7), is(Type.NULL));
		assertThat(jsonArray.getType(8), is(Type.NULL));
		assertThat(jsonArray.getType(9), is(Type.NULL));
		assertThat(jsonArray.getType(10), is(Type.NULL));
	}

	/**
	 * Tests the behavior of {@link JsonArray#add(int, Object)}. and add method of all.
	 * @author vvakame
	 */
	@Test
	public void addWithIndex() {
		JsonArray jsonArray = new JsonArray();

		jsonArray.add(0, (Object) null);
		jsonArray.add(1, "str");
		jsonArray.add(2, true);
		jsonArray.add(3, 1.125);
		jsonArray.add(4, 1.125f);
		jsonArray.add(5, (byte) 1);
		jsonArray.add(6, (short) 1);
		jsonArray.add(7, 1);
		jsonArray.add(8, 1L);
		jsonArray.add(9, new JsonArray());
		jsonArray.add(10, new JsonHash());

		assertThat(jsonArray.getType(0), is(Type.NULL));
		assertThat(jsonArray.getType(1), is(Type.STRING));
		assertThat(jsonArray.getType(2), is(Type.BOOLEAN));
		assertThat(jsonArray.getType(3), is(Type.DOUBLE));
		assertThat(jsonArray.getType(4), is(Type.DOUBLE));
		assertThat(jsonArray.getType(5), is(Type.LONG));
		assertThat(jsonArray.getType(6), is(Type.LONG));
		assertThat(jsonArray.getType(7), is(Type.LONG));
		assertThat(jsonArray.getType(8), is(Type.LONG));
		assertThat(jsonArray.getType(9), is(Type.ARRAY));
		assertThat(jsonArray.getType(10), is(Type.HASH));

		jsonArray.clear();

		jsonArray.add(0, (Object) null);
		jsonArray.add(1, (String) null);
		jsonArray.add(2, (Boolean) null);
		jsonArray.add(3, (Double) null);
		jsonArray.add(4, (Float) null);
		jsonArray.add(5, (Byte) null);
		jsonArray.add(6, (Short) null);
		jsonArray.add(7, (Integer) null);
		jsonArray.add(8, (Long) null);
		jsonArray.add(9, (JsonArray) null);
		jsonArray.add(10, (JsonHash) null);

		assertThat(jsonArray.getType(0), is(Type.NULL));
		assertThat(jsonArray.getType(1), is(Type.NULL));
		assertThat(jsonArray.getType(2), is(Type.NULL));
		assertThat(jsonArray.getType(3), is(Type.NULL));
		assertThat(jsonArray.getType(4), is(Type.NULL));
		assertThat(jsonArray.getType(5), is(Type.NULL));
		assertThat(jsonArray.getType(6), is(Type.NULL));
		assertThat(jsonArray.getType(7), is(Type.NULL));
		assertThat(jsonArray.getType(8), is(Type.NULL));
		assertThat(jsonArray.getType(9), is(Type.NULL));
		assertThat(jsonArray.getType(10), is(Type.NULL));
	}

	/**
	 * Tests the behavior of {@link JsonArray#remove(Object)}. and put method of all.
	 * @author vvakame
	 */
	@Test
	public void remove() {
		JsonArray jsonArray = new JsonArray();
		jsonArray.add("hoge");
		jsonArray.add(1);
		assertThat(jsonArray.getType(0), is(Type.STRING));

		jsonArray.remove("hoge");
		assertThat(jsonArray.getType(0), not(Type.STRING));
		assertThat(jsonArray.getType(0), is(Type.LONG));
	}

	/**
	 * Tests the behavior of {@link JsonArray#equals(Object)}. and put method of all.
	 * @author vvakame
	 */
	@Test
	public void equalsTest() {
		JsonArray jsonArray1 = new JsonArray();
		JsonArray jsonArray2 = new JsonArray();

		assertThat(jsonArray1.equals(jsonArray2), is(true));

		jsonArray1.add("hoge");
		assertThat(jsonArray1.equals(jsonArray2), is(false));

		jsonArray2.add("hoge");
		assertThat(jsonArray1.equals(jsonArray2), is(true));
	}
}
