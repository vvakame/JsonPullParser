package net.vvakame.sample.issue2;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Parent 1 output, Child 0 output.
 * @author vvakame
 */
@JsonModel
public class Child0ValueP1 extends Parent1Value {

	@JsonKey(out = false)
	private int hoge;


	/**
	 * @return the hoge
	 * @category accessor
	 */
	public int getHoge() {
		return hoge;
	}

	/**
	 * @param hoge the hoge to set
	 * @category accessor
	 */
	public void setHoge(int hoge) {
		this.hoge = hoge;
	}
}
