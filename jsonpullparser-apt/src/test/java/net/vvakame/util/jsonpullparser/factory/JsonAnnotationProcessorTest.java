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

package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.sample.AbstractData;
import net.vvakame.sample.BaseData;
import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ContainsAnotherPackageObjectData;
import net.vvakame.sample.ConverterData;
import net.vvakame.sample.ExtendsData;
import net.vvakame.sample.ForInnerClass;
import net.vvakame.sample.GenToPackagePrivateData;
import net.vvakame.sample.JsonMetaToPackagePrivateInvalidData;
import net.vvakame.sample.JsonMetaToPackagePrivateValidData;
import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveWrapperData;
import net.vvakame.sample.PrimitiveWrapperListData;
import net.vvakame.sample.SampleData;
import net.vvakame.sample.SampleEnum;
import net.vvakame.sample.anotherpackage.AnotherPackageClass;
import net.vvakame.sample.twitter.Place;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;

import org.junit.Test;
import org.seasar.aptina.unit.AptinaTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * The test class for {@link JsonAnnotationProcessor}.
 * @author vvakame
 */
public class JsonAnnotationProcessorTest extends AptinaTestCase {

	/**
	 * Tests for proper handling of {@link PrimitiveTypeData} and {@link PrimitiveWrapperData}.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForPrimitive() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(PrimitiveTypeData.class);
		addCompilationUnit(PrimitiveWrapperData.class);
		addCompilationUnit(PrimitiveWrapperListData.class);

		compile();
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(PrimitiveTypeData.class.getName() + "Gen");
		}
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(PrimitiveTypeData.class.getName() + "JsonMeta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for proper handling of {@link SampleData}.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForSampleData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);

		compile();
		@SuppressWarnings("unused")
		String source = getGeneratedSource(SampleData.class.getName() + "Gen");
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for proper handling of {@link SampleData} and {@link ComplexData}.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForComplexData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);
		addCompilationUnit(SampleEnum.class);
		addCompilationUnit(ComplexData.class);

		compile();

		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(ComplexData.class.getName() + "Gen");
		}
		{
			@SuppressWarnings("unused")
			String source = getGeneratedSource(ComplexData.class.getName() + "JsonMeta");
		}
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for proper handling of {@link ConverterData}.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForConverterData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ConverterData.class);

		compile();
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for proper handling of inheritance chain.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForExntedsData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(BaseData.class);
		addCompilationUnit(ExtendsData.class);

		compile();
		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for proper handling of {@link Tweet} class and related.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForTwitter() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(Tweet.class);
		addCompilationUnit(User.class);
		addCompilationUnit(Place.class);

		compile();

		@SuppressWarnings("unused")
		String source = getGeneratedSource(User.class.getName() + "Gen");

		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for error raised to abstract class.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForAbstractClass() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(AbstractData.class);

		compile();

		assertThat(getCompiledResult(), is(false));
	}

	/**
	 * Tests for error raised to abstract class.
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForInnerClass() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ForInnerClass.class);

		compile();

		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for error raised to abstract class.
	 * @throws Exception
	 * @author backpaper0
	 */
	@Test
	public void testForContainsAnotherPackageObject() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(AnotherPackageClass.class);
		addCompilationUnit(ContainsAnotherPackageObjectData.class);

		compile();

		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for generated class. it modifier is "package private".
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForGenToPackagePrivate() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(GenToPackagePrivateData.class);

		compile();

		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for JsonMeta class. it modifier is "package private".
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForJsonMetaToPackagePrivateValid() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(JsonMetaToPackagePrivateValidData.class);

		compile();

		@SuppressWarnings("unused")
		String source =
				getGeneratedSource(JsonMetaToPackagePrivateValidData.class.getName() + "JsonMeta");

		assertThat(getCompiledResult(), is(true));
	}

	/**
	 * Tests for JsonMeta class. invalid case. builder == false && jsonMetaToPackagePrivate == true
	 * @throws Exception
	 * @author vvakame
	 */
	@Test
	public void testForJsonMetaToPackagePrivateInvalid() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(JsonMetaToPackagePrivateInvalidData.class);

		compile();

		assertThat(getCompiledResult(), is(false));
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// ソースパスを追加
		addSourcePath("src/test/java");
		setCharset("utf-8");
	}
}
