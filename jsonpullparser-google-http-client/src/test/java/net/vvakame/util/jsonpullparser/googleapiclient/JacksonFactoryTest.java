package net.vvakame.util.jsonpullparser.googleapiclient;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.test.json.AbstractJsonFactoryTest;

/**
 * Test for {@link JacksonFactory}.
 * @author vvakame
 */
public class JacksonFactoryTest extends AbstractJsonFactoryTest {

	/**
	 * the constructor.
	 * @category constructor
	 */
	public JacksonFactoryTest() {
		super(JacksonFactoryTest.class.getName());
	}

	@Override
	protected JsonFactory newFactory() {
		return new JacksonFactory();
	}
}
