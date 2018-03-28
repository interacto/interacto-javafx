package org.malai.command;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.malai.command.library.OpenWebPage;
import org.malai.HelperTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestOpenWebPage extends BaseCommandTest<OpenWebPage> {
	@Override
	protected OpenWebPage createCommand() {
		return new OpenWebPage();
	}

	@Override
	@Test
	public void testFlush() throws URISyntaxException {
		cmd.setUri(new URI("foo"));
		cmd.flush();
	}

	@Override
	@Test
	public void testDo() throws URISyntaxException {
		cmd.setUri(new URI("http://www.google.com"));
		cmd.doIt();
	}


	@Test
	public void testDoBadURI() throws URISyntaxException {
		cmd.setUri(new URI("foo"));
		cmd.doIt();
	}


	@Override
	@Test
	public void testCanDo() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		cmd.setUri(new URI("foo"));
		assertEquals(cmd.canDo(), Desktop.getDesktop().isSupported(Desktop.Action.BROWSE));
	}

	@Override
	@Test
	public void testIsRegisterable() {
		assertEquals(Command.RegistrationPolicy.NONE, cmd.getRegistrationPolicy());
	}

	@Override
	@Test
	public void testHadEffect() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		cmd.setUri(new URI("http://localhost"));
		cmd.doIt();
		cmd.done();
		assertTrue(cmd.hadEffect());
	}

	@Test
	public void testHadEffectWhenNotDone() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		cmd.setUri(new URI("http://localhost"));
		assertFalse(cmd.hadEffect());
	}

	@Test
	public void testHadEffectBadURI() throws URISyntaxException {
		assumeTrue(HelperTest.isX11Set());
		cmd.setUri(new URI("foo"));
		cmd.doIt();
		cmd.done();
		assertFalse(cmd.hadEffect());
	}
}
