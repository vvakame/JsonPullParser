package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for {@link JsonParseUtil} のテスト.
 * @author vvakame
 */
public class JsonParseUtilTest {

	/**
	 * Tests the parsing of array with {@link Integer} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserInteger() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserInteger(parser), is(0));
		assertThat(JsonParseUtil.parserInteger(parser), nullValue());
		assertThat(JsonParseUtil.parserInteger(parser), is(2));
		assertThat(JsonParseUtil.parserInteger(parser), is(3));
		assertThat(JsonParseUtil.parserInteger(parser), is(4));
	}

	/**
	 * Tests the parsing of array with {@link Long} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserLong() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserLong(parser), is(0L));
		assertThat(JsonParseUtil.parserLong(parser), nullValue());
		assertThat(JsonParseUtil.parserLong(parser), is(2L));
		assertThat(JsonParseUtil.parserLong(parser), is(3L));
		assertThat(JsonParseUtil.parserLong(parser), is(4L));
	}

	/**
	 * Tests the parsing of array with {@link Byte} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserByte() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserByte(parser), is((byte) 0));
		assertThat(JsonParseUtil.parserByte(parser), nullValue());
		assertThat(JsonParseUtil.parserByte(parser), is((byte) 2));
		assertThat(JsonParseUtil.parserByte(parser), is((byte) 3));
		assertThat(JsonParseUtil.parserByte(parser), is((byte) 4));
	}

	/**
	 * Tests the parsing of array with {@link Short} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserShort() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserShort(parser), is((short) 0));
		assertThat(JsonParseUtil.parserShort(parser), nullValue());
		assertThat(JsonParseUtil.parserShort(parser), is((short) 2));
		assertThat(JsonParseUtil.parserShort(parser), is((short) 3));
		assertThat(JsonParseUtil.parserShort(parser), is((short) 4));
	}

	/**
	 * Tests the parsing of array with {@link Boolean} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserBoolean() throws IOException, JsonFormatException {
		String json = "[true,false,null,true]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserBoolean(parser), is(true));
		assertThat(JsonParseUtil.parserBoolean(parser), is(false));
		assertThat(JsonParseUtil.parserBoolean(parser), nullValue());
		assertThat(JsonParseUtil.parserBoolean(parser), is(true));
	}

	/**
	 * Tests the parsing of array with {@link Character} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserCharacter() throws IOException, JsonFormatException {
		String json = "[\"a\",null,\"c\"]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserCharacter(parser), is('a'));
		assertThat(JsonParseUtil.parserCharacter(parser), nullValue());
		assertThat(JsonParseUtil.parserCharacter(parser), is('c'));
	}

	/**
	 * Tests the parsing of array with {@link Double} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserDouble() throws IOException, JsonFormatException {
		String json = "[0.0,null,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserDouble(parser), is(0.0));
		assertThat(JsonParseUtil.parserDouble(parser), nullValue());
		assertThat(JsonParseUtil.parserDouble(parser), is(2.2));
		assertThat(JsonParseUtil.parserDouble(parser), is(3.3));
		assertThat(JsonParseUtil.parserDouble(parser), is(4.4));
	}

	/**
	 * Tests the parsing of array with {@link Float} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserFloat() throws IOException, JsonFormatException {
		String json = "[0.0,null,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		State state = parser.getEventType();
		assertThat(state, is(State.START_ARRAY));

		assertThat(JsonParseUtil.parserFloat(parser), is(0.0F));
		assertThat(JsonParseUtil.parserFloat(parser), nullValue());
		assertThat(JsonParseUtil.parserFloat(parser), is(2.2F));
		assertThat(JsonParseUtil.parserFloat(parser), is(3.3F));
		assertThat(JsonParseUtil.parserFloat(parser), is(4.4F));
	}

	/**
	 * Tests the parsing of array with {@link Integer} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserIntegerList() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Integer> list = JsonParseUtil.parserIntegerList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is(2));
		assertThat(list.get(3), is(3));
		assertThat(list.get(4), is(4));
	}

	/**
	 * Tests the parsing of array with {@link Long} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserLongList() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Long> list = JsonParseUtil.parserLongList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0L));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is(2L));
		assertThat(list.get(3), is(3L));
		assertThat(list.get(4), is(4L));
	}

	/**
	 * Tests the parsing of array with {@link Byte} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserByteList() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Byte> list = JsonParseUtil.parserByteList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is((byte) 0));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is((byte) 2));
		assertThat(list.get(3), is((byte) 3));
		assertThat(list.get(4), is((byte) 4));
	}

	/**
	 * Tests the parsing of array with {@link Short} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserShortList() throws IOException, JsonFormatException {
		String json = "[0,null,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Short> list = JsonParseUtil.parserShortList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is((short) 0));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is((short) 2));
		assertThat(list.get(3), is((short) 3));
		assertThat(list.get(4), is((short) 4));
	}

	/**
	 * Tests the parsing of array with {@link Boolean} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserBooleanList() throws IOException, JsonFormatException {
		String json = "[true,false,null,true]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Boolean> list = JsonParseUtil.parserBooleanList(parser);

		assertThat(list.size(), is(4));
		assertThat(list.get(0), is(true));
		assertThat(list.get(1), is(false));
		assertThat(list.get(2), nullValue());
		assertThat(list.get(3), is(true));
	}

	/**
	 * Tests the parsing of array with {@link Character} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserCharacterList() throws IOException, JsonFormatException {
		String json = "[\"a\",null,\"c\"]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Character> list = JsonParseUtil.parserCharacterList(parser);

		assertThat(list.size(), is(3));
		assertThat(list.get(0), is('a'));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is('c'));
	}

	/**
	 * Tests the parsing of array with {@link Double} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserDoubleList() throws IOException, JsonFormatException {
		String json = "[0.0,null,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Double> list = JsonParseUtil.parserDoubleList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0.0));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is(2.2));
		assertThat(list.get(3), is(3.3));
		assertThat(list.get(4), is(4.4));
	}

	/**
	 * Tests the parsing of array with {@link Float} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserFloatList() throws IOException, JsonFormatException {
		String json = "[0.0,null,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Float> list = JsonParseUtil.parserFloatList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0.0F));
		assertThat(list.get(1), nullValue());
		assertThat(list.get(2), is(2.2F));
		assertThat(list.get(3), is(3.3F));
		assertThat(list.get(4), is(4.4F));
	}

	/**
	 * Tests the parsing of array with {@link String} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserStringList() throws IOException, JsonFormatException {
		String json = "[\"\",\"s\",\"str\"]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<String> list = JsonParseUtil.parserStringList(parser);

		assertThat(list.size(), is(3));
		assertThat(list.get(0), is(""));
		assertThat(list.get(1), is("s"));
		assertThat(list.get(2), is("str"));
	}

	/**
	 * Tests the parsing of array with {@link Date} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserDateList() throws IOException, JsonFormatException {
		String json = "[10000000,10010001,10020002]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Date> list = JsonParseUtil.parserDateList(parser);

		assertThat(list.size(), is(3));
		assertThat(list.get(0).getTime(), is(10000000L));
		assertThat(list.get(1).getTime(), is(10010001L));
		assertThat(list.get(2).getTime(), is(10020002L));
	}

	/**
	 * Tests the parsing of array with {@link Enum} elements.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserEnumList() throws IOException, JsonFormatException {
		String json = "[\"TEST1\",\"TEST2\",\"TEST1\"]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<EnumForTest> list = JsonParseUtil.parserEnumList(parser, EnumForTest.class);

		assertThat(list.size(), is(3));
		assertThat(list.get(0), is(EnumForTest.TEST1));
		assertThat(list.get(1), is(EnumForTest.TEST2));
		assertThat(list.get(2), is(EnumForTest.TEST1));
	}


	/**
	 * Enum for testing
	 * @author vvakame
	 */
	public static enum EnumForTest {
		/** 要素1 */
		TEST1,
		/** 要素2 */
		TEST2
	}
}
