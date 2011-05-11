package net.vvakame.util.jsonpullparser.builder;

import net.vvakame.util.jsonpullparser.util.TokenConverter;

public abstract class JsonPropertyFixed<T> {

	String name;

	TokenConverter<T> conv;


	public abstract void encode(T data);

	public abstract void decode(T data);
}