package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.JsonSlice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for {@link JsonSliceUtil}.
 * @author vvakame
 */
public class JsonSliceUtilTest {

	/**
	 * Tests for specific behavior.
	 * @author vvakame
	 */
	@Test
	public void isNextIsEndArray() {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		assertThat(JsonSliceUtil.isNextIsEndArray(slices, 0), is(true));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		assertThat(JsonSliceUtil.isNextIsEndArray(slices, 0), is(false));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.START_HASH));
		assertThat(JsonSliceUtil.isNextIsEndArray(slices, 0), is(false));
	}

	/**
	 * Tests for specific behavior.
	 * @author vvakame
	 */
	@Test
	public void isNextIsEndHash() {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.END_HASH));
		assertThat(JsonSliceUtil.isNextIsEndHash(slices, 0), is(true));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		assertThat(JsonSliceUtil.isNextIsEndHash(slices, 0), is(false));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.START_HASH));
		assertThat(JsonSliceUtil.isNextIsEndHash(slices, 0), is(false));
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_null() throws JsonFormatException, IOException {
		JsonSliceUtil.slicesToString(null);
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_empty() throws JsonFormatException, IOException {
		JsonSliceUtil.slicesToString(new ArrayList<JsonSlice>());
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test
	public void slicesToString_array() throws JsonFormatException, IOException {
		List<JsonSlice> slices;
		String json;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[]"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.END_HASH));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[{}]"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "hoge"));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, true));
		slices.add(new JsonSlice(State.END_HASH));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[{\"hoge\":true}]"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "hoge"));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, true));
		slices.add(new JsonSlice(State.KEY, "fuga"));
		slices.add(new JsonSlice(State.VALUE_NULL));
		slices.add(new JsonSlice(State.END_HASH));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[{\"hoge\":true,\"fuga\":null}]"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "hoge"));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, true));
		slices.add(new JsonSlice(State.KEY, "fuga"));
		slices.add(new JsonSlice(State.VALUE_NULL));
		slices.add(new JsonSlice(State.END_HASH));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[{\"hoge\":true,\"fuga\":null},[]]"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, true));
		slices.add(new JsonSlice(State.VALUE_LONG, 1));
		slices.add(new JsonSlice(State.VALUE_DOUBLE, 2.2));
		slices.add(new JsonSlice(State.VALUE_STRING, "str"));
		slices.add(new JsonSlice(State.VALUE_NULL));
		slices.add(new JsonSlice(State.END_ARRAY));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("[true,1,2.2,\"str\",null]"));
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test
	public void slicesToString_hash() throws JsonFormatException, IOException {
		List<JsonSlice> slices;
		String json;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.END_HASH));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("{}"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "hoge"));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.END_HASH));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("{\"hoge\":[]}"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "hoge"));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.KEY, "fuga"));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, false));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.END_HASH));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(json, is("{\"hoge\":[],\"fuga\":[false]}"));

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.KEY, "ary"));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.KEY, "hash"));
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.END_HASH));
		slices.add(new JsonSlice(State.KEY, "b"));
		slices.add(new JsonSlice(State.VALUE_BOOLEAN, true));
		slices.add(new JsonSlice(State.KEY, "l"));
		slices.add(new JsonSlice(State.VALUE_LONG, 1));
		slices.add(new JsonSlice(State.KEY, "d"));
		slices.add(new JsonSlice(State.VALUE_DOUBLE, 2.2));
		slices.add(new JsonSlice(State.KEY, "str"));
		slices.add(new JsonSlice(State.VALUE_STRING, "str"));
		slices.add(new JsonSlice(State.KEY, "null"));
		slices.add(new JsonSlice(State.VALUE_NULL));
		slices.add(new JsonSlice(State.END_HASH));
		json = JsonSliceUtil.slicesToString(slices);
		assertThat(
				json,
				is("{\"ary\":[],\"hash\":{},\"b\":true,\"l\":1,\"d\":2.2,\"str\":\"str\",\"null\":null}"));
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_invalid1() throws JsonFormatException, IOException {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		slices.add(new JsonSlice(State.START_ARRAY));
		slices.add(new JsonSlice(State.END_ARRAY));
		slices.add(new JsonSlice(State.END_HASH));
		JsonSliceUtil.slicesToString(slices);
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_invalid2() throws JsonFormatException, IOException {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_HASH));
		JsonSliceUtil.slicesToString(slices);
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_invalid3() throws JsonFormatException, IOException {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.START_ARRAY));
		JsonSliceUtil.slicesToString(slices);
	}

	/**
	 * Tests for specific behavior
	 * @author vvakame
	 * @throws IOException 
	 * @throws JsonFormatException 
	 */
	@Test(expected = JsonFormatException.class)
	public void slicesToString_invalid4() throws JsonFormatException, IOException {
		List<JsonSlice> slices;

		slices = new ArrayList<JsonSlice>();
		slices.add(new JsonSlice(State.VALUE_NULL));
		JsonSliceUtil.slicesToString(slices);
	}
}
