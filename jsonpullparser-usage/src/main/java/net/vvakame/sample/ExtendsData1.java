package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Inheritance chain test class, for subclasses.
 * @author vvakame
 */
@JsonModel
public class ExtendsData1 extends BaseData {

	@JsonKey
	long three;

	@JsonKey
	long four;


	/**
	 * @return the three
	 * @category accessor
	 */
	public long getThree() {
		return three;
	}

	/**
	 * @param three the three to set
	 * @category accessor
	 */
	public void setThree(long three) {
		this.three = three;
	}

	/**
	 * @return the four
	 * @category accessor
	 */
	public long getFour() {
		return four;
	}

	/**
	 * @param four the four to set
	 * @category accessor
	 */
	public void setFour(long four) {
		this.four = four;
	}
}
