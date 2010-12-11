package net.vvakame.util.jsonpullparser.factory;

import static javax.lang.model.util.ElementFilter.typesIn;

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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
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

		for (Element element : typesIn(roundEnv
				.getElementsAnnotatedWith(JsonHash.class))) {

			genSupportClass(element);
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
				ClassWriterHelper w = new ClassWriterHelper(processingEnv,
						new PrintWriter(writer), classElement, classPostfix);

				// package名出力
				w.writePackage();

				// コメント出力
				w.wr("// Do you know Ctrl(Command)+Shift+M?\n");

				// class宣言出力
				w.writeClassSignature();

				genMethodGet(w, classElement);

				genMethodGetList(w, classElement);

				w.wr("}");

				w.flush();
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			Log.e(e.getMessage());
		}
	}

	private void genMethodGet(ClassWriterHelper w, Element classElement) {
		w.wr("public static ").writeClassName();
		w.wr(" get(").wr(JsonPullParser.class).wr(" parser) throws ");
		w.wr(IOException.class).wr(", ");
		w.wr(JsonFormatException.class).wr("{");

		// 結果用変数生成
		w.writeClassName().wr(" obj = new ").writeClassName().wr("();");
		// 最初のbraceを食べる
		w.wr(State.class).wr(" eventType = parser.getEventType();");
		w.wr("if (eventType != ").wr(State.class).wr(".").wr("START_HASH");
		w.wr(") {");
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

		w.wr("}");
		// 返り値の処理
		w.wr("return obj;}");
	}

	private void genMethodGetList(ClassWriterHelper w, Element classElement) {
		w.wr("public static ").writeListClassName();
		w.wr(" getList(").wr(JsonPullParser.class).wr(" parser) throws ");
		w.wr(IOException.class).wr(", ");
		w.wr(JsonFormatException.class).wr("{");

		// 結果用変数生成
		w.writeListClassName().wr(" list = new ").writeListInstance();
		w.wr("();");
		// 最初のbraceを食べる
		w.wr(State.class).wr(" eventType = parser.getEventType();");
		w.wr("if (eventType != ").wr(State.class).wr(".").wr(State.START_ARRAY);
		w.wr(") {");
		w.wr("throw new IllegalStateException(\"not started brace!\");");
		w.wr("}");
		// ループ処理共通部分生成
		w.wr("while (parser.lookAhead() != ");
		w.wr(State.class).wr(".").wr(State.END_ARRAY);
		w.wr("){list.add(get(parser));}");
		w.wr("parser.getEventType();");
		// 返り値の処理
		w.wr("return list;}");
	}

	private void genExtractValues(ClassWriterHelper w, Element element) {
		JsonHash hash = element.getAnnotation(JsonHash.class);
		if (hash != null) {

		}

		ElementInfo value = getElementInfo(element);
		w.wr("if(\"").wr(value.keyName).wr("\".equals(key)){");
		w.wr("obj.").wr(value.accessor.getSimpleName().toString()).wr("(");
		value.type.accept(new TypeVisitor(), w);
		w.wr(");");
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

	private ElementInfo getElementInfo(Element element) {
		JsonKey key = element.getAnnotation(JsonKey.class);
		ElementInfo values = new ElementInfo();
		if (!"".equals(key.value())) {
			values.keyName = key.value();
		} else {
			values.keyName = element.toString();
		}
		values.type = element.asType();
		Element setter = null;
		for (Element m : ElementFilter.methodsIn(element.getEnclosingElement()
				.getEnclosedElements())) {
			if (("set" + element.getSimpleName().toString()).equalsIgnoreCase(m
					.getSimpleName().toString())) {
				// TODO publicかどうかの判定をいれていない
				setter = m;
				break;
			}
		}
		if (setter != null) {
			values.accessor = setter;
		} else {
			Log.e("not exists public setter method.", element);
		}
		return values;
	}

	static class ElementInfo {
		String keyName;
		TypeMirror type;
		Element accessor;
	}

	class TypeVisitor extends StandardTykeKindVisitor<Void, ClassWriterHelper> {

		@Override
		public Void visitPrimitiveAsBoolean(PrimitiveType t, ClassWriterHelper p) {
			p.wr("parser.getValueBoolean()");
			return super.visitPrimitiveAsBoolean(t, p);
		}

		@Override
		public Void visitPrimitiveAsByte(PrimitiveType t, ClassWriterHelper p) {
			p.wr("(byte)parser.getValueInteger()");
			return super.visitPrimitiveAsByte(t, p);
		}

		@Override
		public Void visitPrimitiveAsChar(PrimitiveType t, ClassWriterHelper p) {
			p.wr("parser.getValueString().charAt(0)");
			return super.visitPrimitiveAsChar(t, p);
		}

		@Override
		public Void visitPrimitiveAsDouble(PrimitiveType t, ClassWriterHelper p) {
			p.wr("parser.getValueDouble()");
			return super.visitPrimitiveAsDouble(t, p);
		}

		@Override
		public Void visitPrimitiveAsFloat(PrimitiveType t, ClassWriterHelper p) {
			p.wr("(float)parser.getValueDouble()");
			return super.visitPrimitiveAsFloat(t, p);
		}

		@Override
		public Void visitPrimitiveAsInt(PrimitiveType t, ClassWriterHelper p) {
			p.wr("parser.getValueInteger()");
			return super.visitPrimitiveAsInt(t, p);
		}

		@Override
		public Void visitPrimitiveAsLong(PrimitiveType t, ClassWriterHelper p) {
			p.wr("parser.getValueInteger()");
			return super.visitPrimitiveAsLong(t, p);
		}

		@Override
		public Void visitPrimitiveAsShort(PrimitiveType t, ClassWriterHelper p) {
			p.wr("(short)parser.getValueInteger()");
			return super.visitPrimitiveAsShort(t, p);
		}

		@Override
		public Void visitString(DeclaredType t, ClassWriterHelper p) {
			p.wr("parser.getValueString()");
			return super.visitString(t, p);
		}
	}
}
