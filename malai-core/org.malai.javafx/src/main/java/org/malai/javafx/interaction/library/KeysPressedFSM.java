package org.malai.javafx.interaction.library;

import java.util.EnumSet;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction.FSMDataHandler;
import org.malai.javafx.interaction.JfxFSM;
import org.malai.javafx.interaction.KeyPressureTransition;
import org.malai.javafx.interaction.KeyReleaseTransition;

/**
 * This interaction permits to define combo a key pressed that can be used to define shortcuts, etc.
 * @author Arnaud BLOUIN
 */
public class KeysPressedFSM extends JfxFSM<KeysPressedFSM.KeysPressedFSMHandler> {
	private final EnumSet<KeyCode> currentCodes;

	public KeysPressedFSM() {
		super();
		currentCodes = EnumSet.noneOf(KeyCode.class);
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
		new KeyReleaseTransition(pressed, ended) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof KeyEvent && currentCodes.contains(((KeyEvent) event).getCode());
			}
		};
	}

	@Override
	public void reinit() {
		currentCodes.clear();
		super.reinit();
	}

	class KeysPressedKeyPressureTransition extends KeyPressureTransition {
		KeysPressedKeyPressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected void action(final Event event) {
			if(event instanceof KeyEvent) {
				currentCodes.add(((KeyEvent) event).getCode());
				if(dataHandler != null) {
					dataHandler.onKeyPressed((KeyEvent) event);
				}
			}
		}
	}

	interface KeysPressedFSMHandler extends FSMDataHandler {
		void onKeyPressed(final KeyEvent event);
	}
}
