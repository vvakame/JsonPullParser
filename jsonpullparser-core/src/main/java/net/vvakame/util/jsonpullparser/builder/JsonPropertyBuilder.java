package net.vvakame.util.jsonpullparser.builder;

/**
 * Jsonの各要素に対応する実行時組立用クラス.
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
	 * 変換時に使うKey名を設定する.
	 * @param name
	 * @return this
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 現在組立中の内容で確定する.
	 * @return 固定されたJson変換用インスタンス
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
