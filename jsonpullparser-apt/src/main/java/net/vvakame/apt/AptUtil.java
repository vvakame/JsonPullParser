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
 * Defines misc utils.
 * 
 * @author vvakame
 */
public class AptUtil {

	private AptUtil() {
	}

	/**
	 * Retrieves the super class of the given {@link Element}.
	 * Returns null if {@link Element} represents {@link Object}, or something other than {@link ElementKind#CLASS}.
	 * @param element target {@link Element}.
	 * @return {@link Element} of its super class.
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
	 * Tests if the given element is a kind of {@link Enum}.
	 * @param element
	 * @return true if the element passed is kind of {@link Enum}, false otherwise.
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
	 * Tests if the given element is a primitive wrapper.
	 * @param element
	 * @return true if the element is a primitive wrapper, false otherwise.
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
	 * Test if the given type is an internal type.
	 * @param typeUtils
	 * @param type
	 * @return True if the type is an internal type, false otherwise.
	 * @author vvakame
	 */
	public static boolean isInternalType(Types typeUtils, TypeMirror type) {
		Element element = ((TypeElement) typeUtils.asElement(type)).getEnclosingElement();
		return element.getKind() != ElementKind.PACKAGE;
	}

	/**
	 * Retrieves the corresponding {@link TypeElement} of the given element.
	 * @param typeUtils
	 * @param element
	 * @return The corresponding {@link TypeElement}.
	 * @author vvakame
	 */
	public static TypeElement getTypeElement(Types typeUtils, Element element) {
		TypeMirror type = element.asType();
		return (TypeElement) typeUtils.asElement(type);
	}

	/**	 
	 * Retrieves {@link Element}s matching the given annoation and kind (only if given,) from children of the given root.
	 * @param parent The element search from.
	 * @param annotation Annotation looking for
	 * @param kind {@link ElementKind} looking for
	 * @return {@link Element}s matched
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
	 * Returns the package name of the given element.
	 * NB: This method requires the given element has the kind of {@link ElementKind#CLASS}.
	 * @param element
	 * @return the package name
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
	 * Returns unqualified class name (e.g. String, if java.lang.String)
	 * NB: This method requires the given element has the kind of {@link ElementKind#CLASS}.
	 * @param element
	 * @return unqualified class name
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
	 * Returns unqualified class name (e.g. String, if java.lang.String)
	 * NB: This method requires the given element has the kind of {@link ElementKind#CLASS}.
	 * @param tm
	 * @return unqualified class name
	 * @author vvakame
	 */
	public static String getSimpleName(TypeMirror tm) {
		String str = tm.toString();
		int i = str.lastIndexOf(".");
		return str.substring(i + 1);
	}

	/**
	 * Returns the fully qualified name.
	 * @param tm
	 * @return The fully qualified name
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
	 * Returns the fully qualified name.
	 * @param element
	 * @return The fully qualified name
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
	 * Tests if the given element has the public visibility.
	 * @param element
	 * @return true if public, false otherwise
	 * @author vvakame
	 */
	public static boolean isPublic(Element element) {
		return checkModifier(element, Modifier.PUBLIC);
	}

	/**
	 * Tests if the given element has the protected visibility.
	 * @param element
	 * @return true if protected, false otherwise
	 * @author vvakame
	 */
	public static boolean isProtected(Element element) {
		return checkModifier(element, Modifier.PROTECTED);
	}

	/**
	 * Tests if the given element has the private visibility.
	 * @param element
	 * @return true if private, false otherwise
	 * @author vvakame
	 */
	public static boolean isPrivate(Element element) {
		return checkModifier(element, Modifier.PRIVATE);
	}

	/**
	 * Tests if the given element has the package-private visibility.
	 * @param element
	 * @return true if package-private, false otherwise
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
	 * Tests if the given element has static scope.
	 * @param element
	 * @return true if static, false otherwise
	 * @author vvakame
	 */
	public static boolean isStatic(Element element) {
		return checkModifier(element, Modifier.STATIC);
	}

	/**
	 * Tests if the given element has the method with the given name.<br>
	 * NB: This method requires the given element has the kind of {@link ElementKind#CLASS}.<br>
	 * Also tests the method qualifies all of modifiers if any {@link Modifier} are also given.
	 * @param element
	 * @param methodName
	 * @param modifiers
	 * @return true if a match is found, false otherwise
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
	 * Returns the name of corresponding setter.
	 * @param element the field
	 * @return setter name
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
	 * Returns the name of corresponding getter.
	 * @param element the field
	 * @return getter name
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
