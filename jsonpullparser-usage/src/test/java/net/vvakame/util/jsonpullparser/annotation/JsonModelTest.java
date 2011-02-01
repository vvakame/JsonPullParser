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

package net.vvakame.util.jsonpullparser.annotation;

import java.io.IOException;

import net.vvakame.sample.ConverterDataGenerated;
import net.vvakame.sample.TestDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

public class JsonModelTest {

	@Test(expected = IllegalStateException.class)
	public void treatUnknownKeyAsError() throws IllegalStateException, IOException,
			JsonFormatException {
		String json = "{\"str1\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		ConverterDataGenerated.get(json);
	}

	@Test
	public void ignoreUnknownKey() throws IllegalStateException, IOException, JsonFormatException {
		String json = "{\"name\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		TestDataGenerated.get(json);
	}
}
