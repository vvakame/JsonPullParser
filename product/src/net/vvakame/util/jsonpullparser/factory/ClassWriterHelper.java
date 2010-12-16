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
	boolean encountError = false;

	int indentLevel = 0;
	boolean lineFeed = true;

	public enum Mode {
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

		if (mode == Mode.Mock) {
			return;
		}

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

	public ClassWriterHelper wr() {
		if (mode == Mode.Real && pw != null) {
			if (lineFeed) {
				writeIndent();
				lineFeed = false;
			}
			pw.print("");
		}
		return this;
	}

	public ClassWriterHelper wr(String str) {
		if (mode == Mode.Real && pw != null) {
			if (lineFeed) {
				writeIndent();
				lineFeed = false;
			}
			pw.print(str);
		}
		return this;
	}

	public ClassWriterHelper wr(Element... elements) {
		if (mode == Mode.Real && pw != null) {
			if (lineFeed) {
				writeIndent();
				lineFeed = false;
			}
			Elements utils = processingEnv.getElementUtils();
			utils.printElements(pw, elements);
		}
		return this;
	}

	public ClassWriterHelper wr(Class<?> clazz) {
		wr(clazz.getCanonicalName());
		return this;
	}

	public ClassWriterHelper wr(Object obj) {
		wr(obj.toString());
		return this;
	}

	public ClassWriterHelper ln() {
		wr("\n");
		lineFeed = true;
		return this;
	}

	public ClassWriterHelper lni() {
		incrementIndent();
		ln();
		return this;
	}

	public ClassWriterHelper lnd() {
		decrementIndent();
		ln();
		return this;
	}

	public ClassWriterHelper writePackage() {
		if (mode == Mode.Real && pw != null) {
			pw.print("package ");
			pw.print(getPackageName());
			pw.println(";");
			ln();
		}
		return this;
	}

	public ClassWriterHelper writeClassSignature() {
		if (mode == Mode.Real && pw != null) {
			pw.print("public class ");
			pw.print(getGenerateClassName());
			pw.print("{");
			ln();
		}
		return this;
	}

	public ClassWriterHelper writeClassName() {
		if (mode == Mode.Real && pw != null) {
			pw.print(getClassName());
		}
		return this;
	}

	public ClassWriterHelper writeListClassName() {
		this.wr(List.class).wr("<").writeClassName().wr(">");
		return this;
	}

	public ClassWriterHelper writeListInstance() {
		this.wr(ArrayList.class).wr("<").writeClassName().wr(">");
		return this;
	}

	public String getGenerateCanonicalClassName() {
		return classElement.asType().toString() + classPostfix;
	}

	public String getGenerateCanonicalClassName(Element element) {
		return element.asType().toString() + classPostfix;
	}

	public String getGenerateCanonicalClassName(TypeMirror type) {
		Element element = processingEnv.getTypeUtils().asElement(type);
		return getGenerateCanonicalClassName(element);
	}

	public String getGenerateClassName() {
		return classElement.getSimpleName().toString() + classPostfix;
	}

	public String getClassName() {
		return classElement.getSimpleName().toString();
	}

	public String getPackageName() {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}

	public void flush() {
		if (mode == Mode.Real && pw != null) {
			pw.flush();
		}
	}

	public void close() {
		if (mode == Mode.Real && pw != null) {
			pw.close();
		}
	}

	public void incrementIndent() {
		indentLevel++;
	}

	public void decrementIndent() {
		if (0 < indentLevel) {
			indentLevel--;
		}
	}

	void writeIndent() {
		if (mode == Mode.Real && pw != null) {
			for (int i = 0; i < indentLevel; i++) {
				pw.write("\t");
			}
		}
	}

	public void setIndentLevel(int indentLevel) {
		this.indentLevel = indentLevel;
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

	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}

	/**
	 * @param encountError
	 *            the encountError to set
	 */
	public void setEncountError(boolean encountError) {
		this.encountError = encountError;
	}
}
