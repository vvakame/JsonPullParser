package net.vvakame.sample.issue31;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Base class.
 * @author vvakame
 */
@JsonModel(builder = true)
public class BaseData {

	@JsonKey
	int a;


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
}
