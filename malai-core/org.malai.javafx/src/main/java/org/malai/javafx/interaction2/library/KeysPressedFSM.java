package org.malai.javafx.interaction2.library;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.KeyPressureTransition;
import org.malai.javafx.interaction2.KeyReleaseTransition;

/**
 * This interaction permits to define combo a key pressed that can be used to define shortcuts, etc.
 * @author Arnaud BLOUIN
 */
public class KeysPressedFSM extends JfxFSM<KeysPressedFSM.KeysPressedFSMHandler> {
	public KeysPressedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final KeysPressedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final StdState<Event> pressed = new StdState<>(this, "pressed");
		final TerminalState<Event> ended = new TerminalState<>(this, "ended");
		addState(pressed);
		addState(ended);
		new KeysPressedKeyPressureTransition(initState, pressed);
		new KeysPressedKeyPressureTransition(pressed, pressed);
		new KeyReleaseTransition(pressed, ended);
	}

	class KeysPressedKeyPressureTransition extends KeyPressureTransition {
		KeysPressedKeyPressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected void action(final Event event) {
			if(dataHandler != null && event instanceof KeyEvent) {
				dataHandler.onKeyPressed((KeyEvent) event);
			}
		}
	}

	interface KeysPressedFSMHandler extends FSMDataHandler {
		void onKeyPressed(final KeyEvent event);
	}
}
