package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Test for @JsonModel at inner class.
 * @author vvakame
 */
@JsonModel
public class ForInnerClass {

	@JsonKey
	int a = 0;


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
	 * Test for @JsonModel at inner class. (child) 
	 * @author vvakame
	 */
	@JsonModel
	public static class InnerClass1 {

		@JsonKey
		int b = 0;


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


		/**
		 * Test for @JsonModel at inner class. (grandchild) 
		 * @author vvakame
		 */
		@JsonModel
		public static class InnerClass2 {

			@JsonKey
			int c = 0;


			/**
			 * @return the c
			 * @category accessor
			 */
			public int getC() {
				return c;
			}

			/**
			 * @param c the c to set
			 * @category accessor
			 */
			public void setC(int c) {
				this.c = c;
			}
		}
	}
}
