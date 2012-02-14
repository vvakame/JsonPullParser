package net.vvakame.sample.issue2;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for issue of inherit patter. Issue#2 https://github.com/vvakame/JsonPullParser/issues/2
 * @author vvakame
 */
public class Issue2Test {

	/**
	 * Test for no output values.
	 * @author vvakame
	 */
	@Test
	public void noValues() {
		Child0ValueP0 bar = new Child0ValueP0();
		StringWriter sw = new StringWriter();
		try {
			Child0ValueP0Generated.encode(sw, bar);
		} catch (IOException e) {
		}
		assertThat(sw.toString(), is("{}"));
	}

	/**
	 * Test for poor child.
	 * @author vvakame
	 */
	@Test
	public void childHasNotValues() {
		Child0ValueP1 bar = new Child0ValueP1();
		StringWriter sw = new StringWriter();
		try {
			Child0ValueP1Generated.encode(sw, bar);
		} catch (IOException e) {
		}
		assertThat(sw.toString(), is("{\"piyo\":0}"));
	}

	/**
	 * Test for poor parent.
	 * @author vvakame
	 */
	@Test
	public void parentHasNotValues() {
		Child1ValueP0 bar = new Child1ValueP0();
		StringWriter sw = new StringWriter();
		try {
			Child1ValueP0Generated.encode(sw, bar);
		} catch (IOException e) {
		}
		assertThat(sw.toString(), is("{\"hoge\":0}"));
	}

	/**
	 * Test for normal.
	 * @author vvakame
	 */
	@Test
	public void normal() {
		Child1ValueP1 bar = new Child1ValueP1();
		StringWriter sw = new StringWriter();
		try {
			Child1ValueP1Generated.encode(sw, bar);
		} catch (IOException e) {
		}
		assertThat(sw.toString(), is("{\"piyo\":0,\"hoge\":0}"));
	}

	/**
	 * Test for descendants.
	 * @author vvakame
	 */
	@Test
	public void descendants() {
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild0ValueC0P0Generated.encode(sw, new Grandchild0ValueC0P0());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild0ValueC1P0Generated.encode(sw, new Grandchild0ValueC1P0());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"hoge\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild1ValueC0P0Generated.encode(sw, new Grandchild1ValueC0P0());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"fuga\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild1ValueC1P0Generated.encode(sw, new Grandchild1ValueC1P0());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"hoge\":0,\"fuga\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild0ValueC0P1Generated.encode(sw, new Grandchild0ValueC0P1());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"piyo\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild0ValueC1P1Generated.encode(sw, new Grandchild0ValueC1P1());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"piyo\":0,\"hoge\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild1ValueC0P1Generated.encode(sw, new Grandchild1ValueC0P1());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"piyo\":0,\"fuga\":0}"));
		}
		{
			StringWriter sw = new StringWriter();
			try {
				Grandchild1ValueC1P1Generated.encode(sw, new Grandchild1ValueC1P1());
			} catch (IOException e) {
			}
			assertThat(sw.toString(), is("{\"piyo\":0,\"hoge\":0,\"fuga\":0}"));
		}
	}
}
