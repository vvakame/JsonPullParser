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

package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.io.Writer;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

/**
 * Class to customize the serialization and deserialization.<br>
 * Use with {@link JsonKey#converter()}.<br>
 * If extend this class, when subclass must implement {@code public static TokenConverter getInstance()}.<br>
 * Check for {@link JsonUtil} class. it is useful.
 * 
 * @param <T> 
 * @author vvakame
 */
public class TokenConverter<T> {

	/**
	 * For JSON deserialize.
	 * @param parser {@link JsonPullParser}
	 * @param listener {@link OnJsonObjectAddListener} for sequential processing
	 * @return deserialized instance
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public T parse(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		throw new UnsupportedOperationException("if you use this method. override it.");
	}

	/**
	 * For JSON serialize.<br>
	 * If null is passed to object, write null to writer.
	 * @param writer
	 * @param obj
	 * @throws IOException
	 * @author vvakame
	 */
	public void encodeNullToNull(Writer writer, T obj) throws IOException {
		throw new UnsupportedOperationException("if you use this method. override it.");
	}
}
