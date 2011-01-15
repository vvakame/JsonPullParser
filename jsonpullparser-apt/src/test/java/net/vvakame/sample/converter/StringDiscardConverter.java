package net.vvakame.sample.converter;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

public class StringDiscardConverter extends TokenConverter<String> {

	@Override
	public String parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state = parser.getEventType();

		switch (state) {
		case VALUE_NULL:
			return null;
		case VALUE_STRING:
			return parser.getValueString();
		default:
			throw new IllegalStateException();
		}
	}
}
