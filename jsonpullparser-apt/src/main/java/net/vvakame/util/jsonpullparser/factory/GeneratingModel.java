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

package net.vvakame.util.jsonpullparser.factory;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 1 {@link JsonModel} に対応する生成ソースの表現.
 * @author vvakame
 */
public class GeneratingModel {

	String packageName = "invalid";

	String postfix = "Invalid";

	String target = "Invalid";

	List<JsonElement> elements = new ArrayList<JsonElement>();

	boolean treatUnknownKeyAsError;


	/**
	 * {@link JsonKey} が付加されたフィールドの追加.
	 * @param jsonElement
	 * @author vvakame
	 */
	public void addJsonElement(JsonElement jsonElement) {
		if (jsonElement == null) {
			return;
		}
		elements.add(jsonElement);
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the postfix
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix
	 *            the postfix to set
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the elements
	 */
	public List<JsonElement> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 *            the elements to set
	 */
	public void setElements(List<JsonElement> elements) {
		this.elements = elements;
	}

	/**
	 * @return the treatUnknownKeyAsError
	 */
	public boolean isTreatUnknownKeyAsError() {
		return treatUnknownKeyAsError;
	}

	/**
	 * @param treatUnknownKeyAsError
	 *            the treatUnknownKeyAsError to set
	 */
	public void setTreatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
	}
}
