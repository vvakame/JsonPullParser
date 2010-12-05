package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.Log;
import net.vvakame.util.jsonpullparser.annotation.JSONHash;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JSONAnnotationProcessor extends AbstractProcessor {

	private static final String CLASS_POSTFIX = "Gen";

	Set<? extends TypeElement> annotations;
	RoundEnvironment roundEnv;

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		this.annotations = annotations;
		this.roundEnv = roundEnv;

		Log.init(processingEnv.getMessager());

		for (Element element : roundEnv
				.getElementsAnnotatedWith(JSONHash.class)) {

			if (element.getKind() == ElementKind.CLASS) {
				genMetaClass(element);
			}
		}

		return true;
	}

	void genMetaClass(Element classElement) {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}

		// ここからソース生成
		Filer filer = processingEnv.getFiler();
		try {
			JavaFileObject fileObject = filer.createSourceFile(
					getGenerateCanonicalClassName(classElement), classElement);
			Writer writer = fileObject.openWriter();
			try {
				PrintWriter pw = new PrintWriter(writer);
				pw.print("package ");
				pw.print(getPackageName(classElement));
				pw.println(";");
				pw.print("public class ");
				pw.print(getGenerateClassName(classElement));
				pw.println("{}");
				pw.flush();
			} finally {
				writer.close();
			}

			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
					"Creating " + fileObject.toUri());
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
					e.getMessage());
		}
	}

	String getGenerateCanonicalClassName(Element classElement) {
		return getPackageName(classElement) + "."
				+ getGenerateClassName(classElement);
	}

	String getGenerateClassName(Element classElement) {
		return classElement.getSimpleName().toString() + CLASS_POSTFIX;
	}

	String getPackageName(Element classElement) {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.getEnclosingElement().toString();
		return str.replace("package ", "");
	}
}
