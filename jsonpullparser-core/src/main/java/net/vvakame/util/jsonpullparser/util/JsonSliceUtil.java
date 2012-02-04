package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.JsonSlice;

/**
 * An utility class for {@link JsonSlice}
 * @author vvakame
 */
public class JsonSliceUtil {

	/**
	 * Encodes the given list of {@link JsonSlice}s to valid JSON.
	 *
	 * @param slices List of {@link JsonSlice}s to encode
	 * @return Valid JSON-formatted string
	 * @throws JsonFormatException
	 * @throws IOException
	 * @author vvakame
	 */
	public static String slicesToString(List<JsonSlice> slices) throws JsonFormatException,
			IOException {
		if (slices == null || slices.size() == 0) {
			throw new JsonFormatException("slices is null or empty.");
		}

		StringWriter writer = new StringWriter();

		int cnt = slices.size();
		for (int i = 0; i < cnt; i++) {
			JsonSlice slice = slices.get(i);
			if (slice == null) {
				throw new JsonFormatException("slice is null. i=" + i);
			}
			switch (slice.getState()) {
				case START_ARRAY:
					i = slicesToStringInArray(slices, i, writer);
					break;
				case START_HASH:
					i = slicesToStringInHash(slices, i, writer);
					break;
				default:
					throw new JsonFormatException("invalid state. i=" + i + " is "
							+ slice.getState());
			}
		}

		return writer.toString();
	}

	static int slicesToStringInArray(List<JsonSlice> slices, int index, Writer writer)
			throws JsonFormatException, IOException {

		if (slices.get(index).getState() != State.START_ARRAY) {
			throw new JsonFormatException("invalid state. i=" + index + " is "
					+ slices.get(index).getState());
		}
		JsonUtil.startArray(writer);
		index++;

		int cnt = slices.size();
		for (int i = index; i < cnt; i++) {
			JsonSlice slice = slices.get(i);
			if (slice == null) {
				throw new JsonFormatException("slice is null. i=" + i);
			}
			switch (slice.getState()) {
				case START_ARRAY:
					i = slicesToStringInArray(slices, i, writer);
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case START_HASH:
					i = slicesToStringInHash(slices, i, writer);
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case END_ARRAY:
					JsonUtil.endArray(writer);
					return i;

				case VALUE_BOOLEAN:
					JsonUtil.put(writer, slice.getValueBoolean());
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_LONG:
					JsonUtil.put(writer, slice.getValueLong());
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_DOUBLE:
					JsonUtil.put(writer, slice.getValueDouble());
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_STRING:
					JsonUtil.put(writer, slice.getValueStr());
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_NULL:
					JsonUtil.put(writer, (Object) null);
					if (!isNextIsEndArray(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				default:
					throw new JsonFormatException("invalid state. i=" + i + " is "
							+ slice.getState());
			}
		}
		throw new JsonFormatException("invalid state.");
	}

	static boolean isNextIsEndArray(List<JsonSlice> slices, int index) {
		if (slices.size() <= index + 1) {
			return false;
		} else {
			return slices.get(index + 1).getState() == State.END_ARRAY;
		}
	}

	static int slicesToStringInHash(List<JsonSlice> slices, int index, Writer writer)
			throws JsonFormatException, IOException {

		if (slices.get(index).getState() != State.START_HASH) {
			throw new JsonFormatException("invalid state. i=" + index + " is "
					+ slices.get(index).getState());
		}
		JsonUtil.startHash(writer);
		index++;

		int cnt = slices.size();
		boolean isExpectKey = true;
		for (int i = index; i < cnt; i++) {
			JsonSlice slice = slices.get(i);
			if (slice == null) {
				throw new JsonFormatException("slice is null. i=" + i);
			}

			State state = slice.getState();
			switch (state) {
				case START_ARRAY:
					isExpectKey = checkExpectKey(isExpectKey, state);
					i = slicesToStringInArray(slices, i, writer);
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case START_HASH:
					isExpectKey = checkExpectKey(isExpectKey, state);
					i = slicesToStringInHash(slices, i, writer);
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case END_HASH:
					JsonUtil.endHash(writer);
					return i;

				case KEY:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.putKey(writer, slice.getValueStr());
					break;

				case VALUE_BOOLEAN:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.put(writer, slice.getValueBoolean());
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_LONG:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.put(writer, slice.getValueLong());
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_DOUBLE:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.put(writer, slice.getValueDouble());
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_STRING:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.put(writer, slice.getValueStr());
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				case VALUE_NULL:
					isExpectKey = checkExpectKey(isExpectKey, state);
					JsonUtil.put(writer, (Object) null);
					if (!isNextIsEndHash(slices, i)) {
						JsonUtil.addSeparator(writer);
					}
					break;

				default:
					throw new JsonFormatException("invalid state. i=" + i + " is "
							+ slice.getState());
			}
		}
		throw new JsonFormatException("invalid state.");
	}

	static boolean checkExpectKey(boolean isExpectKey, State state) throws JsonFormatException {
		if (isExpectKey && state != State.KEY) {
			throw new JsonFormatException("expected State is KEY, but get=" + state);
		} else if (!isExpectKey && state == State.KEY) {
			throw new JsonFormatException("expected State is not KEY.");
		}
		return !isExpectKey;
	}

	static boolean isNextIsEndHash(List<JsonSlice> slices, int index) {
		if (slices.size() <= index + 1) {
			return false;
		} else {
			return slices.get(index + 1).getState() == State.END_HASH;
		}
	}

}
