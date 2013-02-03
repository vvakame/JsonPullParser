package net.vvakame.sample.issue30;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.builder.JsonModelCoder;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for issue 30.
 * @author vvakame
 */
public class Issue30Test {

	/**
	 * Not raise stack over flow.
	 * @throws IOException
	 * @author vvakame
	 * @throws JsonFormatException 
	 */
	@Test
	public void recursiveStructure() throws IOException, JsonFormatException {
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

		String json;
		{
			StringWriter writer = new StringWriter();
			coder3.encode(writer, model);
		}
		{
			StringWriter writer = new StringWriter();
			coder2.encode(writer, model);
		}
		{
			StringWriter writer = new StringWriter();
			coder1.encode(writer, model);

			json = writer.toString();
		}
		model = coder1.get(json);

		assertThat(model.getA(), is(1));
		assertThat(model.getB(), is(0L));
		assertThat(model.getC(), nullValue());

		assertThat(model.getSelf().getA(), is(0));
		assertThat(model.getSelf().getB(), is(2L));
		assertThat(model.getSelf().getC(), nullValue());

		assertThat(model.getSelf().getSelf().getA(), is(0));
		assertThat(model.getSelf().getSelf().getB(), is(0L));
		assertThat(model.getSelf().getSelf().getC(), is("C"));
		assertThat(model.getSelf().getSelf().getSelf(), nullValue());
	}

	/**
	 * Test list property.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void primitiveList() throws IOException {
		PrimitiveList model = new PrimitiveList();

		List<Integer> intList = new ArrayList<Integer>();
		intList.add(1);
		intList.add(2);
		model.setIntList(intList);

		RecursiveStructure recursive = new RecursiveStructure();
		recursive.setA(1);
		recursive.setB(2);
		recursive.setC("C");
		recursive.setSelf(recursive);
		List<RecursiveStructure> recursiveList = new ArrayList<RecursiveStructure>();
		recursiveList.add(recursive);
		model.setRecursiveList(recursiveList);

		final RecursiveStructureJsonMeta recMeta = RecursiveStructureJsonMeta.get();
		JsonModelCoder<RecursiveStructure> recCoder =
				recMeta.newBuilder().add(recMeta.a, recMeta.b, recMeta.c).fix();
		final PrimitiveListJsonMeta meta = PrimitiveListJsonMeta.get();
		JsonModelCoder<PrimitiveList> coder =
				meta.newBuilder().add(meta.intList).add(meta.recursiveList.coder(recCoder)).fix();

		StringWriter writer = new StringWriter();
		coder.encode(writer, model);
		System.out.println(writer.toString());
	}
}
