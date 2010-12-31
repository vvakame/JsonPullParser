package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class Velocity {
	public static VelocityEngine newEngine() {
		VelocityEngine engine = new VelocityEngine();
		Properties p = new Properties();
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		p.setProperty("input.encoding", "UTF-8");
		p.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"org.apache.velocity.runtime.log.NullLogChute");
		engine.init(p);

		return engine;
	}

	public static Template newTemplate() {
		VelocityEngine engine = newEngine();
		Template template = engine.getTemplate("JsonModelGen.java.vm");

		return template;
	}

	public static void write(Writer writer, GeneratingModel model) {
		VelocityContext context = new VelocityContext();
		context.put("model", model);

		Template template = newTemplate();
		template.merge(context, writer);
	}

	public static void write(JavaFileObject fileObject, GeneratingModel model)
			throws IOException {

		Writer writer = null;
		try {
			writer = fileObject.openWriter();
			Velocity.write(writer, model);
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
