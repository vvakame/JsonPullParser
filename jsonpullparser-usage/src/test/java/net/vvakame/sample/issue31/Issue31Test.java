package net.vvakame.sample.issue31;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.util.jsonpullparser.builder.JsonModelCoder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for issue 31.
 * @author vvakame
 */
public class Issue31Test {

	/**
	 * Test (pseudo) inherit of parent JsonMeta.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void test() throws IOException {
		{
			final BaseDataJsonMeta meta = BaseDataJsonMeta.get();
			JsonModelCoder<BaseData> coder = meta.newBuilder().add(meta.a).fix();

			BaseData model = new BaseData();
			model.setA(1);

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			assertThat(writer.toString(), is("{\"a\":1}"));
		}

		{
			final ExtendDataJsonMeta meta = ExtendDataJsonMeta.get();
			JsonModelCoder<? super ExtendData> coder = meta.newBuilder().add(meta.a, meta.b).fix();

			ExtendData model = new ExtendData();
			model.setA(1);
			model.setB("B");

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			assertThat(writer.toString(), is("{\"a\":1,\"b\":\"B\"}"));
		}
		{
			final ExtendDataJsonMeta meta = ExtendDataJsonMeta.get();
			JsonModelCoder<? super ExtendData> coder = meta.newBuilder().add(meta.b, meta.a).fix();

			ExtendData model = new ExtendData();
			model.setA(1);
			model.setB("B");

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			assertThat(writer.toString(), is("{\"b\":\"B\",\"a\":1}"));
		}
	}
}
