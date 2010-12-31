package net.vvakame.util.jsonpullparser.factory;

import java.util.ArrayList;
import java.util.List;

public class GeneratingModel {
	String packageName = "invalid";
	String postfix = "Invalid";
	String target = "Invalid";

	List<String> imports = new ArrayList<String>();
	List<JsonElement> elements = new ArrayList<JsonElement>();
	boolean first = true;

	public void addImport(String importStr) {
		// Velocity is not support Set...?
		if (!imports.contains(importStr)) {
			imports.add(importStr);
		}
	}

	public void addJsonElement(JsonElement jsonElement) {
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
}
