package net.vvakame.sample;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

@JsonModel
public class ComplexData {

	@JsonKey
	String name;

	@JsonKey
	List<SampleData> list1;

	@JsonKey
	List<? extends SampleData> list2;

	@JsonKey
	List<? super SampleData> list3;

	@JsonKey
	SampleData data;

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
	public List<SampleData> getList1() {
		return list1;
	}

	/**
	 * @param list1
	 *            the list1 to set
	 */
	public void setList1(List<SampleData> list1) {
		this.list1 = list1;
	}

	/**
	 * @return the list2
	 */
	public List<? extends SampleData> getList2() {
		return list2;
	}

	/**
	 * @param list2
	 *            the list2 to set
	 */
	public void setList2(List<? extends SampleData> list2) {
		this.list2 = list2;
	}

	/**
	 * @return the list3
	 */
	public List<? super SampleData> getList3() {
		return list3;
	}

	/**
	 * @param list3
	 *            the list3 to set
	 */
	public void setList3(List<? super SampleData> list3) {
		this.list3 = list3;
	}

	/**
	 * @return the data
	 */
	public SampleData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(SampleData data) {
		this.data = data;
	}
}
