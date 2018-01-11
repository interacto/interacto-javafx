package test.org.malai.instrument;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.error.ErrorCatcher;
import org.malai.error.ErrorNotifier;
import org.malai.binding.WidgetBinding;
import org.malai.binding.WidgetBindingImpl;
import org.malai.stateMachine.MustCancelStateMachineException;
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
				TestInteractor.this.interactor = new MockInteractor(instrument, false, ActionImplMock.class, new InteractionMock());
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
		interactor = new MockInteractor(null, false, ActionImplMock.class, new InteractionMock());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructorActionNull() throws Exception {
		interactor = new MockInteractor(instrument, false, null, new InteractionMock());
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
		interactor = new MockInteractor(instrument, true, ActionImplMock.class, new InteractionMock());
		assertTrue(interactor.isExecute());
	}


	@Test
	public void testGetInstrument() {
		assertEquals(instrument, interactor.getInstrument());
	}

	@Test
	public void testIsInteractionMustBeCancelled() {
		assertFalse(interactor.isStrictStart());
	}


	@Test
	public void testNotRunning() {
		assertFalse(interactor.isRunning());
	}

	@Test
	public void testInteractionCancelsWhenNotStarted() {
		interactor.interactionCancels();
	}


	@Test
	public void testInteractionUpdatesWhenNotStarted() {
		interactor.interactionUpdates();
	}


	@Test
	public void testInteractionStopsWhenNotStarted() {
		interactor.interactionStops();
	}


	@Test
	public void testInteractionStartsWhenNoCorrectInteraction() throws MustCancelStateMachineException {
		interactor.mustCancel = false;
		instrument.setActivated(false);
		interactor.interactionStarts();
		assertNull(interactor.getAction());

		instrument.setActivated(true);
		interactor.conditionRespected = false;
		interactor.interactionStarts();
		assertNull(interactor.getAction());
	}


	@Test(expected = MustCancelStateMachineException.class)
	public void testInteractionStartsThrowMustCancelStateMachineException() throws MustCancelStateMachineException {
		interactor.mustCancel = true;
		interactor.interactionStarts();
	}


	@Test
	public void testInteractionStartsOk() throws MustCancelStateMachineException {
		instrument.setActivated(true);
		interactor.conditionRespected = true;
		interactor.interactionStarts();
		assertNotNull(interactor.getAction());
	}


	@Test
	public void testInteractionStartsOkCauseOfTheNonPublicAction() throws MustCancelStateMachineException, InstantiationException,
		IllegalAccessException {
		final boolean[] ok = {false};
		ErrorCatcher.INSTANCE.setNotifier(exception -> ok[0] = true);
		MockInstrument ins = new MockInstrument();
		WidgetBinding interactor2 = new WidgetBindingImpl<ActionImplMock2, InteractionMock, MockInstrument>(ins, false, ActionImplMock2.class,
			new InteractionMock()) {
			@Override
			public void first() {//
			}

			@Override
			public boolean when() {
				return true;
			}

			@Override
			protected void executeActionAsync(final Action act) {

			}
		};
		ins.setActivated(true);
		interactor2.interactionStarts();
		assertTrue(ok[0]);
		assertNull(interactor.getAction());

		ok[0] = false;
		ins = new MockInstrument();
		interactor2 = new WidgetBindingImpl<ActionImplMock3, InteractionMock, MockInstrument>(ins, false, ActionImplMock3.class,
			new InteractionMock()) {
			@Override
			public void first() {//
			}

			@Override
			public boolean when() {
				return true;
			}

			@Override
			protected void executeActionAsync(final Action act) {

			}
		};
		ins.setActivated(true);
		interactor2.interactionStarts();
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
	public boolean mustCancel;

	public MockInteractor(final MockInstrument ins, final boolean exec, final Class<ActionImplMock> clazzAction, final
	InteractionMock interaction) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction);
		conditionRespected = false;
		mustCancel = false;
	}

	@Override
	public void first() {
		//
	}

	@Override
	public boolean when() {
		return conditionRespected;
	}

	@Override
	public boolean isStrictStart() {
		return mustCancel;
	}

	@Override
	protected void executeActionAsync(final Action act) {

	}
}
