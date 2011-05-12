package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;

/**
 * Json変換用インスタンス.
 * @author vvakame
 * @param <T>
 */
public class JsonModelCoder<T> {

	Class<T> baseClass;

	boolean treatUnknownKeyAsError;

	Map<String, JsonPropertyCoder<T>> map;


	/**
	 * the constructor.
	 * @param baseClass 
	 * @param treatUnknownKeyAsError
	 * @param map
	 * @category constructor
	 */
	public JsonModelCoder(Class<T> baseClass, boolean treatUnknownKeyAsError,
			Map<String, JsonPropertyCoder<T>> map) {
		this.baseClass = baseClass;
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		this.map = map;
	}

	/**
	 * JSONの {@link List} に変換します.
	 * 
	 * @param json JSONによるインスタンスの表現
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public List<T> getList(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, null);
	}

	/**
	 * JSONの {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによる@return インスタンスの {@link List}の表現
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public List<T> getList(String json, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, listener);
	}

	/**
	 * JSONの {@link List} に変換します.
	 * 
	 * @param stream JSONによるインスタンスの表現
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public List<T> getList(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, null);
	}

	/**
	 * JSONの {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによるインスタンスの表現
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public List<T> getList(InputStream stream, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, listener);
	}

	/**
	 * JSONの {@link List} に変換します.
	 * 
	 * @param parser インスタンスの表現をセットされた {@link JsonPullParser}
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public List<T> getList(JsonPullParser parser) throws IOException, JsonFormatException {
		return getList(parser, null);
	}

	/**
	 * JSONを {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser インスタンスの表現をセットされた {@link JsonPullParser}
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンスの {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、期待しない形式だった場合に投げられます
	 */
	public List<T> getList(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
		List<T> list = new ArrayList<T>();
		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_ARRAY) {
			if (eventType == State.START_HASH) {
				throw new JsonFormatException("not started '('!, Do you want the json hash?");
			} else {
				throw new JsonFormatException("not started '('!");
			}
		}
		while (parser.lookAhead() != State.END_ARRAY) {
			T tmp = get(parser, listener);
			list.add(tmp);
		}
		parser.getEventType();
		return list;
	}

	/**
	 * JSONをインスタンスに変換します.
	 * 
	 * @param json JSONによるインスタンスの表現
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, null);
	}

	/**
	 * JSONをインスタンスに変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによるインスタンスの表現
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(String json, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser);
	}

	/**
	 * JSONをインスタンスに変換します.
	 * 
	 * @param stream JSONによるインスタンスの表現
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, null);
	}

	/**
	 * JSONをインスタンスに変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによるインスタンスの表現
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(InputStream stream, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, listener);
	}

	/**
	 * JSONをインスタンスに変換します.
	 * 
	 * @param parser インスタンスの表現をセットされた {@link JsonPullParser}
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(JsonPullParser parser) throws IOException, JsonFormatException {
		return get(parser, null);
	}

	/**
	 * JSONをインスタンスに変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser インスタンスの表現をセットされた {@link JsonPullParser}
	 * @param listener インスタンス生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return インスタンス
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public T get(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_HASH) {
			if (eventType == State.START_ARRAY) {
				throw new JsonFormatException("not started '{'! Do you want the json array?");
			} else {
				throw new JsonFormatException("not started '{'!");
			}
		}
		T obj;
		try {
			obj = baseClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		while ((eventType = parser.getEventType()) != State.END_HASH) {
			if (eventType != State.KEY) {
				throw new JsonFormatException("expect KEY. we got unexpected value. " + eventType);
			}
			String key = parser.getValueString();

			if (parseValue(parser, listener, key, obj)) {
				continue;

			} else {
				if (treatUnknownKeyAsError) {
					throw new JsonFormatException("unknown key. key=" + key);
				} else {
					parser.discardValue();
				}
			}
		}

		if (listener != null) {
			listener.onAdd(obj);
		}

		return obj;
	}

	/**
	 * ※ このメソッドを呼び出さないでください ※.<br>
	 * 生成クラスの内部から呼び出すためのメソッドです.他パッケージにある生成クラスからアクセス出来るようにするため、publicになっています.
	 * @param parser 利用途中の {@link JsonPullParser}
	 * @param listener インスタンスが生成した場合に通知する {@link OnJsonObjectAddListener}
	 * @param key 処理途中のJSONのkey
	 * @param obj 組み立て途中のインスタンスもしくはそのサブクラスのインスタンス
	 * @return keyの処理に成功したかどうか
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 * @author vvakame
	 */
	public boolean parseValue(JsonPullParser parser, OnJsonObjectAddListener listener, String key,
			T obj) throws IOException, JsonFormatException {

		JsonPropertyCoder<T> dec = map.get(key);
		if (dec == null) {
			return false;
		} else {
			dec.decode(parser, obj);
		}

		return true;
	}

	/**
	 * インスタンスの {@link List} のJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param list JSON変換したいインスタンス の {@link List}
	 * @throws IOException 
	 */
	public void encodeList(OutputStream out, List<T> list) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeListNullToBlank(writer, list);
	}

	/**
	 * インスタンスの {@link List} のJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したいインスタンスの {@link List}
	 * @throws IOException 
	 */
	public void encodeList(Writer writer, List<T> list) throws IOException {
		encodeListNullToBlank(writer, list);
	}

	/**
	 * インスタンスの {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したいインスタンスの {@link List}
	 * @throws IOException 
	 */
	public void encodeListNullToBlank(Writer writer, List<T> list) throws IOException {
		if (list == null) {
			writer.write("[]");
			writer.flush();
			return;
		}

		encodeListNullToNull(writer, list);
	}

	/**
	 * インスタンスの {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したいインスタンスの {@link List}
	 * @throws IOException 
	 */
	public void encodeListNullToNull(Writer writer, List<T> list) throws IOException {
		if (list == null) {
			writer.write("null");
			writer.flush();
			return;
		}
		JsonUtil.startArray(writer);

		int size = list.size();
		for (int i = 0; i < size; i++) {

			encodeNullToNull(writer, list.get(i));

			if (i + 1 < size) {
				JsonUtil.addSeparator(writer);
			}
		}

		JsonUtil.endArray(writer);

		writer.flush();
	}

	/**
	 * インスタンスのJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, Object)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param obj JSON変換したいインスタンス
	 * @throws IOException 
	 */
	public void encode(OutputStream out, T obj) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeNullToBlank(writer, obj);
	}

	/**
	 * インスタンスのJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, Object)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したいインスタンス
	 * @throws IOException 
	 */
	public void encode(Writer writer, T obj) throws IOException {
		encodeNullToBlank(writer, obj);
	}

	/**
	 * インスタンスのJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したいインスタンス
	 * @throws IOException 
	 */
	public void encodeNullToBlank(Writer writer, T obj) throws IOException {
		if (obj == null) {
			writer.write("{}");
			writer.flush();
			return;
		}

		encodeNullToNull(writer, obj);
	}

	/**
	 * インスタンスのJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{@code "null"} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したいインスタンス
	 * @throws IOException 
	 */
	public void encodeNullToNull(Writer writer, T obj) throws IOException {
		if (obj == null) {
			writer.write("null");
			return;
		}

		JsonUtil.startHash(writer);

		encodeValue(writer, obj);

		JsonUtil.endHash(writer);

		writer.flush();
	}

	/**
	 * ※ このメソッドを呼び出さないでください ※.<br>
	 * 生成クラスの内部から呼び出すためのメソッドです.他パッケージにある生成クラスからアクセス出来るようにするため、publicになっています.
	 * @param writer 出力先
	 * @param obj データ元
	 * @throws IOException 
	 * @author vvakame
	 */
	public void encodeValue(Writer writer, T obj) throws IOException {
		int count = 0;
		for (String key : map.keySet()) {
			JsonUtil.putKey(writer, key);
			JsonPropertyCoder<T> enc = map.get(key);
			enc.encode(writer, obj);
			count++;
			if (count != map.size()) {
				JsonUtil.addSeparator(writer);
			}
		}
	}
}
