package net.vvakame.sample;

import java.util.List;

import net.vvakame.sample.ForInnerClass.InnerClass1.InnerClass2;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Test for @JsonModel at inner class.
 * @author vvakame
 */
@JsonModel(builder = true)
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
	// @JsonModel
	// is this bug? can't find up Element on apt round.
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
		@JsonModel(builder = true)
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

	/**
	 * Test for @JsonModel at inner class. (field has inner class) 
	 * @author vvakame
	 */
	@JsonModel(builder = true)
	public static class InnerClass3 {

		@JsonKey
		InnerClass2 a;

		@JsonKey
		List<InnerClass2> b;


		/**
		 * @return the a
		 * @category accessor
		 */
		public InnerClass2 getA() {
			return a;
		}

		/**
		 * @param a the a to set
		 * @category accessor
		 */
		public void setA(InnerClass2 a) {
			this.a = a;
		}

		/**
		 * @return the b
		 * @category accessor
		 */
		public List<InnerClass2> getB() {
			return b;
		}

		/**
		 * @param b the b to set
		 * @category accessor
		 */
		public void setB(List<InnerClass2> b) {
			this.b = b;
		}
	}
}
