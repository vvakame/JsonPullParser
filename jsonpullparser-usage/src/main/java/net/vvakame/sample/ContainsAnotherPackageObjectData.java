package net.vvakame.sample;

import java.util.List;

import net.vvakame.sample.anotherpackage.AnotherPackageClass;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Test for member has class on another package. 
 * @author backpaper0
 */
@JsonModel(builder = true)
public class ContainsAnotherPackageObjectData {

	@JsonKey
	private AnotherPackageClass anotherPackageClass;

	@JsonKey
	private List<AnotherPackageClass> anotherPackageClasses;


	/**
	 * @return the anotherPackageClass
	 * @category accessor
	 */
	public AnotherPackageClass getAnotherPackageClass() {
		return anotherPackageClass;
	}

	/**
	 * @param anotherPackageClass the anotherPackageClass to set
	 * @category accessor
	 */
	public void setAnotherPackageClass(AnotherPackageClass anotherPackageClass) {
		this.anotherPackageClass = anotherPackageClass;
	}

	/**
	 * @return the anotherPackageClasses
	 * @category accessor
	 */
	public List<AnotherPackageClass> getAnotherPackageClasses() {
		return anotherPackageClasses;
	}

	/**
	 * @param anotherPackageClasses the anotherPackageClasses to set
	 * @category accessor
	 */
	public void setAnotherPackageClasses(List<AnotherPackageClass> anotherPackageClasses) {
		this.anotherPackageClasses = anotherPackageClasses;
	}
}
