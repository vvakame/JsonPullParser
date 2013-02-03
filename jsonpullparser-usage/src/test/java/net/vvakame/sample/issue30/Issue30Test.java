package net.vvakame.sample.issue30;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.util.jsonpullparser.builder.JsonModelCoder;

import org.junit.Test;

/**
 * Test for issue 30.
 * @author vvakame
 */
public class Issue30Test {

	/**
	 * Not raise stack over flow.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void test() throws IOException {
		final RecursiveStructureJsonMeta meta = RecursiveStructureJsonMeta.get();

		JsonModelCoder<RecursiveStructure> coder3 = meta.newBuilder().add(meta.c).fix();
		JsonModelCoder<RecursiveStructure> coder2 =
				meta.newBuilder().add(meta.b, meta.self.coder(coder3)).fix();
		JsonModelCoder<RecursiveStructure> coder1 =
				meta.newBuilder().add(meta.a, meta.self.coder(coder2)).fix();

		RecursiveStructure model = new RecursiveStructure();
		model.setA(1);
		model.setB(2);
		model.setC("C");
		model.setSelf(model);

		{
			StringWriter writer = new StringWriter();
			coder3.encode(writer, model);
			System.out.println(writer.toString());
		}
		{
			StringWriter writer = new StringWriter();
			coder2.encode(writer, model);
			System.out.println(writer.toString());
		}
		{
			StringWriter writer = new StringWriter();
			coder1.encode(writer, model);
			System.out.println(writer.toString());
		}
	}
}
