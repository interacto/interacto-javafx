package test.org.malai.interaction;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.interaction.library.DnD;
import org.malai.stateMachine.MustAbortStateMachineException;


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
		interaction.onRelease(15, 10, 20, 3, new JButton());
	}


	@Test
	public void testPressMoveMoveReleaseDiffHid() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 20, 30, true, 3, new JButton());

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
		interaction.onRelease(12, 10, 20, 5, new JButton());
	}


	@Test
	public void testPressMoveMoveReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		interaction.onMove(12, 20, 30, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(20., dnd.getEndPt().getX());
				assertEquals(30., dnd.getEndPt().getY());
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 20, 30, 3, new JButton());
		assertTrue(visitStop);
	}



	@Test
	public void testPressMoveReleaseDiffButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

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
		interaction.onRelease(15, 10, 20, 3, new JButton());
	}


	@Test
	public void testPressMoveReleaseDiffHid() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

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
		interaction.onRelease(12, 10, 20, 5, new JButton());
	}


	@Test
	public void testPressMoveReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(10., dnd.getEndPt().getX());
				assertEquals(20., dnd.getEndPt().getY());
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 10, 20, 3, new JButton());
		assertTrue(visitStop);
	}



	@Test
	public void testPressMoveMoveDiffHid() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

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
		interaction.onMove(12, 20, 30, true, 5, new JButton());
	}


	@Test
	public void testPressMoveMoveSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());
		interaction.onMove(12, 10, 20, true, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(20., dnd.getEndPt().getX());
				assertEquals(30., dnd.getEndPt().getY());
				assertEquals(3, dnd.getLastHIDUsed());
				visitUpdate = true;
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
		interaction.onMove(12, 20, 30, true, 3, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testPressMoveDiffHid() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

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
		interaction.onMove(12, 10, 20, true, 5, new JButton());
	}


	@Test
	public void testPressMoveSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(10., dnd.getEndPt().getX());
				assertEquals(20., dnd.getEndPt().getY());
				assertEquals(3, dnd.getLastHIDUsed());
				visitUpdate = true;
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
		interaction.onMove(12, 10, 20, true, 3, new JButton());
		assertTrue(visitUpdate);
	}


	@Test
	public void testPressReleaseSameHidButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				visitStop = true;
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(1., dnd.getEndPt().getX());
				assertEquals(2., dnd.getEndPt().getY());
				assertEquals(3, dnd.getLastHIDUsed());
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
		interaction.onRelease(12, 1, 2, 3, new JButton());
		assertTrue(visitStop);
	}


	@Test
	public void testPressReleaseWithDiffHID() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

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
		interaction.onRelease(12, 1, 2, 5, new JButton());
	}



	@Test
	public void testPressReleaseWithDiffButton() {
		interaction.onPressure(12, 1, 2, 3, new JButton());

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
		interaction.onRelease(20, 1, 2, 3, new JButton());
	}


	@Test
	public void testOnPress() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				DnD dnd = (DnD) inter;
				assertEquals(1., dnd.getStartPt().getX());
				assertEquals(2., dnd.getStartPt().getY());
				assertEquals(1., dnd.getEndPt().getX());
				assertEquals(2., dnd.getEndPt().getY());
				assertNull(dnd.getEndObjet());
				assertEquals(3, dnd.getLastHIDUsed());
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(12, 1, 2, 3, new JButton());
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}
}
