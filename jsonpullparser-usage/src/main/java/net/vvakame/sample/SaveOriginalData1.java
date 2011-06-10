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
import net.vvakame.util.jsonpullparser.annotation.SaveOriginal;

/**
 * {@link SaveOriginal} 確認用クラス.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = false)
public class SaveOriginalData1 {

	@JsonKey
	String str;

	@JsonKey
	long num;

	@SaveOriginal(treatLogDisabledAsError = true)
	String original;


	/**
	 * @return the str
	 * @category accessor
	 */
	public String getStr() {
		return str;
	}

	/**
	 * @param str the str to set
	 * @category accessor
	 */
	public void setStr(String str) {
		this.str = str;
	}

	/**
	 * @return the num
	 * @category accessor
	 */
	public long getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 * @category accessor
	 */
	public void setNum(long num) {
		this.num = num;
	}

	/**
	 * @return the original
	 * @category accessor
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @param original the original to set
	 * @category accessor
	 */
	public void setOriginal(String original) {
		this.original = original;
	}
}
