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

import net.vvakame.sample.BaseData;
import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ConverterData;
import net.vvakame.sample.ExtendsData;
import net.vvakame.sample.SampleData;
import net.vvakame.sample.twitter.Place;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;

import org.seasar.aptina.unit.AptinaTestCase;

/**
 * {@link JsonAnnotationProcessor} のテスト.
 * @author vvakame
 */
public class JsonAnnotationProcessorTest extends AptinaTestCase {

	/**
	 * {@link SampleData} の処理.
	 * @throws Exception
	 * @author vvakame
	 */
	public void testForSampleData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);

		compile();
	}

	/**
	 * {@link SampleData} と {@link ComplexData} の処理.
	 * @throws Exception
	 * @author vvakame
	 */
	public void testForComplexData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);
		addCompilationUnit(ComplexData.class);

		compile();
	}

	/**
	 * {@link ConverterData} の処理.
	 * @throws Exception
	 * @author vvakame
	 */
	public void testForConverterData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ConverterData.class);

		compile();
	}

	/**
	 * 継承関係の処理.
	 * @throws Exception
	 * @author vvakame
	 */
	public void testForExntedsData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(BaseData.class);
		addCompilationUnit(ExtendsData.class);

		compile();
	}

	/**
	 * {@link Tweet} とそれにぶら下がるクラスの処理.
	 * @throws Exception
	 * @author vvakame
	 */
	public void testForTwitter() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(Tweet.class);
		addCompilationUnit(User.class);
		addCompilationUnit(Place.class);

		compile();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// ソースパスを追加
		addSourcePath("src/test/java");
		setCharset("utf-8");
	}
}
