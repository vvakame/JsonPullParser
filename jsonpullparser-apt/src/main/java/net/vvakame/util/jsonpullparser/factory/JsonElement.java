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

import java.util.Date;
import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * Internal data model for source code generation ({@link JsonKey})
 * @author vvakame
 */
public class JsonElement {

	String originalName;

	String key;

	boolean in;

	String setter;

	boolean out;

	String getter;

	Kind kind;

	String modelName;

	String converter;

	Kind subKind = Kind.UNKNOWN;


	/**
	 * The type {@link JsonModel} represents.
	 * @author vvakame
	 */
	public static enum Kind {
		/** 未指定 */
		UNKNOWN,
		/** {@link String} */
		STRING,
		/** {@code boolean} */
		BOOLEAN,
		/** {@code double} */
		DOUBLE,
		/** {@code long} */
		LONG,
		/** {@code byte} */
		BYTE,
		/** {@code char} */
		CHAR,
		/** {@code float} */
		FLOAT,
		/** {@code int} */
		INT,
		/** {@code short} */
		SHORT,
		/** {@link Date} */
		DATE,
		/** {@link List} */
		LIST,
		/** {@link JsonHash} */
		JSON_HASH,
		/** {@link JsonArray} */
		JSON_ARRAY,
		/** {@link Enum} */
		ENUM,
		/** {@link JsonModel} 付加クラス */
		MODEL,
		/** {@link TokenConverter} 利用 */
		CONVERTER,
		/** {@link Boolean} */
		BOOLEAN_WRAPPER,
		/** {@link Double} */
		DOUBLE_WRAPPER,
		/** {@link Long} */
		LONG_WRAPPER,
		/** {@link Byte} */
		BYTE_WRAPPER,
		/** {@link Character} */
		CHAR_WRAPPER,
		/** {@link Float} */
		FLOAT_WRAPPER,
		/** {@link Integer} */
		INT_WRAPPER,
		/** {@link Short} */
		SHORT_WRAPPER,
	}


	/**
	 * @return the originalName
	 * @category accessor
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName the originalName to set
	 * @category accessor
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the key
	 * @category accessor
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 * @category accessor
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the in
	 * @category accessor
	 */
	public boolean isIn() {
		return in;
	}

	/**
	 * @param in the in to set
	 * @category accessor
	 */
	public void setIn(boolean in) {
		this.in = in;
	}

	/**
	 * @return the setter
	 * @category accessor
	 */
	public String getSetter() {
		return setter;
	}

	/**
	 * @param setter the setter to set
	 * @category accessor
	 */
	public void setSetter(String setter) {
		this.setter = setter;
	}

	/**
	 * @return the out
	 * @category accessor
	 */
	public boolean isOut() {
		return out;
	}

	/**
	 * @param out the out to set
	 * @category accessor
	 */
	public void setOut(boolean out) {
		this.out = out;
	}

	/**
	 * @return the getter
	 * @category accessor
	 */
	public String getGetter() {
		return getter;
	}

	/**
	 * @param getter the getter to set
	 * @category accessor
	 */
	public void setGetter(String getter) {
		this.getter = getter;
	}

	/**
	 * @return the kind
	 * @category accessor
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 * @category accessor
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the modelName
	 * @category accessor
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 * @category accessor
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the converter
	 * @category accessor
	 */
	public String getConverter() {
		return converter;
	}

	/**
	 * @param converter the converter to set
	 * @category accessor
	 */
	public void setConverter(String converter) {
		this.converter = converter;
	}

	/**
	 * @return the subKind
	 * @category accessor
	 */
	public Kind getSubKind() {
		return subKind;
	}

	/**
	 * @param subKind the subKind to set
	 * @category accessor
	 */
	public void setSubKind(Kind subKind) {
		this.subKind = subKind;
	}
}
