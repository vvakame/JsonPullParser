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
 * A class with fields that {@link JsonModel} attached.
 * @author vvakame
 */
@JsonModel(builder = true)
public class ComplexData {

	@JsonKey
	String name;

	@JsonKey
	Date date;

	@JsonKey
	SampleEnum outerEnum;

	@JsonKey
	InternalEnum innerEnum;

	@JsonKey
	List<SampleData> list1;

	@JsonKey
	List<? extends SampleData> list2;

	@JsonKey
	List<? extends SampleData> list3;

	@JsonKey
	SampleData data;


	/** Statically defined {@link Enum} inside class. */
	public static enum InternalEnum {
		/** Test case 1 */
		TEST1,
		/** Test case 2 */
		TEST2
	}


	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @category accessor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date
	 * @category accessor
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 * @category accessor
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the outerEnum
	 * @category accessor
	 */
	public SampleEnum getOuterEnum() {
		return outerEnum;
	}

	/**
	 * @param outerEnum the outerEnum to set
	 * @category accessor
	 */
	public void setOuterEnum(SampleEnum outerEnum) {
		this.outerEnum = outerEnum;
	}

	/**
	 * @return the innerEnum
	 * @category accessor
	 */
	public InternalEnum getInnerEnum() {
		return innerEnum;
	}

	/**
	 * @param innerEnum the innerEnum to set
	 * @category accessor
	 */
	public void setInnerEnum(InternalEnum innerEnum) {
		this.innerEnum = innerEnum;
	}

	/**
	 * @return the list1
	 * @category accessor
	 */
	public List<SampleData> getList1() {
		return list1;
	}

	/**
	 * @param list1 the list1 to set
	 * @category accessor
	 */
	public void setList1(List<SampleData> list1) {
		this.list1 = list1;
	}

	/**
	 * @return the list2
	 * @category accessor
	 */
	public List<? extends SampleData> getList2() {
		return list2;
	}

	/**
	 * @param list2 the list2 to set
	 * @category accessor
	 */
	public void setList2(List<? extends SampleData> list2) {
		this.list2 = list2;
	}

	/**
	 * @return the list3
	 * @category accessor
	 */
	public List<? extends SampleData> getList3() {
		return list3;
	}

	/**
	 * @param list3 the list3 to set
	 * @category accessor
	 */
	public void setList3(List<? extends SampleData> list3) {
		this.list3 = list3;
	}

	/**
	 * @return the data
	 * @category accessor
	 */
	public SampleData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 * @category accessor
	 */
	public void setData(SampleData data) {
		this.data = data;
	}
}
