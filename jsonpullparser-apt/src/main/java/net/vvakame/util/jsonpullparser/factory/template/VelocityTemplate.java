package net.vvakame.util.jsonpullparser.factory.template;

import java.io.IOException;
import java.io.Writer;

import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.factory.GeneratingModel;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityTemplate {
	public static VelocityEngine newEngine() {
		VelocityEngine engine = new VelocityEngine();

		engine.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				VelocityMessagerLog.class.getCanonicalName());
		engine.addProperty(RuntimeConstants.RESOURCE_LOADER, "class");
		engine.addProperty("class.resource.loader.class",
				ClasspathResourceLoader.class.getCanonicalName());
		engine.addProperty("input.encoding", "UTF-8");

		engine.init();

		return engine;
	}

	public static Template newTemplate() {
		VelocityEngine engine = newEngine();
		Template template = engine.getTemplate("/JsonModelGen.java.vm");

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
			VelocityTemplate.write(writer, model);
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}