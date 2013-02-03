package net.vvakame.util.jsonpullparser.builder;

/**
 * Builder factory.
 * @author vvakame
 * @param <T>
 * @param <P> 
 */
public class JsonPropertyMeta<T, P> implements JsonPropertyBuilderCreator {

	private Class<? extends JsonPropertyCoder<T, P>> coderClass;

	private String name;


	@Override
	@SuppressWarnings("unchecked")
	public JsonPropertyBuilder<T, P> get() {
		return getBuilder();
	}

	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @category constructor
	 */
	public JsonPropertyMeta(Class<? extends JsonPropertyCoder<T, P>> coderClass, String name) {
		this.coderClass = coderClass;
		this.name = name;
	}

	JsonPropertyBuilder<T, P> getBuilder() {
		return new JsonPropertyBuilder<T, P>(coderClass, name, null);
	}

	/**
	 * Gets a new instance of property builder for the given key name.
	 * @param name
	 * @return a new property builder instance
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T, P> name(String name) {
		return new JsonPropertyBuilder<T, P>(coderClass, name, null);
	}

	/**
	 * Gets a new instance of property builder for the given value coder.
	 * @param coder
	 * @return a new property builder instance
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T, P> coder(JsonModelCoder<P> coder) {
		return new JsonPropertyBuilder<T, P>(coderClass, name, coder);
	}
}
