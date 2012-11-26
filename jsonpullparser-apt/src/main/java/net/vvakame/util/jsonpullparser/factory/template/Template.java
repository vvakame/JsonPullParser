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

import java.io.IOException;

import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.factory.JsonModelModel;

/**
 * Templating facility in general.
 * @author vvakame
 */
public class Template {

	private Template() {
	}

	/**
	 * Generates source code into the given file object from the given data model, utilizing the templating engine.
	 * @param fileObject Target file object
	 * @param model Data model for source code generation
	 * @throws IOException
	 * @author vvakame
	 */
	public static void writeGen(JavaFileObject fileObject, JsonModelModel model)
			throws IOException {
		MvelTemplate.writeGen(fileObject, model);
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
		MvelTemplate.writeJsonMeta(fileObject, model);
	}
}
