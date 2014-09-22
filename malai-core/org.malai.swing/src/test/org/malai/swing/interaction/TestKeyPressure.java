package org.malai.swing.interaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.interaction.library.KeyPressure;

public class TestKeyPressure extends TestInteraction<KeyPressure> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new KeyPressure();
	}


	@Test
	public void testOnRestart() {
		interaction.onKeyPressure(1234, 'a', 874, new JButton());
		interaction.onKeyRelease(1234, 'a', 874, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(12, ((KeyPressure)inter).getKey());
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(12, 'a', 874, new JButton());
		assertTrue(visitStop);
		assertTrue(visitStart);
	}


	@Test
	public void testOnKeyPressedKeyValue() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(1234, ((KeyPressure)inter).getKey());
				assertEquals('a', ((KeyPressure)inter).getKeyChar());
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(1234, 'a', 874, new JButton());
		assertTrue(visitStop);
		assertTrue(visitStart);
	}
}
