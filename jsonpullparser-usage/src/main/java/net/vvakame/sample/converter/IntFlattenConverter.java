/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.sample.converter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;
import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

/**
 * Converts nested integer array (in JSON form) into {@link List} of {@link Integer}s.
 * @author vvakame
 */
public class IntFlattenConverter extends TokenConverter<List<Integer>> {

	static IntFlattenConverter conv = null;


	/**
	 * Returns an instance of {@link IntFlattenConverter}.<br>
	 * NB: Implemented as singleton, as it has stateless nature.
	 * @return {@link IntFlattenConverter}
	 * @author vvakame
	 */
	public static IntFlattenConverter getInstance() {
		if (conv == null) {
			conv = new IntFlattenConverter();
		}
		return conv;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Integer> parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
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

	List<Integer> parse(JsonPullParser parser, List<Integer> list) throws IOException,
			JsonFormatException {

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void encodeNullToNull(Writer writer, List<Integer> obj) throws IOException {
		if (obj == null) {
			writer.write("null");
			return;
		}

		startArray(writer);

		int size = obj.size();
		for (int i = 0; i < size; i++) {
			JsonUtil.put(writer, (int) obj.get(i));
			if (i + 1 < size) {
				addSeparator(writer);
			}
		}

		endArray(writer);
	}
}
