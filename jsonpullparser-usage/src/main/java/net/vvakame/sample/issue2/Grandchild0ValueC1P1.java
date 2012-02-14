package net.vvakame.sample.issue2;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Parent 1 output, Child 1 output, Grandchild 0 output.
 * @author vvakame
 */
@JsonModel
public class Grandchild0ValueC1P1 extends Child1ValueP1 {

	@JsonKey(out = false)
	private int fuga;


	/**
	 * @return the fuga
	 * @category accessor
	 */
	public int getFuga() {
		return fuga;
	}

	/**
	 * @param fuga the fuga to set
	 * @category accessor
	 */
	public void setFuga(int fuga) {
		this.fuga = fuga;
	}
}
