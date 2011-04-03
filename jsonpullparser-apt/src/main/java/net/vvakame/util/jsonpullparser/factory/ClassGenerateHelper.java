/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser.factory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.apt.AptUtil;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.factory.JsonElement.Kind;
import net.vvakame.util.jsonpullparser.factory.template.Template;
import net.vvakame.util.jsonpullparser.util.TokenConverter;
import static net.vvakame.apt.AptUtil.*;

/**
 * アノテーション処理の本体.<br>
 * 1インスタンス = 1 {@link JsonModel} の処理.
 * @author vvakame
 */
public class ClassGenerateHelper {

	static ProcessingEnvironment processingEnv = null;

	static String postfix = "";

	GeneratingModel g = new GeneratingModel();

	Element classElement;

	boolean encountError = false;


	/**
	 * 初期化処理
	 * @param env
	 * @author vvakame
	 */
	public static void init(ProcessingEnvironment env) {
		processingEnv = env;
	}

	/**
	 * インスタンス生成
	 * @param element
	 * @return {@link ClassGenerateHelper}
	 * @author vvakame
	 */
	public static ClassGenerateHelper newInstance(Element element) {
		return new ClassGenerateHelper(element);
	}

	/**
	 * the constructor.
	 * @param element
	 * @category constructor
	 */
	public ClassGenerateHelper(Element element) {
		classElement = element;

		g.setPackageName(getPackageName(element));
		g.setTarget(getSimpleName(element));

		TypeElement superclass = AptUtil.getSuperClassElement(element);
		if (superclass.getAnnotation(JsonModel.class) != null) {
			g.setTargetBase(AptUtil.getFullQualifiedName(superclass));
			g.setExistsBase(true);
		}

		g.setPostfix(postfix);
		g.setTreatUnknownKeyAsError(getTreatUnknownKeyAsError(element));
	}

	/**
	 * {@link JsonKey} が付加されているフィールドの解釈.
	 * @param element
	 * @author vvakame
	 */
	public void addElement(Element element) {
		JsonElement jsonElement = element.asType().accept(new ValueExtractVisitor(), element);
		g.addJsonElement(jsonElement);
	}

	/**
	 * ソース生成.
	 * @throws IOException
	 * @author vvakame
	 */
	public void write() throws IOException {

		Filer filer = processingEnv.getFiler();
		String generateClassName = classElement.asType().toString() + postfix;
		JavaFileObject fileObject = filer.createSourceFile(generateClassName, classElement);
		Template.write(fileObject, g);
	}

	/**
	 * アノテーション読み取り処理
	 * @author vvakame
	 */
	public void process() {
		// JsonKeyの収集
		List<Element> elements =
				getEnclosedElementsByAnnotation(classElement, JsonKey.class, ElementKind.FIELD);
		if (elements.size() == 0) {
			Log.e("not exists any @JsonKey decorated field.", classElement);
		}

		// JsonKeyに対応する値取得コードを生成する
		for (Element element : elements) {
			addElement(element);
		}
	}

	String getElementKeyString(Element element) {
		JsonKey key = element.getAnnotation(JsonKey.class);
		JsonModel model = element.getEnclosingElement().getAnnotation(JsonModel.class);
		String keyStr;
		if (!"".equals(key.value())) {
			keyStr = key.value();
		} else if ("".equals(key.value()) && key.decamelize()) {
			keyStr = decamelize(element.toString());
		} else if ("".equals(key.value()) && model.decamelize()) {
			keyStr = decamelize(element.toString());
		} else {
			keyStr = element.toString();
		}
		return keyStr;
	}

