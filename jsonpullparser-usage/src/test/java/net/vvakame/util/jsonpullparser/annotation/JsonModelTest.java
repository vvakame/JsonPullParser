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
import java.lang.reflect.Modifier;

import net.vvakame.sample.BaseData;
import net.vvakame.sample.ConverterDataGenerated;
import net.vvakame.sample.GenToPackagePrivateData;
import net.vvakame.sample.JsonMetaToPackagePrivateValidData;
import net.vvakame.sample.TestDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for {@link JsonModel}.
 * @author vvakame
 */
public class JsonModelTest {

	/**
	 * Ensures {@link JsonModel#treatUnknownKeyAsError()} == {@code true} makes unknown keys freaks parsers out.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test(expected = JsonFormatException.class)
	public void treatUnknownKeyAsError() throws IOException, JsonFormatException {
		String json = "{\"str1\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		ConverterDataGenerated.get(json);
	}

	/**
	 * Ensures {@link JsonModel#treatUnknownKeyAsError()} == {@code false} makes unknown keys futile.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void ignoreUnknownKey() throws IOException, JsonFormatException {
		String json = "{\"name\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		TestDataGenerated.get(json);
	}

	/**
	 * Ensures {@link JsonModel#genToPackagePrivate()} == {@code true} change modifier to package private .
	 * @author vvakame
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@Test
	public void genToPackagePrivate() throws ClassNotFoundException, SecurityException,
			NoSuchMethodException {
		Class<?> clazz =
				Class.forName(GenToPackagePrivateData.class.getCanonicalName() + "Generated");
		assertThat(clazz, notNullValue());

		assertThat("class to be package private", Modifier.isPublic(clazz.getModifiers()),
				is(false));
	}

	/**
	 * Ensures {@link JsonModel#jsonMetaToPackagePrivate()} == {@code true} change modifier to package private .
	 * @author vvakame
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@Test
	public void jsonMetaToPackagePrivate() throws ClassNotFoundException, SecurityException,
			NoSuchMethodException {
		Class<?> clazz =
				Class.forName(JsonMetaToPackagePrivateValidData.class.getCanonicalName()
						+ "JsonMeta");
		assertThat(clazz, notNullValue());

		assertThat("class to be package private", Modifier.isPublic(clazz.getModifiers()),
				is(false));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void modifierTestSample() throws SecurityException {
		Class<?> clazz = BaseData.class;
		assertThat(clazz, notNullValue());

		assertThat("class to be package private", Modifier.isPublic(clazz.getModifiers()), is(true));
	}
}
