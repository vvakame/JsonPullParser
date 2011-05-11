package net.vvakame.util.jsonpullparser.builder;

/**
 * 複数の引数パターンで常に {@link JsonPropertyBuilder} を取得できるようにするためのIF
 * @author vvakame
 */
public interface JsonPropertyBuilderCreator {

	/**
	 * {@link JsonPropertyBuilder} を取得する.
	 * @param <T> 
	 * @return ビルダ
	 * @author vvakame
	 */
	<T>JsonPropertyBuilder<T> get();
}
