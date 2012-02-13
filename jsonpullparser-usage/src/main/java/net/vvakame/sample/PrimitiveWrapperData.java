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

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * A class with fields for all the primitive types.
 * @author vvakame
 */
@JsonModel
public class PrimitiveWrapperData {

	@JsonKey
	Boolean bool;

	@JsonKey
	Character c;

	@JsonKey
	Byte b;

	@JsonKey
	Short s;

	@JsonKey
	Integer i;

	@JsonKey
	Long l;

	@JsonKey
	Float f;

	@JsonKey
	Double d;


	/**
	 * @return the bool
	 */
	public boolean isBool() {
		return bool;
	}

	/**
	 * @param bool
	 *            the bool to set
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the c
	 */
	public char getC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setC(char c) {
		this.c = c;
	}

	/**
	 * @return the b
	 */
	public byte getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(byte b) {
		this.b = b;
	}

	/**
	 * @return the s
	 */
	public short getS() {
		return s;
	}

	/**
	 * @param s
	 *            the s to set
	 */
	public void setS(short s) {
		this.s = s;
	}

	/**
	 * @return the i
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i
	 *            the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * @return the l
	 */
	public long getL() {
		return l;
	}

	/**
	 * @param l
	 *            the l to set
	 */
	public void setL(long l) {
		this.l = l;
	}

	/**
	 * @return the f
	 */
	public float getF() {
		return f;
	}

	/**
	 * @param f
	 *            the f to set
	 */
	public void setF(float f) {
		this.f = f;
	}

	/**
	 * @return the d
	 */
	public double getD() {
		return d;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public void setD(double d) {
		this.d = d;
	}
}
