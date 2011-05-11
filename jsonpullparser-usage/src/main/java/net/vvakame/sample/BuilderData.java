package net.vvakame.sample;

import java.util.Date;
import java.util.List;

import net.vvakame.sample.ComplexData.InternalEnum;
import net.vvakame.sample.converter.IntFlattenConverter;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

/**
 * {@link JsonModel#builder()} のテスト用クラス.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, decamelize = true, builder = true)
public class BuilderData {

	@JsonKey
	boolean bool;

	@JsonKey
	char c;

	@JsonKey
	byte b;

	@JsonKey
	short s;

	@JsonKey
	int i;

	@JsonKey
	long l;

	@JsonKey
	float f;

	@JsonKey
	double d;

	@JsonKey
	Boolean wBool;

	@JsonKey
	Character wc;

	@JsonKey
	Byte wb;

	@JsonKey
	Short ws;

	@JsonKey
	Integer wi;

	@JsonKey
	Long wl;

	@JsonKey
	Float wf;

	@JsonKey
	Double wd;

	@JsonKey
	String str;

	@JsonKey
	Date date;

	@JsonKey
	TestData model;

	@JsonKey
	SampleEnum outerEnum;

	@JsonKey
	List<Date> dateList;

	@JsonKey
	List<TestData> list1;

	@JsonKey
	List<? extends TestData> list2;

	@JsonKey
	List<? extends TestData> list3;

	@JsonKey
	List<SampleEnum> outerEnumList;

	@JsonKey
	List<InternalEnum> innerEnumList;

	@JsonKey(converter = IntFlattenConverter.class)
	List<Integer> conv;

	@JsonKey
	JsonHash jsonHash;

	@JsonKey
	JsonArray jsonArray;


	/**
	 * @return the bool
	 * @category accessor
	 */
	public boolean isBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 * @category accessor
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @return the c
	 * @category accessor
	 */
	public char getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 * @category accessor
	 */
	public void setC(char c) {
		this.c = c;
	}

	/**
	 * @return the b
	 * @category accessor
	 */
	public byte getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(byte b) {
		this.b = b;
	}

	/**
	 * @return the s
	 * @category accessor
	 */
	public short getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 * @category accessor
	 */
	public void setS(short s) {
		this.s = s;
	}

	/**
	 * @return the i
	 * @category accessor
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 * @category accessor
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * @return the l
	 * @category accessor
	 */
	public long getL() {
		return l;
	}

	/**
	 * @param l the l to set
	 * @category accessor
	 */
	public void setL(long l) {
		this.l = l;
	}

	/**
	 * @return the f
	 * @category accessor
	 */
	public float getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 * @category accessor
	 */
	public void setF(float f) {
		this.f = f;
	}

	/**
	 * @return the d
	 * @category accessor
	 */
	public double getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 * @category accessor
	 */
	public void setD(double d) {
		this.d = d;
	}

	/**
	 * @return the wBool
	 * @category accessor
	 */
	public Boolean getwBool() {
		return wBool;
	}

	/**
	 * @param wBool the wBool to set
	 * @category accessor
	 */
	public void setwBool(Boolean wBool) {
		this.wBool = wBool;
	}

	/**
	 * @return the wc
	 * @category accessor
	 */
	public Character getWc() {
		return wc;
	}

	/**
	 * @param wc the wc to set
	 * @category accessor
	 */
	public void setWc(Character wc) {
		this.wc = wc;
	}

	/**
	 * @return the wb
	 * @category accessor
	 */
	public Byte getWb() {
		return wb;
	}

	/**
	 * @param wb the wb to set
	 * @category accessor
	 */
	public void setWb(Byte wb) {
		this.wb = wb;
	}

	/**
	 * @return the ws
	 * @category accessor
	 */
	public Short getWs() {
		return ws;
	}

	/**
	 * @param ws the ws to set
	 * @category accessor
	 */
	public void setWs(Short ws) {
		this.ws = ws;
	}

	/**
	 * @return the wi
	 * @category accessor
	 */
	public Integer getWi() {
		return wi;
	}

	/**
	 * @param wi the wi to set
	 * @category accessor
	 */
	public void setWi(Integer wi) {
		this.wi = wi;
	}

	/**
	 * @return the wl
	 * @category accessor
	 */
	public Long getWl() {
		return wl;
	}

	/**
	 * @param wl the wl to set
	 * @category accessor
	 */
	public void setWl(Long wl) {
		this.wl = wl;
	}

	/**
	 * @return the wf
	 * @category accessor
	 */
	public Float getWf() {
		return wf;
	}

	/**
	 * @param wf the wf to set
	 * @category accessor
	 */
	public void setWf(Float wf) {
		this.wf = wf;
	}

	/**
	 * @return the wd
	 * @category accessor
	 */
	public Double getWd() {
		return wd;
	}

	/**
	 * @param wd the wd to set
	 * @category accessor
	 */
	public void setWd(Double wd) {
		this.wd = wd;
	}

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
	 * @return the model
	 * @category accessor
	 */
	public TestData getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 * @category accessor
	 */
	public void setModel(TestData model) {
		this.model = model;
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

	/**
	 * @return the conv
	 * @category accessor
	 */
	public List<Integer> getConv() {
		return conv;
	}

	/**
	 * @param conv the conv to set
	 * @category accessor
	 */
	public void setConv(List<Integer> conv) {
		this.conv = conv;
	}

	/**
	 * @return the jsonHash
	 * @category accessor
	 */
	public JsonHash getJsonHash() {
		return jsonHash;
	}

	/**
	 * @param jsonHash the jsonHash to set
	 * @category accessor
	 */
	public void setJsonHash(JsonHash jsonHash) {
		this.jsonHash = jsonHash;
	}

	/**
	 * @return the jsonArray
	 * @category accessor
	 */
	public JsonArray getJsonArray() {
		return jsonArray;
	}

	/**
	 * @param jsonArray the jsonArray to set
	 * @category accessor
	 */
	public void setJsonArray(JsonArray jsonArray) {
		this.jsonArray = jsonArray;
	}
}
