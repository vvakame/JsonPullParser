package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.sample.BuilderData;
import net.vvakame.sample.BuilderDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonModel#builder()} のテストケース.
 * @author vvakame
 */
public class BuilderTest {

	BuilderDataGenerated meta = BuilderDataGenerated.get();


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
	 * primitiveのdecode確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void primitive_decode() throws IOException, JsonFormatException {
		{
			// boolean true
			JsonPullParser parser = JsonPullParser.newParser("{\"bool\":true}");
			BuilderData data = meta.newBuilder().add(meta.bool).fix().decode(parser);
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
						.decode(parser);
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
			BuilderData data = meta.newBuilder().add(meta.f, meta.d).fix().decode(parser);
			assertThat(data.getF(), is(3.3f));
			assertThat(data.getD(), is(4.4));
		}
	}

	/**
	 * primitiveのencode確認
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
	 * primitiveのdecode確認
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void primitiveWrapper_decode() throws IOException, JsonFormatException {
		{
			// boolean true
			JsonPullParser parser = JsonPullParser.newParser("{\"w_bool\":true}");
			BuilderData data = meta.newBuilder().add(meta.wBool).fix().decode(parser);
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
						.fix().decode(parser);
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
			BuilderData data = meta.newBuilder().add(meta.wf, meta.wd).fix().decode(parser);
			assertThat(data.getWf(), is(3.3f));
			assertThat(data.getWd(), is(4.4));
		}
	}

	void printJson(String json) {
		System.out.println(json.replaceAll("\"", "\\\\\""));
	}
}
