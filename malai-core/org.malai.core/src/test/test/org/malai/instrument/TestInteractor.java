package test.org.malai.instrument;

import org.junit.Before;
import org.junit.Test;
import org.malai.error.ErrorCatcher;
import org.malai.error.ErrorNotifier;
import org.malai.binding.WidgetBinding;
import org.malai.binding.WidgetBindingImpl;
import org.malai.stateMachine.MustAbortStateMachineException;
import test.org.malai.action.ActionImplMock;
import test.org.malai.instrument.TestMockInstrument.MockInstrument;
import test.org.malai.interaction.InteractionMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestInteractor {
	protected MockInteractor interactor;
	protected MockInstrument instrument;

	@Before
	public void setUp() {
		instrument = new MockInstrument() {
			@Override
			protected void configureBindings() throws InstantiationException, IllegalAccessException {
				TestInteractor.this.interactor = new MockInteractor(instrument, false, ActionImplMock.class, InteractionMock.class);
				addBinding(interactor);
			}
		};
		instrument.setActivated(true);
		ErrorCatcher.INSTANCE.setNotifier(new ErrorNotifier() {
			@Override
			public void onException(final Exception exception) {
				fail(exception.toString());
			}
		});
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInstrumentNull() throws Exception {
		interactor = new MockInteractor(null, false, ActionImplMock.class, InteractionMock.class);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorActionNull() throws Exception {
		interactor = new MockInteractor(instrument, false, null, InteractionMock.class);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInteractionNull() throws Exception {
		interactor = new MockInteractor(instrument, false, ActionImplMock.class, null);
	}


	@Test
	public void testConstructorCreatedInteractionNotNull() {
		assertNotNull(interactor.getInteraction());
	}


	@Test
	public void testConstructorCreatedActionIsNull() {
		assertNull(interactor.getAction());
	}


	@Test
	public void testLinkActivation() {
		instrument.setActivated(false);
		assertFalse(interactor.isActivated());
		instrument.setActivated(true);
		assertTrue(interactor.isActivated());
	}


	@Test
	public void testExecute() throws InstantiationException, IllegalAccessException {
		assertFalse(interactor.isExecute());
		interactor = new MockInteractor(instrument, true, ActionImplMock.class, InteractionMock.class);
		assertTrue(interactor.isExecute());
	}


	@Test
	public void testGetInstrument() {
		assertEquals(instrument, interactor.getInstrument());
	}

	@Test
	public void testIsInteractionMustBeAborted() {
		assertFalse(interactor.isInteractionMustBeAborted());
	}


	@Test
	public void testNotRunning() {
		assertFalse(interactor.isRunning());
	}

	@Test
	public void testInteractionAbortWhenNotStarted() {
		interactor.interactionAborts(null);
		interactor.interactionAborts(interactor.getInteraction());
		interactor.interactionAborts(new InteractionMock());
	}


	@Test
	public void testInteractionUpdatesWhenNotStarted() {
		interactor.interactionUpdates(null);
		interactor.interactionUpdates(interactor.getInteraction());
		interactor.interactionUpdates(new InteractionMock());
	}


	@Test
	public void testInteractionStopsWhenNotStarted() {
		interactor.interactionStops(null);
		interactor.interactionStops(interactor.getInteraction());
		interactor.conditionRespected = false;
		interactor.interactionStops(new InteractionMock());
	}


	@Test
	public void testInteractionStartsWhenNoCorrectInteraction() throws MustAbortStateMachineException {
		interactor.mustAbort = true;
		interactor.interactionStarts(null);
		assertNull(interactor.getAction());
		interactor.interactionStarts(new InteractionMock());
		assertNull(interactor.getAction());

		interactor.mustAbort = false;
		instrument.setActivated(false);
		interactor.interactionStarts(interactor.getInteraction());
		assertNull(interactor.getAction());

		instrument.setActivated(true);
		interactor.conditionRespected = false;
		interactor.interactionStarts(interactor.getInteraction());
		assertNull(interactor.getAction());
	}


	@Test(expected = MustAbortStateMachineException.class)
	public void testInteractionStartsThrowMustAbortStateMachineException() throws MustAbortStateMachineException {
		interactor.mustAbort = true;
		interactor.interactionStarts(interactor.getInteraction());
	}


	@Test
	public void testInteractionStartsOk() throws MustAbortStateMachineException {
		instrument.setActivated(true);
		interactor.conditionRespected = true;
		interactor.interactionStarts(interactor.getInteraction());
		assertNotNull(interactor.getAction());
	}


	@Test
	public void testInteractionStartsOkCauseOfTheNonPublicAction() throws MustAbortStateMachineException, InstantiationException,
		IllegalAccessException {
		final boolean[] ok = {false};
		ErrorCatcher.INSTANCE.setNotifier(new ErrorNotifier() {
			@Override
			public void onException(final Exception exception) {
				ok[0] = true;
			}
		});
		MockInstrument ins = new MockInstrument();
		WidgetBinding interactor2 = new WidgetBindingImpl<ActionImplMock2, InteractionMock, MockInstrument>(ins, false, ActionImplMock2.class,
			InteractionMock.class) {
			@Override
			public void initAction() {//
			}

			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};
		ins.setActivated(true);
		interactor2.interactionStarts(interactor2.getInteraction());
		assertTrue(ok[0]);
		assertNull(interactor.getAction());

		ok[0] = false;
		ins = new MockInstrument();
		interactor2 = new WidgetBindingImpl<ActionImplMock3, InteractionMock, MockInstrument>(ins, false, ActionImplMock3.class,
			InteractionMock.class) {
			@Override
			public void initAction() {//
			}

			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};
		ins.setActivated(true);
		interactor2.interactionStarts(interactor2.getInteraction());
		assertTrue(ok[0]);
		assertNull(interactor2.getAction());
	}
}


class ActionImplMock2 extends ActionImplMock {
	//
}


class ActionImplMock3 extends ActionImplMock {
	ActionImplMock3(@SuppressWarnings("unused") final int foo) {
		//
	}
}


class MockInteractor extends WidgetBindingImpl<ActionImplMock, InteractionMock, MockInstrument> {
	public boolean conditionRespected;
	public boolean mustAbort;

	public MockInteractor(final MockInstrument ins, final boolean exec, final Class<ActionImplMock> clazzAction, final
	Class<InteractionMock> clazzInteraction) throws InstantiationException, IllegalAccessException {
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
