package net.vvakame.util.jsonpullparser.builder;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class JsonModelBuilder<T> {

	protected boolean treatUnknownKeyAsError;

	protected Map<String, JsonPropertyBuilder<T>> map =
			new LinkedHashMap<String, JsonPropertyBuilder<T>>();


	public JsonModelBuilder(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
	}

	public JsonModelBuilder<T> add(JsonPropertyBuilder builder) {
		map.put(builder.name, builder);
		return this;
	}

	public JsonModelBuilder<T> add(JsonPropertyBuilderCreator<T>... creators) {
		for (JsonPropertyBuilderCreator<T> creator : creators) {
			add(creator.get());
		}
		return this;
	}

	public JsonModelBuilder<T> treatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		return this;
	}

	public abstract JsonModelFixed<T> fix();
}
