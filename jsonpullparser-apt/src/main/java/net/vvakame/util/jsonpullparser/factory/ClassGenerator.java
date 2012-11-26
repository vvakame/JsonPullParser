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
import java.util.Collections;
import java.util.Comparator;
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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import net.vvakame.util.jsonpullparser.annotation.StoreJson;
import net.vvakame.util.jsonpullparser.factory.JsonKeyModel.Kind;
import net.vvakame.util.jsonpullparser.factory.template.Template;
import net.vvakame.util.jsonpullparser.util.TokenConverter;
import static net.vvakame.apt.AptUtil.*;

/**
 * Annotation processor.<br>
 * Handles 1 {@link JsonModel} per instance (i.e. cannot be reused.)
 * @author vvakame
 */
public class ClassGenerator {

	static ProcessingEnvironment processingEnv = null;

	static Types typeUtils;

	static Elements elementUtils;

	String postfix;

	JsonModelModel jsonModel = new JsonModelModel();

	Element classElement;

	boolean encountError = false;


	/**
	 * Initialization.
	 * @param env
	 * @author vvakame
	 */
	public static void init(ProcessingEnvironment env) {
		processingEnv = env;
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
	}

	/**
	 * Process {@link Element} to generate.
	 * @param element
	 * @param classNamePostfix 
	 * @return {@link ClassGenerator}
	 * @author vvakame
	 */
	public static ClassGenerator from(Element element, String classNamePostfix) {
		ClassGenerator generator = new ClassGenerator(element, classNamePostfix);
		generator.process();
		return generator;
	}

	/**
	 * the constructor.
	 * @param element
	 * @param classNamePostfix 
	 * @category constructor
	 */
	ClassGenerator(Element element, String classNamePostfix) {
		classElement = element;
		postfix = classNamePostfix;

		jsonModel.setPackageName(getPackageName(elementUtils, element));
		jsonModel.setTarget(getSimpleName(element));
		jsonModel.setTargetNew(getNameForNew(element));

		TypeElement superClass = getSuperClassElement(element);
		if (superClass.getAnnotation(JsonModel.class) != null) {
			jsonModel.setTargetBase(getFullQualifiedName(superClass));
			jsonModel.setExistsBase(true);
		}

		jsonModel.setPostfix(postfix);
		jsonModel.setTreatUnknownKeyAsError(getTreatUnknownKeyAsError(element));
		jsonModel.setGenToPackagePrivate(getGenToPackagePrivate(element));
		jsonModel.setJsonMetaToPackagePrivate(getJsonMetaToPackagePrivate(element));
		jsonModel.setBuilder(getBuilder(element));
	}

	/**
	 * Keeps the given element.
	 * @param element
	 * @author vvakame
	 */
	public void addJsonKey(Element element) {
		JsonKeyModel jsonKey = element.asType().accept(new ValueExtractVisitor(), element);
		jsonModel.addJsonKey(jsonKey);
	}

	/**
	 * Generates the source code.
	 * @throws IOException
	 * @author vvakame
	 */
	public void write() throws IOException {
		{
			Filer filer = processingEnv.getFiler();
			String generateClassName =
					jsonModel.getPackageName() + "." + jsonModel.getTarget() + postfix;
			JavaFileObject fileObject = filer.createSourceFile(generateClassName, classElement);
			Template.writeGen(fileObject, jsonModel);
		}
		if (jsonModel.isBuilder()) {
			Filer filer = processingEnv.getFiler();
			String generateClassName =
					jsonModel.getPackageName() + "." + jsonModel.getTarget() + "JsonMeta";
			JavaFileObject fileObject = filer.createSourceFile(generateClassName, classElement);
			Template.writeJsonMeta(fileObject, jsonModel);
		}
	}

