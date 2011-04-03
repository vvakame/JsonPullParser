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

package net.vvakame.apt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

/**
 * APT周りであったら便利なユーティリティを定義.
 * 
 * @author vvakame
 */
public class AptUtil {

	private AptUtil() {
	}

	/**
	 * elementがClassを表す {@link Element} である場合、super classの {@link Element} を返します.<br>
	 * {@link Object} の {@link Element} が渡されたか、 {@link ElementKind#CLASS} 以外の {@link Element} が渡された場合 {@code null} を返します.
	 * @param element super classを取得したい {@link Element}
	 * @return elementの super classの {@link Element}
	 * @author vvakame
	 */
	public static TypeElement getSuperClassElement(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			return null;
		}
		TypeMirror superclass = ((TypeElement) element).getSuperclass();
		if (superclass.getKind() == TypeKind.NONE) {
			return null;
		}
		DeclaredType kind = (DeclaredType) superclass;
		return (TypeElement) kind.asElement();
	}

	/**
	 * elementが {@link Enum} の型であるかを調べます.
	 * @param element
	 * @return {@link Enum} の子クラスか否か
	 * @author vvakame
	 */
	public static boolean isEnum(Element element) {
		if (element == null) {
			return false;
		} else if (element.getKind() == ElementKind.ENUM) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * elementが プリミティブ型のラッパクラスであるかを調べます.
	 * @param element
	 * @return プリミティブ型のラッパクラスか否か
	 * @author vvakame
	 */
	public static boolean isPrimitiveWrapper(Element element) {
		if (element == null) {
			return false;
		} else if (element.toString().equals(Boolean.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Integer.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Long.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Byte.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Short.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Character.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Double.class.getCanonicalName())) {
			return true;
		} else if (element.toString().equals(Float.class.getCanonicalName())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 指定された type が Internalな要素かどうかをチェック.
	 * @param typeUtils
	 * @param type
	 * @return Internalな要素か否か
	 * @author vvakame
	 */
	public static boolean isInternalType(Types typeUtils, TypeMirror type) {
		Element element = ((TypeElement) typeUtils.asElement(type)).getEnclosingElement();
		return element.getKind() != ElementKind.PACKAGE;
	}

	/**
	 * 指定された element の {@link TypeElement} を取得する.
	 * @param typeUtils
	 * @param element
	 * @return element の {@link TypeElement}
	 * @author vvakame
	 */
	public static TypeElement getTypeElement(Types typeUtils, Element element) {
		TypeMirror type = element.asType();
		return (TypeElement) typeUtils.asElement(type);
	}

	/**
	 * parentが含む {@link Element} の中で、 annotationで修飾され、kindに一致する種類の {@link Element} を集めて返す.<br>
	 * kind が渡されなかった場合は種類での絞り込みを行わない.
	 * @param parent この要素に含まれる {@link Element} を集める
	 * @param annotation このアノテーションがついている {@link Element} を集める
	 * @param kind 指定したい {@link ElementKind} があったら指定 
	 * @return 集められた {@link Element}
	 * @author vvakame
	 */
	public static List<Element> getEnclosedElementsByAnnotation(Element parent,
			Class<? extends Annotation> annotation, ElementKind kind) {
		List<? extends Element> elements = parent.getEnclosedElements();
		List<Element> results = new ArrayList<Element>();

		for (Element element : elements) {
			if (kind != null && element.getKind() != kind) {
				continue;
			}
			Object key = element.getAnnotation(annotation);
			if (key == null) {
				continue;
			}
			results.add(element);
		}

		return results;
	}

	/**
	 * elementのpackage名を返す.<br>
	 * elementの {@link ElementKind} は {@link ElementKind#CLASS} である必要がある.
	 * @param element
	 * @return package名
	 * @author vvakame
	 */
	public static String getPackageName(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = element.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}

	/**
	 * SimpleNameを取得する java.lang.String だったら String ←この部分.<br>
	 * elementの {@link ElementKind} は {@link ElementKind#CLASS} である必要がある.
	 * @param element
	 * @return SimpleName
	 * @author vvakame
	 */
	public static String getSimpleName(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = element.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	/**
	 * SimpleNameを取得する java.lang.String だったら String ←この部分.
	 * @param tm
	 * @return SimpleName
	 * @author vvakame
	 */
	public static String getSimpleName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	/**
	 * FQNを取得する.
	 * @param tm
	 * @return FQN
	 * @author vvakame
	 */
	public static String getFullQualifiedName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf("<");
		if (0 < i) {
			return str.substring(i + 1);
		} else {
			return str;
		}
	}

	/**
	 * FQNを取得する.
	 * @param element
	 * @return FQN
	 * @author vvakame
	 */
	public static String getFullQualifiedName(Element element) {
		return element.toString();
	}

	static boolean checkModifier(Element element, Modifier modifier) {
		for (Modifier m : element.getModifiers()) {
			if (modifier == m) {
				return true;
			}
		}
		return false;
	}

	/**
	 * elementの可視性が public かを判定する
	 * @param element
	 * @return publicかどうか
	 * @author vvakame
	 */
	public static boolean isPublic(Element element) {
		return checkModifier(element, Modifier.PUBLIC);
	}

	/**
	 * elementの可視性が protected かを判定する
	 * @param element
	 * @return protectedかどうか
	 * @author vvakame
	 */
	public static boolean isProtected(Element element) {
		return checkModifier(element, Modifier.PROTECTED);
	}

	/**
	 * elementの可視性が private かを判定する
	 * @param element
	 * @return privateかどうか
	 * @author vvakame
	 */
	public static boolean isPrivate(Element element) {
		return checkModifier(element, Modifier.PRIVATE);
	}

	/**
	 * elementの可視性が package private かを判定する
	 * @param element
	 * @return package privateかどうか
	 * @author vvakame
	 */
	public static boolean isPackagePrivate(Element element) {
		if (isPublic(element)) {
			return false;
		} else if (isProtected(element)) {
			return false;
		} else if (isPrivate(element)) {
			return false;
		}
		return true;
	}

	/**
	 * elementが static かを判定する
	 * @param element
	 * @return staticかどうか
	 * @author vvakame
	 */
	public static boolean isStatic(Element element) {
		return checkModifier(element, Modifier.STATIC);
	}

	/**
	 * element に methodName という名前のメソッドが存在するか調べる.<br>
	 * elementの {@link ElementKind} は {@link ElementKind#CLASS} である必要がある.<br>
	 * {@link Modifier} を渡した場合、そのメソッドが modifiersの特徴を全て備えているかをチェック
	 * @param element
	 * @param methodName
	 * @param modifiers
	 * @return メソッドが存在するか
	 * @author vvakame
	 */
	public static boolean isMethodExists(Element element, String methodName, Modifier... modifiers) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		List<Modifier> modifiersList = Arrays.asList(modifiers);

		List<ExecutableElement> methods = ElementFilter.methodsIn(element.getEnclosedElements());
		for (ExecutableElement method : methods) {
			if (method.getSimpleName().toString().equals(methodName)
					&& method.getModifiers().containsAll(modifiersList)) {
				return true;
			}
		}
		return false;
	}

	static String cutAfterString(String base, char key) {
		if (base == null) {
			return null;
		}
		int lastIndexOf = base.lastIndexOf(key);
		if (lastIndexOf < 0) {
			return base;
		} else {
			return base.substring(0, lastIndexOf);
		}
	}

	/**
	 * elementに対応するsetterの名前を探し返す.
	 * @param element フィールド
	 * @return setter名
	 * @author vvakame
	 */
	public static String getElementSetter(Element element) {
		// 後続処理注意 hogeに対して sethoge が取得される. setHoge ではない.
		String setterName;
		if (element.getSimpleName().toString().startsWith("is")) {
			// boolean isHoge; に対して setIsHoge ではなく setHoge が生成される
			setterName = "set" + element.getSimpleName().toString().substring(2);
		} else {
			setterName = "set" + element.getSimpleName().toString();
		}

		Element setter = null;
		for (Element method : ElementFilter.methodsIn(element.getEnclosingElement()
			.getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (setterName.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method) || isPackagePrivate(method)) {
					setter = method;
					break;
				}
			}
		}
		if (setter != null) {
			return setter.getSimpleName().toString();
		} else {
			return null;
		}
	}

	/**
	 * elementに対応するgetterの名前を探し返す.
	 * @param element フィールド
	 * @return getter名
	 * @author vvakame
	 */
	public static String getElementGetter(Element element) {
		// TODO 型(boolean)による絞り込みをするべき
		String getterName1 = "get" + element.getSimpleName().toString();
		String getterName2 = "is" + element.getSimpleName().toString();
		String getterName3 = element.getSimpleName().toString();

		Element getter = null;
		for (Element method : ElementFilter.methodsIn(element.getEnclosingElement()
			.getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (getterName1.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method) || isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName2.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method) || isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName3.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method) || isPackagePrivate(method)) {
					getter = method;
					break;
				}
			}
		}
		if (getter != null) {
			return getter.getSimpleName().toString();
		} else {
			return null;
		}
	}
}
