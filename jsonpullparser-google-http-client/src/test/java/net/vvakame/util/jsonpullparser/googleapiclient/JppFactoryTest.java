package net.vvakame.util.jsonpullparser.googleapiclient;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.test.json.AbstractJsonFactoryTest;

/**
 * Test for {@link JppFactory}.
 * @author vvakame
 */
public class JppFactoryTest extends AbstractJsonFactoryTest {

	/**
	 * the constructor.
	 * @category constructor
	 */
	public JppFactoryTest() {
		super(JppFactoryTest.class.getSimpleName());
	}

	@Override
	protected JsonFactory newFactory() {
		return new JppFactory();
	}
}
