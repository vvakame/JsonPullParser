package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.sample.ComplexData;

import org.seasar.aptina.unit.AptinaTestCase;

public class JsonAnnotationProcessorTest extends AptinaTestCase {

	public void test() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ComplexData.class);

		compile();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// ソースパスを追加
		addSourcePath("test");
		setCharset("utf-8");
	}
}
