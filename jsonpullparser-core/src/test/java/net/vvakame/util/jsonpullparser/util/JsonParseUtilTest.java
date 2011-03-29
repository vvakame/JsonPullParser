package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonParseUtil} のテスト.
 * @author vvakame
 */
public class JsonParseUtilTest {

	/**
	 * {@link Integer} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserIntegerList() throws IOException, JsonFormatException {
		String json = "[0,1,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Integer> list = JsonParseUtil.parserIntegerList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0));
		assertThat(list.get(1), is(1));
		assertThat(list.get(2), is(2));
		assertThat(list.get(3), is(3));
		assertThat(list.get(4), is(4));
	}

	/**
	 * {@link Long} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserLongList() throws IOException, JsonFormatException {
		String json = "[0,1,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Long> list = JsonParseUtil.parserLongList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0L));
		assertThat(list.get(1), is(1L));
		assertThat(list.get(2), is(2L));
		assertThat(list.get(3), is(3L));
		assertThat(list.get(4), is(4L));
	}

	/**
	 * {@link Byte} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserByteList() throws IOException, JsonFormatException {
		String json = "[0,1,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Byte> list = JsonParseUtil.parserByteList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is((byte) 0));
		assertThat(list.get(1), is((byte) 1));
		assertThat(list.get(2), is((byte) 2));
		assertThat(list.get(3), is((byte) 3));
		assertThat(list.get(4), is((byte) 4));
	}

	/**
	 * {@link Short} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserShortList() throws IOException, JsonFormatException {
		String json = "[0,1,2,3,4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Short> list = JsonParseUtil.parserShortList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is((short) 0));
		assertThat(list.get(1), is((short) 1));
		assertThat(list.get(2), is((short) 2));
		assertThat(list.get(3), is((short) 3));
		assertThat(list.get(4), is((short) 4));
	}

	/**
	 * {@link Boolean} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserBooleanList() throws IOException, JsonFormatException {
		String json = "[true,false,true,true]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Boolean> list = JsonParseUtil.parserBooleanList(parser);

		assertThat(list.size(), is(4));
		assertThat(list.get(0), is(true));
		assertThat(list.get(1), is(false));
		assertThat(list.get(2), is(true));
		assertThat(list.get(3), is(true));
	}

	/**
	 * {@link Character} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserCharacterList() throws IOException, JsonFormatException {
		String json = "[\"a\",\"b\",\"c\"]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Character> list = JsonParseUtil.parserCharacterList(parser);

		assertThat(list.size(), is(3));
		assertThat(list.get(0), is('a'));
		assertThat(list.get(1), is('b'));
		assertThat(list.get(2), is('c'));
	}

	/**
	 * {@link Double} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserDoubleList() throws IOException, JsonFormatException {
		String json = "[0.0,1.1,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Double> list = JsonParseUtil.parserDoubleList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0.0));
		assertThat(list.get(1), is(1.1));
		assertThat(list.get(2), is(2.2));
		assertThat(list.get(3), is(3.3));
		assertThat(list.get(4), is(4.4));
	}

	/**
	 * {@link Float} を要素とするJSONの配列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parserFloatList() throws IOException, JsonFormatException {
		String json = "[0.0,1.1,2.2,3.3,4.4]";
		JsonPullParser parser = JsonPullParser.newParser(json);
		List<Float> list = JsonParseUtil.parserFloatList(parser);

		assertThat(list.size(), is(5));
		assertThat(list.get(0), is(0.0F));
		assertThat(list.get(1), is(1.1F));
		assertThat(list.get(2), is(2.2F));
		assertThat(list.get(3), is(3.3F));
		assertThat(list.get(4), is(4.4F));
	}

	/**
	 * {@link String} を要素とするJSONの配列の解釈.
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
	 * {@link Date} を要素とするJSONの配列の解釈.
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
	 * {@link Enum} を要素とするJSONの配列の解釈.
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
	 * テスト利用向けenum
	 * @author vvakame
	 */
	public static enum EnumForTest {
		/** 要素1 */
		TEST1,
		/** 要素2 */
		TEST2
	}
}
