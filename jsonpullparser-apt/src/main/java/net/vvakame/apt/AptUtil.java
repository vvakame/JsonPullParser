package net.vvakame.apt;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
}
