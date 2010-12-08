package net.vvakame.sample;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonHash;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

@JsonHash
public class ComplexData {

	@JsonKey
	String name;

	@JsonKey
	List<TestData> list1;

	@JsonKey
	List<? extends TestData> list2;

	@JsonKey
	List<? super TestData> list3;

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
	public List<? super TestData> getList3() {
		return list3;
	}

	/**
	 * @param list3
	 *            the list3 to set
	 */
	public void setList3(List<? super TestData> list3) {
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
