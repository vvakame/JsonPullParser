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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.vvakame.sample.BaseData;
import net.vvakame.sample.BaseDataGenerated;
import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ComplexData.InternalEnum;
import net.vvakame.sample.ComplexDataGenerated;
import net.vvakame.sample.ContainsAnotherPackageObjectData;
import net.vvakame.sample.ContainsAnotherPackageObjectDataGenerated;
import net.vvakame.sample.ContainsAnotherPackageObjectDataJsonMeta;
import net.vvakame.sample.ExtendsData1;
import net.vvakame.sample.ExtendsData1Generated;
import net.vvakame.sample.ExtendsData2;
import net.vvakame.sample.ExtendsData2Generated;
import net.vvakame.sample.ForInnerClassData;
import net.vvakame.sample.ForInnerClassData.InnerClassA.InnerClassAB;
import net.vvakame.sample.ForInnerClassData.InnerClassB;
import net.vvakame.sample.ForInnerClassDataGenerated;
import net.vvakame.sample.ForInnerClassDataJsonMeta;
import net.vvakame.sample.InnerClassABGenerated;
import net.vvakame.sample.InnerClassABJsonMeta;
import net.vvakame.sample.InnerClassBGenerated;
import net.vvakame.sample.InnerClassBJsonMeta;
import net.vvakame.sample.MiniData;
import net.vvakame.sample.MiniDataGenerated;
import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveTypeDataGenerated;
import net.vvakame.sample.PrimitiveWrapperListData;
import net.vvakame.sample.PrimitiveWrapperListDataGenerated;
import net.vvakame.sample.SampleEnum;
import net.vvakame.sample.TestData;
import net.vvakame.sample.TestDataGenerated;
import net.vvakame.sample.anotherpackage.AnotherPackageClass;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.TweetGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.builder.JsonModelCoder;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test case for JsonAnnotationProcessor generated codes.
 * @author vvakame
 */
public class JsonAnnotationProcessorTest {

	/**
	 * Tests for the parsing of hashes.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
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

	/**
	 * Tests for the parsing of primitives.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
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

	/**
	 * Tests for the parsing of arrays.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void jsonArray() throws IOException, JsonFormatException {
		String json =
				"[{\"bool\":false,\"c\":\"CHAR\",\"b\":-128,\"s\":1,\"i\":1,\"l\":1,\"f\":1,\"d\":1}, {\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":2147483647,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}]";

		JsonPullParser parser = JsonPullParser.newParser(json);

		List<PrimitiveTypeData> list = PrimitiveTypeDataGenerated.getList(parser);

		assertThat(list.size(), is(2));
	}

	/**
	 * Tests for the parsing of hairy hashes.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void jsonHashComplex() throws IOException, JsonFormatException {
		String tmpl =
				"{\"name\":\"%s\",\"package_name\":\"%s\",\"version_code\":%d,\"weight\":%f,\"has_data\":%b}";
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{\"name\":\"hoge\",");
		jsonBuilder.append("\"date\":12345678,");
		jsonBuilder.append("\"list1\":[");
		jsonBuilder.append(String.format(tmpl, "a1", "ho.ge", 1, 2.2, true)).append(",");
		jsonBuilder.append(String.format(tmpl, "a2", "fu.ga", 2, 3.2, false)).append(",");
		jsonBuilder.append(String.format(tmpl, "a3", "ho.ge", 1, 2.2, true)).append("],");
		jsonBuilder.append("\"list2\":[");
		jsonBuilder.append(String.format(tmpl, "b1", "th.is", 10, 11.11, true)).append("],");
		jsonBuilder.append("\"list3\":[],");
		jsonBuilder.append("\"data\":");
		jsonBuilder.append(String.format(tmpl, "c1", "fi.zz", 111, 0.01, false)).append(",");
		jsonBuilder.append("\"innerEnum\":\"TEST2\",");
		jsonBuilder.append("\"outerEnum\":\"PRODUCT\"}");

		JsonPullParser parser = JsonPullParser.newParser(jsonBuilder.toString());

		ComplexData data = ComplexDataGenerated.get(parser);

		assertThat(data.getName(), is("hoge"));
		assertThat(data.getDate(), is(new Date(12345678)));
		assertThat(data.getList1().size(), is(3));
		assertThat(data.getList2().size(), is(1));
		assertThat(data.getList3().size(), is(0));
		assertThat(data.getData().getPackageName(), is("fi.zz"));
		assertThat(data.getInnerEnum(), is(InternalEnum.TEST2));
		assertThat(data.getOuterEnum(), is(SampleEnum.PRODUCT));
	}

	/**
	 * Tests for the parsing of hairy hashes.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
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

	/**
	 * Tests for the parsing with inherited models.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void inheritModel() throws IllegalStateException, IOException, JsonFormatException {
		{
			String json = "{\"one\":1,\"two\":2}";
			BaseData data = BaseDataGenerated.get(json);

			assertThat(data.getOne(), is(1L));
			assertThat(data.getTwo(), is(2L));
		}

		{
			String json = "{\"one\":1,\"two\":2,\"three\":3,\"four\":4}";
			ExtendsData1 data = ExtendsData1Generated.get(json);

			assertThat(data.getOne(), is(1L));
			assertThat(data.getTwo(), is(2L));
			assertThat(data.getThree(), is(3L));
			assertThat(data.getFour(), is(4L));
		}

		{
			String json = "{\"one\":1,\"two\":2,\"three\":3,\"four\":4,\"five\":5}";
			ExtendsData2 data = ExtendsData2Generated.get(json);

			assertThat(data.getOne(), is(1L));
			assertThat(data.getTwo(), is(2L));
			assertThat(data.getThree(), is(3L));
			assertThat(data.getFour(), is(4L));
			assertThat(data.getFive(), is(5L));

			StringWriter writer = new StringWriter();
			ExtendsData2Generated.encode(writer, data);

			data = ExtendsData2Generated.get(writer.toString());

			assertThat(data.getOne(), is(1L));
			assertThat(data.getTwo(), is(2L));
			assertThat(data.getThree(), is(3L));
			assertThat(data.getFour(), is(4L));
			assertThat(data.getFive(), is(5L));
		}
	}

	/**
	 * Tests for the encode method family.
	 * @throws IOException 
	 */
	@Test
	public void encode() throws IOException {
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MiniData data = new MiniData();
			data.setId(7);
			MiniDataGenerated.encode(outputStream, data);
			String json = new String(outputStream.toByteArray());
			assertThat(json, is("{\"id\":7}"));
		}

