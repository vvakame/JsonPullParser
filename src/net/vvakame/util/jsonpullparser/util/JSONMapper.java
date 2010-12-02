package net.vvakame.util.jsonpullparser.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.vvakame.sample.TestData;
import net.vvakame.util.jsonpullparser.JSONFormatException;
import net.vvakame.util.jsonpullparser.JSONPullParser;
import net.vvakame.util.jsonpullparser.JSONPullParser.State;
import net.vvakame.util.jsonpullparser.annotation.JSONArray;
import net.vvakame.util.jsonpullparser.annotation.JSONHash;
import net.vvakame.util.jsonpullparser.annotation.JSONKey;

public class JSONMapper<T> {

	public static void main(String[] args) throws IOException,
			InstantiationException, IllegalAccessException, JSONFormatException {
		String str = "{\"name\":\"test\"}";

		JSONMapper<TestData> mapper = new JSONMapper<TestData>();
		TestData parseJSON = mapper.parseJSON(
				new ByteArrayInputStream(str.getBytes()), TestData.class);
		parseJSON.toString();
	}

	@SuppressWarnings("unchecked")
	public T parseJSON(InputStream is, Class<T> clazz) throws IOException,
			InstantiationException, IllegalAccessException, JSONFormatException {

		Annotation[] as;
		as = clazz.getDeclaredAnnotations();
		boolean hash = false;
		boolean array = false;
		for (Annotation a : as) {
			if (a instanceof JSONHash) {
				hash = true;
			} else if (a instanceof JSONArray) {
				array = true;
			}
		}
		if (hash && array) {
			throw new IllegalStateException();
		}

		Map<String, Field> map = getConvertMapping(clazz);

		JSONPullParser parser = new JSONPullParser();
		parser.setInput(is);

		Object obj = clazz.newInstance();

		State s = null;
		Field f = null;
		while ((s = parser.getEventType()) != State.END_HASH) {
			switch (s) {
			case START_ARRAY:
			case START_HASH:
			case END_ARRAY:
			case END_HASH:
				break;

			case KEY:
				f = map.get(parser.getValueString());
				f.setAccessible(true);
				break;
			case VALUE_BOOLEAN:
				f.set(obj, parser.getValueBoolean());
				break;
			case VALUE_STRING:
				f.set(obj, parser.getValueString());
				break;
			case VALUE_DOUBLE:
				f.set(obj, parser.getValueDouble());
				break;
			case VALUE_INTEGER:
				f.set(obj, parser.getValueInt());
				break;
			case VALUE_NULL:
				break;
			default:
				break;
			}
		}

		return (T) obj;
	}

	private Map<String, Field> getConvertMapping(Class<T> clazz) {
		Map<String, Field> map = new HashMap<String, Field>();
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a instanceof JSONKey) {
					JSONKey k = (JSONKey) a;
					String name = k.value();
					if ("".equals(name)) {
						name = f.getName();
					}
					map.put(name, f);
					break;
				}
			}
		}
		return map;
	}
}
