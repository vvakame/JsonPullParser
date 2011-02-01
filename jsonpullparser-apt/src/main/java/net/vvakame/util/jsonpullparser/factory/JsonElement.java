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

public class JsonElement {

	String key;

	boolean in;

	String setter;

	boolean out;

	String getter;

	Kind kind;

	String modelName;

	String converter;


	public static enum Kind {
		STRING, BOOLEAN, DOUBLE, LONG, MODEL, BYTE, CHAR, FLOAT, INT, SHORT, LIST, JSON_HASH,
		JSON_ARRAY, CONVERTER
	}


	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the in
	 */
	public boolean isIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(boolean in) {
		this.in = in;
	}

	/**
	 * @return the setter
	 */
	public String getSetter() {
		return setter;
	}

	/**
	 * @param setter
	 *            the setter to set
	 */
	public void setSetter(String setter) {
		this.setter = setter;
	}

	/**
	 * @return the out
	 */
	public boolean isOut() {
		return out;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut(boolean out) {
		this.out = out;
	}

	/**
	 * @return the getter
	 */
	public String getGetter() {
		return getter;
	}

	/**
	 * @param getter
	 *            the getter to set
	 */
	public void setGetter(String getter) {
		this.getter = getter;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the converter
	 */
	public String getConverter() {
		return converter;
	}

	/**
	 * @param converter
	 *            the converter to set
	 */
	public void setConverter(String converter) {
		this.converter = converter;
	}
}
