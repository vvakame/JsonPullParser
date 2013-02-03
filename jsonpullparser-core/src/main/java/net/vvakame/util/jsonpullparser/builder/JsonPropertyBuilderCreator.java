package net.vvakame.util.jsonpullparser.builder;

/**
 * An interface for providing consistent access to {@link JsonPropertyBuilder} across various argument patterns.
 * @author vvakame
 */
public interface JsonPropertyBuilderCreator {

	/**
	 * Gets a new {@link JsonPropertyBuilder} instance.
	 * @param <T> 
	 * @return An instance of {@link JsonPropertyBuilder}
	 * @author vvakame
	 */
	<T, P>JsonPropertyBuilder<T, P> get();
}
