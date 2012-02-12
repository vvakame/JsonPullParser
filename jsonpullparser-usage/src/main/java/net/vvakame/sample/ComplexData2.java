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

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * A class with fields that {@link JsonModel} attached (with {@link JsonKey#in()} and {@link JsonKey#out()})
 * @author vvakame
 */
@JsonModel
public class ComplexData2 {

	@JsonKey(in = false)
	String name;

	@JsonKey(out = false)
	List<MiniData> list1;

	@JsonKey(in = false, out = false)
	List<? extends MiniData> list2;

	@JsonKey
	MiniData data;


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the list1
	 */
	public List<MiniData> getList1() {
		return list1;
	}

	/**
	 * @param list1
	 *            the list1 to set
	 */
	public void setList1(List<MiniData> list1) {
		this.list1 = list1;
	}

	/**
	 * @return the list2
	 */
	public List<? extends MiniData> getList2() {
		return list2;
	}

	/**
	 * @param list2
	 *            the list2 to set
	 */
	public void setList2(List<? extends MiniData> list2) {
		this.list2 = list2;
	}

	/**
	 * @return the data
	 */
	public MiniData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(MiniData data) {
		this.data = data;
	}
}
