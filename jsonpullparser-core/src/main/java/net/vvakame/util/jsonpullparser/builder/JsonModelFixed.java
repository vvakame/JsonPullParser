package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;

/**
 * Json変換用インスタンス.
 * @author vvakame
 * @param <T>
 */
public class JsonModelFixed<T> {

	Class<T> baseClass;

	boolean treatUnknownKeyAsError;

	Map<String, JsonPropertyFixed<T>> map;


	/**
	 * the constructor.
	 * @param baseClass 
	 * @param treatUnknownKeyAsError
	 * @param map
	 * @category constructor
	 */
	public JsonModelFixed(Class<T> baseClass, boolean treatUnknownKeyAsError,
			Map<String, JsonPropertyFixed<T>> map) {
		this.baseClass = baseClass;
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		this.map = map;
	}

	/**
	 * JsonからJavaインスタンスへの変換を行う.
	 * @param out
	 * @param data
	 * @author vvakame
	 * @throws IOException 
	 */
	public void encode(OutputStream out, T data) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encode(writer, data);
	}

	/**
	 * JsonからJavaインスタンスへの変換を行う.
	 * @param writer 
	 * @param data
	 * @author vvakame
	 * @throws IOException 
	 */
	public void encode(Writer writer, T data) throws IOException {
		JsonUtil.startHash(writer);
		int count = 0;
		for (String key : map.keySet()) {
			JsonUtil.putKey(writer, key);
			JsonPropertyFixed<T> enc = map.get(key);
			enc.encode(writer, data);
			count++;
			if (count != map.size()) {
				JsonUtil.addSeparator(writer);
			}
		}
		JsonUtil.endHash(writer);
	}

	/**
	 * JavaインスタンスからJsonへの変換を行う.
	 * @param parser 
	 * @author vvakame
	 * @return 生成したJavaインスタンス
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	public T decode(JsonPullParser parser) throws IOException, JsonFormatException {
		T data;
		try {
			data = baseClass.newInstance();
		} catch (InstantiationException e) {
			// FIXME
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// FIXME
			throw new RuntimeException(e);
		}

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
		while ((eventType = parser.getEventType()) != State.END_HASH) {
			if (eventType != State.KEY) {
				throw new JsonFormatException("expect KEY. we got unexpected value. " + eventType);
			}
			String key = parser.getValueString();

			JsonPropertyFixed<T> dec = map.get(key);
			if (dec == null && treatUnknownKeyAsError) {
				throw new JsonFormatException("unsupported key. key=" + key);
			} else if (dec == null) {
				parser.discardValue();
			} else {
				dec.decode(parser, data);
			}
		}
		return data;
	}
}
