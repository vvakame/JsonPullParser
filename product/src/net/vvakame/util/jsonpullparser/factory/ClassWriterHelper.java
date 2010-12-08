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
		wr(clazz.getCanonicalName());
		return this;
	}

	ClassWriterHelper writePackage() {
		pw.print("package ");
		pw.print(getPackageName());
		pw.println(";");
		return this;
	}

	@Deprecated
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
		pw.print(getClassName());
		return this;
	}

	String getGenerateCanonicalClassName() {
		return getGenerateCanonicalClassName(classElement, CLASS_POSTFIX);
	}

	String getGenerateClassName() {
		return getGenerateClassName(classElement, CLASS_POSTFIX);
	}

	String getClassName() {
		return getClassName(classElement);
	}

	String getPackageName() {
		return getPackageName(classElement);
	}

	void flush() {
		pw.flush();
	}

	static String getGenerateCanonicalClassName(Element classElement,
			String postfix) {
		return classElement.asType().toString() + postfix;
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
		String str = classElement.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}
}
