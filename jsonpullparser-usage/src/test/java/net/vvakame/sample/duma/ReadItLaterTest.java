package net.vvakame.sample.duma;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test case for {@link ReadItLater} and {@link ItemMapConverter}.
 * @author vvakame
 */
public class ReadItLaterTest {

	/**
	 * A basic test case.
	 * @throws JsonFormatException 
	 * @throws IOException 
	 */
	@Test
	public void hoge() throws IOException, JsonFormatException {
		String json =
				"{\"status\":\"1\",\"since\":\"1245626956\",\"list\":{\"93817\":{\"item_id\":\"93817\",\"url\":\"http://url.com\",\"title\":\"Page Title\",\"time_updated\":\"1245626956\",\"time_added\":\"1245626956\",\"tags\":\"comma,seperated,list\",\"state\":\"0\"},\"935812\":{\"item_id\":\"935812\",\"url\":\"http://google.com\",\"title\":\"Google\",\"time_updated\":\"1245626956\",\"time_added\":\"1245626956\",\"tags\":\"comma,seperated,list\",\"state\":\"1\"}}}";

		// JSON→POJO
		ReadItLater later = ReadItLaterGenerated.get(json);

		assertThat(later.getStatus(), is("1"));
		assertThat(later.getSince(), is("1245626956"));
		assertThat(later.getList().size(), is(2));

		Item item;

		item = later.getList().get("93817");
		assertThat(item.getItemId(), is("93817"));
		assertThat(item.getUrl(), is("http://url.com"));
		assertThat(item.getTitle(), is("Page Title"));
		assertThat(item.getTimeUpdated(), is("1245626956"));
		assertThat(item.getTimeAdded(), is("1245626956"));
		assertThat(item.getTags(), is("comma,seperated,list"));
		assertThat(item.getState(), is("0"));

		item = later.getList().get("935812");
		assertThat(item.getItemId(), is("935812"));
		assertThat(item.getUrl(), is("http://google.com"));
		assertThat(item.getTitle(), is("Google"));
		assertThat(item.getTimeUpdated(), is("1245626956"));
		assertThat(item.getTimeAdded(), is("1245626956"));
		assertThat(item.getTags(), is("comma,seperated,list"));
		assertThat(item.getState(), is("1"));

		// POJO→JSON
		// めんどいからもっかい復号しておｋだったらおｋ
		StringWriter writer = new StringWriter();
		ReadItLaterGenerated.encode(writer, later);
		json = writer.toString();

		// JSON→POJO
		later = ReadItLaterGenerated.get(json);

		assertThat(later.getStatus(), is("1"));
		assertThat(later.getSince(), is("1245626956"));
		assertThat(later.getList().size(), is(2));

		item = later.getList().get("93817");
		assertThat(item.getItemId(), is("93817"));
		assertThat(item.getUrl(), is("http://url.com"));
		assertThat(item.getTitle(), is("Page Title"));
		assertThat(item.getTimeUpdated(), is("1245626956"));
		assertThat(item.getTimeAdded(), is("1245626956"));
		assertThat(item.getTags(), is("comma,seperated,list"));
		assertThat(item.getState(), is("0"));

		item = later.getList().get("935812");
		assertThat(item.getItemId(), is("935812"));
		assertThat(item.getUrl(), is("http://google.com"));
		assertThat(item.getTitle(), is("Google"));
		assertThat(item.getTimeUpdated(), is("1245626956"));
		assertThat(item.getTimeAdded(), is("1245626956"));
		assertThat(item.getTags(), is("comma,seperated,list"));
		assertThat(item.getState(), is("1"));
	}
}
