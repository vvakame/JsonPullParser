/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	public static void write(JavaFileObject fileObject, GeneratingModel model) throws IOException {
		Map<String, Object> map = convModelToMap(model);

		Writer writer = fileObject.openWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		String generated = (String) TemplateRuntime.eval(getTemplateString(), map);
		printWriter.write(generated);
		printWriter.flush();
		printWriter.close();
	}

	static Map<String, Object> convModelToMap(GeneratingModel model) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("packageName", model.getPackageName());
		map.put("postfix", model.getPostfix());
		map.put("target", model.getTarget());
		{
			List<Map<String, String>> jsonElements = new ArrayList<Map<String, String>>();
			for (JsonElement jsonElement : model.getElements()) {
				Map<String, String> toMap = convInJsonElementToMap(jsonElement);
				if (toMap != null) {
					jsonElements.add(toMap);
				}
			}
			map.put("inElements", jsonElements);
		}
		{
			List<Map<String, String>> jsonElements = new ArrayList<Map<String, String>>();
			for (JsonElement jsonElement : model.getElements()) {
				Map<String, String> toMap = convOutJsonElementToMap(jsonElement);
				if (toMap != null) {
					jsonElements.add(toMap);
				}
			}
			map.put("outElements", jsonElements);
		}
		map.put("treatUnknownKeyAsError", model.isTreatUnknownKeyAsError());

		return map;
	}

	static Map<String, String> convInJsonElementToMap(JsonElement el) {
		if (!el.isIn()) {
			return null;
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", el.getKey());
		map.put("modelName", el.getModelName());
		map.put("setter", el.getSetter());
		map.put("getter", el.getGetter());
		map.put("kind", el.getKind().toString());
		map.put("converter", el.getConverter());

		return map;
	}

	static Map<String, String> convOutJsonElementToMap(JsonElement el) {
		if (!el.isOut()) {
			return null;
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", el.getKey());
		map.put("modelName", el.getModelName());
		map.put("setter", el.getSetter());
		map.put("getter", el.getGetter());
		map.put("kind", el.getKind().toString());
		map.put("converter", el.getConverter());

		return map;
	}

	static String getTemplateString() {
		InputStream stream =
				MvelTemplate.class.getClassLoader().getResourceAsStream("JsonModelGen.java.mvel");
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
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line).append("\n");
		}

		return builder.toString();
	}
}
