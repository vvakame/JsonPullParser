package net.vvakame.sample;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

public class JsonModelTest {
	
	@Test(expected = IllegalStateException.class)
	public void treatUnknownKeyAsError() throws IllegalStateException,
			IOException, JsonFormatException {
		String json = "{\"str1\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		ConverterDataGenerated.get(json);
	}

	@Test
	public void ignoreUnknownKeyAsError() throws IllegalStateException,
			IOException, JsonFormatException {
		String json = "{\"name\":\"hoge\", \"unknown\":{\"test\":[]}, \"str2\":\"fuga\"}";

		TestDataGenerated.get(json);
	}
}