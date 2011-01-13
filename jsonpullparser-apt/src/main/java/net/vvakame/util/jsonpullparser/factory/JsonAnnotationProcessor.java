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
	private static final String DEBUG_OPTION = "JsonPullParserDebug";

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		Log.init(processingEnv.getMessager());

		// デバッグログ出力の設定を行う
		String debug = getOption(DEBUG_OPTION);
		if ("true".equalsIgnoreCase(debug)) {
			Log.setDebug(true);
		}

		Log.d("init JsonAnotationProcessor");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		ClassGenerateHelper.init(processingEnv);

		Log.d("start process method.");

		// 生成するクラスのpostfixが指定されてたらそっちにする
		String postfix;
		String optPostfix = getOption(CLASS_POSTFIX_OPTION);
		if (optPostfix == null) {
			postfix = "Gen";
		} else {
			postfix = optPostfix;
		}
		ClassGenerateHelper.setPostfix(postfix);

		for (Element element : typesIn(roundEnv
				.getElementsAnnotatedWith(JsonModel.class))) {

			Log.d("process " + element.toString());

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

	String getOption(String key) {
		Map<String, String> options = processingEnv.getOptions();
		if (options.containsKey(key) && !"".equals(options.get(key))) {
			return options.get(key);
		} else {
			return null;
		}
	}
}
