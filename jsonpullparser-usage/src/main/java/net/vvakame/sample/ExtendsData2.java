package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * 継承関係チェック用クラス 継承用.
 * @author vvakame
 */
@JsonModel
public class ExtendsData2 extends ExtendsData1 {

	@JsonKey
	long five;


	/**
	 * @return the five
	 * @category accessor
	 */
	public long getFive() {
		return five;
	}

	/**
	 * @param five the five to set
	 * @category accessor
	 */
	public void setFive(long five) {
		this.five = five;
	}
}
