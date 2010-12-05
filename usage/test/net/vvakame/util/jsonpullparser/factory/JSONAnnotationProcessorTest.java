package net.vvakame.util.jsonpullparser.factory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveTypeDataGen;
import net.vvakame.sample.TestData;
import net.vvakame.sample.TestDataGen;
import net.vvakame.util.jsonpullparser.JSONFormatException;
import net.vvakame.util.jsonpullparser.JSONPullParser;

import org.junit.Test;

public class JSONAnnotationProcessorTest {

	@Test
	public void jsonHashParser() throws IOException, JSONFormatException {
		String json = "{\"name\":\"vvakame\",\"package_name\":\"net.vvakame\",\"version_code\":7,\"weight\":66.66,\"has_data\":true}";
		JSONPullParser parser = new JSONPullParser();
		parser.setInput(getStream(json));

		TestData data = TestDataGen.get(parser);

		assertThat(data.getName(), is("vvakame"));
		assertThat(data.getPackageName(), is("net.vvakame"));
		assertThat(data.getVersionCode(), is(7));
		assertThat(data.getWeight(), is(66.66));
		assertThat(data.isHasData(), is(true));
	}

	@Test
	public void jsonHashPrimitive() throws IOException, JSONFormatException {
		String json = "{\"bool\":true,\"c\":\"char\",\"b\":127,\"s\":32767,\"i\":2147483647,\"l\":2147483647,\"f\":3.4028235E38,\"d\":1.7976931348623157E308}";
		JSONPullParser parser = new JSONPullParser();
		parser.setInput(getStream(json));

		PrimitiveTypeData data = PrimitiveTypeDataGen.get(parser);

		assertThat(data.isBool(), is(true));
		assertThat(data.getC(), is('c'));
		assertThat(data.getB(), is(Byte.MAX_VALUE));
		assertThat(data.getS(), is(Short.MAX_VALUE));
		assertThat(data.getI(), is(Integer.MAX_VALUE));
		// TODO Long.MAX_VALUE は対応してない
		assertThat(data.getL(), is((long) Integer.MAX_VALUE));
		assertThat(data.getF(), is(Float.MAX_VALUE));
		assertThat(data.getD(), is(Double.MAX_VALUE));
	}

	public InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
