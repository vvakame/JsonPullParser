package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
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
import net.vvakame.util.jsonpullparser.annotation.JsonHash;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.vvakame.util.jsonpullparser.annotation.*")
public class JsonAnnotationProcessor extends AbstractProcessor {

	private static final String CLASS_POSTFIX = "Gen";

	Set<? extends TypeElement> annotations;
	RoundEnvironment roundEnv;

	PrintWriter pw;

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		this.annotations = annotations;
		this.roundEnv = roundEnv;

		Log.init(processingEnv.getMessager());

		for (Element element : roundEnv
				.getElementsAnnotatedWith(JsonHash.class)) {

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
				pw = new PrintWriter(writer);

				writePackage(classElement);
				writeImport(IOException.class);
				writeImport(JsonPullParser.class);
				writeImport(JsonPullParser.State.class);
				writeImport(JsonFormatException.class);

				write("public class ");
				write(getGenerateClassName(classElement));
				write("{");

				// ここからgetメソッド
				write("public static ");
				write(getClassName(classElement));
				write(" get(JSONPullParser parser) throws IOException, JSONFormatException {");

				// 結果用変数生成
				write(getClassName(classElement));
				write(" obj = new ");
				write(getClassName(classElement));
				write("();");
				// 最初のbraceを食べる TODO Arrayが考慮されていない
				write("State eventType = parser.getEventType();");
				write("if (eventType != State.START_HASH) {");
				write("throw new IllegalStateException(\"not started hash brace!\");");
				write("}");
				// ループ処理共通部分生成
				write("while ((eventType = parser.getEventType()) != State.END_HASH) {");
				write("if (eventType != State.KEY) {");
				write("throw new IllegalStateException(\"expect KEY. we got unexpected value. \" + eventType);");
				write("}");
				write("String key = parser.getValueString();");
				write("eventType = parser.getEventType();");
				// 値の独自処理
				// JSONKeyの収集
				List<ElementValues> values = filterJSONKey(classElement);
				boolean first = true;
				for (ElementValues value : values) {
					if (first) {
						first = false;
					} else {
						write("else ");
					}
					write("if(\"");
					write(value.keyName);
					write("\".equals(key)){");
					// 代入処理
					write("obj." + value.accessor + " = "
							+ getValueString(value.type) + ";");

					write("}");
				}
				// TODO JSONValueの取得
				// TODO 対象の型判別

				// TODO 本来いらん
				write("}");
				// 返り値の処理
				write("return obj;}");
				write("}");

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

	private List<ElementValues> filterJSONKey(Element parent) {
		List<? extends Element> elements = parent.getEnclosedElements();
		List<ElementValues> results = new ArrayList<ElementValues>();

		for (Element element : elements) {
			if (element.getKind() != ElementKind.FIELD) {
				continue;
			}
			JsonKey key = element.getAnnotation(JsonKey.class);
			if (key == null) {
				continue;
			}
			ElementValues values = new ElementValues();
			if (!"".equals(key.value())) {
				values.keyName = key.value();
			} else {
				values.keyName = element.toString();
			}
			values.type = element.asType().toString();
			values.accessor = element.toString();
			results.add(values);
		}

		return results;
	}

	static class ElementValues {
		String keyName;
		String type;
		String accessor;
	}

	void write(String str) {
		pw.print(str);
	}

	void writePackage(Element classElement) {
		pw.print("package ");
		pw.print(getPackageName(classElement));
		pw.println(";");
	}

	void writeImport(Class<?> clazz) {
		pw.print("import ");
		pw.print(clazz.getCanonicalName());
		pw.println(";");
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

	String getGenerateCanonicalClassName(Element classElement) {
		return getPackageName(classElement) + "."
				+ getGenerateClassName(classElement);
	}

	String getGenerateClassName(Element classElement) {
		return classElement.getSimpleName().toString() + CLASS_POSTFIX;
	}

	String getClassName(Element classElement) {
		return classElement.getSimpleName().toString();
	}

	String getPackageName(Element classElement) {
		if (classElement.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = classElement.getEnclosingElement().toString();
		return str.replace("package ", "");
	}
}
