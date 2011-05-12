package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.vvakame.sample.BuilderData;
import net.vvakame.sample.BuilderDataJsonMeta;
import net.vvakame.sample.SampleEnum;
import net.vvakame.sample.TestData;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonModel#builder()} のテストケース.
 * @author vvakame
 */
public class BuilderTest {

	BuilderDataJsonMeta meta = BuilderDataJsonMeta.get();


	/**
	 * StringとDateのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void nameChange_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setStr("hoge");
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.str.name("hage")).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"hage\":\"hoge\"}"));
	}

	/**
	 * StringとDateのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void nameChange_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"hage\":\"hoge\"}");
		BuilderData data = meta.newBuilder().add(meta.str.name("hage")).fix().get(parser);
		assertThat(data.getStr(), is("hoge"));
	}

	/**
	 * 未知のKeyでエラーが通知されるか
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void treatUnknownKeyAsError_on() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"hage\":\"hoge\"}");
		meta.newBuilder().treatUnknownKeyAsError(true).add(meta.str).fix().get(parser);
	}

	/**
	 * 未知のKeyでエラーが通知されないか
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void treatUnknownKeyAsError_off() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"hage\":\"hoge\"}");
		meta.newBuilder().treatUnknownKeyAsError(false).add(meta.str).fix().get(parser);
	}

	/**
	 * primitiveのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void primitive_encode() throws IOException {
		{
			// boolean true
			BuilderData data = new BuilderData();
			data.setBool(true);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.bool).fix().encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"bool\":true}"));
		}
		{
			// boolean false
			// char, byte, short, int, long
			BuilderData data = new BuilderData();
			data.setBool(false);
			data.setC('F');
			data.setB((byte) 1);
			data.setS((short) 2);
			data.setI(3);
			data.setL(4);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.bool, meta.c, meta.b, meta.s, meta.i, meta.l).fix()
				.encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"bool\":false,\"c\":\"F\",\"b\":1,\"s\":2,\"i\":3,\"l\":4}"));
		}
		{
			// float, double
			BuilderData data = new BuilderData();
			data.setF(3.3f);
			data.setD(4.4);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.f, meta.d).fix().encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"f\":3.3,\"d\":4.4}"));
		}
	}

	/**
	 * primitiveのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void primitive_get() throws IOException, JsonFormatException {
		{
			// boolean true
			JsonPullParser parser = JsonPullParser.newParser("{\"bool\":true}");
			BuilderData data = meta.newBuilder().add(meta.bool).fix().get(parser);
			assertThat(data.isBool(), is(true));
		}
		{
			// boolean false
			// char, byte, short, int, long
			JsonPullParser parser =
					JsonPullParser
						.newParser("{\"bool\":false,\"c\":\"F\",\"b\":1,\"s\":2,\"i\":3,\"l\":4}");
			BuilderData data =
					meta.newBuilder().add(meta.bool, meta.c, meta.b, meta.s, meta.i, meta.l).fix()
						.get(parser);
			assertThat(data.isBool(), is(false));
			assertThat(data.getC(), is('F'));
			assertThat(data.getB(), is((byte) 1));
			assertThat(data.getS(), is((short) 2));
			assertThat(data.getI(), is(3));
			assertThat(data.getL(), is(4L));
		}
		{
			// float, double
			JsonPullParser parser = JsonPullParser.newParser("{\"f\":3.3,\"d\":4.4}");
			BuilderData data = meta.newBuilder().add(meta.f, meta.d).fix().get(parser);
			assertThat(data.getF(), is(3.3f));
			assertThat(data.getD(), is(4.4));
		}
	}

	/**
	 * primitiveのwrapperのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void primitiveWrapper_encode() throws IOException {
		{
			// boolean true
			BuilderData data = new BuilderData();
			data.setwBool(true);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.wBool).fix().encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"w_bool\":true}"));
		}
		{
			// boolean false
			// char, byte, short, int, long
			BuilderData data = new BuilderData();
			data.setwBool(false);
			data.setWc('F');
			data.setWb((byte) 1);
			data.setWs((short) 2);
			data.setWi(3);
			data.setWl(4L);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.wBool, meta.wc, meta.wb, meta.ws, meta.wi, meta.wl).fix()
				.encode(writer, data);
			String json = writer.toString();
			assertThat(json,
					is("{\"w_bool\":false,\"wc\":\"F\",\"wb\":1,\"ws\":2,\"wi\":3,\"wl\":4}"));
		}
		{
			// float, double
			BuilderData data = new BuilderData();
			data.setWf(3.3f);
			data.setWd(4.4);
			StringWriter writer = new StringWriter();
			meta.newBuilder().add(meta.wf, meta.wd).fix().encode(writer, data);
			String json = writer.toString();
			assertThat(json, is("{\"wf\":3.3,\"wd\":4.4}"));
		}
	}

	/**
	 * primitiveのwrapperのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void primitiveWrapper_get() throws IOException, JsonFormatException {
		{
			// boolean true
			JsonPullParser parser = JsonPullParser.newParser("{\"w_bool\":true}");
			BuilderData data = meta.newBuilder().add(meta.wBool).fix().get(parser);
			assertThat(data.getwBool(), is(true));
		}
		{
			// boolean false
			// char, byte, short, int, long
			JsonPullParser parser =
					JsonPullParser
						.newParser("{\"w_bool\":false,\"wc\":\"F\",\"wb\":1,\"ws\":2,\"wi\":3,\"wl\":4}");
			BuilderData data =
					meta.newBuilder().add(meta.wBool, meta.wc, meta.wb, meta.ws, meta.wi, meta.wl)
						.fix().get(parser);
			assertThat(data.getwBool(), is(false));
			assertThat(data.getWc(), is('F'));
			assertThat(data.getWb(), is((byte) 1));
			assertThat(data.getWs(), is((short) 2));
			assertThat(data.getWi(), is(3));
			assertThat(data.getWl(), is(4L));
		}
		{
			// float, double
			JsonPullParser parser = JsonPullParser.newParser("{\"wf\":3.3,\"wd\":4.4}");
			BuilderData data = meta.newBuilder().add(meta.wf, meta.wd).fix().get(parser);
			assertThat(data.getWf(), is(3.3f));
			assertThat(data.getWd(), is(4.4));
		}
	}

	/**
	 * StringとDateのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void strDate_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setStr("hoge");
		data.setDate(new Date(1000000));
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.str, meta.date).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"str\":\"hoge\",\"date\":1000000}"));
	}

	/**
	 * StringとDateのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void strDate_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"str\":\"hoge\",\"date\":1000000}");
		BuilderData data = meta.newBuilder().add(meta.str, meta.date).fix().get(parser);
		assertThat(data.getStr(), is("hoge"));
		assertThat(data.getDate(), is(new Date(1000000)));
	}

	/**
	 * modelのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void model_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setModel(new TestData());
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.model).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(
				json,
				is("{\"model\":{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}}"));
	}

	/**
	 * modelのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void model_get() throws IOException, JsonFormatException {
		JsonPullParser parser =
				JsonPullParser
					.newParser("{\"model\":{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}}");
		BuilderData data = meta.newBuilder().add(meta.model).fix().get(parser);
		assertThat(data.getModel(), notNullValue());
	}

	/**
	 * enumのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void enum_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setOuterEnum(SampleEnum.PRODUCT);
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.outerEnum).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"outer_enum\":\"PRODUCT\"}"));
	}

	/**
	 * enumのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void enum_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"outer_enum\":\"PRODUCT\"}");
		BuilderData data = meta.newBuilder().add(meta.outerEnum).fix().get(parser);
		assertThat(data.getOuterEnum(), is(SampleEnum.PRODUCT));
	}

	/**
	 * listのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void list_encode() throws IOException {
		BuilderData data = new BuilderData();
		List<TestData> list = new ArrayList<TestData>();
		list.add(new TestData());
		data.setList1(list);
		data.setList2(list);
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.list1).add(meta.list2).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(
				json,
				is("{\"list1\":[{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}],\"list2\":[{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}]}"));
	}

	/**
	 * listのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void list_get() throws IOException, JsonFormatException {
		JsonPullParser parser =
				JsonPullParser
					.newParser("{\"list1\":[{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}],\"list2\":[{\"has_data\":false,\"name\":null,\"package_name\":null,\"version_code\":0,\"weight\":0.0}]}");
		BuilderData data = meta.newBuilder().add(meta.list1, meta.list2).fix().get(parser);
		assertThat(data.getList1().size(), is(1));
		assertThat(data.getList2().size(), is(1));
	}

	/**
	 * converterのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void converter_encode() throws IOException {
		BuilderData data = new BuilderData();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		data.setConv(list);
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.conv).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"conv\":[1]}"));
	}

	/**
	 * converterのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void converter_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"conv\":[1,2,[3,4,[5]]]}");
		BuilderData data = meta.newBuilder().add(meta.conv).fix().get(parser);
		assertThat(data.getConv().size(), is(5));
	}

	/**
	 * JsonHashのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void jsonHash_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setJsonHash(new JsonHash());
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.jsonHash).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"json_hash\":{}}"));
	}

	/**
	 * JsonHashのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void jsonHash_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"json_hash\":{}}");
		BuilderData data = meta.newBuilder().add(meta.jsonHash).fix().get(parser);
		assertThat(data.getJsonHash().size(), is(0));
	}

	/**
	 * JsonArrayのencode確認
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void jsonArray_encode() throws IOException {
		BuilderData data = new BuilderData();
		data.setJsonArray(new JsonArray());
		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.jsonArray).fix().encode(writer, data);
		String json = writer.toString();
		assertThat(json, is("{\"json_array\":[]}"));
	}

	/**
	 * JsonArrayのget確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void jsonArray_get() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("{\"json_array\":[]}");
		BuilderData data = meta.newBuilder().add(meta.jsonArray).fix().get(parser);
		assertThat(data.getJsonArray().size(), is(0));
	}

	/**
	 * #encodeList の確認
	 * @author vvakame
	 * @throws IOException 
	 */
	@Test
	public void encodeList() throws IOException {
		BuilderData data;
		List<BuilderData> list = new ArrayList<BuilderData>();
		data = new BuilderData();
		data.setI(1);
		list.add(data);
		data = new BuilderData();
		data.setI(2);
		list.add(data);

		StringWriter writer = new StringWriter();
		meta.newBuilder().add(meta.i).fix().encodeList(writer, list);

		assertThat(writer.toString(), is("[{\"i\":1},{\"i\":2}]"));
	}

	/**
	 * #getList の確認
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test
	public void getList() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("[{\"i\":1},{\"i\":2}]");

		List<BuilderData> list =
				meta.newBuilder().add(meta.i).fix().getList(parser, new OnJsonObjectAddListener() {

					@Override
					public void onAdd(Object obj) {
					}
				});
		assertThat(list.size(), is(2));
	}

	void printJson(String json) {
		System.out.println(json.replaceAll("\"", "\\\\\""));
	}
}
