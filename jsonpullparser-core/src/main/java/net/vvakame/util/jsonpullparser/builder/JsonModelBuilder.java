package net.vvakame.util.jsonpullparser.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Jsonに対応する実行時組立用クラス.
 * @author vvakame
 * @param <T>
 */
public abstract class JsonModelBuilder<T> {

	protected Class<T> baseClass;

	protected boolean treatUnknownKeyAsError;

	protected Map<String, JsonPropertyBuilder<T>> map =
			new LinkedHashMap<String, JsonPropertyBuilder<T>>();


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
	 * 全要素のJsonの各要素に対応したビルダの追加
	 * @return this
	 * @author vvakame
	 */
	public abstract JsonModelBuilder<T> addAll();

	/**
	 * Jsonの各要素に対応したビルダの追加
	 * @param creators
	 * @return this
	 * @author vvakame
	 */
	public JsonModelBuilder<T> add(JsonPropertyBuilderCreator... creators) {
		for (JsonPropertyBuilderCreator creator : creators) {
			addSub(creator.<T> get());
		}
		return this;
	}

	protected void addSub(JsonPropertyBuilder<T> builder) {
		map.put(builder.name, builder);
	}

	/**
	 * 指定されたビルダを取り除く
	 * @param creators
	 * @return this
	 * @author vvakame
	 */
	public JsonModelBuilder<T> rm(JsonPropertyBuilderCreator... creators) {
		for (JsonPropertyBuilderCreator creator : creators) {
			rmSub(creator.<T> get());
		}
		return this;
	}

	protected void rmSub(JsonPropertyBuilder<T> builder) {
		map.remove(builder.name);
	}

	/**
	 * 指定されたビルダを取り除く
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
	 * Json組立時未知のKeyを検知した際、無視するか例外を投げるか.
	 * @param treatUnknownKeyAsError
	 * @return this
	 * @author vvakame
	 */
	public JsonModelBuilder<T> treatUnknownKeyAsError(boolean treatUnknownKeyAsError) {
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		return this;
	}

	/**
	 * 現在組立中の内容で確定する.
	 * @return 固定されたJson変換用インスタンス
	 * @author vvakame
	 */
	public abstract JsonModelCoder<T> fix();
}
