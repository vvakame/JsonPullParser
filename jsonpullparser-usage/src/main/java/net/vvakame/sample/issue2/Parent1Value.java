package net.vvakame.sample.issue2;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Parent 1 output.
 * @author vvakame
 */
@JsonModel
public class Parent1Value {

	@JsonKey
	private int piyo;


	/**
	 * @return the piyo
	 * @category accessor
	 */
	public int getPiyo() {
		return piyo;
	}

	/**
	 * @param piyo the piyo to set
	 * @category accessor
	 */
	public void setPiyo(int piyo) {
		this.piyo = piyo;
	}
}
