package net.vvakame.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.vvakame.util.jsonpullparser.JSONFormatException;
import net.vvakame.util.jsonpullparser.JSONPullParser;
import net.vvakame.util.jsonpullparser.JSONPullParser.State;

public class TestDataMeta {
	public static TestData get(JSONPullParser parser) throws IOException,
			JSONFormatException {
		TestData obj = new TestData();

		State eventType = parser.getEventType();
		if (eventType != State.START_HASH) {
			throw new IllegalStateException("not started hash brace!");
		}
		while ((eventType = parser.getEventType()) != State.END_HASH) {
			if (eventType != State.KEY) {
				throw new IllegalStateException(
						"expect KEY. we got unexpected value. " + eventType);
			}
			String key = parser.getValueString();
			eventType = parser.getEventType();
			if ("name".equals(key)) {
				obj.setName(parser.getValueString());
			} else if ("package_name".equals(key)) {
				obj.setPackageName(parser.getValueString());
			} else if ("version_code".equals(key)) {
				obj.setVersionCode(parser.getValueInt());
			} else if ("weight".equals(key)) {
				obj.setWeight(parser.getValueDouble());
			} else if ("has_data".equals(key)) {
				obj.setHasData(parser.getValueBoolean());
			}

		}

		return obj;
	}

	public static void main(String[] args) throws IOException,
			JSONFormatException {
		String json = "{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";
		JSONPullParser parser = new JSONPullParser();
		parser.setInput(new ByteArrayInputStream(json.getBytes()));

		TestData data = TestDataMeta.get(parser);
		data.toString();
	}
}
