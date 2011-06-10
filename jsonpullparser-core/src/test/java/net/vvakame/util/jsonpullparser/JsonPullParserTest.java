/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonSliceUtil;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * {@link JsonPullParser} のテスト.
 * @author vvakame
 */
public class JsonPullParserTest {

	/**
	 * {@link JsonPullParser} に <code>{}</code>を喰わせた時のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmptyHash() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser = JsonPullParser.newParser("{}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link JsonPullParser} に <code>[]</code>を喰わせた時のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmptyArray() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser = JsonPullParser.newParser("[]");
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * {@link JsonPullParser} に適当な空要素({}, [])を喰わせた時のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmptyJSON() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser = JsonPullParser.newParser("[{}]");
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * {@link State#VALUE_STRING} に解釈されるデータを与えた時のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleString() throws IOException, JsonFormatException {
		State type;
		String str;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":\"value\"}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value"));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * JSONの定義上特殊な文字であると定義されている文字を含む文字列の解釈.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSpecialChar() throws IOException, JsonFormatException {
		State type;
		String str;

		JsonPullParser parser =
				JsonPullParser.newParser("{\"key\":\" \\\" \\t \\r \\n \\b \\f \\ \"}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is(" \" \t \r \n \b \f \\ "));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * \\uxxxx 形式の文字の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseCharByHex() throws IOException, JsonFormatException {
		State type;
		String str;

		JsonPullParser parser =
				JsonPullParser
					.newParser("[\"json \\u30e1\\u30e2\", \"\\u123x\", \"\\u30E1\\u30E2\"]");
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("json メモ"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("\\u123x"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("メモ"));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * 空白扱いの文字の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseEmptyChar() throws IOException, JsonFormatException {
		State type;
		String str;

		JsonPullParser parser = JsonPullParser.newParser("{ \t\"key\" \n \t \r : \"value\" }");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value"));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#VALUE_BOOLEAN} の {@code true} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleBooleanTrue() throws IOException, JsonFormatException {
		State type;
		String str;
		Boolean bool;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":true}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(true));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#VALUE_BOOLEAN} の {@code false} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleBooleanFalse() throws IOException, JsonFormatException {
		State type;
		String str;
		Boolean bool;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":false}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		bool = parser.getValueBoolean();
		assertThat(bool, is(false));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#VALUE_NULL} のの解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleNull() throws IOException, JsonFormatException {
		State type;
		String str;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":null}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#VALUE_LONG} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleLong() throws IOException, JsonFormatException {
		State type;
		String str;
		long i;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":-1}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(-1L));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#VALUE_DOUBLE} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseSimpleDouble() throws IOException, JsonFormatException {
		State type;
		String str;
		double d;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":-1e6}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));
		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(-1000000.0));
		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link State#START_HASH} と {@link State#END_HASH} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseHash() throws IOException, JsonFormatException {
		State type;
		String str;
		long i;
		double d;
		boolean b;

		JsonPullParser parser =
				JsonPullParser
					.newParser("{\"key1\":\"value1\", \"key2\":2, \"key3\":0.1 ,\"key4\":true ,\"key5\":false ,\"key6\":null}");
		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key1"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value1"));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key2"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(2L));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key3"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(0.1));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key4"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(true));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key5"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(false));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));
		str = parser.getValueString();
		assertThat(str, is("key6"));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * { の直後に空白文字があると解釈に失敗するバグの修正確認.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void parseHash_2() throws IOException, JsonFormatException {
		// String json = " {\n\r\t\f\b} "; // is \f and \bsssssssssssssssssssssssssssssss________________________________________________________________________________ is valid? i think there are invalid...
		String json = " {\n\r\t } ";
		JsonPullParser parser = JsonPullParser.newParser(json);
		assertThat(parser.getEventType(), is(State.START_HASH));
		assertThat(parser.getEventType(), is(State.END_HASH));
	}

	/**
	 * {@link State#START_ARRAY} と {@link State#END_ARRAY} の解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseArray() throws IOException, JsonFormatException {
		State type;
		String str;
		long i;
		double d;
		boolean b;

		JsonPullParser parser = JsonPullParser.newParser("[\"value1\", 2, 0.1 ,true ,false ,null]");
		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));
		str = parser.getValueString();
		assertThat(str, is("value1"));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
		i = parser.getValueLong();
		assertThat(i, is(2L));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));
		d = parser.getValueDouble();
		assertThat(d, is(0.1));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(true));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));
		b = parser.getValueBoolean();
		assertThat(b, is(false));
		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));
		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * こみいっててめんどいJSONの解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseComplex1() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser =
				JsonPullParser
					.newParser("[\"value1\", 2, 0.1 ,true ,{\"test1\":1, \"test2\":null   \n  } ,null]");

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_DOUBLE));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * こみいっててめんどいJSONの解釈
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parseComplex2() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser = JsonPullParser.newParser("{\"key\":\"value\", \"list\":[]}");

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));
	}

	/**
	 * {@link JsonPullParser#lookAhead()} のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void lookAhead() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser =
				JsonPullParser
					.newParser("[\"value1\", 2, 0.1 ,true ,{\"test1\":1, \"test2\":null   \n  } ,null]");

		type = parser.lookAhead();
		assertThat(type, is(State.START_ARRAY));

		type = parser.lookAhead();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.lookAhead();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_STRING));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_LONG));
	}

	/**
	 * {@link JsonPullParser#discardValue()} のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void discardToken() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser =
				JsonPullParser
					.newParser("[null,\"str\",true,1,1.1,{},[],{\"key1\":true,\"key2\":false}]");

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		parser.discardValue();
		parser.discardValue();
		parser.discardValue();
		parser.discardValue();
		parser.discardValue();
		parser.discardValue();
		parser.discardValue();

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		parser.discardValue();

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_BOOLEAN));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * {@link JsonPullParser#discardArrayToken()} のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void discardArrayToken() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser = JsonPullParser.newParser("[null,[[{}]]]");

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		type = parser.getEventType();
		assertThat(type, is(State.VALUE_NULL));

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		parser.discardArrayToken();

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * {@link JsonPullParser#discardHashToken()} のテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void discardHashToken() throws IOException, JsonFormatException {
		State type;

		JsonPullParser parser =
				JsonPullParser
					.newParser("[null,{\"key1\":true,{\"key2\":false,\"key3\":{\"key4\":{}}}}]");

		type = parser.getEventType();
		assertThat(type, is(State.START_ARRAY));

		parser.discardValue();

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		parser.discardValue();

		type = parser.getEventType();
		assertThat(type, is(State.START_HASH));

		parser.discardValue();

		type = parser.getEventType();
		assertThat(type, is(State.KEY));

		parser.discardHashToken();

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.END_HASH));

		type = parser.getEventType();
		assertThat(type, is(State.END_ARRAY));
	}

	/**
	 * 解釈に失敗するパターンのテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test(expected = JsonFormatException.class)
	public void parseFailure1() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("[}");

		parser.getEventType();
		parser.getEventType();
	}

	/**
	 * 解釈に失敗するパターンのテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test(expected = JsonFormatException.class)
	public void parseFailure2() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("[+0]");

		parser.getEventType();
		parser.getEventType();
	}

	/**
	 * 解釈に失敗するパターンのテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test(expected = JsonFormatException.class)
	public void parseFailure3() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("[0-0]");

		parser.getEventType();
		parser.getEventType();
	}

	/**
	 * 解釈に失敗するパターンのテスト.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test(expected = JsonFormatException.class)
	public void parseFailure4() throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser("[0,,0]");

		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
	}

	/**
	 * {@link JsonSlice} の生成テスト.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void jsonSlices() throws IOException, JsonFormatException {
		String json;
		JsonPullParser parser;

		json = "[1,2.2,true,\"str\"]";
		parser = JsonPullParser.newParser(json).setLogEnable();
		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
		assertThat(JsonSliceUtil.slicesToString(parser.getSlices()), is(json));

		json = "[1,2.2,true,\"str\"]";
		parser = JsonPullParser.newParser(json).setLogEnable();
		parser.discardArrayToken();
		assertThat(JsonSliceUtil.slicesToString(parser.getSlices()), is(json));

		json = "{\"str\":\"hoge\"}";
		parser = JsonPullParser.newParser(json).setLogEnable();
		parser.discardHashToken();
		assertThat(JsonSliceUtil.slicesToString(parser.getSlices()), is(json));

		// json = " {\n\r\t\f\b}    ";
		json = " {} ";
		parser = JsonPullParser.newParser(json).setLogEnable();
		parser.discardValue();
		assertThat(JsonSliceUtil.slicesToString(parser.getSlices()), is("{}"));
	}

	/**
	 * {@link JsonPullParser#setLogEnable()} が呼ばれていない場合に例外を飛ばす.
	 * @author vvakame
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void sliceSizeForLookAhead() throws IOException, JsonFormatException {
		String json = "[{},{}]";
		JsonPullParser parser = JsonPullParser.newParser(json).setLogEnable();

		parser.getEventType();
		parser.getEventType();
		parser.getEventType();
		parser.lookAhead();
		int start = parser.getSliceSize();
		parser.getEventType();
		parser.getEventType();
		parser.lookAhead();
		int end = parser.getSliceSize();
		parser.getEventType();

		List<JsonSlice> list = parser.getSlices().subList(start, end);

		assertThat(JsonSliceUtil.slicesToString(list), is("{}"));
	}

	InputStream getStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
