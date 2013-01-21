package net.vvakame.sample.issue28;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.builder.JsonModelCoder;

import org.junit.Test;

/**
 * Test for issue of inherit patter. Issue#28 https://github.com/vvakame/JsonPullParser/issues/28
 * @author vvakame
 */
public class Issue28Test {

	/**
	 * Test for issue28. do not raise NPE.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void test() throws IOException, JsonFormatException {
		Issue28 obj = new Issue28();
		{
			StringWriter writer = new StringWriter();
			Issue28Generated.encode(writer, obj);

			Issue28Generated.get(writer.toString());
		}
		{
			Issue28JsonMeta meta = Issue28JsonMeta.get();
			JsonModelCoder<Issue28> coder = meta.newBuilder().fix();

			StringWriter writer = new StringWriter();
			coder.encode(writer, obj);

			coder.get(writer.toString());
		}
	}
}
