package org.malai.instrument;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.action.Action;
import org.malai.action.ActionImplStub;
import org.malai.binding.WidgetBinding;
import org.malai.binding.WidgetBindingImpl;
import org.malai.error.ErrorCatcher;
import org.malai.fsm.CancelFSMException;
import org.malai.interaction.InteractionMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestInteractor {
	protected StubInteractor interactor;
	protected StubInstrument instrument;

	@BeforeEach
	public void setUp() {
		instrument = new StubInstrument() {
			@Override
			protected void configureBindings() {
				TestInteractor.this.interactor = new StubInteractor(instrument, false, ActionImplStub.class, new InteractionMock());
				addBinding(interactor);
			}
		};
		instrument.setActivated(true);
		ErrorCatcher.INSTANCE.setNotifier(exception -> fail(exception.toString()));
	}

	@Test
	void testConstructorInstrumentNull() {
		assertThrows(IllegalArgumentException.class, () -> new StubInteractor(null, false, ActionImplStub.class, new InteractionMock()));
	}

	@Test
	void testConstructorActionNull() {
		assertThrows(IllegalArgumentException.class, () -> new StubInteractor(instrument, false, null, new InteractionMock()));
	}

	@Test
	void testConstructorInteractionNull() {
		assertThrows(IllegalArgumentException.class, () -> new StubInteractor(instrument, false, ActionImplStub.class, null));
	}

	@Test
	void testConstructorCreatedInteractionNotNull() {
		assertNotNull(interactor.getInteraction());
	}

	@Test
	void testConstructorCreatedActionIsNull() {
		assertNull(interactor.getAction());
	}

	@Test
	void testLinkDeActivation() {
		instrument.setActivated(true);
		instrument.setActivated(false);
		assertFalse(interactor.isActivated());
	}

	@Test
	void testLinkActivation() {
		instrument.setActivated(false);
		instrument.setActivated(true);
		assertTrue(interactor.isActivated());
	}

	@Test
	void testExecuteNope() {
		assertFalse(interactor.isExecute());
	}

	@Test
	void testExecuteOK() {
		interactor = new StubInteractor(instrument, true, ActionImplStub.class, new InteractionMock());
		assertTrue(interactor.isExecute());
	}

	@Test
	void testGetInstrument() {
		assertEquals(instrument, interactor.getInstrument());
	}

	@Test
	void testIsInteractionMustBeCancelled() {
		assertFalse(interactor.isStrictStart());
	}

	@Test
	void testNotRunning() {
		assertFalse(interactor.isRunning());
	}

	@Test
	void testInteractionCancelsWhenNotStarted() {
		interactor.fsmCancels();
	}

	@Test
	void testInteractionUpdatesWhenNotStarted() {
		interactor.fsmUpdates();
	}

	@Test
	void testInteractionStopsWhenNotStarted() {
		interactor.fsmStops();
	}

	@Test
	void testInteractionStartsWhenNoCorrectInteractionNotActivated() throws CancelFSMException {
		interactor.mustCancel = false;
		instrument.setActivated(false);
		interactor.fsmStarts();
		assertNull(interactor.getAction());
	}

	@Test
	void testInteractionStartsWhenNoCorrectInteractionActivated() throws CancelFSMException {
		interactor.mustCancel = false;
		instrument.setActivated(true);
		interactor.conditionRespected = false;
		interactor.fsmStarts();
		assertNull(interactor.getAction());
	}

	@Test
	void testInteractionStartsThrowMustCancelStateMachineException() {
		interactor.mustCancel = true;
		assertThrows(CancelFSMException.class, () -> interactor.fsmStarts());
	}

	@Test
	void testInteractionStartsOk() throws CancelFSMException {
		instrument.setActivated(true);
		interactor.conditionRespected = true;
		interactor.fsmStarts();
		assertNotNull(interactor.getAction());
	}

	@Test
	void testInteractionStartsOkCauseOfTheNonPublicAction() throws CancelFSMException {
		final boolean[] ok = {false};
		ErrorCatcher.INSTANCE.setNotifier(exception -> ok[0] = true);
		StubInstrument ins = new StubInstrument();
		WidgetBinding interactor2 = new WidgetBindingImpl<ActionImplStub2, InteractionMock, StubInstrument>(ins, false, ActionImplStub2.class, new InteractionMock()) {
			@Override
			public void first() {
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
		interactor2.fsmStarts();
		assertTrue(ok[0]);
		assertNull(interactor.getAction());
	}

	@Test
	void testInteractionStartsOkCauseOfTheNonPublicAction2() throws CancelFSMException {
		final boolean[] ok = {false};
		ErrorCatcher.INSTANCE.setNotifier(exception -> ok[0] = true);
		StubInstrument ins = new StubInstrument();
		WidgetBinding interactor2 = new WidgetBindingImpl<ActionImplStub3, InteractionMock, StubInstrument>(ins, false, ActionImplStub3.class, new InteractionMock()) {
			@Override
			public void first() {
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
		interactor2.fsmStarts();
		assertTrue(ok[0]);
		assertNull(interactor2.getAction());
	}

	public static class StubInstrument extends InstrumentImpl<WidgetBindingImpl<?, ?, StubInstrument>> {
		@Override
		protected void configureBindings() throws InstantiationException, IllegalAccessException {
		}
	}

	static class StubInteractor extends WidgetBindingImpl<ActionImplStub, InteractionMock, StubInstrument> {
		public boolean conditionRespected;
		public boolean mustCancel;

		public StubInteractor(final StubInstrument ins, final boolean exec, final Class<ActionImplStub> clazzAction, final InteractionMock interaction) {
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

	static class ActionImplStub2 extends ActionImplStub {
	}

	static class ActionImplStub3 extends ActionImplStub {
		ActionImplStub3(final int foo) {
		}
	}
}

