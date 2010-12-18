package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.sample.ComplexData;
import net.vvakame.sample.SampleData;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;

import org.seasar.aptina.unit.AptinaTestCase;

public class JsonAnnotationProcessorTest extends AptinaTestCase {

	public void test() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(SampleData.class);
		addCompilationUnit(ComplexData.class);

		compile();
	}

	public void testForTwitter() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(Tweet.class);
		addCompilationUnit(User.class);

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