	String decamelize(String str) {
		StringBuilder builder = new StringBuilder();
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if ('A' <= c && c <= 'Z') {
				builder.append('_').append(c);
			} else {
				builder.append(c);
			}
		}
		return builder.toString().toLowerCase(Locale.ENGLISH);
	}

	String getConverterClassName(Element el) {

		AnnotationValue converter = null;

		for (AnnotationMirror am : el.getAnnotationMirrors()) {
			Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues =
					am.getElementValues();
			for (ExecutableElement e : elementValues.keySet()) {
				if ("converter".equals(e.getSimpleName().toString())) {
					converter = elementValues.get(e);
				}
			}
		}

		String result = null;
		if (converter != null && !TokenConverter.class.getCanonicalName().equals(converter)) {
			String tmp = converter.toString();
			if (tmp.endsWith(".class")) {
				int i = tmp.lastIndexOf('.');
				result = tmp.substring(0, i);
			} else {
				result = tmp;
			}
		}

		return result;
	}

	boolean getTreatUnknownKeyAsError(Element element) {
		JsonModel model = element.getAnnotation(JsonModel.class);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		return model.treatUnknownKeyAsError();
	}


	class ValueExtractVisitor extends StandardTypeKindVisitor<JsonElement, Element> {

		JsonElement genJsonElement(TypeMirror t, Element el, Kind kind) {
			if (kind == null) {
				Log.e("invalid state. this is APT bugs.");
				encountError = true;
				return defaultAction(t, el);
			}

			JsonElement jsonElement = new JsonElement();
			jsonElement.setKey(getElementKeyString(el));

			JsonKey key = el.getAnnotation(JsonKey.class);

			String setter = getElementSetter(el);
			if (key.in() && setter == null) {
				Log.e("can't find setter method", el);
				encountError = true;
				return defaultAction(t, el);
			}

			String getter = getElementGetter(el);
			if (key.out() && getter == null) {
				Log.e("can't find getter method", el);
				encountError = true;
				return defaultAction(t, el);
			}

			String converterClassName = getConverterClassName(el);
			if (converterClassName != null) {
				TypeElement element =
						processingEnv.getElementUtils().getTypeElement(converterClassName);
				Log.d(element != null ? element.asType().toString() : null);
				if (element == null
						|| !isMethodExists(element, "getInstance", Modifier.PUBLIC, Modifier.STATIC)) {
					Log.e("converter needs [public static getInstance()].", element);
				}
				kind = Kind.CONVERTER;
			}

			jsonElement.setIn(key.in());
			jsonElement.setSetter(setter);
			jsonElement.setOut(key.out());
			jsonElement.setGetter(getter);
			jsonElement.setModelName(t.toString());
			jsonElement.setKind(kind);
			jsonElement.setConverter(converterClassName);

			return jsonElement;
		}

		@Override
		public JsonElement visitPrimitiveAsBoolean(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.BOOLEAN);
		}

		@Override
		public JsonElement visitPrimitiveAsByte(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.BYTE);
		}

		@Override
		public JsonElement visitPrimitiveAsChar(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.CHAR);
		}

		@Override
		public JsonElement visitPrimitiveAsDouble(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.DOUBLE);
		}

		@Override
		public JsonElement visitPrimitiveAsFloat(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.FLOAT);
		}

		@Override
		public JsonElement visitPrimitiveAsInt(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.INT);
		}

		@Override
		public JsonElement visitPrimitiveAsLong(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.LONG);
		}

		@Override
		public JsonElement visitPrimitiveAsShort(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.SHORT);
		}

		@Override
		public JsonElement visitString(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.STRING);
		}

		@Override
		public JsonElement visitList(DeclaredType t, Element el) {

			JsonElement jsonElement;

			String converterClassName = getConverterClassName(el);
			if (converterClassName != null) {
				jsonElement = genJsonElement(t, el, Kind.CONVERTER);

			} else {

				List<? extends TypeMirror> generics = t.getTypeArguments();
				if (generics.size() != 1) {
					Log.e("expected single type generics.", el);
					encountError = true;
					return defaultAction(t, el);
				}
				TypeMirror tm = generics.get(0);
				if (tm instanceof WildcardType) {
					WildcardType wt = (WildcardType) tm;
					TypeMirror extendsBound = wt.getExtendsBound();
					if (extendsBound != null) {
						tm = extendsBound;
					}
					TypeMirror superBound = wt.getSuperBound();
					if (superBound != null) {
						Log.e("super is not supported.", el);
						encountError = true;
						return defaultAction(t, el);
					}
				}

				jsonElement = new JsonElement();

				Element type = processingEnv.getTypeUtils().asElement(tm);
				JsonModel hash = type.getAnnotation(JsonModel.class);
				if (hash != null) {
					// OK
					jsonElement.setSubKind(Kind.MODEL); // FQNではModelとEnumの区別がつかない
				} else if (AptUtil.isPrimitiveWrapper(type)) {
					// OK
				} else if (type.toString().equals(Date.class.getCanonicalName())) {
					// OK
				} else if (type.toString().equals(String.class.getCanonicalName())) {
					// OK
				} else if (AptUtil.isEnum(type)) {
					// OK
					jsonElement.setSubKind(Kind.ENUM);
				} else {
					Log.e("expect for use decorated class by JsonModel annotation.", el);
					encountError = true;
					return defaultAction(t, el);
				}

				jsonElement.setKey(getElementKeyString(el));

				JsonKey key = el.getAnnotation(JsonKey.class);

				String setter = getElementSetter(el);
				if (key.in() && setter == null) {
					Log.e("can't find setter method", el);
					encountError = true;
					return defaultAction(t, el);
				}

				String getter = getElementGetter(el);
				if (key.out() && getter == null) {
					Log.e("can't find getter method", el);
					encountError = true;
					return defaultAction(t, el);
				}

				jsonElement.setIn(key.in());
				jsonElement.setSetter(setter);
				jsonElement.setOut(key.out());
				jsonElement.setGetter(getter);
				jsonElement.setModelName(tm.toString());
				jsonElement.setKind(Kind.LIST);
			}

			return jsonElement;
		}

		@Override
		public JsonElement visitDate(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.DATE);
		}

		@Override
		public JsonElement visitEnum(DeclaredType t, Element el) {
			Types typeUtils = processingEnv.getTypeUtils();
			if (AptUtil.isInternalType(typeUtils, el.asType())) {
				// InternalなEnum
				TypeElement typeElement = AptUtil.getTypeElement(typeUtils, el);
				if (AptUtil.isPublic(typeElement)) {
					return genJsonElement(t, el, Kind.ENUM);
				} else {
					Log.e("Internal EnumType must use public & static.", el);
					encountError = true;
					return defaultAction(t, el);
				}
			} else {
				// InternalじゃないEnum
				return genJsonElement(t, el, Kind.ENUM);
			}
		}

		@Override
		public JsonElement visitBooleanWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.BOOLEAN_WRAPPER);
		}

		@Override
		public JsonElement visitDoubleWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.DOUBLE_WRAPPER);
		}

		@Override
		public JsonElement visitLongWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.LONG_WRAPPER);
		}

		@Override
		public JsonElement visitByteWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.BYTE_WRAPPER);
		}

		@Override
		public JsonElement visitCharacterWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.CHAR_WRAPPER);
		}

		@Override
		public JsonElement visitFloatWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.FLOAT_WRAPPER);
		}

		@Override
		public JsonElement visitIntegerWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.INT_WRAPPER);
		}

		@Override
		public JsonElement visitShortWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.SHORT_WRAPPER);
		}

		@Override
		public JsonElement visitJsonHash(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.JSON_HASH);
		}

		@Override
		public JsonElement visitJsonArray(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.JSON_ARRAY);
		}

		@Override
		public JsonElement visitUndefinedClass(DeclaredType t, Element el) {

			TypeMirror tm = t.asElement().asType();
			Element type = processingEnv.getTypeUtils().asElement(tm);
			JsonModel model = type.getAnnotation(JsonModel.class);
			String converterClassName = getConverterClassName(el);
			if (model == null && converterClassName == null) {
				Log.e("expect for use decorated class by JsonModel annotation.", el);
				encountError = true;
				return defaultAction(t, el);
			}

			return genJsonElement(t, el, Kind.MODEL);
		}
	}


	/**
	 * @param postfix
	 *            the postfix to set
	 */
	public static void setPostfix(String postfix) {
		ClassGenerateHelper.postfix = postfix;
	}

	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}
}
