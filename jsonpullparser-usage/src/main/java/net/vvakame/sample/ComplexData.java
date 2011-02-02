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
 * {@link JsonModel} がついたクラスをフィールドに持つクラス.
 * @author vvakame
 */
@JsonModel
public class ComplexData {

	@JsonKey
	String name;

	@JsonKey
	List<TestData> list1;

	@JsonKey
	List<? extends TestData> list2;

	@JsonKey
	List<? extends TestData> list3;

	@JsonKey
	TestData data;


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
	void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the list1
	 */
	public List<TestData> getList1() {
		return list1;
	}

	/**
	 * @param list1
	 *            the list1 to set
	 */
	void setList1(List<TestData> list1) {
		this.list1 = list1;
	}

	/**
	 * @return the list2
	 */
	public List<? extends TestData> getList2() {
		return list2;
	}

	/**
	 * @param list2
	 *            the list2 to set
	 */
	public void setList2(List<? extends TestData> list2) {
		this.list2 = list2;
	}

	/**
	 * @return the list3
	 */
	public List<? extends TestData> getList3() {
		return list3;
	}

	/**
	 * @param list3
	 *            the list3 to set
	 */
	public void setList3(List<? extends TestData> list3) {
		this.list3 = list3;
	}

	/**
	 * @return the data
	 */
	public TestData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(TestData data) {
		this.data = data;
	}
}
