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
 * シリアライザ・デシリアライザをカスタマイズしたい場合実装するクラスです.<br> {@link JsonKey#converter()} に指定させて使います.<br>
 * 実装クラスでは {@code public static TokenConverter getInstance()} を実装してください.<br>
 * getInstance() で取得できるインスタンスはSingletonでも都度生成でもいいですが、
 * {@link #parse(JsonPullParser, OnJsonObjectAddListener)} や {@link #encodeNullToNull(Writer, Object)} が再利用可能であるように配慮してください.<br>
 * 本インターフェイスの実装として役に立つユーティリティとして {@link JsonUtil} が用意されています.
 * 
 * @param <T> 
 * @author vvakame
 */
public class TokenConverter<T> {

	/**
	 * デシリアライズ用メソッド.
	 * @param parser 処理対象の {@link JsonPullParser}
	 * @param listener 逐次処理結果を渡す {@link OnJsonObjectAddListener}. nullが渡される場合も考慮すること.
	 * @return デシリアライズしたインスタンス
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public T parse(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		throw new UnsupportedOperationException("if you use this method. override it.");
	}

	/**
	 * シリアライズ用メソッド.<br>
	 * obj に {@code null} が渡された場合、 writer に {@code null} を書きこむこと.<br>
	 * 本処理はシリアライズ結果のJSONの一部分だけを出力することを考慮して処理すること.
	 * @param writer
	 * @param obj
	 * @throws IOException
	 * @author vvakame
	 */
	public void encodeNullToNull(Writer writer, T obj) throws IOException {
		throw new UnsupportedOperationException("if you use this method. override it.");
	}
}
