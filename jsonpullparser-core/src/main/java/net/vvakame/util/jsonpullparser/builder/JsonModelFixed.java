package net.vvakame.util.jsonpullparser.builder;

import java.util.Map;

public class JsonModelFixed<T> {

	boolean treatUnknownKeyAsError;

	Map<String, JsonPropertyFixed<T>> map;


	public JsonModelFixed(boolean treatUnknownKeyAsError, Map<String, JsonPropertyFixed<T>> map) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		this.map = map;
	}

	public void encode(T data) {
		for (String key : map.keySet()) {
			JsonPropertyFixed<T> enc = map.get(key);
			enc.encode(data);
		}
	}

	public void decode(T data) {
		for (String key : map.keySet()) {
			JsonPropertyFixed<T> dec = map.get(key);
			dec.decode(data);
		}
	}
}
