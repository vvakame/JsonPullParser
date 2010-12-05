package net.vvakame.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.vvakame.util.jsonpullparser.JSONFormatException;
import net.vvakame.util.jsonpullparser.JSONPullParser;

public class Main {
	public static void main(String[] args) throws IOException,
			JSONFormatException {
		String json = "{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";
		JSONPullParser parser = new JSONPullParser();
		parser.setInput(new ByteArrayInputStream(json.getBytes()));

		TestData data = TestDataGen.get(parser);
		data.toString();
	}
}
