package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.sample.ComplexData;
import net.vvakame.sample.ConverterData;
import net.vvakame.sample.SampleData;
import net.vvakame.sample.twitter.Place;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;

import org.seasar.aptina.unit.AptinaTestCase;

public class JsonAnnotationProcessorTest extends AptinaTestCase {

	public void testForSampleData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);

		compile();
	}

	public void testForComplexData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);
		addCompilationUnit(ComplexData.class);

		compile();
	}

	public void testForConverterData() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(ConverterData.class);

		compile();
	}

	public void testForTwitter() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(Tweet.class);
		addCompilationUnit(User.class);
		addCompilationUnit(Place.class);

		compile();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// ソースパスを追加
		addSourcePath("src/test/java");
		setCharset("utf-8");
	}
}
