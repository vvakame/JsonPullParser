package net.vvakame.util.jsonpullparser.factory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ComplexDataGenerated;
import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveTypeDataGenerated;
import net.vvakame.sample.TestData;
import net.vvakame.sample.TestDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

import org.junit.Test;

public class JsonAnnotationProcessorTest {

	@Test
	public void jsonHash() throws IOException, JsonFormatException {
		String json = "{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";

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
		String json = "{\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":9223372036854775807,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}";

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
		String json = "[{\"bool\":false,\"c\":\"CHAR\",\"b\":-128,\"s\":1,\"i\":1,\"l\":1,\"f\":1,\"d\":1}, {\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":2147483647,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}]";

		JsonPullParser parser = JsonPullParser.newParser(json);

		List<PrimitiveTypeData> list = PrimitiveTypeDataGenerated
				.getList(parser);

		assertThat(list.size(), is(2));
	}

	@Test
	public void jsonHashComplex() throws IOException, JsonFormatException {
		String tmpl = "{\"name\":\"%s\",\"package_name\":\"%s\",\"version_code\":%d,\"weight\":%f,\"has_data\":%b}";
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("{\"name\":\"hoge\",");
		jsonBuilder.append("\"list1\":[");
		jsonBuilder.append(String.format(tmpl, "a1", "ho.ge", 1, 2.2, true))
				.append(",");
		jsonBuilder.append(String.format(tmpl, "a2", "fu.ga", 2, 3.2, false))
				.append(",");
		jsonBuilder.append(String.format(tmpl, "a3", "ho.ge", 1, 2.2, true))
				.append("],");
		jsonBuilder.append("\"list2\":[");
		jsonBuilder.append(String.format(tmpl, "b1", "th.is", 10, 11.11, true))
				.append("],");
		jsonBuilder.append("\"list3\":[],");
		jsonBuilder.append("\"data\":");
		jsonBuilder
				.append(String.format(tmpl, "c1", "fi.zz", 111, 0.01, false))
				.append("}");

		JsonPullParser parser = JsonPullParser
				.newParser(jsonBuilder.toString());

		ComplexData data = ComplexDataGenerated.get(parser);

		assertThat(data.getName(), is("hoge"));
		assertThat(data.getList1().size(), is(3));
		assertThat(data.getList2().size(), is(1));
		assertThat(data.getList3().size(), is(0));
		assertThat(data.getData().getPackageName(), is("fi.zz"));
	}

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
