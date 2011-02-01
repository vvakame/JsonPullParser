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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ComplexDataGenerated;
import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveTypeDataGenerated;
import net.vvakame.sample.TestData;
import net.vvakame.sample.TestDataGenerated;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.TweetGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;

import org.junit.Test;

public class JsonAnnotationProcessorTest {

	@Test
	public void jsonHash() throws IOException, JsonFormatException {
		String json =
				"{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";

		JsonPullParser parser = JsonPullParser.newParser(json);

		TestData data = TestDataGenerated.get(parser);

		assertThat(data.getName(), is("vvakame"));
		assertThat(data.getPackageName(), is("net.vvakame"));
		assertThat(data.getVersionCode(), is(7));
		assertThat(data.getWeight(), is(66.66));
		assertThat(data.isHasData(), is(true));
	}

	@Test
	public void jsonHashPrimitive() throws IOException, JsonFormatException {
		String json =
				"{\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":9223372036854775807,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}";

		JsonPullParser parser = JsonPullParser.newParser(json);

		PrimitiveTypeData data = PrimitiveTypeDataGenerated.get(parser);

		assertThat(data.isBool(), is(true));
		assertThat(data.getC(), is('c'));
		assertThat(data.getB(), is(Byte.MAX_VALUE));
		assertThat(data.getS(), is(Short.MAX_VALUE));
		assertThat(data.getI(), is(Integer.MAX_VALUE));
		assertThat(data.getL(), is(Long.MAX_VALUE));
		assertThat(data.getF(), is(Float.MAX_VALUE));
		assertThat(data.getD(), is(Double.MAX_VALUE));
	}

	@Test
	public void jsonArray() throws IOException, JsonFormatException {
		String json =
				"[{\"bool\":false,\"c\":\"CHAR\",\"b\":-128,\"s\":1,\"i\":1,\"l\":1,\"f\":1,\"d\":1}, {\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":2147483647,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}]";

		JsonPullParser parser = JsonPullParser.newParser(json);

		List<PrimitiveTypeData> list = PrimitiveTypeDataGenerated.getList(parser);

		assertThat(list.size(), is(2));
	}

	@Test
	public void jsonHashComplex() throws IOException, JsonFormatException {
		String tmpl =
				"{\"name\":\"%s\",\"package_name\":\"%s\",\"version_code\":%d,\"weight\":%f,\"has_data\":%b}";
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{\"name\":\"hoge\",");
		jsonBuilder.append("\"list1\":[");
		jsonBuilder.append(String.format(tmpl, "a1", "ho.ge", 1, 2.2, true)).append(",");
		jsonBuilder.append(String.format(tmpl, "a2", "fu.ga", 2, 3.2, false)).append(",");
		jsonBuilder.append(String.format(tmpl, "a3", "ho.ge", 1, 2.2, true)).append("],");
		jsonBuilder.append("\"list2\":[");
		jsonBuilder.append(String.format(tmpl, "b1", "th.is", 10, 11.11, true)).append("],");
		jsonBuilder.append("\"list3\":[],");
		jsonBuilder.append("\"data\":");
		jsonBuilder.append(String.format(tmpl, "c1", "fi.zz", 111, 0.01, false)).append("}");

		JsonPullParser parser = JsonPullParser.newParser(jsonBuilder.toString());

		ComplexData data = ComplexDataGenerated.get(parser);

		assertThat(data.getName(), is("hoge"));
		assertThat(data.getList1().size(), is(3));
		assertThat(data.getList2().size(), is(1));
		assertThat(data.getList3().size(), is(0));
		assertThat(data.getData().getPackageName(), is("fi.zz"));
	}

	@Test
	public void jsonHashComplexWithNull() throws IOException, JsonFormatException {
		String tmpl =
				"{\"name\":\"%s\",\"package_name\":\"%s\",\"version_code\":%d,\"weight\":%f,\"has_data\":%b}";
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{\"name\":\"hoge\",");
		jsonBuilder.append("\"list1\":null,");
		jsonBuilder.append("\"list2\":[");
		jsonBuilder.append(String.format(tmpl, "b1", "th.is", 10, 11.11, true)).append("],");
		jsonBuilder.append("\"list3\":[],");
		jsonBuilder.append("\"data\":null}");

		JsonPullParser parser = JsonPullParser.newParser(jsonBuilder.toString());

		ComplexData data = ComplexDataGenerated.get(parser);

		assertThat(data.getName(), is("hoge"));
		assertThat(data.getList1(), is(nullValue()));
		assertThat(data.getList2().size(), is(1));
		assertThat(data.getList3().size(), is(0));
		assertThat(data.getData(), is(nullValue()));
	}

	@Test
	public void twitterPublicTimelinePOJO() throws IOException, JsonFormatException {
		final String PUBLIC_TIMELINE_URL = "http://api.twitter.com/1/statuses/public_timeline.json";

		URL url = new URL(PUBLIC_TIMELINE_URL);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			String json;
			{
				StringBuilder builder = new StringBuilder();
				BufferedReader br =
						new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				json = builder.toString();
			}
			System.out.println(json);

			JsonPullParser parser = JsonPullParser.newParser(json);
			List<Tweet> list = TweetGenerated.getList(parser);
			list.toString();
		} finally {
			urlConnection.disconnect();
		}

	}

	@Test
	public void twitterPublicTimelineJson() throws IOException, JsonFormatException {
		final String PUBLIC_TIMELINE_URL = "http://api.twitter.com/1/statuses/public_timeline.json";

		URL url = new URL(PUBLIC_TIMELINE_URL);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			String json;
			{
				StringBuilder builder = new StringBuilder();
				BufferedReader br =
						new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					builder.append(line).append("\n");
				}
				json = builder.toString();
			}
			System.out.println(json);

			JsonPullParser parser = JsonPullParser.newParser(json);
			JsonArray jsonArray = JsonArray.fromParser(parser);
			jsonArray.toString();
		} finally {
			urlConnection.disconnect();
		}

	}

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}