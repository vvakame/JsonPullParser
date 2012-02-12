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
@JsonModel
public class ComplexData {

	@JsonKey
	String name;

	@JsonKey
	Date date;

	@JsonKey
	List<Date> dateList;

	@JsonKey
	List<TestData> list1;

	@JsonKey
	List<? extends TestData> list2;

	@JsonKey
	List<? extends TestData> list3;

	@JsonKey
	TestData data;

	@JsonKey
	SampleEnum outerEnum;

	@JsonKey
	List<SampleEnum> outerEnumList;

	@JsonKey
	InternalEnum innerEnum;

	@JsonKey
	List<InternalEnum> innerEnumList;


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
	 * @return the dateList
	 * @category accessor
	 */
	public List<Date> getDateList() {
		return dateList;
	}

	/**
	 * @param dateList the dateList to set
	 * @category accessor
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * @return the list1
	 * @category accessor
	 */
	public List<TestData> getList1() {
		return list1;
	}

	/**
	 * @param list1 the list1 to set
	 * @category accessor
	 */
	public void setList1(List<TestData> list1) {
		this.list1 = list1;
	}

	/**
	 * @return the list2
	 * @category accessor
	 */
	public List<? extends TestData> getList2() {
		return list2;
	}

	/**
	 * @param list2 the list2 to set
	 * @category accessor
	 */
	public void setList2(List<? extends TestData> list2) {
		this.list2 = list2;
	}

	/**
	 * @return the list3
	 * @category accessor
	 */
	public List<? extends TestData> getList3() {
		return list3;
	}

	/**
	 * @param list3 the list3 to set
	 * @category accessor
	 */
	public void setList3(List<? extends TestData> list3) {
		this.list3 = list3;
	}

	/**
	 * @return the data
	 * @category accessor
	 */
	public TestData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 * @category accessor
	 */
	public void setData(TestData data) {
		this.data = data;
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
	 * @return the outerEnumList
	 * @category accessor
	 */
	public List<SampleEnum> getOuterEnumList() {
		return outerEnumList;
	}

	/**
	 * @param outerEnumList the outerEnumList to set
	 * @category accessor
	 */
	public void setOuterEnumList(List<SampleEnum> outerEnumList) {
		this.outerEnumList = outerEnumList;
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
	 * @return the innerEnumList
	 * @category accessor
	 */
	public List<InternalEnum> getInnerEnumList() {
		return innerEnumList;
	}

	/**
	 * @param innerEnumList the innerEnumList to set
	 * @category accessor
	 */
	public void setInnerEnumList(List<InternalEnum> innerEnumList) {
		this.innerEnumList = innerEnumList;
	}
}