	/**
	 * Processes annotations.
	 * @author vvakame
	 */
	void process() {
		// check @JsonModel parameter combination
		if (jsonModel.isBuilder() == false && jsonModel.isJsonMetaToPackagePrivate() == true) {
			Log.e("builder parameter or jsonMetaToPackagePrivate parameter change value.",
					classElement);
			encountError = true;
		}

		List<Element> elements;

		// JsonKeyの収集
		elements = getEnclosedElementsByAnnotation(classElement, JsonKey.class, ElementKind.FIELD);
		if (elements.size() == 0) {
			Log.e("not exists any @JsonKey decorated field.", classElement);
		}
		Comparator<Element> comparator = new Comparator<Element>() {

			@Override
			public int compare(Element e1, Element e2) {
				JsonKey jsonKey1 = e1.getAnnotation(JsonKey.class);
				JsonKey jsonKey2 = e2.getAnnotation(JsonKey.class);

				return jsonKey1.sortOrder() - jsonKey2.sortOrder();
			}
		};
		Collections.sort(elements, comparator);

		// JsonKeyに対応する値取得コードを生成する
		for (Element element : elements) {
			addJsonKey(element);
		}

		// StoreJsonの収集
		elements =
				getEnclosedElementsByAnnotation(classElement, StoreJson.class, ElementKind.FIELD);
		if (elements.size() == 0) {
			return;
		} else if (elements.size() != 1) {
			Log.e("too much @StoreJson decorated field exists.", classElement);
			encountError = true;
			return;
		}

		// StoreJsonに対応する値取得コードを生成する
		Element element = elements.get(0);
		StoreJson store = element.getAnnotation(StoreJson.class);
		StoreJsonModel storeJson = jsonModel.getStoreJson();
		storeJson.setStoreJson(true);
		storeJson.setTreatLogDisabledAsError(store.treatLogDisabledAsError());

		String setter = getElementSetter(element);
		if (setter == null) {
			Log.e("can't find setter method", element);
			encountError = true;
			return;
		}
		storeJson.setSetter(setter);
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

	boolean getGenToPackagePrivate(Element element) {
		JsonModel model = element.getAnnotation(JsonModel.class);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		return model.genToPackagePrivate();
	}

	boolean getJsonMetaToPackagePrivate(Element element) {
		JsonModel model = element.getAnnotation(JsonModel.class);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		return model.jsonMetaToPackagePrivate();
	}

	boolean getBuilder(Element element) {
		JsonModel model = element.getAnnotation(JsonModel.class);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		return model.builder();
	}


	class ValueExtractVisitor extends StandardTypeKindVisitor<JsonKeyModel, Element> {

		JsonKeyModel genJsonElement(TypeMirror t, Element el, Kind kind) {
			if (kind == null) {
				Log.e("invalid state. this is APT bugs.");
				encountError = true;
				return defaultAction(t, el);
			}

			for (Modifier modifier : el.getEnclosingElement().getModifiers()) {
				if (Modifier.ABSTRACT == modifier) {
					Log.e("abstract class that can not be applied to @JsonModel",
							el.getEnclosingElement());
					encountError = true;
					return defaultAction(t, el);
				}
			}

			JsonKeyModel jsonKey = new JsonKeyModel();
			jsonKey.setKey(getElementKeyString(el));
			jsonKey.setOriginalName(el.toString());

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

			jsonKey.setIn(key.in());
			jsonKey.setSetter(setter);
			jsonKey.setOut(key.out());
			jsonKey.setGetter(getter);
			jsonKey.setModelName(t.toString());
			if (kind == Kind.MODEL) {
				String packageName = getPackageName(elementUtils, typeUtils, el.asType());
				jsonKey.setGenName(packageName + "." + getSimpleName(el.asType()));
			} else {
				jsonKey.setGenName(t.toString());
			}
			jsonKey.setKind(kind);
			jsonKey.setConverter(converterClassName);

			return jsonKey;
		}

		@Override
		public JsonKeyModel visitPrimitiveAsBoolean(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.BOOLEAN);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsByte(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.BYTE);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsChar(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.CHAR);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsDouble(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.DOUBLE);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsFloat(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.FLOAT);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsInt(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.INT);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsLong(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.LONG);
		}

		@Override
		public JsonKeyModel visitPrimitiveAsShort(PrimitiveType t, Element el) {
			return genJsonElement(t, el, Kind.SHORT);
		}

		@Override
		public JsonKeyModel visitString(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.STRING);
		}

