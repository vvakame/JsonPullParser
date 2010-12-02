package net.vvakame.sample;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import net.vvakame.util.jsonpullparser.annotation.JSONHash;
import net.vvakame.util.jsonpullparser.annotation.JSONKey;

@JSONHash
public class TestData {
	@JSONKey
	String name;

	@JSONKey("package_name")
	String packageName;

	@JSONKey("version_code")
	int versionCode;

	@JSONKey
	double weight;

	@JSONKey("has_data")
	boolean hasData;

	public static void main(String[] args) {
		Class<TestData> clazz = TestData.class;

		Annotation[] annotations;
		annotations = clazz.getDeclaredAnnotations();
		dump("class", annotations);

		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			dump("field", f.getDeclaredAnnotations());
		}
	}

	public static void dump(String message, Annotation[] as) {
		System.out.println(message);
		for (Annotation a : as) {
			if (a instanceof JSONKey) {
				JSONKey k = (JSONKey) a;
				System.out.println(k.value());
			}
			System.out.println(a);
		}
	}
}
