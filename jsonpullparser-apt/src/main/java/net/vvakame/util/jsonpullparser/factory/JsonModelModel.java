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

import javax.lang.model.element.Element;

import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Internal data model for source code generation ({@link JsonModel})
 * @author vvakame
 */
public class JsonModelModel {

	String packageName = "invalid";

	String postfix = "Invalid";

	boolean existsBase = false;

	Element targetBaseElement;

	String targetBase = "Invalid";

	String target = "Invalid";

	String targetNew = "Invalid";

	List<JsonKeyModel> keys = new ArrayList<JsonKeyModel>();

	List<JsonKeyModel> inheritKeys = new ArrayList<JsonKeyModel>();

	boolean treatUnknownKeyAsError;

	boolean genToPackagePrivate;

	boolean jsonMetaToPackagePrivate;

	boolean builder;

	StoreJsonModel storeJson = new StoreJsonModel();


	/**
	 * Keeps the given element.
	 * @param jsonKey
	 * @author vvakame
	 */
	public void addJsonKey(JsonKeyModel jsonKey) {
		if (jsonKey == null) {
			return;
		}
		keys.add(jsonKey);
	}

	/**
	 * @return the packageName
	 * @category accessor
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 * @category accessor
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the postfix
	 * @category accessor
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix the postfix to set
	 * @category accessor
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * @return the existsBase
	 * @category accessor
	 */
	public boolean isExistsBase() {
		return existsBase;
	}

	/**
	 * @param existsBase the existsBase to set
	 * @category accessor
	 */
	public void setExistsBase(boolean existsBase) {
		this.existsBase = existsBase;
	}

	/**
	 * @return the targetBaseElement
	 * @category accessor
	 */
	public Element getTargetBaseElement() {
		return targetBaseElement;
	}

	/**
	 * @param targetBaseElement the targetBaseElement to set
	 * @category accessor
	 */
	public void setTargetBaseElement(Element targetBaseElement) {
		this.targetBaseElement = targetBaseElement;
	}

	/**
	 * @return the targetBase
	 * @category accessor
	 */
	public String getTargetBase() {
		return targetBase;
	}

	/**
	 * @param targetBase the targetBase to set
	 * @category accessor
	 */
	public void setTargetBase(String targetBase) {
		this.targetBase = targetBase;
	}

	/**
	 * @return the target
	 * @category accessor
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 * @category accessor
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the targetNew
	 * @category accessor
	 */
	public String getTargetNew() {
		return targetNew;
	}

	/**
	 * @param targetNew the targetNew to set
	 * @category accessor
	 */
	public void setTargetNew(String targetNew) {
		this.targetNew = targetNew;
	}

	/**
	 * @return the keys
	 * @category accessor
	 */
	public List<JsonKeyModel> getKeys() {
		return keys;
	}

	/**
	 * @param keys the keys to set
	 * @category accessor
	 */
	public void setKeys(List<JsonKeyModel> keys) {
		this.keys = keys;
	}

	/**
	 * @return the inheritKeys
	 * @category accessor
	 */
	public List<JsonKeyModel> getInheritKeys() {
		return inheritKeys;
	}

	/**
	 * @param inheritKeys the inheritKeys to set
	 * @category accessor
	 */
	public void setInheritKeys(List<JsonKeyModel> inheritKeys) {
		this.inheritKeys = inheritKeys;
	}

	/**
	 * @return the treatUnknownKeyAsError
	 * @category accessor
	 */
	public boolean isTreatUnknownKeyAsError() {
		return treatUnknownKeyAsError;
	}

	/**
	 * @param treatUnknownKeyAsError the treatUnknownKeyAsError to set
	 * @category accessor
	 */
	public void setTreatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
	}

	/**
	 * @return the genToPackagePrivate
	 * @category accessor
	 */
	public boolean isGenToPackagePrivate() {
		return genToPackagePrivate;
	}

	/**
	 * @param genToPackagePrivate the genToPackagePrivate to set
	 * @category accessor
	 */
	public void setGenToPackagePrivate(boolean genToPackagePrivate) {
		this.genToPackagePrivate = genToPackagePrivate;
	}

	/**
	 * @return the jsonMetaToPackagePrivate
	 * @category accessor
	 */
	public boolean isJsonMetaToPackagePrivate() {
		return jsonMetaToPackagePrivate;
	}

	/**
	 * @param jsonMetaToPackagePrivate the jsonMetaToPackagePrivate to set
	 * @category accessor
	 */
	public void setJsonMetaToPackagePrivate(boolean jsonMetaToPackagePrivate) {
		this.jsonMetaToPackagePrivate = jsonMetaToPackagePrivate;
	}

	/**
	 * @return the builder
	 * @category accessor
	 */
	public boolean isBuilder() {
		return builder;
	}

	/**
	 * @param builder the builder to set
	 * @category accessor
	 */
	public void setBuilder(boolean builder) {
		this.builder = builder;
	}

	/**
	 * @return the storeJson
	 * @category accessor
	 */
	public StoreJsonModel getStoreJson() {
		return storeJson;
	}

	/**
	 * @param storeJson the storeJson to set
	 * @category accessor
	 */
	public void setStoreJson(StoreJsonModel storeJson) {
		this.storeJson = storeJson;
	}
}
