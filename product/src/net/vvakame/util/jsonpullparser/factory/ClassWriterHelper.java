package net.vvakame.util.jsonpullparser.factory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

public class ClassWriterHelper {

	final String CLASS_POSTFIX;

	ProcessingEnvironment prosessingEnv;
	PrintWriter pw;
	Element classElement;

	public ClassWriterHelper(ProcessingEnvironment prosessingEnv,
			PrintWriter pw, Element classElement, String postfix) {
		this.prosessingEnv = prosessingEnv;
		this.pw = pw;
		this.classElement = classElement;
		CLASS_POSTFIX = postfix;
	}

	ClassWriterHelper wr(String str) {
		pw.print(str);
		return this;
	}

	ClassWriterHelper wr(Element... elements) {
		Elements utils = prosessingEnv.getElementUtils();
		utils.printElements(pw, elements);
		return this;
	}

	ClassWriterHelper wr(Class<?> clazz) {
		wr(clazz.getCanonicalName());
		return this;
	}

	ClassWriterHelper wr(Object obj) {
		wr(obj.toString());
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

	ClassWriterHelper writeListClassName() {
		this.wr(List.class).wr("<").writeClassName().wr(">");
		return this;
	}

	ClassWriterHelper writeListInstance() {
		this.wr(ArrayList.class).wr("<").writeClassName().wr(">");
		return this;
	}

	String getGenerateCanonicalClassName() {
		return getGenerateCanonicalClassName(classElement, CLASS_POSTFIX);
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
		String str = classElement.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}

	void flush() {
		pw.flush();
	}

	static String getGenerateCanonicalClassName(Element classElement,
			String postfix) {
		return classElement.asType().toString() + postfix;
	}
}
