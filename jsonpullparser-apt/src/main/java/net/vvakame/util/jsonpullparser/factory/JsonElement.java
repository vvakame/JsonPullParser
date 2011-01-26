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
	boolean first = false;
	String key;
	String setter;
	Kind kind;
	String modelName;
	String converter;

	public static enum Kind {
		STRING, BOOLEAN, DOUBLE, LONG, MODEL, BYTE, CHAR, FLOAT, INT, SHORT, LIST, JSON_HASH, JSON_ARRAY, CONVERTER
	}

	/**
	 * @return the first
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
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
