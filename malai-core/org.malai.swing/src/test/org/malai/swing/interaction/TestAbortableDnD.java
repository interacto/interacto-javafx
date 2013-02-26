package org.malai.swing.interaction;

import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.interaction.library.AbortableDnD;

import test.org.malai.interaction.TestDnD;

public class TestAbortableDnD extends TestDnD {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new AbortableDnD();
	}


	@Test
	public void testKeyEscapeToAbortOnPressMoveMove() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 100, 200, true, 3, new JButton());

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
			@SuppressWarnings("synthetic-access")
			@Override
			public void interactionAborts(final Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(KeyEvent.VK_ESCAPE, 'e', 123, new JButton());
		assertTrue(visitAbort);
	}



	@Test
	public void testKeyEscapeToAbortOnPressMove() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

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
			@SuppressWarnings("synthetic-access")
			@Override
			public void interactionAborts(final Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(KeyEvent.VK_ESCAPE, 'e', 123, new JButton());
		assertTrue(visitAbort);
	}




	@Test
	public void testKeyEscapeToAbortOnPress() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

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
			@SuppressWarnings("synthetic-access")
			@Override
			public void interactionAborts(final Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(KeyEvent.VK_ESCAPE, 'e', 123, new JButton());
		assertTrue(visitAbort);
	}



	@Override
	@Test
	public void testPressReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

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
			@SuppressWarnings("synthetic-access")
			@Override
			public void interactionAborts(final Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(12, 1, 2, 3, new JButton());
		assertTrue(visitAbort);
	}
}
