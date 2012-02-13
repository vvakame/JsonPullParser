package net.vvakame.util.jsonpullparser.builder;

import java.io.IOException;
import java.io.Writer;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

/**
 * JSON property coder.
 * @author vvakame
 * @param <T>
 */
public abstract class JsonPropertyCoder<T> {

	String name;


	/**
	 * Encodes the given instance to JSON format.
	 * @param writer 
	 * @param data
	 * @author vvakame
	 * @throws IOException 
	 */
	public abstract void encode(Writer writer, T data) throws IOException;

	/**
	 * Decodes the given JSON data to an instance.
	 * @param parser 
	 * @param data
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	public abstract void decode(JsonPullParser parser, T data) throws IOException,
			JsonFormatException;
}
