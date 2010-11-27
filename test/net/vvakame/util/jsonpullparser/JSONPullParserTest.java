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
	public void parseEmptyHash() throws IOException {
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
	public void parseEmptyArray() throws IOException {
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
	public void parseEmptyJSON() throws IOException {
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

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}