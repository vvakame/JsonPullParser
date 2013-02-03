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
 * JSON coding facility.
 * @author vvakame
 * @param <T>
 */
public class JsonModelCoder<T> {

	Class<T> baseClass;

	boolean treatUnknownKeyAsError;

	Map<String, JsonPropertyCoder<T, ?>> map;


	/**
	 * the constructor.
	 * @param baseClass 
	 * @param treatUnknownKeyAsError
	 * @param map
	 * @category constructor
	 */
	public JsonModelCoder(Class<T> baseClass, boolean treatUnknownKeyAsError,
			Map<String, JsonPropertyCoder<T, ?>> map) {
		this.baseClass = baseClass;
		this.treatUnknownKeyAsError = treatUnknownKeyAsError;
		this.map = map;
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.
	 *
	 * @param json JSON-formatted data
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public List<T> getList(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, null);
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param json JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public List<T> getList(String json, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, listener);
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.
	 *
	 * @param stream JSON-formatted data
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public List<T> getList(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, null);
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param stream JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public List<T> getList(InputStream stream, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, listener);
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.
	 * 
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public List<T> getList(JsonPullParser parser) throws IOException, JsonFormatException {
		return getList(parser, null);
	}

	/**
	 * Attempts to parse the given data as {@link List} of objects.<br>
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return {@link List} of objects
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
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
				throw new JsonFormatException("not started '('!, Do you want the json hash?",
						parser);
			} else {
				throw new JsonFormatException("not started '('!", parser);
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
	 * Attempts to parse the given data as an object.
	 *
	 * @param json JSON-formatted data
	 * @return An object instance
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, null);
	}

	/**
	 * Attempts to parse the given data as an object.<br>
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param json JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return An object instance
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(String json, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser);
	}

	/**
	 * Attempts to parse the given data as an object.
	 *
	 * @param stream JSON-formatted data
	 * @return An object instance
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, null);
	}

	/**
	 * Attempts to parse the given data as an object.<br>
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param stream JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return An object instance
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(InputStream stream, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, listener);
	}

	/**
	 * Attempts to parse the given data as an object.
	 *
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @return An object instance
	 * @throws IOException 
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(JsonPullParser parser) throws IOException, JsonFormatException {
		return get(parser, null);
	}

	/**
	 * Attempts to parse the given data as an object.<br>
	 * Accepts {@link OnJsonObjectAddListener}; allows you to peek various intermittent instances as parsing goes.
	 * 
	 * @param parser {@link JsonPullParser} with some JSON-formatted data
	 * @param listener {@link OnJsonObjectAddListener} to notify
	 * @return An object instance
	 * @throws IOException 
	 * @throws IllegalStateException @SaveOrigin is enabled while {@link JsonPullParser#setLogEnable()} is not called yet.
	 * @throws JsonFormatException The given data is malformed, or its type is unexpected
	 */
	public T get(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {
		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			return null;
		}
		if (eventType != State.START_HASH) {
			if (eventType == State.START_ARRAY) {
				throw new JsonFormatException("not started '{'! Do you want the json array?",
						parser);
			} else {
				throw new JsonFormatException("not started '{'!", parser);
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
				throw new JsonFormatException("expect KEY. we got unexpected value. " + eventType,
						parser);
			}
			String key = parser.getValueString();

			if (parseValue(parser, listener, key, obj)) {
				continue;

			} else {
				if (treatUnknownKeyAsError) {
					throw new JsonFormatException("unknown key. key=" + key, parser);
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
	 * *** Internal use only; please do not call directly. ***<br>
	 * *** Public access granted only to allow reusing from other packages. ***
	 * 
	 * @param parser {@link JsonPullParser} in action
	 * @param listener {@link OnJsonObjectAddListener} to listen new instance creations
	 * @param key JSON key being parsed
	 * @param obj target (and like) being rebuilt
	 * @return True if the given key is parsed successfully, false otherwise
	 * @throws IOException 
	 * @throws JsonFormatException Data is malformed, or its type is unexpected
	 * @author vvakame
	 */
	public boolean parseValue(JsonPullParser parser, OnJsonObjectAddListener listener, String key,
			T obj) throws IOException, JsonFormatException {

		JsonPropertyCoder<T, ?> dec = map.get(key);
		if (dec == null) {
			return false;
		} else {
			dec.decode(parser, obj);
		}

		return true;
	}

	/**
	 * Encodes the given {@link List} of values into the JSON format, and appends it into the given stream using {@link JsonPullParser#DEFAULT_CHARSET}.<br>
	 * This method is an alias of {@link #encodeListNullToBlank(Writer, List)}.
	 * 
	 * @param out {@link OutputStream} to be written
	 * @param list {@link List} of values to be encoded
	 * @throws IOException 
	 */
	public void encodeList(OutputStream out, List<? extends T> list) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeListNullToBlank(writer, list);
	}

	/**
	 * Encodes the given {@link List} of values into the JSON format, and writes it using the given writer.<br>
	 * This method is an alias of {@link #encodeListNullToBlank(Writer, List)}.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param list {@link List} of values to be encoded
	 * @throws IOException 
	 */
	public void encodeList(Writer writer, List<? extends T> list) throws IOException {
		encodeListNullToBlank(writer, list);
	}

	/**
	 * Encodes the given {@link List} of values into the JSON format, and writes it using the given writer.<br>
	 * Writes "[]" if null is given.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param list {@link List} of values to be encoded
	 * @throws IOException 
	 */
	public void encodeListNullToBlank(Writer writer, List<? extends T> list) throws IOException {
		if (list == null) {
			writer.write("[]");
			writer.flush();
			return;
		}

		encodeListNullToNull(writer, list);
	}

	/**
	 * Encodes the given {@link List} of values into the JSON format, and writes it using the given writer.<br>
	 * Writes "null" if null is given.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param list {@link List} of values to be encoded
	 * @throws IOException 
	 */
	public void encodeListNullToNull(Writer writer, List<? extends T> list) throws IOException {
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
	 * Encodes the given value into the JSON format, and appends it into the given stream using {@link JsonPullParser#DEFAULT_CHARSET}.<br>
	 * This method is an alias of {@link #encodeListNullToBlank(Writer, List)}.
	 * 
	 * @param out {@link OutputStream} to be written
	 * @param obj Value to encoded
	 * @throws IOException 
	 */
	public void encode(OutputStream out, T obj) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeNullToBlank(writer, obj);
	}

	/**
	 * Encodes the given value into the JSON format, and writes it using the given writer.<br>
	 * This method is an alias of {@link #encodeListNullToBlank(Writer, List)}.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param obj Value to encoded
	 * @throws IOException 
	 */
	public void encode(Writer writer, T obj) throws IOException {
		encodeNullToBlank(writer, obj);
	}

	/**
	 * Encodes the given value into the JSON format, and writes it using the given writer.<br>
	 * Writes "{}" if null is given.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param obj Value to encoded
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
	 * Encodes the given value into the JSON format, and writes it using the given writer.<br>
	 * Writes "null" if null is given.
	 * 
	 * @param writer {@link Writer} to be used for writing value
	 * @param obj Value to encoded
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
	 * *** Internal use only; please do not call directly. ***<br>
	 * *** Public access granted only to allow reusing from other packages. ***
	 * 
	 * @param writer {@link Writer}
	 * @param obj value
	 * @throws IOException 
	 * @author vvakame
	 */
	public void encodeValue(Writer writer, T obj) throws IOException {
		int count = 0;
		for (String key : map.keySet()) {
			JsonUtil.putKey(writer, key);
			JsonPropertyCoder<T, ?> enc = map.get(key);
			enc.encode(writer, obj);
			count++;
			if (count != map.size()) {
				JsonUtil.addSeparator(writer);
			}
		}
	}
}
