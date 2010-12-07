package net.vvakame.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.JsonFormatException;

public class ComplexDataSample {
	public static ComplexData get(JsonPullParser parser) throws IOException,
			JsonFormatException {
		ComplexData obj = new ComplexData();
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
			if ("name".equals(key)) {
				eventType = parser.getEventType();
				obj.name = parser.getValueString();
			} else if ("list1".equals(key)) {
				List<TestData> list = new ArrayList<TestData>();
				eventType = parser.getEventType();
				if (eventType != State.START_ARRAY) {
					throw new JsonFormatException("not started bracket!");
				}
				while ((eventType = parser.lookAhead()) != State.END_ARRAY) {
					list.add(TestDataGenerated.get(parser));
				}
				parser.getEventType();
				obj.list1 = list;
			} else if ("list2".equals(key)) {
				List<TestData> list = new ArrayList<TestData>();
				eventType = parser.getEventType();
				if (eventType != State.START_ARRAY) {
					throw new JsonFormatException("not started bracket!");
				}
				while ((eventType = parser.lookAhead()) != State.END_ARRAY) {
					list.add(TestDataGenerated.get(parser));
				}
				parser.getEventType();
				obj.list2 = list;
			} else if ("list3".equals(key)) {
				List<TestData> list = new ArrayList<TestData>();
				eventType = parser.getEventType();
				if (eventType != State.START_ARRAY) {
					throw new JsonFormatException("not started bracket!");
				}
				while ((eventType = parser.lookAhead()) != State.END_ARRAY) {
					list.add(TestDataGenerated.get(parser));
				}
				parser.getEventType();
				obj.list3 = list;
			} else if ("data".equals(key)) {
				obj.data = TestDataGenerated.get(parser);
			}
		}
		return obj;
	}
}