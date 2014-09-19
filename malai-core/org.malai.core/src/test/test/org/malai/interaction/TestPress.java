package test.org.malai.interaction;

import org.junit.Before;
import org.junit.Test;
import org.malai.interaction.Interaction;
import org.malai.interaction.InteractionHandler;
import org.malai.interaction.library.Press;
import org.malai.stateMachine.MustAbortStateMachineException;

import static org.junit.Assert.*;

public class TestPress extends TestInteraction<Press> {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		interaction = new Press();
	}


	@Test
	public void testOnRestart() {
		interaction.onPressure(10, 100, -23, 1, null);
		interaction.onRelease(10, 100, -23, 1, null);

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
				final Press press = (Press) inter;
				assertEquals(100, press.getButton());
				assertEquals(1000., press.getPoint().getX(), 0.0);
				assertEquals(-203., press.getPoint().getY(), 0.0);
				assertEquals(1783, press.getLastHIDUsed());
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(100, 1000, -203, 1783, null);
		assertTrue(visitStop);
		assertTrue(visitStart);
	}


	@Test
	public void testOnPressureValue() {
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
				final Press press = (Press) inter;
				assertEquals(10, press.getButton());
				assertEquals(100., press.getPoint().getX(), 0.0);
				assertEquals(-23., press.getPoint().getY(), 0.0);
				assertEquals(1, press.getLastHIDUsed());
				visitStart = true;
			}
			@Override
			public void interactionAborts(final Interaction inter) {
				fail();
			}
		};

		interaction.addHandler(handler);
		interaction.onPressure(10, 100, -23, 1, null);
		assertTrue(visitStart);
		assertTrue(visitStop);
	}
}
