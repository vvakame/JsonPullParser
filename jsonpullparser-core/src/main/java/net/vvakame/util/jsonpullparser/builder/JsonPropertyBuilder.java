package net.vvakame.util.jsonpullparser.builder;

/**
 * JSON property builder.
 * @author vvakame
 * @param <T>
 */
public class JsonPropertyBuilder<T> implements JsonPropertyBuilderCreator {

	Class<? extends JsonPropertyCoder<T>> coderClass;

	String name;


	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @category constructor
	 */
	public JsonPropertyBuilder(Class<? extends JsonPropertyCoder<T>> coderClass, String name) {
		this.coderClass = coderClass;
		this.name = name;
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
	 * Fixiates the current state for coding.
	 * 
	 * @return A JsonModelCoder instance for the actual coding.
	 * @author vvakame
	 */
	public JsonPropertyCoder<T> fix() {
		JsonPropertyCoder<T> coder = null;
		try {
			coder = coderClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		coder.name = name;

		return coder;
	}
}
