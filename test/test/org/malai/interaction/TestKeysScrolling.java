package test.org.malai.interaction;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.interaction.library.KeysScrolling;
import org.malai.stateMachine.MustAbortStateMachineException;


public class TestKeysScrolling extends TestInteraction<KeysScrolling> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new KeysScrolling();
	}



	@Test
	public void testKeyPressKeyPressScroll() {
		interaction.onKeyPressure(123, 100, new JButton());
		interaction.onKeyPressure(234, 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				assertEquals(interaction.getIncrement(), 3);
				assertEquals(interaction.getKeys().size(), 2);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals((int)interaction.getKeys().get(1), 234);
				assertEquals(interaction.getLastHIDUsed(), 33);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 200.);
				assertEquals(interaction.getPy(), 300.);
				visitStop = true;
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;// Because of key event recycling.
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onScroll(200, 300, -1, 3, 100, 33, new JButton());
		assertTrue(visitUpdate);
		assertTrue(visitStop);
		assertTrue(visitStart);
	}



	@Test
	public void testKeyPressKeyPressKeyRelease3() {
		interaction.onKeyPressure(123, 100, new JButton());
		interaction.onKeyPressure(234, 100, new JButton());

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
		interaction.onKeyRelease(123, 101, new JButton());
	}


	@Test
	public void testKeyPressKeyPressKeyRelease2() {
		interaction.onKeyPressure(123, 100, new JButton());
		interaction.onKeyPressure(234, 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 234);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0.);
				assertEquals(interaction.getPy(), 0.);
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
		interaction.onKeyRelease(123, 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyPressKeyRelease1() {
		interaction.onKeyPressure(123, 100, new JButton());
		interaction.onKeyPressure(234, 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0.);
				assertEquals(interaction.getPy(), 0.);
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
		interaction.onKeyRelease(234, 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyPressWidthDiffHid() {
		interaction.onKeyPressure(123, 100, new JButton());

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
		interaction.onKeyPressure(234, 101, new JButton());
	}



	@Test
	public void testKeyPressKeyPress() {
		interaction.onKeyPressure(123, 100, new JButton());

		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 2);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals((int)interaction.getKeys().get(1), 234);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0.);
				assertEquals(interaction.getPy(), 0.);
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
		interaction.onKeyPressure(234, 100, new JButton());
		assertTrue(visitUpdate);
	}



	@Test
	public void testKeyPressKeyReleaseWithDiffHid() {
		interaction.onKeyPressure(123, 100, new JButton());

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
		interaction.onKeyRelease(123, 200, new JButton());
	}



	@Test
	public void testKeyPressKeyReleaseWithDiffKey() {
		interaction.onKeyPressure(123, 100, new JButton());

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
		interaction.onKeyRelease(1213, 100, new JButton());
	}


	@Test
	public void testKeyPressKeyRelease() {
		interaction.onKeyPressure(123, 100, new JButton());

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
		interaction.onKeyRelease(123, 100, new JButton());
		assertTrue(visitAbort);
	}


	@Test
	public void testKeyPress() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				visitUpdate = true;
				assertEquals(interaction.getIncrement(), 0);
				assertEquals(interaction.getKeys().size(), 1);
				assertEquals((int)interaction.getKeys().get(0), 123);
				assertEquals(interaction.getLastHIDUsed(), -1);
				assertEquals(interaction.getKeyHIDUsed(), 100);
				assertEquals(interaction.getPx(), 0.);
				assertEquals(interaction.getPy(), 0.);
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
		interaction.onKeyPressure(123, 100, new JButton());
		assertTrue(visitStart);
		assertTrue(visitUpdate);
	}


	@Test
	public void testScroll() {
		handler = new InteractionHandler() {
			@Override
			public void interactionUpdates(Interaction inter) throws MustAbortStateMachineException {
				fail();
			}
			@Override
			public void interactionStops(Interaction inter) throws MustAbortStateMachineException {
				visitStop = true;
			}
			@Override
			public void interactionStarts(Interaction inter) throws MustAbortStateMachineException {
				visitStart = true;
				assertEquals(interaction.getIncrement(), -5);
				assertEquals(interaction.getKeys().size(), 0);
				assertEquals(interaction.getLastHIDUsed(), 50);
				assertEquals(interaction.getKeyHIDUsed(), -1);
				assertEquals(interaction.getPx(), 10.);
				assertEquals(interaction.getPy(), 20.);
			}
			@Override
			public void interactionAborts(Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onScroll(10, 20, 1, 5, 30, 50, new JButton());

		assertTrue(visitStart);
		assertTrue(visitStop);
	}
}
