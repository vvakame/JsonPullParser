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

import java.util.Date;
import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 全てのプリミティブ型をフィールドに持つクラス.
 * @author vvakame
 */
@JsonModel
public class PrimitiveWrapperListData {

	@JsonKey
	List<Boolean> bool;

	@JsonKey
	List<Character> c;

	@JsonKey
	List<Byte> b;

	@JsonKey
	List<Short> s;

	@JsonKey
	List<Integer> i;

	@JsonKey
	List<Long> l;

	@JsonKey
	List<Float> f;

	@JsonKey
	List<Double> d;

	@JsonKey
	List<String> str;

	@JsonKey
	List<Date> date;

	@JsonKey
	List<SampleEnum> enums;


	/**
	 * @return the bool
	 * @category accessor
	 */
	public List<Boolean> getBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 * @category accessor
	 */
	public void setBool(List<Boolean> bool) {
		this.bool = bool;
	}

	/**
	 * @return the c
	 * @category accessor
	 */
	public List<Character> getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 * @category accessor
	 */
	public void setC(List<Character> c) {
		this.c = c;
	}

	/**
	 * @return the b
	 * @category accessor
	 */
	public List<Byte> getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(List<Byte> b) {
		this.b = b;
	}

	/**
	 * @return the s
	 * @category accessor
	 */
	public List<Short> getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 * @category accessor
	 */
	public void setS(List<Short> s) {
		this.s = s;
	}

	/**
	 * @return the i
	 * @category accessor
	 */
	public List<Integer> getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 * @category accessor
	 */
	public void setI(List<Integer> i) {
		this.i = i;
	}

	/**
	 * @return the l
	 * @category accessor
	 */
	public List<Long> getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 * @category accessor
	 */
	public void setL(List<Long> l) {
		this.l = l;
	}

	/**
	 * @return the f
	 * @category accessor
	 */
	public List<Float> getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 * @category accessor
	 */
	public void setF(List<Float> f) {
		this.f = f;
	}

	/**
	 * @return the d
	 * @category accessor
	 */
	public List<Double> getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 * @category accessor
	 */
	public void setD(List<Double> d) {
		this.d = d;
	}

	/**
	 * @return the str
	 * @category accessor
	 */
	public List<String> getStr() {
		return str;
	}

	/**
	 * @param str the str to set
	 * @category accessor
	 */
	public void setStr(List<String> str) {
		this.str = str;
	}

	/**
	 * @return the date
	 * @category accessor
	 */
	public List<Date> getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 * @category accessor
	 */
	public void setDate(List<Date> date) {
		this.date = date;
	}

	/**
	 * @return the enums
	 * @category accessor
	 */
	public List<SampleEnum> getEnums() {
		return enums;
	}

	/**
	 * @param enums the enums to set
	 * @category accessor
	 */
	public void setEnums(List<SampleEnum> enums) {
		this.enums = enums;
	}
}
