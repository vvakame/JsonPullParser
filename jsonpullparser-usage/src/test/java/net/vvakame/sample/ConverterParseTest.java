package net.vvakame.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;

import org.junit.Test;

public class ConverterParseTest {

	@Test
	public void parse() throws IOException, JsonFormatException {
		String twitterData = "{\"str1\":\"value1\", \"str2\":\"value2\"}";

		JsonPullParser parser = JsonPullParser.newParser(twitterData);

		ConverterData converterData = ConverterDataGenerated.get(parser);

		assertThat(converterData.getStr1(), is("value1"));
		assertThat(converterData.getStr2(), is("value2"));
	}
}