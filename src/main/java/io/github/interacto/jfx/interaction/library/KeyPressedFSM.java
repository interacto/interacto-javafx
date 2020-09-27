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

import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.KeyPressureTransition;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

/**
 * An FSM for a single key pressure.
 */
public class KeyPressedFSM extends JfxFSM<KeyPressedFSM.KeyPressedFSMHandler> {
	private final boolean modifiersAccepted;

	/**
	 * Creates the FSM.
	 * @param modifiersAccepted True: the FSM will consider key modifiers.
	 */
	public KeyPressedFSM(final boolean modifiersAccepted) {
		super();
		this.modifiersAccepted = modifiersAccepted;
	}

	@Override
	protected void buildFSM(final KeyPressedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new KeyPressureTransition(initState, pressed) {
			@Override
			protected void action(final KeyEvent event) {
				if(dataHandler != null) {
					dataHandler.onKeyPressure(event);
				}
			}

			@Override
			protected boolean isGuardOK(final KeyEvent event) {
				return modifiersAccepted || (!event.getCode().isModifierKey() &&
					!event.getCode().isFunctionKey() && !event.getCode().isMediaKey());
			}
		};
	}

	@Override
	public void reinit() {
		super.reinit();
		if(eventsToProcess != null) {
			eventsToProcess.clear();
		}
	}

	interface KeyPressedFSMHandler extends FSMDataHandler {
		void onKeyPressure(final KeyEvent event);
	}
}
