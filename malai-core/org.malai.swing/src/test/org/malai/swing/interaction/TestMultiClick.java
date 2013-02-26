package org.malai.swing.interaction;

import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.interaction.library.MultiClick;

public class TestMultiClick extends TestInteraction<MultiClick> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new MultiClick();
	}


	@Test
	public void testCheckNbPointsAbort() {
		interaction.setMinPoints(3);
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

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
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(2, 100, -23, 1, new JButton());
		assertTrue(visitAbort);
	}


	@Test
	public void testCheckNbPoints3Stop() {
		interaction.setMinPoints(3);
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());
		interaction.onPressure(1, 200, -23, 1, new JButton());
		interaction.onRelease(1, 200, -23, 1, new JButton());

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
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(2, 100, -23, 1, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testCheckNbPointsStop() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

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
				fail();
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(2, 100, -23, 1, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testOnReleaseKeyPressedNotEscape() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

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
		interaction.onKeyPressure(KeyEvent.VK_I, 'a', 2, new JButton());
	}


	@Test
	public void testReleaseOnKeyPressedEscape() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

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
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(KeyEvent.VK_ESCAPE, 'a', 2, new JButton());
		assertTrue(visitAbort);
	}
	@Test
	public void testOnKeyPressedNotEscape() {
		interaction.onPressure(1, 100, -23, 1, new JButton());

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
		interaction.onKeyPressure(KeyEvent.VK_I, 'a', 2, new JButton());
	}


	@Test
	public void testOnKeyPressedEscape() {
		interaction.onPressure(1, 100, -23, 1, new JButton());

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
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onKeyPressure(KeyEvent.VK_ESCAPE, 'a', 2, new JButton());
		assertTrue(visitAbort);
	}



	@Test
	public void testOnPressReleaseMoveHIDDiff() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

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
		interaction.onMove(1, 100, -23, true, 2, null);
	}


	@Test
	public void testOnPressureReleasePressureValue() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, null);

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				final MultiClick multi = (MultiClick) inter;
				assertEquals(200., multi.getPoints().get(1).getX());
				assertEquals(-123., multi.getPoints().get(1).getY());
				assertEquals(100., multi.getPoints().get(0).getX());
				assertEquals(-23., multi.getPoints().get(0).getY());
				assertEquals(1, multi.getLastHIDUsed());
				assertEquals(200., multi.getCurrentPosition().getX());
				assertEquals(-123., multi.getCurrentPosition().getY());
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
		interaction.onPressure(1, 200, -123, 1, null);
		assertTrue(visitUpdate);
	}


	@Test
	public void testOnPressReleasePressHIDDiff() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, null);

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
		interaction.onPressure(1, 200, 123, 2, null);
	}



	@Test
	public void testOnPressReleaseHIDDiff() {
		interaction.onPressure(1, 100, -23, 1, new JButton());

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
		interaction.onRelease(1, 100, -23, 2, null);
	}



	@Test
	public void testOnPressReleaseMoveValue() {
		interaction.onPressure(1, 100, -23, 1, new JButton());
		interaction.onRelease(1, 100, -23, 1, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				final MultiClick multi = (MultiClick) inter;
				assertEquals(100., multi.getPoints().get(0).getX());
				assertEquals(-23., multi.getPoints().get(0).getY());
				assertEquals(1, multi.getLastHIDUsed());
				assertEquals(120., multi.getCurrentPosition().getX());
				assertEquals(-13., multi.getCurrentPosition().getY());
				visitUpdate = true;
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
		interaction.onMove(1, 120, -13, true, 1, null);
		assertTrue(visitUpdate);
	}




	@Test
	public void testOnPressReleaseValue() {
		interaction.onPressure(1, 100, -23, 1, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				final MultiClick multi = (MultiClick) inter;
				assertEquals(100., multi.getPoints().get(0).getX());
				assertEquals(-23., multi.getPoints().get(0).getY());
				assertEquals(1, multi.getLastHIDUsed());
				visitUpdate = true;
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
		interaction.onRelease(1, 100, -23, 1, null);
		assertTrue(visitUpdate);
	}




	@Test
	public void testOnPressureValue() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				final MultiClick multi = (MultiClick) inter;
				assertEquals(100., multi.getPoints().get(0).getX());
				assertEquals(-23., multi.getPoints().get(0).getY());
				assertEquals(1, multi.getLastHIDUsed());
				assertEquals(100., multi.getCurrentPosition().getX());
				assertEquals(-23., multi.getCurrentPosition().getY());
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(1, 100, -23, 1, null);
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}




	@Test
	public void testOnPressureButton1() {
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
		interaction.onPressure(10, 100, -23, 2, null);
	}
}
