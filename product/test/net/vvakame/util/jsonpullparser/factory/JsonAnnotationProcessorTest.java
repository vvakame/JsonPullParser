package net.vvakame.util.jsonpullparser.factory;

import net.vvakame.sample.ComplexData;
import net.vvakame.sample.TestData;
import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;

import org.seasar.aptina.unit.AptinaTestCase;

public class JsonAnnotationProcessorTest extends AptinaTestCase {

	public void test() throws Exception {
		JsonAnnotationProcessor processor = new JsonAnnotationProcessor();
		addProcessor(processor);

		addCompilationUnit(TestData.class);
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
		addSourcePath("test");
		setCharset("utf-8");
	}
}
