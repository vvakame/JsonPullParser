package net.vvakame.sample;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.builder.JsonModelBuilder;
import net.vvakame.util.jsonpullparser.builder.JsonModelFixed;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyBuilder;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyFixed;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyMeta;
import net.vvakame.util.jsonpullparser.util.JsonUtil;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class BaseDataGen2 {

	public JsonPropertyMeta<BaseData> one = new JsonPropertyMeta<BaseData>(Property_one.class,
			"one", long.class, true);

	public JsonPropertyMeta<BaseData> two = new JsonPropertyMeta<BaseData>(Property_two.class,
			"two", long.class, true);

	public static BaseDataGen2 meta = new BaseDataGen2();


	@Test
	public void testMain() throws IOException, JsonFormatException {
		BaseData data = new BaseData();

		StringWriter writer = new StringWriter();

		BaseDataGen2 meta = BaseDataGen2.get();

		meta.newBuilder().add(meta.one.name("hoge")).fix().encode(writer, data);
		meta.newBuilder().add(meta.two).fix().encode(writer, data);
		meta.newBuilder().add(meta.one, meta.two.name("2")).fix().encode(writer, data);

		JsonPullParser parser = JsonPullParser.newParser("{\"hoge\":1,\"two\":2}");
		data = meta.newBuilder().add(meta.one.name("hoge"), meta.two).fix().decode(parser);
		assertThat(data.getOne(), is(1L));
		assertThat(data.getTwo(), is(2L));
	}

	public static BaseDataGen2 get() {
		return meta;
	}

	private JsonModelBuilder<BaseData> newBuilder() {
		return new BaseDataBuilder(BaseData.class, true);
	}


	static class BaseDataBuilder extends JsonModelBuilder<BaseData> {

		public BaseDataBuilder(Class<BaseData> baseClass, boolean treatUnknownKeyAsError) {
			super(baseClass, treatUnknownKeyAsError);
		}

		@Override
		public JsonModelFixed<BaseData> fix() {
			Map<String, JsonPropertyFixed<BaseData>> properties =
					new LinkedHashMap<String, JsonPropertyFixed<BaseData>>();
			for (String key : map.keySet()) {
				JsonPropertyBuilder<BaseData> builder = map.get(key);
				JsonPropertyFixed<BaseData> fixed = builder.fix();
				properties.put(key, fixed);
			}

			JsonModelFixed<BaseData> fixed =
					new JsonModelFixed<BaseData>(baseClass, treatUnknownKeyAsError, properties);
			return fixed;
		}
	}

	public static class Property_one extends JsonPropertyFixed<BaseData> {

		@Override
		public void encode(Writer writer, BaseData data) throws IOException {
			JsonUtil.put(writer, data.getOne());
		}

		@Override
		@SuppressWarnings("unchecked")
		public void encode(Writer writer, TokenConverter<?> conv, BaseData data) throws IOException {
			((TokenConverter<Long>) conv).encodeNullToNull(writer, data.getOne());
		}

		@Override
		public void decode(JsonPullParser parser, BaseData data) throws IOException,
				JsonFormatException {
			State state = parser.getEventType();
			if (!State.VALUE_LONG.equals(state)) {
				throw new JsonFormatException("unexpected state. state=" + state.name()
						+ ", but expecred=VALUE_LONG");
			}
			data.setOne(parser.getValueLong());
		}

		@Override
		@SuppressWarnings("unchecked")
		public void decode(JsonPullParser parser, TokenConverter<?> conv, BaseData data)
				throws IOException, JsonFormatException {
			((TokenConverter<Long>) conv).parse(parser, null);
		}
	}

	public static class Property_two extends JsonPropertyFixed<BaseData> {

		@Override
		public void encode(Writer writer, BaseData data) throws IOException {
			JsonUtil.put(writer, data.getTwo());
		}

		@Override
		public void decode(JsonPullParser parser, BaseData data) throws IOException,
				JsonFormatException {
			State state = parser.getEventType();
			if (!State.VALUE_LONG.equals(state)) {
				throw new JsonFormatException("unexpected state. state=" + state.name()
						+ ", but expecred=VALUE_LONG");
			}
			data.setTwo(parser.getValueLong());
		}

		@Override
		public void encode(Writer writer, TokenConverter<?> conv, BaseData data) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void decode(JsonPullParser parser, TokenConverter<?> conv, BaseData data)
				throws IOException, JsonFormatException {
			// TODO Auto-generated method stub

		}
	}
}
