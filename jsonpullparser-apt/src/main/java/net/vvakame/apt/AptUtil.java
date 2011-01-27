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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/**
 * APT周りであったら便利なユーティリティを定義.
 * 
 * @author vvakame
 */
public class AptUtil {

	private AptUtil() {
	}

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

	public static String getPackageName(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = element.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(0, i);
	}

	public static String getSimpleName(Element element) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		String str = element.asType().toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	public static String getSimpleName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	public static String getFullQualifiedName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf("<");
		if (0 < i) {
			return str.substring(i + 1);
		} else {
			return str;
		}
	}

	static boolean checkModifier(Element element, Modifier modifier) {
		for (Modifier m : element.getModifiers()) {
			if (modifier == m) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPublic(Element element) {
		return checkModifier(element, Modifier.PUBLIC);
	}

	public static boolean isProtected(Element element) {
		return checkModifier(element, Modifier.PROTECTED);
	}

	public static boolean isPrivate(Element element) {
		return checkModifier(element, Modifier.PRIVATE);
	}

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

	public static boolean isStatic(Element element) {
		return checkModifier(element, Modifier.STATIC);
	}

	public static boolean isMethodExists(Element element, String methodName,
			Modifier... modifiers) {
		if (element.getKind() != ElementKind.CLASS) {
			throw new IllegalStateException();
		}
		List<Modifier> modifiersList = Arrays.asList(modifiers);

		List<ExecutableElement> methods = ElementFilter.methodsIn(element
				.getEnclosedElements());
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

	public static String getElementSetter(Element element) {
		// 後続処理注意 hogeに対して sethoge が取得される. setHoge ではない.
		String setterName;
		if (element.getSimpleName().toString().startsWith("is")) {
			// boolean isHoge; に対して setIsHoge ではなく setHoge が生成される
			setterName = "set"
					+ element.getSimpleName().toString().substring(2);
		} else {
			setterName = "set" + element.getSimpleName().toString();
		}

		Element setter = null;
		for (Element method : ElementFilter.methodsIn(element
				.getEnclosingElement().getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (setterName.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
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

	public static String getElementGetter(Element element) {
		// TODO 型(boolean)による絞り込みをするべき
		String getterName1 = "get" + element.getSimpleName().toString();
		String getterName2 = "is" + element.getSimpleName().toString();
		String getterName3 = element.getSimpleName().toString();

		Element getter = null;
		for (Element method : ElementFilter.methodsIn(element
				.getEnclosingElement().getEnclosedElements())) {
			String methodName = method.getSimpleName().toString();

			if (getterName1.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName2.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
					getter = method;
					break;
				}
			} else if (getterName3.equalsIgnoreCase(methodName)) {
				if (isStatic(method) == false && isPublic(method)
						|| isPackagePrivate(method)) {
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
