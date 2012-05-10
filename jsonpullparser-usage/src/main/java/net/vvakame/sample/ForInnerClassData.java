package net.vvakame.sample;

import java.util.List;

import net.vvakame.sample.ForInnerClassData.InnerClassA.InnerClassAB;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Generate class for inner class. 
 * @author vvakame
 */
@JsonModel(builder = true)
public class ForInnerClassData {

	@JsonKey
	int a;


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
	 * Generate class for inner class. 
	 * @author vvakame
	 */
	// @JsonModel
	public static class InnerClassA {

		@JsonKey
		int b;


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
		 * Generate class for inner class. 
		 * @author vvakame
		 */
		@JsonModel(builder = true)
		public static class InnerClassAB {

			@JsonKey
			int c;


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
	 * Generate class for inner class. 
	 * @author vvakame
	 */
	@JsonModel(builder = true)
	public static class InnerClassB {

		@JsonKey
		InnerClassAB d;

		@JsonKey
		List<InnerClassAB> e;


		/**
		 * @return the d
		 * @category accessor
		 */
		public InnerClassAB getD() {
			return d;
		}

		/**
		 * @param d the d to set
		 * @category accessor
		 */
		public void setD(InnerClassAB d) {
			this.d = d;
		}

		/**
		 * @return the e
		 * @category accessor
		 */
		public List<InnerClassAB> getE() {
			return e;
		}

		/**
		 * @param e the e to set
		 * @category accessor
		 */
		public void setE(List<InnerClassAB> e) {
			this.e = e;
		}
	}
}
