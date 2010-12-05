package net.vvakame.util.jsonpullparser.factory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.DebugUtil;
import net.vvakame.util.jsonpullparser.annotation.JSONArray;
import net.vvakame.util.jsonpullparser.annotation.JSONHash;
import net.vvakame.util.jsonpullparser.annotation.JSONKey;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JSONAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		try {
			DebugUtil.getWriter(new File(
					"/Users/vvakame/Dropbox/work/JSONPullPurser/log"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		DebugUtil.write("test1");

		for (TypeElement annotation : annotations) {
			DebugUtil.write("test2", 1);
			DebugUtil.write(annotation.toString(), 1);

			for (Element element : roundEnv
					.getElementsAnnotatedWith(JSONHash.class)) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
						"JSONHash " + element.toString());
			}
			for (Element element : roundEnv
					.getElementsAnnotatedWith(JSONArray.class)) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
						"JSONArray " + element.toString());
			}

			for (Element element : roundEnv
					.getElementsAnnotatedWith(JSONKey.class)) {

				// ここからソース生成
				Filer filer = processingEnv.getFiler();
				try {
					JavaFileObject fileObject = filer.createSourceFile(
							element.getSimpleName() + "Gen", element);
					Writer writer = fileObject.openWriter();
					try {
						PrintWriter pw = new PrintWriter(writer);
						pw.println("package hogehoge;");
						pw.println("public class Testhoge{}");
						pw.flush();
					} finally {
						writer.close();
					}

					processingEnv.getMessager().printMessage(
							Diagnostic.Kind.NOTE,
							"Creating " + fileObject.toUri());
				} catch (IOException e) {
					processingEnv.getMessager().printMessage(
							Diagnostic.Kind.ERROR, e.getMessage());
				}
				// ここまでソース生成

				TypeMirror type = element.asType();

				DebugUtil.write("test3", 2);
				DebugUtil.write(type.toString(), 2);
				DebugUtil.write("enclosed", 2);
				DebugUtil.write(element.getEnclosedElements().toString(), 2);
				DebugUtil.write("enclosing", 2);
				DebugUtil.write(element.getEnclosingElement().toString(), 2);
				DebugUtil.write("simplename", 2);
				DebugUtil.write(element.getSimpleName().toString(), 2);

				JSONKey palindrome = element.getAnnotation(JSONKey.class);

				if (palindrome != null) {
					// 警告メッセージを出力
					Messager messager = processingEnv.getMessager();
					String message = String.format("とりあえずだめ=%s",
							palindrome.value());
					messager.printMessage(Diagnostic.Kind.WARNING, message,
							element);
				}
			}
		}
		return true;
	}
}
