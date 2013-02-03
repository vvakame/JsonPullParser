package net.vvakame.util.jsonpullparser.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON model builder.
 * @author vvakame
 * @param <T>
 */
public abstract class JsonModelBuilder<T> {

	protected Class<T> baseClass;

	protected boolean treatUnknownKeyAsError;

	protected Map<String, JsonPropertyBuilder<T, ?>> map =
			new LinkedHashMap<String, JsonPropertyBuilder<T, ?>>();


	/**
	 * the constructor.
	 * @param baseClass 
	 * @param treatUnknownKeyAsError
	 * @category constructor
	 */
	public JsonModelBuilder(Class<T> baseClass, boolean treatUnknownKeyAsError) {
		this.baseClass = baseClass;
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
	}

	/**
	 * Attaches property builder for the all types.
	 * @return this
	 * @author vvakame
	 */
	public abstract JsonModelBuilder<T> addAll();

	/**
	 * Attaches property builder for the given types.
	 * @param creators
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator... creators) {
		for (JsonPropertyBuilderCreator creator : creators) {
			addSub(creator.<T, P> get());
		}
		return this;
	}

	protected <P>void addSub(JsonPropertyBuilder<T, P> builder) {
		map.put(builder.name, builder);
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creators
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator... creators) {
		for (JsonPropertyBuilderCreator creator : creators) {
			rmSub(creator.<T, P> get());
		}
		return this;
	}

	protected <P>void rmSub(JsonPropertyBuilder<T, P> builder) {
		map.remove(builder.name);
	}

	/**
	 * Detaches property builder for the given names.
	 * @param names
	 * @return this
	 * @author vvakame
	 */
	public JsonModelBuilder<T> rm(String... names) {
		for (String name : names) {
			rmSub(name);
		}
		return this;
	}

	protected void rmSub(String name) {
		map.remove(name);
	}

	/**
	 * Sets if the exception should be thrown upon deserializing unknown keys.
	 * @param treatUnknownKeyAsError
	 * @return this
	 * @author vvakame
	 */
	public JsonModelBuilder<T> treatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		return this;
	}

	/**
	 * Fixiates the current state for coding.
	 * @return A JsonModelCoder instance for the actual coding.
	 * @author vvakame
	 */
	public abstract JsonModelCoder<T> fix();
}
