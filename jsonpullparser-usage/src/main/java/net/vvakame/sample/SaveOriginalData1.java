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
