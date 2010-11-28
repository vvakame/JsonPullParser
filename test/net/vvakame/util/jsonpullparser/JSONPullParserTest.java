package net.vvakame.util.jsonpullparser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.vvakame.util.jsonpullparser.JSONPullParser.Current;

import org.junit.Test;

public class JSONPullParserTest {

	@Test
	public void parseEmptyHash() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;

		is = getStream("{}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseEmptyArray() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;

		is = getStream("[]");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(Current.END_ARRAY));
	}

	@Test
	public void parseEmptyJSON() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;

		is = getStream("[{}]");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.END_ARRAY));
	}

	@Test
	public void parseSimpleString() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;

		is = getStream("{\"key\":\"value\"}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value"));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseSimpleBooleanTrue() throws IOException,
			JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;
		Boolean bool;

		is = getStream("{\"key\":true}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(true));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseSimpleBooleanFalse() throws IOException,
			JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;
		Boolean bool;

		is = getStream("{\"key\":false}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(false));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseSimpleNull() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;

		is = getStream("{\"key\":null}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_NULL));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseSimpleInt() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;
		int i;

		is = getStream("{\"key\":-1}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_INTEGER));
		type = parser.getEventType();
		i = parser.getValueInt();
		assertThat(i, is(-1));
		assertThat(type, is(Current.END_HASH));
	}

	@Test
	public void parseSimpleDouble() throws IOException, JSONFormatException {
		JSONPullParser parser = new JSONPullParser();
		InputStream is;
		Current type;
		String str;
		double d;

		is = getStream("{\"key\":-1e6}");
		parser.setInput(is);
		type = parser.getEventType();
		assertThat(type, is(Current.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(Current.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(Current.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(-1000000.0));
		type = parser.getEventType();
		assertThat(type, is(Current.END_HASH));
	}

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}