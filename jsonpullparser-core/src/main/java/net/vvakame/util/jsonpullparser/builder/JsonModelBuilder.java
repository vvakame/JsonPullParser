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
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T>... creators) {
		for (JsonPropertyBuilderCreator<T> creator : creators) {
			addSub(creator.<P> get());
		}
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator) {
		addSub(creator.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		addSub(creator6.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		addSub(creator6.<P> get());
		addSub(creator7.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		addSub(creator6.<P> get());
		addSub(creator7.<P> get());
		addSub(creator8.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @param creator9 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8, JsonPropertyBuilderCreator<T> creator9) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		addSub(creator6.<P> get());
		addSub(creator7.<P> get());
		addSub(creator8.<P> get());
		addSub(creator9.<P> get());
		return this;
	}

	/**
	 * Attaches property builder for the given types.
	 * @param creator1 
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @param creator9 
	 * @param creator10 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8, JsonPropertyBuilderCreator<T> creator9,
			JsonPropertyBuilderCreator<T> creator10) {
		addSub(creator1.<P> get());
		addSub(creator2.<P> get());
		addSub(creator3.<P> get());
		addSub(creator4.<P> get());
		addSub(creator5.<P> get());
		addSub(creator6.<P> get());
		addSub(creator7.<P> get());
		addSub(creator8.<P> get());
		addSub(creator9.<P> get());
		addSub(creator10.<P> get());
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
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T>... creators) {
		for (JsonPropertyBuilderCreator<T> creator : creators) {
			rmSub(creator.<P> get());
		}
		return this;
	}

	protected <P>void rmSub(JsonPropertyBuilder<T, P> builder) {
		map.remove(builder.name);
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator) {
		rmSub(creator.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		rmSub(creator6.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		rmSub(creator6.<P> get());
		rmSub(creator7.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		rmSub(creator6.<P> get());
		rmSub(creator7.<P> get());
		rmSub(creator8.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @param creator9 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8, JsonPropertyBuilderCreator<T> creator9) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		rmSub(creator6.<P> get());
		rmSub(creator7.<P> get());
		rmSub(creator8.<P> get());
		rmSub(creator9.<P> get());
		return this;
	}

	/**
	 * Detaches property builder for the given types.
	 * @param creator1
	 * @param creator2 
	 * @param creator3 
	 * @param creator4 
	 * @param creator5 
	 * @param creator6 
	 * @param creator7 
	 * @param creator8 
	 * @param creator9 
	 * @param creator10 
	 * @return this
	 * @author vvakame
	 */
	public <P>JsonModelBuilder<T> rm(JsonPropertyBuilderCreator<T> creator1,
			JsonPropertyBuilderCreator<T> creator2, JsonPropertyBuilderCreator<T> creator3,
			JsonPropertyBuilderCreator<T> creator4, JsonPropertyBuilderCreator<T> creator5,
			JsonPropertyBuilderCreator<T> creator6, JsonPropertyBuilderCreator<T> creator7,
			JsonPropertyBuilderCreator<T> creator8, JsonPropertyBuilderCreator<T> creator9,
			JsonPropertyBuilderCreator<T> creator10) {
		rmSub(creator1.<P> get());
		rmSub(creator2.<P> get());
		rmSub(creator3.<P> get());
		rmSub(creator4.<P> get());
		rmSub(creator5.<P> get());
		rmSub(creator6.<P> get());
		rmSub(creator7.<P> get());
		rmSub(creator8.<P> get());
		rmSub(creator9.<P> get());
		rmSub(creator10.<P> get());
		return this;
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
	public JsonModelCoder<T> fix() {
		Map<String, JsonPropertyCoder<T, ?>> properties =
				new LinkedHashMap<String, JsonPropertyCoder<T, ?>>();
		for (String key : map.keySet()) {
			JsonPropertyBuilder<T, ?> builder = map.get(key);
			JsonPropertyCoder<T, ?> fixed = builder.fix();
			properties.put(key, fixed);
		}

		JsonModelCoder<T> fixed =
				new JsonModelCoder<T>(baseClass, treatUnknownKeyAsError, properties);
		return fixed;
	}
}
