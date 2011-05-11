package net.vvakame.sample;

import java.util.LinkedHashMap;
import java.util.Map;

import net.vvakame.util.jsonpullparser.builder.JsonModelBuilder;
import net.vvakame.util.jsonpullparser.builder.JsonModelFixed;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyBuilder;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyFixed;
import net.vvakame.util.jsonpullparser.builder.JsonPropertyMeta;

import org.junit.Test;

public class BaseDataGen2 {

	public JsonPropertyMeta<BaseData> one = new JsonPropertyMeta<BaseData>(Property_one.class,
			"one", long.class, true);

	public JsonPropertyMeta<BaseData> two = new JsonPropertyMeta<BaseData>(Property_two.class,
			"two", long.class, true);

	public static BaseDataGen2 meta = new BaseDataGen2();


	@Test
	public void testMain() {
		BaseData data = new BaseData();

		BaseDataGen2 meta = BaseDataGen2.get();
		meta.newBuilder().add(meta.one.name("hoge")).fix().encode(data);
		meta.newBuilder().add(meta.two).fix().encode(data);
		meta.newBuilder().add(meta.one, meta.two.name("2")).fix().encode(data);

		JsonModelFixed<BaseData> fix = meta.newBuilder().add(meta.one.name("hoge")).fix();
		fix.toString();
	}

	public static BaseDataGen2 get() {
		return meta;
	}

	private JsonModelBuilder<BaseData> newBuilder() {
		return new BaseDataBuilder(true);
	}


	static class BaseDataBuilder extends JsonModelBuilder<BaseData> {

		public BaseDataBuilder(boolean treatUnknownKeyAsError) {
			super(treatUnknownKeyAsError);
		}

		public JsonModelFixed<BaseData> fix() {
			Map<String, JsonPropertyFixed<BaseData>> properties =
					new LinkedHashMap<String, JsonPropertyFixed<BaseData>>();
			for (String key : map.keySet()) {
				JsonPropertyBuilder<BaseData> builder = map.get(key);
				JsonPropertyFixed<BaseData> fixed = builder.fix();
				properties.put(key, fixed);
			}

			JsonModelFixed<BaseData> fixed =
					new JsonModelFixed<BaseData>(treatUnknownKeyAsError, properties);
			return fixed;
		}
	}

	public static class Property_one extends JsonPropertyFixed<BaseData> {

		@Override
		public void encode(BaseData data) {
			data.getOne();
		}

		@Override
		public void decode(BaseData data) {
			data.setOne(1);
		}
	}

	public static class Property_two extends JsonPropertyFixed<BaseData> {

		@Override
		public void encode(BaseData data) {
			data.getOne();
		}

		@Override
		public void decode(BaseData data) {
			data.setOne(1);
		}
	}
}
