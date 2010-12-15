package net.vvakame.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

public class Main {
	public static void main(String[] args) throws IOException,
			JsonFormatException {
		String json = "{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";
		JsonPullParser parser = new JsonPullParser();
		parser.setSource(new ByteArrayInputStream(json.getBytes()));

		TestData data = TestDataGenerated.get(parser);
		data.toString();
	}
}
