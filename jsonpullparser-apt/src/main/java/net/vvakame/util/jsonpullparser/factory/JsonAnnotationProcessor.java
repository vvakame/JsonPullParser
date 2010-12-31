package net.vvakame.util.jsonpullparser.factory;

import static javax.lang.model.util.ElementFilter.typesIn;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JsonAnnotationProcessor extends AbstractProcessor {

	private static final String CLASS_POSTFIX_OPTION = "JsonPullParserClassPostfix";

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		Log.init(processingEnv.getMessager());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		ClassGenerateHelper.init(processingEnv);

		// 生成するクラスのpostfixが指定されてたらそっちにする
		{
			String postfix;
			Map<String, String> options = processingEnv.getOptions();
			if (options.containsKey(CLASS_POSTFIX_OPTION)
					&& !"".equals(options.get(CLASS_POSTFIX_OPTION))) {
				postfix = options.get(CLASS_POSTFIX_OPTION);
			} else {
				postfix = "Gen";
			}
			ClassGenerateHelper.setPostfix(postfix);
		}

		for (Element element : typesIn(roundEnv
				.getElementsAnnotatedWith(JsonModel.class))) {

			ClassGenerateHelper generater = ClassGenerateHelper
					.newInstance(element);

			generater.process();

			// 構文上のエラーに遭遇していたら処理を中断する
			if (generater.isEncountError()) {
				continue;
			}

			try {
				generater.write();
			} catch (IOException e) {
				Log.e(e);
			}
		}

		return true;
	}
}
