package net.vvakame.sample.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

public class IntFlattenConverter extends TokenConverter<List<Integer>> {

	static IntFlattenConverter conv = null;

	public static IntFlattenConverter getInstance() {
		if (conv == null) {
			conv = new IntFlattenConverter();
		}
		return conv;
	}

	@Override
	public List<Integer> parse(JsonPullParser parser,
			OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state = parser.getEventType();

		switch (state) {
		case VALUE_NULL:
			return null;
		case START_ARRAY:
			List<Integer> list = parse(parser, new ArrayList<Integer>());
			if (listener != null) {
				listener.onAdd(list);
			}
			return list;
		default:
			throw new IllegalStateException();
		}
	}

	List<Integer> parse(JsonPullParser parser, List<Integer> list)
			throws IOException, JsonFormatException {

		State state;
		while ((state = parser.lookAhead()) != State.END_ARRAY) {
			switch (state) {
			case VALUE_LONG:
				parser.getEventType();
				list.add((int) parser.getValueLong());
				break;
			case START_ARRAY:
				parser.getEventType();
				parse(parser, list);
				break;
			default:
				throw new IllegalStateException();
			}
		}
		parser.getEventType();

		return list;
	}
}
