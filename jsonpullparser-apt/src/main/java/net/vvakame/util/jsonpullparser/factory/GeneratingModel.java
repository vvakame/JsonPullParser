package net.vvakame.util.jsonpullparser.factory;

import java.util.ArrayList;
import java.util.List;

public class GeneratingModel {
	static List<String> ignoreImport;
	{
		ignoreImport = new ArrayList<String>();
		ignoreImport.add(String.class.getCanonicalName());
		ignoreImport.add(List.class.getCanonicalName());
		ignoreImport.add(ArrayList.class.getCanonicalName());
		ignoreImport.add("byte");
		ignoreImport.add("short");
		ignoreImport.add("int");
		ignoreImport.add("long");
		ignoreImport.add("char");
		ignoreImport.add("float");
		ignoreImport.add("double");
		ignoreImport.add("boolean");
	}

	String packageName = "invalid";
	String postfix = "Invalid";
	String target = "Invalid";

	List<String> imports = new ArrayList<String>();
	List<JsonElement> elements = new ArrayList<JsonElement>();
	boolean first = true;

	public void addImport(String importStr) {
		// Velocity is not support Set...?
		if (imports.contains(importStr)) {
			return;
		}

		String packageName = "";
		String fqnClassName = "";

		{
			int i;
			i = importStr.lastIndexOf(".");
			if (i > 0) {
				packageName = importStr.substring(0, i);
			} else {
				packageName = "";
			}
			i = importStr.lastIndexOf("<");
			if (i > 0) {
				fqnClassName = importStr.substring(0, i);
			} else {
				fqnClassName = importStr;
			}
		}

		if (this.packageName.equals(packageName)) {
			return;
		}
		if (ignoreImport.contains(fqnClassName)) {
			return;
		}

		imports.add(fqnClassName);
	}

	public void addJsonElement(JsonElement jsonElement) {
		jsonElement.first = first;
		elements.add(jsonElement);
		first = false;
	}

	/**
	 * @return the ignoreImport
	 */
	public static List<String> getIgnoreImport() {
		return ignoreImport;
	}

	/**
	 * @param ignoreImport
	 *            the ignoreImport to set
	 */
	public static void setIgnoreImport(List<String> ignoreImport) {
		GeneratingModel.ignoreImport = ignoreImport;
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
	 * @return the imports
	 */
	public List<String> getImports() {
		return imports;
	}

	/**
	 * @param imports
	 *            the imports to set
	 */
	public void setImports(List<String> imports) {
		this.imports = imports;
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
}
