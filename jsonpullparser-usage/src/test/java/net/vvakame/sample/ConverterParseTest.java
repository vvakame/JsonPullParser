package net.vvakame.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

import org.junit.Test;

public class ConverterParseTest {

	@Test
	public void stringConverter() throws IOException, JsonFormatException {
		String twitterData = "{\"str1\":\"value1\", \"str2\":\"value2\"}";

		JsonPullParser parser = JsonPullParser.newParser(twitterData);

		ConverterData converterData = ConverterDataGenerated.get(parser);

		assertThat(converterData.getStr1(), is("value1"));
		assertThat(converterData.getStr2(), is("value2"));
	}

	@Test
	public void initegerFlattenConverter() throws IOException,
			JsonFormatException {
		String twitterData = "{\"str1\":\"value1\", \"str2\":\"value2\", \"flatten\":[1,[2,3],[[4,[5,6],7]],[],8]}";

		JsonPullParser parser = JsonPullParser.newParser(twitterData);

		ConverterData converterData = ConverterDataGenerated.get(parser);

		assertThat(converterData.getStr1(), is("value1"));
		assertThat(converterData.getStr2(), is("value2"));
		assertThat(converterData.getFlatten().size(), is(8));
		for (int i = 1; i <= 8; i++) {
			assertThat(converterData.getFlatten().get(i - 1), is(i));
		}
	}
}