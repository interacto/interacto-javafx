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
import org.malai.swing.interaction.library.KeyTyped;

public class TestKeyTyped extends TestInteraction<KeyTyped> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new KeyTyped();
	}


	@Test
	public void testDiffKey() {
		interaction.onKeyPressure(1234, 'a', 0, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(12, 'a', 0, new JButton());
	}


	@Test
	public void testDiffHID() {
		interaction.onKeyPressure(1234, 'a', 874, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(1234, 'a', 1, new JButton());
	}


	@Test
	public void testOnRestop() {
		interaction.onKeyPressure(1234, 'a', 874, new JButton());
		interaction.onKeyRelease(1234, 'a', 874, new JButton());
		interaction.onKeyPressure(8973, 'a', 71, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(8973, ((KeyTyped)inter).getKey());
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(8973, 'a', 71, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testOnRestart() {
		interaction.onKeyPressure(1234, 'a', 874, new JButton());
		interaction.onKeyRelease(1234, 'a', 874, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(8973, ((KeyTyped)inter).getKey());
				visitUpdate = true;
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(8973, ((KeyTyped)inter).getKey());
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(8973, 'a', 71, new JButton());
		assertTrue(visitUpdate);
		assertTrue(visitStart);
	}


	@Test
	public void testOnKeyReleasedKeyValue() {
		interaction.onKeyPressure(1234, 'a', 874, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(1234, ((KeyTyped)inter).getKey());
				assertEquals('a', ((KeyTyped)inter).getKeyChar());
				visitStop = true;
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyRelease(1234, 'a', 874, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testOnKeyPressedKeyValue() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(1234, ((KeyTyped)inter).getKey());
				assertEquals('a', ((KeyTyped)inter).getKeyChar());
				visitUpdate = true;
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				assertEquals(1234, ((KeyTyped)inter).getKey());
				visitStop = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(1234, 'a', 874, new JButton());
		assertTrue(visitStop);
		assertTrue(visitUpdate);
	}
}