		{
			StringWriter writer = new StringWriter();
			MiniData data = new MiniData();
			data.setId(7);
			MiniDataGenerated.encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"id\":7}"));
		}

		{
			StringWriter writer = new StringWriter();
			MiniDataGenerated.encodeNullToBlank(writer, null);
			String json = writer.toString();
			assertThat(json, is("{}"));
		}

		{
			StringWriter writer = new StringWriter();
			MiniDataGenerated.encodeNullToNull(writer, null);
			String json = writer.toString();
			assertThat(json, is("null"));
		}

		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			List<MiniData> list = new ArrayList<MiniData>();
			MiniData data;
			data = new MiniData();
			data.setId(7);
			list.add(data);
			data = new MiniData();
			data.setId(8);
			list.add(data);

			MiniDataGenerated.encodeList(outputStream, list);
			String json = new String(outputStream.toByteArray());
			assertThat(json, is("[{\"id\":7},{\"id\":8}]"));
		}

		{
			StringWriter writer = new StringWriter();
			List<MiniData> list = new ArrayList<MiniData>();
			MiniData data;
			data = new MiniData();
			data.setId(7);
			list.add(data);
			data = new MiniData();
			data.setId(8);
			list.add(data);

			MiniDataGenerated.encodeList(writer, list);
			String json = writer.toString();
			assertThat(json, is("[{\"id\":7},{\"id\":8}]"));
		}

		{
			StringWriter writer = new StringWriter();
			MiniDataGenerated.encodeListNullToBlank(writer, null);
			String json = writer.toString();
			assertThat(json, is("[]"));
		}

		{
			StringWriter writer = new StringWriter();
			MiniDataGenerated.encodeListNullToNull(writer, null);
			String json = writer.toString();
			assertThat(json, is("null"));
		}
	}

	/**
	 * Tests for the encode method family.
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void encodeAnyTypes() throws IOException, JsonFormatException {
		ComplexData data = new ComplexData();
		data.setDate(new Date(1234567));
		data.setInnerEnum(InternalEnum.TEST1);
		data.setOuterEnum(SampleEnum.PRODUCT);
		data.setDateList(Arrays.asList(new Date(1111), new Date(2222)));
		data.setInnerEnumList(Arrays.asList(InternalEnum.TEST1, InternalEnum.TEST2));
		data.setOuterEnumList(Arrays.asList(SampleEnum.PRODUCT, SampleEnum.TEST));

		StringWriter writer = new StringWriter();
		ComplexDataGenerated.encode(writer, data);
		String json = writer.toString();
		JsonHash hash = JsonHash.fromString(json);
		assertThat(hash.getLongOrNull("date"), is(1234567L));
		assertThat(hash.getStringOrNull("innerEnum"), is("TEST1"));
		assertThat(hash.getStringOrNull("outerEnum"), is("PRODUCT"));

		assertThat(hash.getJsonArrayOrNull("dateList").size(), is(2));
		assertThat(hash.getJsonArrayOrNull("dateList").getLongOrNull(0), is(1111L));
		assertThat(hash.getJsonArrayOrNull("dateList").getLongOrNull(1), is(2222L));

		assertThat(hash.getJsonArrayOrNull("innerEnumList").size(), is(2));
		assertThat(hash.getJsonArrayOrNull("innerEnumList").getStringOrNull(0), is("TEST1"));
		assertThat(hash.getJsonArrayOrNull("innerEnumList").getStringOrNull(1), is("TEST2"));

		assertThat(hash.getJsonArrayOrNull("outerEnumList").size(), is(2));
		assertThat(hash.getJsonArrayOrNull("outerEnumList").getStringOrNull(0), is("PRODUCT"));
		assertThat(hash.getJsonArrayOrNull("outerEnumList").getStringOrNull(1), is("TEST"));
	}

	/**
	 * TODO テストを適当な所に移す.<br>
	 * Tests for the list of PrimitiveWrapper.
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void primitiveWrapperList() throws IOException, IllegalStateException,
			JsonFormatException {
		PrimitiveWrapperListData data1 = new PrimitiveWrapperListData();
		data1.setB(Arrays.asList(new Byte[] {
			1,
			2,
			3
		}));
		data1.setBool(Arrays.asList(new Boolean[] {
			true,
			false
		}));
		data1.setC(Arrays.asList(new Character[] {
			'a',
			'b',
			'c'
		}));
		data1.setD(Arrays.asList(new Double[] {
			1.1,
			2.2,
			3.3
		}));
		data1.setDate(Arrays.asList(new Date[] {
			new Date(111111),
			new Date(222222),
			new Date(333333)
		}));
		data1.setEnums(Arrays.asList(new SampleEnum[] {
			SampleEnum.PRODUCT,
			SampleEnum.TEST
		}));
		data1.setF(Arrays.asList(new Float[] {
			4.4F,
			5.5F,
			6.6F
		}));
		data1.setI(Arrays.asList(new Integer[] {
			4,
			5,
			6
		}));
		data1.setL(Arrays.asList(new Long[] {
			7L,
			8L,
			9L
		}));
		data1.setS(Arrays.asList(new Short[] {
			10,
			11,
			12
		}));
		data1.setStr(Arrays.asList(new String[] {
			"abc",
			"def"
		}));

		StringWriter writer = new StringWriter();
		PrimitiveWrapperListDataGenerated.encode(writer, data1);

		String json = writer.toString();

		PrimitiveWrapperListData data2 = PrimitiveWrapperListDataGenerated.get(json);

		assertThat(data2.getB(), is(data1.getB()));
		assertThat(data2.getBool(), is(data1.getBool()));
		assertThat(data2.getC(), is(data1.getC()));
		assertThat(data2.getD(), is(data1.getD()));
		assertThat(data2.getDate(), is(data1.getDate()));
		assertThat(data2.getEnums(), is(data1.getEnums()));
		assertThat(data2.getF(), is(data1.getF()));
		assertThat(data2.getI(), is(data1.getI()));
		assertThat(data2.getL(), is(data1.getL()));
		assertThat(data2.getS(), is(data1.getS()));
		assertThat(data2.getStr(), is(data1.getStr()));
	}

	/**
	 * TODO テストを適当な所に移す.<br>
	 * Tests for the inner class by Gen.
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void innerClassGen() throws IOException, JsonFormatException {
		{ // normal
			ForInnerClassData data = new ForInnerClassData();
			data.setA(1);
			StringWriter writer = new StringWriter();
			ForInnerClassDataGenerated.encode(writer, data);
			assertThat("{\"a\":1}", is(writer.toString()));
		}
		{ // grandchild
			InnerClassAB data = new InnerClassAB();
			data.setC(2);
			StringWriter writer = new StringWriter();
			InnerClassABGenerated.encode(writer, data);
			assertThat("{\"c\":2}", is(writer.toString()));

			data = InnerClassABGenerated.get(writer.toString());
			assertThat(data.getC(), is(2));
		}
		{ // child
			InnerClassB data = new InnerClassB();
			data.setD(new InnerClassAB());
			List<InnerClassAB> list = new ArrayList<InnerClassAB>();
			list.add(new InnerClassAB());
			data.setE(list);
			StringWriter writer = new StringWriter();
			InnerClassBGenerated.encode(writer, data);
			System.out.println(writer.toString());
			assertThat("{\"d\":{\"c\":0},\"e\":[{\"c\":0}]}", is(writer.toString()));

			data = InnerClassBGenerated.get(writer.toString());
			assertThat(data.getD(), notNullValue());
			assertThat(data.getE().size(), is(1));
		}
	}

	/**
	 * TODO テストを適当な所に移す.<br>
	 * Tests for the inner class by JsonMeta.
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void innerClassMeta() throws IOException, JsonFormatException {
		{ // normal
			ForInnerClassData data = new ForInnerClassData();
			data.setA(1);
			StringWriter writer = new StringWriter();
			ForInnerClassDataJsonMeta.get().newBuilder().addAll().fix().encode(writer, data);
			assertThat("{\"a\":1}", is(writer.toString()));
		}
		{ // grandchild
			JsonModelCoder<InnerClassAB> coder =
					InnerClassABJsonMeta.get().newBuilder().addAll().fix();
			InnerClassAB data = new InnerClassAB();
			data.setC(2);
			StringWriter writer = new StringWriter();
			coder.encode(writer, data);
			assertThat("{\"c\":2}", is(writer.toString()));

			data = coder.get(writer.toString());
			assertThat(data.getC(), is(2));
		}
		{ // child
			JsonModelCoder<InnerClassB> coder =
					InnerClassBJsonMeta.get().newBuilder().addAll().fix();
			InnerClassB data = new InnerClassB();
			data.setD(new InnerClassAB());
			List<InnerClassAB> list = new ArrayList<InnerClassAB>();
			list.add(new InnerClassAB());
			data.setE(list);
			StringWriter writer = new StringWriter();
			coder.encode(writer, data);
			System.out.println(writer.toString());
			assertThat("{\"d\":{\"c\":0},\"e\":[{\"c\":0}]}", is(writer.toString()));

			data = coder.get(writer.toString());
			assertThat(data.getD(), notNullValue());
			assertThat(data.getE().size(), is(1));
		}
	}

	/**
	 * TODO テストを適当な所に移す.<br>
	 * Tests for member has class on another package by Gen.
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void anotherPackageClassGen() throws IOException, JsonFormatException {
		ContainsAnotherPackageObjectData data = new ContainsAnotherPackageObjectData();
		data.setAnotherPackageClass(new AnotherPackageClass());
		StringWriter writer = new StringWriter();
		ContainsAnotherPackageObjectDataGenerated.encode(writer, data);
		assertThat("{\"anotherPackageClass\":{\"value\":null},\"anotherPackageClasses\":null}",
				is(writer.toString()));
	}

	/**
	 * TODO テストを適当な所に移す.<br>
	 * Tests for member has class on another package by JsonMeta.
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void anotherPackageClassMeta() throws IOException, JsonFormatException {
		ContainsAnotherPackageObjectData data = new ContainsAnotherPackageObjectData();
		data.setAnotherPackageClass(new AnotherPackageClass());
		StringWriter writer = new StringWriter();
		ContainsAnotherPackageObjectDataJsonMeta.get().newBuilder().addAll().fix()
			.encode(writer, data);
		assertThat("{\"anotherPackageClass\":{\"value\":null},\"anotherPackageClasses\":null}",
				is(writer.toString()));
	}

	/**
	 * We can read Twitter timelines as well! #1: to POJO
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
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

	/**
	 * We can read Twitter timelines as well! #2: to JsonArray
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
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

	InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
