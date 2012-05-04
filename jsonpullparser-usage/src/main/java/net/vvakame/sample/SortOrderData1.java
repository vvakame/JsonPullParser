package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Test for {@link JsonKey#sortOrder()}.
 * @author vvakame
 */
@JsonModel
public class SortOrderData1 {

	@JsonKey(sortOrder = 1)
	int a;

	@JsonKey(sortOrder = 2)
	int b;


	/**
	 * @return the a
	 * @category accessor
	 */
	public int getA() {
		return a;
	}

	/**
	 * @param a the a to set
	 * @category accessor
	 */
	public void setA(int a) {
		this.a = a;
	}

	/**
	 * @return the b
	 * @category accessor
	 */
	public int getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(int b) {
		this.b = b;
	}
}
