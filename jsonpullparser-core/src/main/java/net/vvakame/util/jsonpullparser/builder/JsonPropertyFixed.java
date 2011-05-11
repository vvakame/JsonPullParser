package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.Writer;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

/**
 * Jsonの各要素変換用インスタンス.
 * @author vvakame
 * @param <T>
 */
public abstract class JsonPropertyFixed<T> {

	String name;


	/**
	 * JsonからJavaインスタンスへの変換を行う.
	 * @param writer 
	 * @param data
	 * @author vvakame
	 * @throws IOException 
	 */
	public abstract void encode(Writer writer, T data) throws IOException;

	/**
	 * JavaインスタンスからJsonへの変換を行う.
	 * @param parser 
	 * @param data
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	public abstract void decode(JsonPullParser parser, T data) throws IOException,
			JsonFormatException;
}
