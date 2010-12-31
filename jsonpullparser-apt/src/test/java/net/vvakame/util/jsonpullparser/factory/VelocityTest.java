package net.vvakame.util.jsonpullparser.factory;

import java.io.StringWriter;

import net.vvakame.sample.twitter.Tweet;
import net.vvakame.sample.twitter.User;
import net.vvakame.util.jsonpullparser.factory.JsonElement.Kind;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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

		Velocity.init();// 初期化
		VelocityContext context = new VelocityContext();
		context.put("model", model);
		StringWriter out = new StringWriter();
		Template template = Velocity.getTemplate(
				"src/main/resources/JsonModelGen.java.vm", "UTF-8");
		template.merge(context, out);
		System.out.println(out.toString());
		out.flush();
	}
}
