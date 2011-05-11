package net.vvakame.util.jsonpullparser.builder;

import net.vvakame.util.jsonpullparser.util.TokenConverter;

public class JsonPropertyBuilder<T> implements JsonPropertyBuilderCreator<T> {

	Class<? extends JsonPropertyFixed<T>> fixedClass;

	String name;

	Class<?> clazz;

	TokenConverter<T> conv;


	public JsonPropertyBuilder(Class<? extends JsonPropertyFixed<T>> fixedClass, String name,
			Class<?> propertyClass) {
		this.fixedClass = fixedClass;
		this.name = name;
		this.clazz = propertyClass;
	}

	@Override
	public JsonPropertyBuilder<T> get() {
		return this;
	}

	public JsonPropertyBuilder<T> converter(TokenConverter<T> conv) {
		this.conv = conv;
		return this;
	}

	public JsonPropertyBuilder<T> name(String name) {
		this.name = name;
		return this;
	}

	public JsonPropertyFixed<T> fix() {
		JsonPropertyFixed<T> fixed = null;
		try {
			fixed = fixedClass.newInstance();
		} catch (InstantiationException e) {
			// FIXME
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// FIXME
			throw new RuntimeException(e);
		}
		fixed.name = name;
		fixed.conv = conv;

		return fixed;
	}
}
