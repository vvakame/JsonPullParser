package net.vvakame.util.jsonpullparser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.arnx.jsonic.JSON;
import net.vvakame.util.jsonpullparser.JSONPullParser.State;

import org.junit.Test;

public class JSONPullParserSpecTest {

	@Test
	public void specMyLib() throws IOException, JSONFormatException {
		String json = getJson();
		System.gc();
		long start = System.currentTimeMillis();
		JSONPullParser parser = new JSONPullParser();
		ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes());
		parser.setInput(stream);
		State current = null;
		while (current != State.END_ARRAY) {
			current = parser.getEventType();
		}
		long end = System.currentTimeMillis();

		System.out.println("My");
		System.out.println(end - start);
	}

	@Test
	public void specJSONIC() throws IOException, JSONFormatException {
		String json = getJson();
		System.gc();
		long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		Object decode = JSON.decode(json);
		long end = System.currentTimeMillis();

		System.out.println("JSONIC");
		System.out.println(end - start);
	}

	public String getJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < 100000; i++) {
			builder.append("{\"key\":").append(i).append(".1")
					.append(",\"test\":\"str\"},");
			// builder.append("{\"test\":\"str\"},");
		}
		builder.setLength(builder.length() - 1);
		builder.append("]");
		return builder.toString();
	}
}