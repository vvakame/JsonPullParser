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
import java.io.StringWriter;

import net.vvakame.sample.ConverterData;
import net.vvakame.sample.ConverterDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link TokenConverter} のテスト.
 * @author vvakame
 */
public class TokenConverterTest {

	/**
	 * {@link TokenConverter} を利用したクラスのデシリアライズをテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void stringConverter() throws IOException, JsonFormatException {
		String json = "{\"str1\":\"value1\", \"str2\":\"value2\"}";

		ConverterData converterData = ConverterDataGenerated.get(json);

		assertThat(converterData.getStr1(), is("value1"));
		assertThat(converterData.getStr2(), is("value2"));
	}

	/**
	 * {@link TokenConverter} を利用したクラスのデシリアライズとシリアライズをテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void integerFlattenConverter() throws IOException, JsonFormatException {
		String json =
				"{\"str1\":\"value1\", \"str2\":\"value2\", \"flatten\":[1,[2,3],[[4,[5,6],7]],[],8]}";

		ConverterData converterData = ConverterDataGenerated.get(json);

		assertThat(converterData.getStr1(), is("value1"));
		assertThat(converterData.getStr2(), is("value2"));
		assertThat(converterData.getFlatten().size(), is(8));
		for (int i = 1; i <= 8; i++) {
			assertThat(converterData.getFlatten().get(i - 1), is(i));
		}

		StringWriter writer = new StringWriter();
		ConverterDataGenerated.encode(writer, converterData);
		String toJson = writer.toString();

		JsonHash jsonHash = JsonHash.fromString(toJson);
		assertThat(jsonHash.size(), is(3));
	}
}
