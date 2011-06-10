package net.vvakame.sample.duma;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;
import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

/**
 * ReadItLaterの何かのAPIのItemをMap<String, Item>としてパースするための {@link TokenConverter}
 * @author vvakame
 */
public class ItemMapConverter extends TokenConverter<Map<String, Item>> {

	static ItemMapConverter conv = null;


	/**
	 * インスタンスの取得
	 * @return インスタンス
	 * @author vvakame
	 */
	public static ItemMapConverter getInstance() {
		if (conv == null) {
			conv = new ItemMapConverter();
		}
		return conv;
	}

	@Override
	public Map<String, Item> parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {

		State state = parser.lookAhead();

		if (state == State.VALUE_NULL) {
			return null;
		}

		Map<String, Item> resultMap = new HashMap<String, Item>();

		state = parser.getEventType();
		if (state != State.START_HASH) {
			throw new JsonFormatException("expected state is START_HASH, but get=" + state);
		}

		while ((state = parser.lookAhead()) != State.END_HASH) {

			state = parser.getEventType();

			if (state != State.KEY) {
				throw new JsonFormatException("expected state is VALUE_STRING, but get=" + state);
			}
			String key = parser.getValueString();

			Item item = ItemGenerated.get(parser, listener);
			resultMap.put(key, item);

		}
		parser.getEventType();

		return resultMap;
	}

	@Override
	public void encodeNullToNull(Writer writer, Map<String, Item> obj) throws IOException {
		if (obj == null) {
			writer.write("null");
			return;
		}

		startHash(writer);

		int size = obj.size();
		int i = 0;
		for (String key : obj.keySet()) {
			Item item = obj.get(key);

			JsonUtil.putKey(writer, key);
			ItemGenerated.encode(writer, item);

			if (i + 1 < size) {
				addSeparator(writer);
			}
			i++;
		}

		endHash(writer);
	}
}
