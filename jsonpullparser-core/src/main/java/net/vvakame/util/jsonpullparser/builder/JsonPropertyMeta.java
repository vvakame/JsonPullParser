package net.vvakame.util.jsonpullparser.builder;

/**
 * Builder factory.
 * @author vvakame
 * @param <T>
 */
public class JsonPropertyMeta<T> implements JsonPropertyBuilderCreator {

	private Class<? extends JsonPropertyCoder<T>> coderClass;

	private String name;


	@Override
	@SuppressWarnings("unchecked")
	public JsonPropertyBuilder<T> get() {
		return getBuilder();
	}

	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @category constructor
	 */
	public JsonPropertyMeta(Class<? extends JsonPropertyCoder<T>> coderClass, String name) {
		this.coderClass = coderClass;
		this.name = name;
	}

	JsonPropertyBuilder<T> getBuilder() {
		return new JsonPropertyBuilder<T>(coderClass, name, null);
	}

	/**
	 * Gets a new instance of property builder for the given key name.
	 * @param name
	 * @return a new property builder instance
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> name(String name) {
		return new JsonPropertyBuilder<T>(coderClass, name, null);
	}

	/**
	 * Gets a new instance of property builder for the given value coder.
	 * @param coder
	 * @return a new property builder instance
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> coder(JsonModelCoder<?> coder) {
		return new JsonPropertyBuilder<T>(coderClass, name, coder);
	}
}
