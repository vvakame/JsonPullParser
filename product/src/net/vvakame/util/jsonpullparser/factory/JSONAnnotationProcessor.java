package net.vvakame.util.jsonpullparser.factory;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import net.vvakame.util.jsonpullparser.annotation.JSONKey;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JSONAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			for (Element element : roundEnv
					.getElementsAnnotatedWith(annotation)) {
				JSONKey palindrome = element.getAnnotation(JSONKey.class);

				// (4) Palindrome アノテーションの値が回文になっているか判定
				// 警告メッセージを出力
				Messager messager = processingEnv.getMessager();
				String message = String.format("「%s」は回文ではありません",
						palindrome.value());
				messager.printMessage(Diagnostic.Kind.WARNING, message, element);
			}
		}
		return true;
	}
}
