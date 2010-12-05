package net.vvakame.util.jsonpullparser.factory;

import java.io.PrintWriter;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ClassWriterHelper {

	final String CLASS_POSTFIX;

	PrintWriter pw;
	Element classElement;

	public ClassWriterHelper(PrintWriter pw, Element classElement,
			String postfix) {
		this.pw = pw;
		this.classElement = classElement;
		CLASS_POSTFIX = postfix;
	}

	ClassWriterHelper wr(String str) {
		pw.print(str);
		return this;
	}

	ClassWriterHelper wr(Class<?> clazz) {
		wr(clazz.getSimpleName());
		return this;
	}

	ClassWriterHelper writePackage() {
		pw.print("package ");
		pw.print(getPackageName());
		pw.println(";");
		return this;
	}

	ClassWriterHelper writeImport(Class<?> clazz) {
		pw.print("import ");
		pw.print(clazz.getCanonicalName());
		pw.println(";");
		return this;
	}

	ClassWriterHelper writeClassSignature() {
		pw.print("public class ");
		pw.print(getGenerateClassName());
		pw.print("{");
		return this;
	}

	ClassWriterHelper writeClassName() {
		pw.print(getClassName(classElement));
		return this;
	}

	String getGenerateCanonicalClassName() {
		return getPackageName() + "." + getGenerateClassName();
	}

	String getGenerateClassName() {
		return classElement.getSimpleName().toString() + CLASS_POSTFIX;
	}

	String getClassName() {
		return classElement.getSimpleName().toString();
	}

	String getPackageName() {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.getEnclosingElement().toString();
		return str.replace("package ", "");
	}

	void flush() {
		pw.flush();
	}

	static String getGenerateCanonicalClassName(Element classElement,
			String postfix) {
		return getPackageName(classElement) + "."
				+ getGenerateClassName(classElement, postfix);
	}

	static String getGenerateClassName(Element classElement, String postfix) {
		return classElement.getSimpleName().toString() + postfix;
	}

	static String getClassName(Element classElement) {
		return classElement.getSimpleName().toString();
	}

	static String getPackageName(Element classElement) {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.getEnclosingElement().toString();
		return str.replace("package ", "");
	}
}