		@Override
		public JsonKeyModel visitList(DeclaredType t, Element el) {

			JsonKeyModel jsonKey;

			String converterClassName = getConverterClassName(el);
			if (converterClassName != null) {
				jsonKey = genJsonElement(t, el, Kind.CONVERTER);

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

				jsonKey = new JsonKeyModel();

				Element type = processingEnv.getTypeUtils().asElement(tm);
				JsonModel jsonModel = type.getAnnotation(JsonModel.class);
				if (jsonModel != null) {
					// OK
					jsonKey.setSubKind(Kind.MODEL); // FQNではModelとEnumの区別がつかない
				} else if (isPrimitiveWrapper(type)) {
					// OK
				} else if (type.toString().equals(Date.class.getCanonicalName())) {
					// OK
				} else if (type.toString().equals(String.class.getCanonicalName())) {
					// OK
				} else if (isEnum(type)) {
					// OK
					jsonKey.setSubKind(Kind.ENUM);
				} else {
					Log.e("expect for use decorated class by JsonModel annotation.", el);
					encountError = true;
					return defaultAction(t, el);
				}

				jsonKey.setKey(getElementKeyString(el));
				jsonKey.setOriginalName(el.toString());

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

				jsonKey.setIn(key.in());
				jsonKey.setSetter(setter);
				jsonKey.setOut(key.out());
				jsonKey.setGetter(getter);
				jsonKey.setModelName(tm.toString());

				String packageName = getPackageName(elementUtils, typeUtils, tm);

				jsonKey.setGenName(packageName + "." + getSimpleName(type.asType()));
				jsonKey.setKind(Kind.LIST);
			}

			return jsonKey;
		}

		@Override
		public JsonKeyModel visitDate(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.DATE);
		}

		@Override
		public JsonKeyModel visitEnum(DeclaredType t, Element el) {
			Types typeUtils = processingEnv.getTypeUtils();
			if (isInternalType(typeUtils, el.asType())) {
				// InternalなEnum
				TypeElement typeElement = getTypeElement(typeUtils, el);
				if (isPublic(typeElement)) {
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
		public JsonKeyModel visitBooleanWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.BOOLEAN_WRAPPER);
		}

		@Override
		public JsonKeyModel visitDoubleWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.DOUBLE_WRAPPER);
		}

		@Override
		public JsonKeyModel visitLongWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.LONG_WRAPPER);
		}

		@Override
		public JsonKeyModel visitByteWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.BYTE_WRAPPER);
		}

		@Override
		public JsonKeyModel visitCharacterWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.CHAR_WRAPPER);
		}

		@Override
		public JsonKeyModel visitFloatWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.FLOAT_WRAPPER);
		}

		@Override
		public JsonKeyModel visitIntegerWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.INT_WRAPPER);
		}

		@Override
		public JsonKeyModel visitShortWrapper(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.SHORT_WRAPPER);
		}

		@Override
		public JsonKeyModel visitJsonHash(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.JSON_HASH);
		}

		@Override
		public JsonKeyModel visitJsonArray(DeclaredType t, Element el) {
			return genJsonElement(t, el, Kind.JSON_ARRAY);
		}

		@Override
		public JsonKeyModel visitUndefinedClass(DeclaredType t, Element el) {

			TypeMirror tm = t.asElement().asType();
			Element type = processingEnv.getTypeUtils().asElement(tm);
			JsonModel jsonModel = type.getAnnotation(JsonModel.class);
			String converterClassName = getConverterClassName(el);
			if (jsonModel == null && converterClassName == null) {
				Log.e("expect for use decorated class by JsonModel annotation.", el);
				encountError = true;
				return defaultAction(t, el);
			}

			return genJsonElement(t, el, Kind.MODEL);
		}
	}


	/**
	 * @return the encountError
	 */
	public boolean isEncountError() {
		return encountError;
	}
}
