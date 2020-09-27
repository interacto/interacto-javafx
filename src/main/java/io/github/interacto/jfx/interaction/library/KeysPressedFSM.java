/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.fsm.StdState;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.KeyPressureTransition;
import io.github.interacto.jfx.interaction.KeyReleaseTransition;
import java.util.EnumSet;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This interaction permits to define combo a key pressed that can be used to define shortcuts, etc.
 * @author Arnaud BLOUIN
 */
public class KeysPressedFSM extends JfxFSM<KeysPressedFSM.KeysPressedFSMHandler> {
	private final EnumSet<KeyCode> currentCodes;

	/**
	 * Creates the FSM.
	 */
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
			protected boolean isGuardOK(final KeyEvent event) {
				return currentCodes.contains(event.getCode());
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
		protected void action(final KeyEvent evt) {
			currentCodes.add(evt.getCode());
			if(evt.isMetaDown()) {
				currentCodes.add(KeyCode.META);
			}
			if(evt.isAltDown()) {
				currentCodes.add(KeyCode.ALT);
			}
			if(evt.isControlDown()) {
				currentCodes.add(KeyCode.CONTROL);
			}
			if(evt.isShiftDown()) {
				currentCodes.add(KeyCode.SHIFT);
			}
			if(evt.isShortcutDown()) {
				currentCodes.add(KeyCode.SHORTCUT);
			}
			if(dataHandler != null) {
				dataHandler.onKeyPressed(evt);
			}
		}
	}

	interface KeysPressedFSMHandler extends FSMDataHandler {
		void onKeyPressed(final KeyEvent event);
	}
}
