package test.org.malai.interaction;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.interaction.library.DnD;
import org.malai.stateMachine.MustAbortStateMachineException;
import static org.junit.Assert.*;

public class TestDnD extends TestInteraction<DnD> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new DnD();
	}


	@Test
	public void testPressMoveMoveReleaseDiffButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 20, 30, true, 3, new JButton());

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
		interaction.onRelease(15, 10, 20, 3, new JButton());
	}


	@Test
	public void testPressMoveMoveReleaseDiffHid() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 20, 30, true, 3, new JButton());

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
		interaction.onRelease(12, 10, 20, 5, new JButton());
	}


	@Test
	public void testPressMoveMoveReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 20, 30, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(20., dnd.getEndPt().getX(), 0.0);
				assertEquals(30., dnd.getEndPt().getY(), 0.0);
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 20, 30, 3, new JButton());
		assertTrue(visitStop);
	}



	@Test
	public void testPressMoveReleaseDiffButton() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(15, 10, 20, 3, new JButton());
	}


	@Test
	public void testPressMoveReleaseDiffHid() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(12, 10, 20, 5, new JButton());
	}


	@Test
	public void testPressMoveReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(final Interaction inter) throws MustAbortStateMachineException {
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(10., dnd.getEndPt().getX(), 0.0);
				assertEquals(20., dnd.getEndPt().getY(), 0.0);
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 10, 20, 3, new JButton());
		assertTrue(visitStop);
	}



	@Test
	public void testPressMoveMoveDiffHid() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onMove(12, 20, 30, true, 5, new JButton());
	}


	@Test
	public void testPressMoveMoveSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(20., dnd.getEndPt().getX(), 0.0);
				assertEquals(30., dnd.getEndPt().getY(), 0.0);
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onMove(12, 20, 30, true, 3, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testPressMoveDiffHid() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onMove(12, 10, 20, true, 5, new JButton());
	}


	@Test
	public void testPressMoveSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(10., dnd.getEndPt().getX(), 0.0);
				assertEquals(20., dnd.getEndPt().getY(), 0.0);
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		assertTrue(visitUpdate);
	}


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
				visitStop = true;
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(1., dnd.getEndPt().getX(), 0.0);
				assertEquals(2., dnd.getEndPt().getY(), 0.0);
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 1, 2, 3, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testPressReleaseWithDiffHID() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(12, 1, 2, 5, new JButton());
	}



	@Test
	public void testPressReleaseWithDiffButton() {
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
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onRelease(20, 1, 2, 3, new JButton());
	}


	@Test
	public void testOnPress() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(final Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				final DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX(), 0.0);
				assertEquals(2., dnd.getStartPt().getY(), 0.0);
				assertEquals(1., dnd.getEndPt().getX(), 0.0);
				assertEquals(2., dnd.getEndPt().getY(), 0.0);
				assertNull(dnd.getEndObjet());
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onPressure(12, 1, 2, 3, new JButton());
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}
}
