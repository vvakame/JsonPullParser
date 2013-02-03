package net.vvakame.util.jsonpullparser.builder;

/**
 * JSON property builder.
 * @author vvakame
 * @param <T>
 */
public class JsonPropertyBuilder<T> implements JsonPropertyBuilderCreator {

	Class<? extends JsonPropertyCoder<T>> coderClass;

	String name;

	JsonModelCoder<?> coder;


	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @param coder 
	 * @category constructor
	 */
	public JsonPropertyBuilder(Class<? extends JsonPropertyCoder<T>> coderClass, String name,
			JsonModelCoder<?> coder) {
		this.coderClass = coderClass;
		this.name = name;
		this.coder = coder;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JsonPropertyBuilder<T> get() {
		return this;
	}

	/**
	 * Sets the JSON key name it uses.
	 * @param name
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the JSON value coder it uses.
	 * @param coder
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> coder(JsonModelCoder<?> coder) {
		this.coder = coder;
		return this;
	}

	/**
	 * Fixiates the current state for coding.
	 * 
	 * @return A JsonModelCoder instance for the actual coding.
	 * @author vvakame
	 */
	@SuppressWarnings("unchecked")
	public JsonPropertyCoder<T> fix() {
		JsonPropertyCoder<T> coder = null;
		try {
			coder = coderClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		coder.name = this.name;
		coder.coder = (JsonModelCoder<Object>) this.coder;

		return coder;
	}
}
