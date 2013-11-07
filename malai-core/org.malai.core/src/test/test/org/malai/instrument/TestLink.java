package test.org.malai.instrument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.malai.error.ErrorCatcher;
import org.malai.error.ErrorNotifier;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.stateMachine.MustAbortStateMachineException;

import test.org.malai.action.ActionMock;
import test.org.malai.interaction.InteractionMock;

public class TestLink {
	protected MockLink link;
	protected InstrumentMock instrument;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException {
		instrument = new InstrumentMock();
		link = new MockLink(instrument, false, ActionMock.class, InteractionMock.class);
		ErrorCatcher.INSTANCE.setNotifier(new ErrorNotifier() {
			@Override
			public void onMalaiException(final Exception exception) {
				fail(exception.toString());
			}
		});
	}



	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInstrumentNull() throws Exception {
		link = new MockLink(null, false, ActionMock.class, InteractionMock.class);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorActionNull() throws Exception {
		link = new MockLink(instrument, false, null, InteractionMock.class);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInteractionNull() throws Exception {
		link = new MockLink(instrument, false, ActionMock.class, null);
	}


	@Test public void testConstructorCreatedInteractionNotNull() {
		assertNotNull(link.getInteraction());
	}


	@Test public void testConstructorCreatedActionIsNull() {
		assertNull(link.getAction());
	}


	@Test public void testLinkActivation() {
		instrument.setActivated(false);
		assertFalse(link.isActivated());
		instrument.setActivated(true);
		assertTrue(link.isActivated());
	}


	@Test public void testExecute() throws InstantiationException, IllegalAccessException {
		assertFalse(link.isExecute());
		link = new MockLink(instrument, true, ActionMock.class, InteractionMock.class);
		assertTrue(link.isExecute());
	}


	@Test public void testGetInstrument() {
		assertEquals(instrument, link.getInstrument());
	}

	@Test public void testIsInteractionMustBeAborted() {
		assertFalse(link.isInteractionMustBeAborted());
	}


	@Test public void testNotRunning() {
		assertFalse(link.isRunning());
	}

	@Test public void testInteractionAbortWhenNotStarted() {
		link.interactionAborts(null);
		link.interactionAborts(link.getInteraction());
		link.interactionAborts(new InteractionMock());
	}


	@Test public void testInteractionUpdatesWhenNotStarted() {
		link.interactionUpdates(null);
		link.interactionUpdates(link.getInteraction());
		link.interactionUpdates(new InteractionMock());
	}


	@Test public void testInteractionStopsWhenNotStarted() {
		link.interactionStops(null);
		link.interactionStops(link.getInteraction());
		link.conditionRespected = false;
		link.interactionStops(new InteractionMock());
	}


	@Test public void testInteractionStartsWhenNoCorrectInteraction() throws MustAbortStateMachineException {
		link.mustAbort = true;
		link.interactionStarts(null);
		assertNull(link.getAction());
		link.interactionStarts(new InteractionMock());
		assertNull(link.getAction());

		link.mustAbort = false;
		instrument.setActivated(false);
		link.interactionStarts(link.getInteraction());
		assertNull(link.getAction());

		instrument.setActivated(true);
		link.conditionRespected = false;
		link.interactionStarts(link.getInteraction());
		assertNull(link.getAction());
	}


	@Test(expected=MustAbortStateMachineException.class)
	public void testInteractionStartsThrowMustAbortStateMachineException() throws MustAbortStateMachineException {
		link.mustAbort = true;
		link.interactionStarts(link.getInteraction());
	}


	@Test public void testInteractionStartsOk() throws MustAbortStateMachineException {
		instrument.setActivated(true);
		link.conditionRespected = true;
		link.interactionStarts(link.getInteraction());
		assertNotNull(link.getAction());
	}


	@Test public void testInteractionStartsOkCauseOfTheNonPublicAction()
				throws MustAbortStateMachineException, InstantiationException, IllegalAccessException {
		final boolean[] ok = {false};
		ErrorCatcher.INSTANCE.setNotifier(new ErrorNotifier() {
			@Override
			public void onMalaiException(final Exception exception) {
				ok[0] = true;
			}
		});
		Link<?,?,?> link2 = new Link<ActionMock2, InteractionMock, InstrumentMock>
							(new InstrumentMock(), false, ActionMock2.class, InteractionMock.class) {
			@Override
			public void initAction() {//
			}
			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};
		link2.getInstrument().setActivated(true);
		link2.interactionStarts(link2.getInteraction());
		assertTrue(ok[0]);
		assertNull(link2.getAction());

		ok[0] = false;
		link2 = new Link<ActionMock3, InteractionMock, InstrumentMock>(new InstrumentMock(), false, ActionMock3.class, InteractionMock.class) {
			@Override
			public void initAction() {//
			}
			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};
		link2.getInstrument().setActivated(true);
		link2.interactionStarts(link2.getInteraction());
		assertTrue(ok[0]);
		assertNull(link2.getAction());
	}
}


class ActionMock2 extends ActionMock {
//
}


class ActionMock3 extends ActionMock {
	ActionMock3(@SuppressWarnings("unused") final int foo) {
		//
	}
}


class MockLink extends Link<ActionMock, InteractionMock, InstrumentMock>{
	public boolean conditionRespected;
	public boolean mustAbort;

	public MockLink(final InstrumentMock ins, final boolean exec, final Class<ActionMock> clazzAction, final Class<InteractionMock> clazzInteraction)
			throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction);
		conditionRespected = false;
		mustAbort = false;
	}
	@Override
	public void initAction() {
		//
	}
	@Override
	public boolean isConditionRespected() {
		return conditionRespected;
	}

	@Override
	public boolean isInteractionMustBeAborted() {
		return mustAbort;
	}
}


class InstrumentMock extends Instrument {
	@Override
	protected void initialiseLinks() {
		//
	}
}
