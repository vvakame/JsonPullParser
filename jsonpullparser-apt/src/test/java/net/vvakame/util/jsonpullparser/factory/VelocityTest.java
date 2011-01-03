package net.vvakame.util.jsonpullparser.factory;

import java.io.StringWriter;

import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;
import net.vvakame.util.jsonpullparser.factory.JsonElement.Kind;
import net.vvakame.util.jsonpullparser.factory.velocity.Velocity;

import org.junit.Test;

public class VelocityTest {
	@Test
	public void test() {
		GeneratingModel model = new GeneratingModel();
		model.addImport(User.class.getCanonicalName());
		model.packageName = "net.vvakame";
		model.setTarget(Tweet.class.getSimpleName());
		model.postfix = "Gen";

		JsonElement element;

		element = new JsonElement();
		element.setKey("id");
		element.setSetter("setId");
		element.setKind(Kind.STRING);
		element.setFirst(true);
		model.elements.add(element);

		element = new JsonElement();
		element.setKey("id");
		element.setSetter("setId");
		element.setKind(Kind.BOOLEAN);
		element.setFirst(false);
		model.elements.add(element);

		StringWriter out = new StringWriter();
		Velocity.write(out, model);
		out.flush();
		System.out.println(out.toString());
	}
}
