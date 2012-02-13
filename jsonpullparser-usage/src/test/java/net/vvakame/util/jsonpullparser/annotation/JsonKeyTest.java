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

package net.vvakame.util.jsonpullparser.annotation;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.sample.ComplexData2;
import net.vvakame.sample.ComplexData2Generated;
import net.vvakame.sample.MiniData;
import net.vvakame.sample.PrimitiveTypeData;
import net.vvakame.sample.PrimitiveTypeDataGenerated;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test class for passing {@link JsonKey} parameters.
 * @author vvakame
 */
public class JsonKeyTest {

	/**
	 * Without {@link JsonKey#in()} and {@link JsonKey#out()}.<br>
	 * Serialization and deserialization.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void inAndOut1() throws IllegalStateException, IOException, JsonFormatException {
		String json =
				"{\"bool\":true,\"c\":\"g\",\"b\":1,\"s\":2,\"i\":3,\"l\":4,\"f\":1.1,\"d\":2.2}";

		PrimitiveTypeData data = PrimitiveTypeDataGenerated.get(json);

		StringWriter writer = new StringWriter();
		PrimitiveTypeDataGenerated.encode(writer, data);

		String toJson = writer.toString();
		JsonHash jsonHash = JsonHash.fromString(toJson);

		assertThat(jsonHash.getBooleanOrNull("bool"), is(true));
		assertThat(jsonHash.getStringOrNull("c"), is("g"));
		assertThat(jsonHash.getLongOrNull("b"), is(1L));
		assertThat(jsonHash.getLongOrNull("s"), is(2L));
		assertThat(jsonHash.getLongOrNull("i"), is(3L));
		assertThat(jsonHash.getLongOrNull("l"), is(4L));
		assertThat(jsonHash.getDoubleOrNull("f"), is(1.1));
		assertThat(jsonHash.getDoubleOrNull("d"), is(2.2));
	}

	/**
	 * With {@link JsonKey#in()} and {@link JsonKey#out()}.<br>
	 * Serialization and deserialization.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void inAndOut2() throws IllegalStateException, IOException, JsonFormatException {
		String json =
				"{\"name\":\"hoge\",\"list1\":[{\"id\":1}],\"list2\":[{\"id\":2},{\"id\":3}],\"data\":{\"id\":4}}";

		ComplexData2 data = ComplexData2Generated.get(json);

		assertThat(data.getName(), nullValue());
		assertThat(data.getList1(), notNullValue());
		assertThat(data.getList2(), nullValue());
		assertThat(data.getData(), notNullValue());

		data.setName("fuga");
		data.setList2(new ArrayList<MiniData>());

		StringWriter writer = new StringWriter();
		ComplexData2Generated.encode(writer, data);

		String toJson = writer.toString();
		JsonHash jsonHash = JsonHash.fromString(toJson);
		assertThat(jsonHash.size(), is(2));
		assertThat(jsonHash.getStringOrNull("name"), is("fuga"));
		assertThat(jsonHash.getJsonHashOrNull("data").getLongOrNull("id"), is(4L));
	}

	/**
	 * Serialization with null.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void Out1() throws IllegalStateException, IOException, JsonFormatException {

		StringWriter writer = new StringWriter();
		ComplexData2Generated.encode(writer, null);

		String toJson = writer.toString();
		assertThat(toJson, is("{}"));
	}

	/**
	 * Serialization of instances filled with nulls.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void Out2() throws IllegalStateException, IOException, JsonFormatException {

		ComplexData2 data = new ComplexData2();
		StringWriter writer = new StringWriter();
		ComplexData2Generated.encode(writer, data);

		String toJson = writer.toString();
		JsonHash jsonHash = JsonHash.fromString(toJson);
		assertThat(jsonHash.size(), is(2));
		assertThat(jsonHash.get("name"), nullValue());
		assertThat(jsonHash.get("data"), nullValue());
	}

	/**
	 * Serialization with null.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void Out3() throws IllegalStateException, IOException, JsonFormatException {

		StringWriter writer = new StringWriter();
		ComplexData2Generated.encodeList(writer, null);

		String toJson = writer.toString();
		assertThat(toJson, is("[]"));
	}

	/**
	 * Serialization of instances filled with nulls.
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void Out4() throws IllegalStateException, IOException, JsonFormatException {

		List<ComplexData2> list = new ArrayList<ComplexData2>();
		list.add(new ComplexData2());
		StringWriter writer = new StringWriter();
		ComplexData2Generated.encodeList(writer, list);

		String toJson = writer.toString();
		JsonArray jsonArray = JsonArray.fromString(toJson);
		assertThat(jsonArray.size(), is(1));
		JsonHash jsonHash = jsonArray.getJsonHashOrNull(0);
		assertThat(jsonHash.size(), is(2));
		assertThat(jsonHash.get("name"), nullValue());
		assertThat(jsonHash.get("data"), nullValue());
	}
}
