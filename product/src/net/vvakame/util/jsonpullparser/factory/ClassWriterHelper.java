package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

public class ClassWriterHelper {

	String classPostfix;

	ProcessingEnvironment processingEnv;
	PrintWriter pw;
	Element classElement;
	Element holder;
	Mode mode;

	enum Mode {
		Real, Mock
	}

	public ClassWriterHelper(ProcessingEnvironment prosessingEnv,
			Element classElement, String postfix) throws IOException {
		this(prosessingEnv, classElement, postfix, Mode.Real);
	}

	public ClassWriterHelper(ProcessingEnvironment prosessingEnv,
			Element classElement, String postfix, Mode mode) throws IOException {

		if (mode == null) {
			throw new IllegalStateException();
		} else {
			this.mode = mode;
		}
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}

		this.processingEnv = prosessingEnv;
		this.classElement = classElement;
		this.holder = classElement; // 初期値
		classPostfix = postfix;

		Filer filer = processingEnv.getFiler();

		String generateClassName = getGenerateCanonicalClassName(classElement);
		JavaFileObject fileObject;
		Writer writer = null;
		try {
			fileObject = filer
					.createSourceFile(generateClassName, classElement);
			writer = fileObject.openWriter();
			this.pw = new PrintWriter(writer);
		} catch (IOException e) {
			if (writer != null) {
				writer.close();
				writer = null;
				this.pw = null;
			}
			throw e;
		}
	}

	ClassWriterHelper wr(String str) {
		pw.print(str);
		return this;
	}

	ClassWriterHelper wr(Element... elements) {
		Elements utils = processingEnv.getElementUtils();
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
		return getGenerateCanonicalClassName(classElement, classPostfix);
	}

	String getGenerateCanonicalClassName(Element element) {
		return element.asType().toString() + classPostfix;
	}

	String getGenerateCanonicalClassName(TypeMirror type) {
		Element element = processingEnv.getTypeUtils().asElement(type);
		return getGenerateCanonicalClassName(element);
	}

	String getGenerateClassName() {
		return classElement.getSimpleName().toString() + classPostfix;
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

	void close() {
		pw.close();
	}

	static String getGenerateCanonicalClassName(Element classElement,
			String postfix) {
		return classElement.asType().toString() + postfix;
	}

	/**
	 * @return the holder
	 */
	public Element getHolder() {
		return holder;
	}

	/**
	 * @param holder
	 *            the holder to set
	 */
	public void setHolder(Element holder) {
		this.holder = holder;
	}

	/**
	 * @return the classPostfix
	 */
	public String getClassPostfix() {
		return classPostfix;
	}
}
