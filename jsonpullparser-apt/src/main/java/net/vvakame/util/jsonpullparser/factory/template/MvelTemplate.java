package net.vvakame.util.jsonpullparser.factory.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.factory.GeneratingModel;
import net.vvakame.util.jsonpullparser.factory.JsonElement;
import net.vvakame.util.jsonpullparser.factory.Log;

import org.mvel2.templates.TemplateRuntime;

public class MvelTemplate {
	private MvelTemplate() {
	}

	public static void write(JavaFileObject fileObject, GeneratingModel model)
			throws IOException {
		Map<String, Object> map = convModelToMap(model);

		Writer writer = fileObject.openWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		String generated = (String) TemplateRuntime.eval(getTemplateString(),
				map);
		printWriter.write(generated);
		printWriter.flush();
		printWriter.close();
	}

	static Map<String, Object> convModelToMap(GeneratingModel model) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("packageName", model.getPackageName());
		map.put("imports", model.getImports());
		map.put("postfix", model.getPostfix());
		map.put("target", model.getTarget());
		List<Map<String, String>> jsonElements = new ArrayList<Map<String, String>>();
		for (JsonElement jsonElement : model.getElements()) {
			jsonElements.add(convJsonElementToMap(jsonElement));
		}
		map.put("elements", jsonElements);

		return map;
	}

	static Map<String, String> convJsonElementToMap(JsonElement el) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", el.getKey());
		map.put("modelName", el.getModelName());
		map.put("setter", el.getSetter());
		map.put("kind", el.getKind().toString());

		return map;
	}

	static String getTemplateString() {
		InputStream stream = MvelTemplate.class.getClassLoader()
				.getResourceAsStream("JsonModelGen.java.mvel");
		try {
			String template = streamToString(stream);
			// Log.d(template);
			return template;
		} catch (IOException e) {
			Log.e(e);
			return null;
		}
	}

	static String streamToString(InputStream is) throws IOException {
		if (is == null) {
			Log.e("not expected null value.");
			throw new IllegalStateException("not expected null value.");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line).append("\n");
		}

		return builder.toString();
	}
}
