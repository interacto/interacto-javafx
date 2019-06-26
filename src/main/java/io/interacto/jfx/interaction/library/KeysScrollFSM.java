/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.interacto.jfx.interaction.library;

import io.interacto.fsm.CancellingState;
import io.interacto.fsm.InputState;
import io.interacto.fsm.OutputState;
import io.interacto.fsm.StdState;
import io.interacto.fsm.SubFSMTransition;
import io.interacto.fsm.TerminalState;
import io.interacto.jfx.interaction.FSMDataHandler;
import io.interacto.jfx.interaction.JfxFSM;
import io.interacto.jfx.interaction.KeyPressureTransition;
import io.interacto.jfx.interaction.KeyReleaseTransition;
import java.util.HashSet;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeysScrollFSM extends JfxFSM<KeysScrollFSM.KeysScrollFSMHandler> {
	protected final ScrollFSM scrollFSM;
	private final Set<KeyCode> currentCodes;

	public KeysScrollFSM() {
		super();
		scrollFSM = new ScrollFSM();
		currentCodes = new HashSet<>();
	}

	@Override
	protected void buildFSM(final KeysScrollFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);

		final StdState<Event> keyPressed = new StdState<>(this, "keyPressed");
		final CancellingState<Event> keyReleased = new CancellingState<>(this, "keyReleased");
		final TerminalState<Event> scrolled = new TerminalState<>(this, "scrolled");

		addState(keyPressed);
		addState(keyReleased);
		addState(scrolled);

		new SubFSMTransition<>(initState, scrolled, scrollFSM);
		new SubFSMTransition<>(keyPressed, scrolled, scrollFSM);
		new KeysScrollFSMKeyPressureTransition(initState, keyPressed);
		new KeysScrollFSMKeyPressureTransition(keyPressed, keyPressed);
		new KeyReleaseTransition(keyPressed, keyPressed) {
			@Override
			protected void action(final Event event) {
				if(event instanceof KeyEvent) {
					currentCodes.remove(((KeyEvent) event).getCode());
					if(dataHandler != null) {
						dataHandler.onKeyReleased((KeyEvent) event);
					}
				}
			}

			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof KeyEvent && currentCodes.contains(((KeyEvent) event).getCode()) && currentCodes.size() > 1;
			}
		};
		new KeyReleaseTransition(keyPressed, keyReleased) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof KeyEvent && currentCodes.contains(((KeyEvent) event).getCode()) && currentCodes.size() == 1;
			}
		};
	}

	private final class KeysScrollFSMKeyPressureTransition extends KeyPressureTransition {
		KeysScrollFSMKeyPressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
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

	interface KeysScrollFSMHandler extends FSMDataHandler {
		void onKeyPressed(final KeyEvent event);

		void onKeyReleased(final KeyEvent event);
	}
}
