package net.vvakame.util.jsonpullparser.googleapiclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Deque;

import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;

/**
 * Implementation for {@link JsonGenerator} by JsonPullParser.
 * @author vvakame
 */
public class JppGenerator extends JsonGenerator {

	final JppFactory factory;

	final Writer writer;


	/**
	 * the constructor.
	 * @param factory
	 * @param out
	 * @param enc
	 * @category constructor
	 */
	public JppGenerator(JppFactory factory, OutputStream out, Charset enc) {
		this.factory = factory;
		this.writer = new OutputStreamWriter(out, enc);
	}

	/**
	 * the constructor.
	 * @param factory
	 * @param writer
	 * @category constructor
	 */
	public JppGenerator(JppFactory factory, Writer writer) {
		this.factory = factory;
		this.writer = writer;
	}

	@Override
	public JsonFactory getFactory() {
		return factory;
	}


	// START_ARRAY or START_HASH
	Deque<State> stateList = new ArrayDeque<State>();

	Deque<Boolean> firstList = new ArrayDeque<Boolean>();


	/**
	 * inc depth
	 * @param state
	 * @author vvakame
	 */
	void push(State state) {
		stateList.addLast(state);
		firstList.addLast(true);
	}

	/**
	 * dec depth
	 * @author vvakame
	 */
	void pop() {
		stateList.removeLast();
		firstList.removeLast();
	}

	void eat(boolean key) throws IOException {
		if (firstList.isEmpty()) {
			return;
		}
		boolean value = firstList.getLast();
		if (value) {
			firstList.removeLast();
			firstList.addLast(false);
		} else if (isInArray()) {
			JsonUtil.addSeparator(writer);
		} else if (isInObject() && key) {
			JsonUtil.addSeparator(writer);
		}
	}

	boolean isInArray() {
		return stateList.getLast() == State.START_ARRAY;
	}

	boolean isInObject() {
		return stateList.getLast() == State.START_HASH;
	}

	@Override
	public void writeStartArray() throws IOException {
		eat(false);
		JsonUtil.startArray(writer);
		push(State.START_ARRAY);
	}

	@Override
	public void writeEndArray() throws IOException {
		JsonUtil.endArray(writer);
		pop();
	}

	@Override
	public void writeStartObject() throws IOException {
		eat(false);
		JsonUtil.startHash(writer);
		push(State.START_HASH);
	}

	@Override
	public void writeEndObject() throws IOException {
		JsonUtil.endHash(writer);
		pop();
	}

	@Override
	public void writeFieldName(String name) throws IOException {
		eat(true);
		JsonUtil.putKey(writer, name);
	}

	@Override
	public void writeNull() throws IOException {
		eat(false);
		JsonUtil.put(writer, (Object) null);
	}

	@Override
	public void writeString(String value) throws IOException {
		eat(false);
		JsonUtil.put(writer, value);
	}

	@Override
	public void writeBoolean(boolean state) throws IOException {
		eat(false);
		JsonUtil.put(writer, state);
	}

	@Override
	public void writeNumber(int v) throws IOException {
		eat(false);
		JsonUtil.put(writer, v);
	}

	@Override
	public void writeNumber(long v) throws IOException {
		JsonUtil.put(writer, v);
	}

	@Override
	public void writeNumber(BigInteger v) throws IOException {
		eat(false);
		writer.write(v.toString());
	}

	@Override
	public void writeNumber(float v) throws IOException {
		eat(false);
		JsonUtil.put(writer, v);
	}

	@Override
	public void writeNumber(double v) throws IOException {
		eat(false);
		JsonUtil.put(writer, v);
	}

	@Override
	public void writeNumber(BigDecimal v) throws IOException {
		eat(false);
		writer.write(v.toString());
	}

	@Override
	public void writeNumber(String encodedValue) throws IOException {
		eat(false);
		writer.write(encodedValue);
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
}
