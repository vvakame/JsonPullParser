package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.annotation.SaveOriginal;

/**
 * {@link SaveOriginal} 確認用クラス.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = false)
public class SaveOriginalData2 {

	@JsonKey
	String str;

	@JsonKey
	long num;

	@JsonKey
	SaveOriginalData1 data1;

	@SaveOriginal(treatLogDisabledAsError = false)
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
	 * @return the data1
	 * @category accessor
	 */
	public SaveOriginalData1 getData1() {
		return data1;
	}

	/**
	 * @param data1 the data1 to set
	 * @category accessor
	 */
	public void setData1(SaveOriginalData1 data1) {
		this.data1 = data1;
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
