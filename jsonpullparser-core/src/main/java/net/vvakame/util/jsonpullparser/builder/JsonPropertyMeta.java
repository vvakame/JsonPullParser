package net.vvakame.util.jsonpullparser.builder;

import net.vvakame.util.jsonpullparser.util.TokenConverter;

public class JsonPropertyMeta<T> implements JsonPropertyBuilderCreator<T> {

	Class<? extends JsonPropertyFixed<T>> fixedClass;

	String name;

	Class<?> clazz;


	@Override
	public JsonPropertyBuilder<T> get() {
		return getBuilder();
	}

	public JsonPropertyMeta(Class<? extends JsonPropertyFixed<T>> fixedClass, String name,
			Class<?> propertyClass, boolean treatUnknownKeyAsError) {
		this.fixedClass = fixedClass;
		this.name = name;
		this.clazz = propertyClass;
	}

	JsonPropertyBuilder<T> getBuilder() {
		return new JsonPropertyBuilder<T>(fixedClass, name, clazz);
	}

	public JsonPropertyBuilder name(String name) {
		return new JsonPropertyBuilder<T>(fixedClass, name, clazz);
	}

	public JsonPropertyBuilder converter(TokenConverter<T> conv) {
		return new JsonPropertyBuilder<T>(fixedClass, name, clazz).converter(conv);
	}
}
