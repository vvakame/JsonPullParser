package net.vvakame.util.jsonpullparser.factory;

import java.util.ArrayList;
import java.util.List;

public class GeneratingModel {

	String packageName = "invalid";
	String postfix = "Invalid";
	String target = "Invalid";

	List<JsonElement> elements = new ArrayList<JsonElement>();
	boolean first = true;
	boolean treatUnknownKeyAsError;

	public void addJsonElement(JsonElement jsonElement) {
		if (jsonElement == null) {
			return;
		}
		jsonElement.first = first;
		elements.add(jsonElement);
		first = false;
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
	 * @return the postfix
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * @param postfix
	 *            the postfix to set
	 */
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the elements
	 */
	public List<JsonElement> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 *            the elements to set
	 */
	public void setElements(List<JsonElement> elements) {
		this.elements = elements;
	}

	/**
	 * @return the first
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}

	/**
	 * @return the treatUnknownKeyAsError
	 */
	public boolean isTreatUnknownKeyAsError() {
		return treatUnknownKeyAsError;
	}

	/**
	 * @param treatUnknownKeyAsError
	 *            the treatUnknownKeyAsError to set
	 */
	public void setTreatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
	}
}
