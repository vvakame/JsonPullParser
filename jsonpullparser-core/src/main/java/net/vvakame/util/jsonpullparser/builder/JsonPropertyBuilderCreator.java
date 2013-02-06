package net.vvakame.util.jsonpullparser.builder;

/**
 * An interface for providing consistent access to {@link JsonPropertyBuilder} across various argument patterns.
 * @param <T> 
 * @author vvakame
 */
public interface JsonPropertyBuilderCreator<T> {

	/**
	 * Gets a new {@link JsonPropertyBuilder} instance.
	 * @return An instance of {@link JsonPropertyBuilder}
	 * @author vvakame
	 */
	<P>JsonPropertyBuilder<T, P> get();
}
