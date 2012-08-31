package net.vvakame.sample.anotherpackage;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Test for member has class on another package. 
 * @author backpaper0
 */
@JsonModel
public class AnotherPackageClass {

	@JsonKey
	private String value;


	/**
	 * @return the value
	 * @category accessor
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 * @category accessor
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
