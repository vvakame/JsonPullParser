package net.vvakame.sample;

import java.util.List;

import net.vvakame.sample.anotherpackage.AnotherPackageClass;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel
public class ContainsAnotherPackageObjectData {

	@JsonKey
	private AnotherPackageClass anotherPackageClass;

	@JsonKey
	private List<AnotherPackageClass> anotherPackageClasses;


	public AnotherPackageClass getAnotherPackageClass() {
		return anotherPackageClass;
	}

	public void setAnotherPackageClass(AnotherPackageClass anotherPackageClass) {
		this.anotherPackageClass = anotherPackageClass;
	}

	public List<AnotherPackageClass> getAnotherPackageClasses() {
		return anotherPackageClasses;
	}

	public void setAnotherPackageClasses(List<AnotherPackageClass> anotherPackageClasses) {
		this.anotherPackageClasses = anotherPackageClasses;
	}

}
