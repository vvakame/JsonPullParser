package net.vvakame.util.jsonpullparser.builder;


/**
 * Jsonの各要素に対応する実行時組立用クラス.
 * @author vvakame
 * @param <T>
 */
public class JsonPropertyBuilder<T> implements JsonPropertyBuilderCreator {

	Class<? extends JsonPropertyFixed<T>> fixedClass;

	String name;


	/**
	 * the constructor.
	 * @param fixedClass
	 * @param name
	 * @category constructor
	 */
	public JsonPropertyBuilder(Class<? extends JsonPropertyFixed<T>> fixedClass, String name) {
		this.fixedClass = fixedClass;
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
	public JsonPropertyFixed<T> fix() {
		JsonPropertyFixed<T> fixed = null;
		try {
			fixed = fixedClass.newInstance();
		} catch (InstantiationException e) {
			// FIXME
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// FIXME
			throw new RuntimeException(e);
		}
		fixed.name = name;

		return fixed;
	}
}
