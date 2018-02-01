package org.malai.action;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.malai.action.library.OpenWebPage;
import org.malai.HelperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestOpenWebPage extends TestAbstractAction<OpenWebPage> {
	@Override
	protected OpenWebPage createAction() {
		return new OpenWebPage();
	}

	@Override
	@Test
	public void testFlush() throws URISyntaxException {
		action.setUri(new URI("foo"));
		action.flush();
	}

	@Override
	@Test
	public void testDo() throws URISyntaxException {
		action.setUri(new URI("http://www.google.com"));
		action.doIt();
	}


	@Test
	public void testDoBadURI() throws URISyntaxException {
		action.setUri(new URI("foo"));
		action.doIt();
	}


	@Override
	@Test
	public void testCanDo() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		action.setUri(new URI("foo"));
		assertEquals(action.canDo(), Desktop.getDesktop().isSupported(Desktop.Action.BROWSE));
	}

	@Override
	@Test
	public void testIsRegisterable() {
		assertEquals(Action.RegistrationPolicy.NONE, action.getRegistrationPolicy());
	}

	@Override
	@Test
	public void testHadEffect() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		action.setUri(new URI("http://localhost"));
		action.doIt();
		action.done();
		assertTrue(action.hadEffect());
	}

	@Test
	public void testHadEffectWhenNotDone() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		action.setUri(new URI("http://localhost"));
		assertFalse(action.hadEffect());
	}

	@Test
	public void testHadEffectBadURI() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		action.setUri(new URI("foo"));
		action.doIt();
		action.done();
		assertFalse(action.hadEffect());
	}
}
