package net.vvakame.util.jsonpullparser.builder;

/**
 * JSON property builder.
 * @author vvakame
 * @param <T>
 * @param <P> 
 */
public class JsonPropertyBuilder<T, P> implements JsonPropertyBuilderCreator<T> {

	Class<? extends JsonPropertyCoder<T, P>> coderClass;

	String name;

	JsonModelCoder<P> coder;

	JsonCoderRouter<P> router;


	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @param coder 
	 * @param router 
	 * @category constructor
	 */
	public JsonPropertyBuilder(Class<? extends JsonPropertyCoder<T, P>> coderClass, String name,
			JsonModelCoder<P> coder, JsonCoderRouter<P> router) {
		this.coderClass = coderClass;
		this.name = name;
		this.coder = coder;
		this.router = router;
	}

	@Override
	@SuppressWarnings("unchecked")
	public JsonPropertyBuilder<T, P> get() {
		return this;
	}

	/**
	 * Sets the JSON key name it uses.
	 * @param name
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T, P> name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the JSON value coder it uses.
	 * @param coder
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T, P> coder(JsonModelCoder<P> coder) {
		this.coder = coder;
		return this;
	}

	/**
	 * Sets the JSON value router it uses.
	 * @param router 
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T, P> router(JsonCoderRouter<P> router) {
		this.router = router;
		return this;
	}

	/**
	 * Fixiates the current state for coding.
	 * 
	 * @return A JsonModelCoder instance for the actual coding.
	 * @author vvakame
	 */
	public JsonPropertyCoder<T, P> fix() {
		JsonPropertyCoder<T, P> coder = null;
		try {
			coder = coderClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		coder.name = this.name;
		coder.coder = this.coder;
		coder.router = this.router;

		return coder;
	}
}
