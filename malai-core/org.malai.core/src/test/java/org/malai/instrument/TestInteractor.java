package org.malai.instrument;

import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.binding.WidgetBindingImpl;
import org.malai.command.Command;
import org.malai.command.CommandImplStub;
import org.malai.error.ErrorCatcher;
import org.malai.fsm.CancelFSMException;
import org.malai.interaction.InteractionData;
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
				TestInteractor.this.interactor = new StubInteractor(instrument, false, CommandImplStub::new, new InteractionMock());
				addBinding(interactor);
			}
		};
		instrument.setActivated(true);
		ErrorCatcher.INSTANCE.setNotifier(exception -> fail(exception.toString()));
	}

	@AfterEach
	void tearDown() {
		ErrorCatcher.INSTANCE.setNotifier(null);
		instrument.uninstallBindings();
	}

	@Test
	void testConstructorInstrumentNull() {
		assertThrows(IllegalArgumentException.class, () -> new StubInteractor(null, false, CommandImplStub::new, new InteractionMock()));
	}

	@Test
	void testConstructorInteractionNull() {
		assertThrows(IllegalArgumentException.class, () -> new StubInteractor(instrument, false, CommandImplStub::new, null));
	}

	@Test
	void testConstructorCreatedInteractionNotNull() {
		assertNotNull(interactor.getInteraction());
	}

	@Test
	void testConstructorCreatedActionIsNull() {
		assertNull(interactor.getCommand());
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
		interactor = new StubInteractor(instrument, true, CommandImplStub::new, new InteractionMock());
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
		assertNull(interactor.getCommand());
	}

	@Test
	void testInteractionStartsWhenNoCorrectInteractionActivated() throws CancelFSMException {
		interactor.mustCancel = false;
		instrument.setActivated(true);
		interactor.conditionRespected = false;
		interactor.fsmStarts();
		assertNull(interactor.getCommand());
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
		assertNotNull(interactor.getCommand());
	}

	public static class StubInstrument extends InstrumentImpl<WidgetBindingImpl<?, ?, StubInstrument, ?>> {
		@Override
		protected void configureBindings() {
		}
	}

	static class StubInteractor extends WidgetBindingImpl<CommandImplStub, InteractionMock, StubInstrument, InteractionData> {
		public boolean conditionRespected;
		public boolean mustCancel;

		public StubInteractor(final StubInstrument ins, final boolean exec, final Supplier<CommandImplStub> cmdCreation, final InteractionMock interaction) {
			this(ins, exec, i -> cmdCreation.get(), interaction);
		}

		public StubInteractor(final StubInstrument ins, final boolean exec, final Function<InteractionData, CommandImplStub> cmdCreation, final InteractionMock interaction) {
			super(ins, exec, cmdCreation, interaction);
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
		protected void executeCmdAsync(final Command cmd) {

		}
	}
}

