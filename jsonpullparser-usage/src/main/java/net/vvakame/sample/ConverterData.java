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

package net.vvakame.sample;

import java.util.List;

import net.vvakame.sample.converter.IntFlattenConverter;
import net.vvakame.sample.converter.StringDiscardConverter;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * {@link JsonKey#converter()} を利用するクラス.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true)
public class ConverterData {

	@JsonKey(converter = StringDiscardConverter.class)
	String str1;

	@JsonKey
	String str2;

	@JsonKey(converter = IntFlattenConverter.class)
	List<Integer> flatten;


	/**
	 * @return the str1
	 */
	public String getStr1() {
		return str1;
	}

	/**
	 * @param str1
	 *            the str1 to set
	 */
	public void setStr1(String str1) {
		this.str1 = str1;
	}

	/**
	 * @return the str2
	 */
	public String getStr2() {
		return str2;
	}

	/**
	 * @param str2
	 *            the str2 to set
	 */
	public void setStr2(String str2) {
		this.str2 = str2;
	}

	/**
	 * @return the flatten
	 */
	public List<Integer> getFlatten() {
		return flatten;
	}

	/**
	 * @param flatten
	 *            the flatten to set
	 */
	public void setFlatten(List<Integer> flatten) {
		this.flatten = flatten;
	}
}
