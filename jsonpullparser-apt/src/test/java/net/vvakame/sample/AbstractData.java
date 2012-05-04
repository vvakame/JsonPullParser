package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Abstract class test. 
 * @author vvakame
 */
@JsonModel
public abstract class AbstractData {

	@JsonKey
	long one;


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
}
