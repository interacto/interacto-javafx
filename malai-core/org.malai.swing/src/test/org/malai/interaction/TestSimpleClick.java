package org.malai.interaction;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.swing.interaction.library.SimpleClick;


public class TestSimpleClick extends TestInteraction<SimpleClick> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new SimpleClick();
	}


	@Test
	public void testMoveAbort() {
		interaction.onPressure(1, 1234, 0, 10, null);

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(Interaction inter) {
				visitAbort = true;
			}
		};

		interaction.addHandler(handler);
		interaction.onMove(1, 1235, 10, true, 10, null);
		assertTrue(visitAbort);
	}


	@Test
	public void testDiffButton() {
		interaction.onPressure(1, 1234, 0, 10, null);

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(2, 1234, 0, 10, null);
	}


	@Test
	public void testDiffHID() {
		interaction.onPressure(10, 100, -23, 1, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(5, 200, -13, 2, null);
	}

	@Test
	public void testOnRestop() {
		interaction.onPressure(10, 100, -23, 1, new JButton());
		interaction.onRelease(10, 100, -23, 1, new JButton());
		interaction.onPressure(5, 200, -13, 2, null);

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				SimpleClick press = (SimpleClick) inter;
				assertEquals(5, press.getButton());
				assertEquals(200., press.getPoint().getX());
				assertEquals(-13., press.getPoint().getY());
				assertEquals(2, press.getLastHIDUsed());
				visitStop = true;
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(5, 200, -13, 2, null);
		assertTrue(visitStop);
	}


	@Test
	public void testOnRestart() {
		interaction.onPressure(10, 100, -23, 1, new JButton());
		interaction.onRelease(10, 100, -23, 1, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				SimpleClick press = (SimpleClick) inter;
				assertEquals(5, press.getButton());
				assertEquals(200., press.getPoint().getX());
				assertEquals(-13., press.getPoint().getY());
				assertEquals(2, press.getLastHIDUsed());
				visitStart = true;
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(5, 200, -13, 2, null);
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}


	@Test
	public void testOnReleaseValue() {
		interaction.onPressure(10, 100, -23, 1, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				SimpleClick press = (SimpleClick) inter;
				assertEquals(10, press.getButton());
				assertEquals(100., press.getPoint().getX());
				assertEquals(-23., press.getPoint().getY());
				assertEquals(1, press.getLastHIDUsed());
				visitStop = true;
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(10, 100, -23, 1, null);
		assertTrue(visitStop);
	}



	@Test
	public void testOnPressureValue() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				SimpleClick press = (SimpleClick) inter;
				assertEquals(10, press.getButton());
				assertEquals(100., press.getPoint().getX());
				assertEquals(-23., press.getPoint().getY());
				assertEquals(1, press.getLastHIDUsed());
				visitStart = true;
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(10, 100, -23, 1, null);
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}
}
