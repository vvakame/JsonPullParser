package net.vvakame.sample;

import net.vvakame.util.jsonpullparser.annotation.JSONHash;
import net.vvakame.util.jsonpullparser.annotation.JSONKey;

@JSONHash
public class TestData1 {
	@JSONKey
	String name;

	@JSONKey("package_name")
	String packageName;

	@JSONKey("version_code")
	int versionCode;

	@JSONKey
	double weight;

	@JSONKey("has_data")
	boolean hasData;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the versionCode
	 */
	public int getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode
	 *            the versionCode to set
	 */
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the hasData
	 */
	public boolean isHasData() {
		return hasData;
	}

	/**
	 * @param hasData
	 *            the hasData to set
	 */
	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
}
