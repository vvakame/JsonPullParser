package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.annotation.JsonHash;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JsonAnnotationProcessor extends AbstractProcessor {

	private static final String CLASS_PREFIX_OPTION = "JsonPullParserClassPrefix";
	private static String classPostfix;

	Set<? extends TypeElement> annotations;
	RoundEnvironment roundEnv;

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		Log.init(processingEnv.getMessager());
		this.annotations = annotations;
		this.roundEnv = roundEnv;

		// 生成するクラスのpostfixが指定されてたらそっちにする
		Map<String, String> options = processingEnv.getOptions();
		if (options.containsKey(CLASS_PREFIX_OPTION)) {
			classPostfix = options.get(CLASS_PREFIX_OPTION);
		} else {
			classPostfix = "Gen";
		}

		for (Element element : roundEnv
				.getElementsAnnotatedWith(JsonHash.class)) {

			if (element.getKind() == ElementKind.CLASS) {
				genSupportClass(element);
			}
		}

		return true;
	}

	void genSupportClass(Element classElement) {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}

		// ここからソース生成
		Filer filer = processingEnv.getFiler();
		try {

			String generateClassName = ClassWriterHelper
					.getGenerateCanonicalClassName(classElement, classPostfix);
			JavaFileObject fileObject = filer.createSourceFile(
					generateClassName, classElement);

			Writer writer = fileObject.openWriter();
			try {
				ClassWriterHelper w = new ClassWriterHelper(new PrintWriter(
						writer), classElement, classPostfix);

				// package名出力
				w.writePackage();

				// コメント出力
				w.wr("// Do you know Ctrl(Command)+Shift+M?\n");

				// class宣言出力
				w.writeClassSignature();

				// ここからgetメソッド signiture
				w.wr("public static ").writeClassName();
				w.wr(" get(").wr(JsonPullParser.class).wr(" parser) throws ");
				w.wr(IOException.class).wr(", ");
				w.wr(JsonFormatException.class).wr("{");

				// 結果用変数生成
				w.writeClassName().wr(" obj = new ").writeClassName().wr("();");
				// 最初のbraceを食べる TODO Arrayが考慮されていない
				w.wr(State.class).wr(" eventType = parser.getEventType();");
				w.wr("if (eventType != ").wr(State.class).wr(".START_HASH) {");
				w.wr("throw new IllegalStateException(\"not started hash brace!\");");
				w.wr("}");
				// ループ処理共通部分生成
				w.wr("while ((eventType = parser.getEventType()) != ");
				w.wr(State.class).wr(".").wr(State.END_HASH.toString());
				w.wr("){");
				w.wr("if (eventType != ").wr(State.class).wr(".KEY) {");
				w.wr("throw new IllegalStateException(\"expect KEY. we got unexpected value. \" + eventType);");
				w.wr("}");
				w.wr("String key = parser.getValueString();");
				w.wr("eventType = parser.getEventType();");

				// 値の独自処理
				// JsonKeyの収集
				List<Element> elements = filterJsonKeyElement(classElement);
				// JsonKeyに対応する値取得コードを生成する
				boolean first = true;
				for (Element element : elements) {
					if (first) {
						first = false;
					} else {
						w.wr("else ");
					}
					genExtractValues(w, element);
				}

				// TODO 本来いらん
				w.wr("}");
				// 返り値の処理
				w.wr("return obj;}");
				w.wr("}");

				w.flush();
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

	private void genExtractValues(ClassWriterHelper w, Element element) {
		JsonHash hash = element.getAnnotation(JsonHash.class);
		if (hash != null) {

		}

		ElementValues value = getElementValues(element);
		w.wr("if(\"").wr(value.keyName).wr("\".equals(key)){");
		w.wr("obj.").wr(value.accessor).wr(" = ");
		w.wr(getValueString(value.type)).wr(";");
		w.wr("}");
	}

	private List<Element> filterJsonKeyElement(Element parent) {
		List<? extends Element> elements = parent.getEnclosedElements();
		List<Element> results = new ArrayList<Element>();

		for (Element element : elements) {
			if (element.getKind() != ElementKind.FIELD) {
				continue;
			}
			JsonKey key = element.getAnnotation(JsonKey.class);
			if (key == null) {
				continue;
			}
			results.add(element);
		}

		return results;
	}

	private ElementValues getElementValues(Element element) {
		JsonKey key = element.getAnnotation(JsonKey.class);
		ElementValues values = new ElementValues();
		if (!"".equals(key.value())) {
			values.keyName = key.value();
		} else {
			values.keyName = element.toString();
		}
		values.type = element.asType().toString();
		values.accessor = element.toString();
		return values;
	}

	static class ElementValues {
		String keyName;
		String type;
		String accessor;
	}

	String getValueString(String type) {
		if ("int".equals(type)) {
			return "parser.getValueInteger()";

		} else if ("double".equals(type)) {
			return "parser.getValueDouble()";

		} else if ("boolean".equals(type)) {
			return "parser.getValueBoolean()";

		} else if ("java.lang.String".equals(type)) {
			return "parser.getValueString()";

		} else if ("short".equals(type)) {
			return "(short)parser.getValueInteger()";

		} else if ("long".equals(type)) {
			return "parser.getValueInteger()";

		} else if ("float".equals(type)) {
			return "(float)parser.getValueDouble()";

		} else if ("byte".equals(type)) {
			return "(byte)parser.getValueInteger()";

		} else if ("char".equals(type)) {
			return "parser.getValueString().charAt(0)";

		} else {
			Log.d("unknown type=" + type);
			return null;
		}
	}
}
