package net.vvakame.sample.issue30;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Class for issue30, recursive structure.
 * @author vvakame
 */
@JsonModel(builder = true)
public class RecursiveStructure {

	@JsonKey
	int a;

	@JsonKey
	long b;

	@JsonKey
	String c;

	@JsonKey
	RecursiveStructure self;


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
	public long getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 * @category accessor
	 */
	public void setB(long b) {
		this.b = b;
	}

	/**
	 * @return the c
	 * @category accessor
	 */
	public String getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 * @category accessor
	 */
	public void setC(String c) {
		this.c = c;
	}

	/**
	 * @return the self
	 * @category accessor
	 */
	public RecursiveStructure getSelf() {
		return self;
	}

	/**
	 * @param self the self to set
	 * @category accessor
	 */
	public void setSelf(RecursiveStructure self) {
		this.self = self;
	}
}
