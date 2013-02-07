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

import net.vvakame.util.jsonpullparser.factory.JsonKeyModel;
import net.vvakame.util.jsonpullparser.factory.JsonModelModel;
import net.vvakame.util.jsonpullparser.factory.Log;
import net.vvakame.util.jsonpullparser.factory.StoreJsonModel;

import org.mvel2.templates.TemplateRuntime;

/**
 * MVEL templating facility.
 * @author vvakame
 */
public class MvelTemplate {

	private MvelTemplate() {
	}

	/**
	 * Generates source code into the given file object from the given data model, utilizing the templating engine.
	 * @param fileObject Target file object
	 * @param model Data model for source code generation
	 * @throws IOException
	 * @author vvakame
	 */
	public static void writeGen(JavaFileObject fileObject, JsonModelModel model) throws IOException {
		Map<String, Object> map = convModelToMap(model);

		Writer writer = fileObject.openWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		String generated =
				(String) TemplateRuntime.eval(getTemplateString("JsonModelGen.java.mvel"), map);
		try {
			printWriter.write(generated);
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			throw new RuntimeException("error raised in process " + model.getTarget(), e);
		}
	}

	/**
	 * Generates source code into the given file object from the given data model, utilizing the templating engine.
	 * @param fileObject Target file object
	 * @param model Data model for source code generation
	 * @throws IOException
	 * @author vvakame
	 */
	public static void writeJsonMeta(JavaFileObject fileObject, JsonModelModel model)
			throws IOException {
		Map<String, Object> map = convModelToMap(model);

		Writer writer = fileObject.openWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		String generated =
				(String) TemplateRuntime.eval(getTemplateString("JsonModelMeta.java.mvel"), map);
		try {
			printWriter.write(generated);
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			throw new RuntimeException("error raised in process " + model.getTarget(), e);
		}
	}

	static Map<String, Object> convModelToMap(JsonModelModel model) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("packageName", model.getPackageName());
		map.put("postfix", model.getPostfix());
		map.put("existsBase", model.isExistsBase());
		map.put("targetBase", model.getTargetBase());
		map.put("target", model.getTarget());
		map.put("targetNew", model.getTargetNew());
		{
			List<Map<String, String>> jsonKeys = new ArrayList<Map<String, String>>();
			for (JsonKeyModel jsonKey : model.getKeys()) {
				if (!jsonKey.isIn()) {
					continue;
				}
				Map<String, String> toMap = convJsonModelToMap(jsonKey);
				jsonKeys.add(toMap);
			}
			map.put("inElements", jsonKeys);
		}
		{
			List<Map<String, String>> jsonKeys = new ArrayList<Map<String, String>>();
			for (JsonKeyModel jsonKey : model.getKeys()) {
				if (!jsonKey.isOut()) {
					continue;
				}
				Map<String, String> toMap = convJsonModelToMap(jsonKey);
				jsonKeys.add(toMap);
			}
			map.put("outElements", jsonKeys);
		}
		{
			List<Map<String, String>> jsonKeys = new ArrayList<Map<String, String>>();
			for (JsonKeyModel jsonKey : model.getKeys()) {
				Map<String, String> toMap = convJsonModelToMap(jsonKey);
				jsonKeys.add(toMap);
			}
			map.put("allElements", jsonKeys);
		}
		{
			List<Map<String, String>> jsonKeys = new ArrayList<Map<String, String>>();
			for (JsonKeyModel jsonKey : model.getInheritKeys()) {
				Map<String, String> toMap = convJsonModelToMap(jsonKey);
				jsonKeys.add(toMap);
			}
			map.put("inheritElements", jsonKeys);
		}
		{
			Map<String, Object> toMap = convStoreJsonModelToMap(model.getStoreJson());
			map.put("storeJsonElement", toMap);
		}
		map.put("treatUnknownKeyAsError", model.isTreatUnknownKeyAsError());
		map.put("genToPackagePrivate", model.isGenToPackagePrivate());
		map.put("jsonMetaToPackagePrivate", model.isJsonMetaToPackagePrivate());

		return map;
	}

	static Map<String, String> convJsonModelToMap(JsonKeyModel key) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key", key.getKey());
		map.put("originalName", key.getOriginalName());
		map.put("modelName", key.getModelName());
		map.put("parameterClass", key.getParameterClass());
		map.put("genName", key.getGenName());
		map.put("setter", key.getSetter());
		map.put("getter", key.getGetter());
		map.put("kind", key.getKind().name());
		map.put("converter", key.getConverter());
		map.put("subKind", key.getSubKind().name());

		return map;
	}

	static Map<String, Object> convStoreJsonModelToMap(StoreJsonModel storeJson) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("storeJson", storeJson.isStoreJson());
		map.put("treatLogDisabledAsError", storeJson.isTreatLogDisabledAsError());
		map.put("setter", storeJson.getSetter());

		return map;
	}

	static String getTemplateString(String resourceName) {
		InputStream stream = MvelTemplate.class.getClassLoader().getResourceAsStream(resourceName);
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
