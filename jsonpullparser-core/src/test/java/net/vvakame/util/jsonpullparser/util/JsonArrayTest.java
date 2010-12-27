package net.vvakame.util.jsonpullparser.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import org.junit.Test;

public class JsonArrayTest {

	@Test
	public void parseEmpty() throws IOException, JsonFormatException {
		String json = "[]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(0));
	}

	@Test
	public void parseSimple1() throws IOException, JsonFormatException {
		String json = "[1]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.getLongOrNull(0), is(Long.valueOf(1)));
		assertThat(jsonArray.getState(0), is(State.VALUE_LONG));
	}

	@Test
	public void parseSimpleNull() throws IOException, JsonFormatException {
		String json = "[null]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.get(0), is(nullValue()));
		assertThat(jsonArray.getState(0), is(State.VALUE_NULL));
	}

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

	@Test
	public void parseAllTypes() throws IOException, JsonFormatException {
		String json = "[null, true, \"fuga\", 2.3, 1, []]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		JsonArray jsonArray = JsonArray.fromParser(parser);

		assertThat(jsonArray.size(), is(6));

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
	}

	// TODO テストを書いてない操作用関数が多数ある…
}