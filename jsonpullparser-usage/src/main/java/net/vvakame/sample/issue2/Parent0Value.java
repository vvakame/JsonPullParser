package net.vvakame.sample.issue2;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Parent 0 output.
 * @author vvakame
 */
@JsonModel
public class Parent0Value {

	@JsonKey(out = false)
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
