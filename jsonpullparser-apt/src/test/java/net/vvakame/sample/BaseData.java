package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 継承関係チェック用クラス 基底用.
 * @author vvakame
 */
@JsonModel
public class BaseData {

	@JsonKey
	long one;

	@JsonKey
	long two;


	/**
	 * @return the one
	 * @category accessor
	 */
	public long getOne() {
		return one;
	}

	/**
	 * @param one the one to set
	 * @category accessor
	 */
	public void setOne(long one) {
		this.one = one;
	}

	/**
	 * @return the two
	 * @category accessor
	 */
	public long getTwo() {
		return two;
	}

	/**
	 * @param two the two to set
	 * @category accessor
	 */
	public void setTwo(long two) {
		this.two = two;
	}
}
