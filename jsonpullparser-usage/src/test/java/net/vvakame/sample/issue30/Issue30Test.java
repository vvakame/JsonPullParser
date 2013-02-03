package net.vvakame.sample.issue30;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.builder.JsonCoderRouter;
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
	}

	/**
	 * Test router.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void router_singleProperty() throws IOException {
		final RecursiveStructureJsonMeta meta = RecursiveStructureJsonMeta.get();

		final JsonCoderRouter<RecursiveStructure> router =
				new JsonCoderRouter<RecursiveStructure>() {

					@Override
					public JsonModelCoder<RecursiveStructure> resolve(RecursiveStructure obj) {
						if (obj.getA() == 0) {
							return meta.newBuilder().add(meta.a).fix();
						} else if (obj.getA() == 1) {
							return meta.newBuilder().add(meta.b).fix();
						} else if (obj.getA() == 2) {
							return meta.newBuilder().add(meta.c).fix();
						} else {
							return meta.newBuilder().add(meta.self).fix();
						}
					}
				};

		RecursiveStructure model = new RecursiveStructure();
		model.setB(2);
		model.setC("C");
		model.setSelf(new RecursiveStructure());

		{
			model.setA(0);
			JsonModelCoder<RecursiveStructure> coder = router.resolve(model);

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			System.out.println(writer.toString());
			assertThat(writer.toString(), is("{\"a\":0}"));
		}
		{
			model.setA(1);
			JsonModelCoder<RecursiveStructure> coder = router.resolve(model);

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			System.out.println(writer.toString());
			assertThat(writer.toString(), is("{\"b\":2}"));
		}
		{
			model.setA(2);
			JsonModelCoder<RecursiveStructure> coder = router.resolve(model);

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			System.out.println(writer.toString());
			assertThat(writer.toString(), is("{\"c\":\"C\"}"));
		}
		{
			model.setA(3);
			JsonModelCoder<RecursiveStructure> coder =
					meta.newBuilder().add(meta.self.router(router)).fix();

			StringWriter writer = new StringWriter();
			coder.encode(writer, model);
			System.out.println(writer.toString());
			assertThat(writer.toString(), is("{\"self\":{\"a\":0}}"));
		}
	}

	/**
	 * Test router.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void router_listProperty() throws IOException {
		PrimitiveList model = new PrimitiveList();
		List<RecursiveStructure> list = new ArrayList<RecursiveStructure>();
		{
			RecursiveStructure child = new RecursiveStructure();
			child.setA(0);
			list.add(child);
		}
		{
			RecursiveStructure child = new RecursiveStructure();
			child.setA(1);
			list.add(child);
		}
		{
			RecursiveStructure child = new RecursiveStructure();
			child.setA(2);
			list.add(child);
		}
		{
			RecursiveStructure child = new RecursiveStructure();
			child.setA(3);
			list.add(child);
		}
		model.setRecursiveList(list);

		final PrimitiveListJsonMeta meta = PrimitiveListJsonMeta.get();
		final RecursiveStructureJsonMeta childMeta = RecursiveStructureJsonMeta.get();

		final JsonCoderRouter<RecursiveStructure> router =
				new JsonCoderRouter<RecursiveStructure>() {

					@Override
					public JsonModelCoder<RecursiveStructure> resolve(RecursiveStructure obj) {
						if (obj.getA() == 0) {
							return childMeta.newBuilder().add(childMeta.a).fix();
						} else if (obj.getA() == 1) {
							return childMeta.newBuilder().add(childMeta.b).fix();
						} else if (obj.getA() == 2) {
							return childMeta.newBuilder().add(childMeta.c).fix();
						} else {
							return childMeta.newBuilder().add(childMeta.self).fix();
						}
					}
				};
		JsonModelCoder<PrimitiveList> coder =
				meta.newBuilder().add(meta.recursiveList.router(router)).fix();
		StringWriter writer = new StringWriter();
		coder.encode(writer, model);

		assertThat(writer.toString(),
				is("{\"recursiveList\":[{\"a\":0},{\"b\":0},{\"c\":null},{\"self\":null}]}"));
	}
}
