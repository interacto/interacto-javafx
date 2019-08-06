package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancellingState;
import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.fsm.StdState;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.DragTransition;
import io.github.interacto.jfx.interaction.EscapeKeyPressureTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.PressureTransition;
import io.github.interacto.jfx.interaction.ReleaseTransition;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DnDFSM extends JfxFSM<DnDFSM.DnDFSMHandler> {
	private final boolean cancellable;
	private MouseButton buttonToCheck;

	public DnDFSM(final boolean cancellable) {
		super();
		this.cancellable = cancellable;
	}

	@Override
	protected void buildFSM(final DnDFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}

		super.buildFSM(dataHandler);

		final StdState<Event> pressed = new StdState<>(this, "pressed");
		final StdState<Event> dragged = new StdState<>(this, "dragged");
		final TerminalState<Event> released = new TerminalState<>(this, "released");
		final CancellingState<Event> cancelled = new CancellingState<>(this, "cancelled");

		addState(pressed);
		addState(dragged);
		addState(released);
		addState(cancelled);

		startingState = dragged;

		new PressureTransition(initState, pressed) {
			@Override
			protected void action(final Event event) {
				if(event instanceof MouseEvent) {
					buttonToCheck = ((MouseEvent) event).getButton();
					if(dataHandler != null) {
						dataHandler.onPress((MouseEvent) event);
					}
				}
			}
		};
		new ReleaseTransition(pressed, cancelled) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof MouseEvent && ((MouseEvent) event).getButton() == buttonToCheck;
			}
		};
		new DnDFSMDragTransition(pressed, dragged);
		new DnDFSMDragTransition(dragged, dragged);
		new ReleaseTransition(dragged, released) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof MouseEvent && ((MouseEvent) event).getButton() == buttonToCheck;
			}

			@Override
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof MouseEvent) {
					dataHandler.onRelease((MouseEvent) event);
				}
			}
		};

		if(cancellable) {
			new EscapeKeyPressureTransition(pressed, cancelled);
			new EscapeKeyPressureTransition(dragged, cancelled);
		}

		super.buildFSM(dataHandler);
	}

	@Override
	public void reinit() {
		super.reinit();
		buttonToCheck = null;
	}

	class DnDFSMDragTransition extends DragTransition {
		DnDFSMDragTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected boolean isGuardOK(final Event event) {
			return event instanceof MouseEvent && ((MouseEvent) event).getButton() == buttonToCheck;
		}

		@Override
		protected void action(final Event event) {
			if(dataHandler != null && event instanceof MouseEvent) {
				dataHandler.onDrag((MouseEvent) event);
			}
		}
	}

	interface DnDFSMHandler extends FSMDataHandler {
		void onPress(final MouseEvent event);

		void onDrag(final MouseEvent event);

		void onRelease(final MouseEvent event);
	}
}
