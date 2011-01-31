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
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

/**
 * シリアライザ・デシリアライザをカスタマイズしたい場合実装するクラスです.<br> {@link JsonKey#converter()} に指定させて使います.<br>
 * 実装クラスでは {@code public static TokenConverter getInstance()} を実装してください.<br>
 * getInstance() で取得できるインスタンスはSingletonでも都度生成でもいいですが、
 * {@link #parse(JsonPullParser)}が再利用可能であるように配慮してください.
 * 
 * @author vvakame
 */
public class TokenConverter<T> {

	public T parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encode(Writer writer, T obj) throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encodeList(Writer writer, List<T> list) throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encodeNullToNull(Writer writer, T obj) throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encodeListNullToNull(Writer writer, List<T> list)
			throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encodeNullToBlank(Writer writer, T obj) throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}

	public void encodeListNullToBlank(Writer writer, List<T> list)
			throws IOException {
		throw new UnsupportedOperationException(
				"if you use this method. override it.");
	}
}
