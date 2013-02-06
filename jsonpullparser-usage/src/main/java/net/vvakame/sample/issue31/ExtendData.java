package net.vvakame.sample.issue31;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Extend class from base class.
 * @author vvakame
 */
@JsonModel(builder = true)
public class ExtendData extends BaseData {

	@JsonKey
	String b;


	/**
	 * @return the b
	 * @category accessor
	 */
	public String getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(String b) {
		this.b = b;
	}
}
