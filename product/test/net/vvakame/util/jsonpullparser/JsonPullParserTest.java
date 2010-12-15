package net.vvakame.util.jsonpullparser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import org.junit.Test;

public class JsonPullParserTest {

	@Test
	public void parseEmptyHash() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("{}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseEmptyArray() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("[]");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	@Test
	public void parseEmptyJSON() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("[{}]");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	@Test
	public void parseSimpleString() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;

		is = getStream("{\"key\":\"value\"}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value"));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseSpecialChar() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;

		is = getStream("{\"key\":\" \\\" \\t \\r \\n \\b \\f \\ \"}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is(" \" \t \r \n \b \f \\ "));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseCharByHex() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;

		is = getStream("[\"json \\u30e1\\u30e2\", \"\\u123x\", \"\\u30E1\\u30E2\"]");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("json メモ"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("\\u123x"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("メモ"));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	@Test
	public void parseSimpleBooleanTrue() throws IOException,
			JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		Boolean bool;

		is = getStream("{\"key\":true}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(true));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseSimpleBooleanFalse() throws IOException,
			JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		Boolean bool;

		is = getStream("{\"key\":false}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(false));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseSimpleNull() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;

		is = getStream("{\"key\":null}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseSimpleLong() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		long i;

		is = getStream("{\"key\":-1}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(-1L));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseSimpleDouble() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		double d;

		is = getStream("{\"key\":-1e6}");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(-1000000.0));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseHash() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		long i;
		double d;
		boolean b;

		is = getStream("{\"key1\":\"value1\", \"key2\":2, \"key3\":0.1 ,\"key4\":true ,\"key5\":false ,\"key6\":null}");
		parser.setSource(is);

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key1"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value1"));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key2"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(2L));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key3"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(0.1));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key4"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(true));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key5"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(false));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key6"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void parseArray() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;
		String str;
		long i;
		double d;
		boolean b;

		is = getStream("[\"value1\", 2, 0.1 ,true ,false ,null]");
		parser.setSource(is);
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value1"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(2L));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(0.1));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(true));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(false));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	@Test
	public void parseComplex1() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("[\"value1\", 2, 0.1 ,true ,{\"test1\":1, \"test2\":null   \n  } ,null]");
		parser.setSource(is);

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	@Test
	public void parseComplex2() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("{\"key\":\"value\", \"list\":[]}");
		parser.setSource(is);

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	@Test
	public void lookAhead() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;
		State type;

		is = getStream("[\"value1\", 2, 0.1 ,true ,{\"test1\":1, \"test2\":null   \n  } ,null]");
		parser.setSource(is);

		type = parser.lookAhead();
		assertThat(type, is(State.START_ARRAY));

		type = parser.lookAhead();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.lookAhead();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
	}

	@Test(expected = JsonFormatException.class)
	public void parseFailure1() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;

		is = getStream("[}");
		parser.setSource(is);

		parser.getEventType();
		parser.getEventType();
	}

	@Test(expected = JsonFormatException.class)
	public void parseFailure2() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;

		is = getStream("[+0]");
		parser.setSource(is);

		parser.getEventType();
		parser.getEventType();
	}

	@Test(expected = JsonFormatException.class)
	public void parseFailure3() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;

		is = getStream("[0-0]");
		parser.setSource(is);

		parser.getEventType();
		parser.getEventType();
	}

	@Test(expected = JsonFormatException.class)
	public void parseFailure4() throws IOException, JsonFormatException {
		JsonPullParser parser = new JsonPullParser();
		InputStream is;

		is = getStream("[0,,0]");
		parser.setSource(is);

		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
	}

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}