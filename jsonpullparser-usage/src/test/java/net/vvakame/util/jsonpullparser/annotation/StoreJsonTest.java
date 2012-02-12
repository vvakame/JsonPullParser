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
import java.util.List;

import net.vvakame.sample.StoreJsonData1;
import net.vvakame.sample.StoreJsonData1Generated;
import net.vvakame.sample.StoreJsonData2;
import net.vvakame.sample.StoreJsonData2Generated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonSliceUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for passing {@link StoreJson} parameters.
 * @author vvakame
 */
public class StoreJsonTest {

	/**
	 * Ensures an exception should be raised if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test(expected = IllegalStateException.class)
	public void treatError() throws IOException, JsonFormatException {
		String json = "{\"str\":\"hoge\",\"num\":1}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		StoreJsonData1Generated.get(parser);
	}

	/**
	 * Ensures an exception should not be raised even if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void ignoreError() throws IOException, JsonFormatException {
		String json = "{\"str\":\"hoge\",\"num\":1}";
		JsonPullParser parser = JsonPullParser.newParser(json);
		StoreJsonData2 data = StoreJsonData2Generated.get(parser);

		assertThat(data.getStr(), is("hoge"));
		assertThat(data.getNum(), is(1L));
		assertThat(data.getOriginal(), nullValue());
	}

	/**
	 * Ensures an exception should be raised if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void saveOriginal1() throws IOException, JsonFormatException {
		String json = "{\"str\":\"hoge\",\"num\":1    ,   \"test\":true}";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		StoreJsonData1 data = StoreJsonData1Generated.get(parser);

		assertThat(data.getOriginal(), is("{\"str\":\"hoge\",\"num\":1,\"test\":true}"));
	}

	/**
	 * Ensures an exception should be raised if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void saveOriginal2() throws IOException, JsonFormatException {
		String json =
				"{\"str\":\"hoge\",\"num\":1    ,   \"test\":true, \"ary\":[1,2,3,4,5,6], \t \r\n \"hash\":{\"╮( ╹ω╹ )╭\": false}}";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		StoreJsonData1 data = StoreJsonData1Generated.get(parser);

		assertThat(
				data.getOriginal(),
				is("{\"str\":\"hoge\",\"num\":1,\"test\":true,\"ary\":[1,2,3,4,5,6],\"hash\":{\"╮( ╹ω╹ )╭\":false}}"));
	}

	/**
	 * Ensures an exception should be raised if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void saveOriginal3() throws IOException, JsonFormatException {
		String json =
				"{\"str\":\"hoge\",\"nest\":[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]}";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		StoreJsonData1 data = StoreJsonData1Generated.get(parser);

		assertThat(
				data.getOriginal(),
				is("{\"str\":\"hoge\",\"nest\":[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]}"));
	}

	/**
	 * Ensures an exception should be raised if {@link JsonPullParser#setLogEnable()} has not been called yet.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void decodeUnicode() throws IOException, JsonFormatException {
		String json = "{\"str\":\"\\u30e1\\u30e2\"}";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		StoreJsonData1 data = StoreJsonData1Generated.get(parser);

		assertThat(data.getStr(), is("メモ"));
		assertThat(data.getOriginal(), is("{\"str\":\"メモ\"}"));
	}

	/**
	 * Tests the handling of nested JsonModels.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void partialJson() throws IOException, JsonFormatException {
		String json = "{\"str\":\"parent\",\"num\":2,\"data1\":{\"str\":\"child\",\"num\":100}}";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		StoreJsonData2 data2 = StoreJsonData2Generated.get(parser);

		assertThat(data2.getOriginal(),
				is("{\"str\":\"parent\",\"num\":2,\"data1\":{\"str\":\"child\",\"num\":100}}"));
		assertThat(data2.getData1().getOriginal(), is("{\"str\":\"child\",\"num\":100}"));
	}

	/**
	 * Tests the handling of lists of JsonModels.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void listJson() throws IOException, JsonFormatException {
		String json = "[{\"num\"  :  1},\n{\"num\"  :  2},\n{\"num\"  :  3},\n{\"num\"  :  4}]";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();
		List<StoreJsonData2> list = StoreJsonData2Generated.getList(parser);

		assertThat(JsonSliceUtil.slicesToString(parser.getSlices()),
				is("[{\"num\":1},{\"num\":2},{\"num\":3},{\"num\":4}]"));

		assertThat(list.size(), is(4));
		assertThat(list.get(0).getOriginal(), is("{\"num\":1}"));
		assertThat(list.get(1).getOriginal(), is("{\"num\":2}"));
		assertThat(list.get(2).getOriginal(), is("{\"num\":3}"));
		assertThat(list.get(3).getOriginal(), is("{\"num\":4}"));
	}
}
