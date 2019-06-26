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

import io.interacto.fsm.TerminalState;
import io.interacto.jfx.interaction.FSMDataHandler;
import io.interacto.jfx.interaction.JfxFSM;
import io.interacto.jfx.interaction.PressureTransition;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class PressFSM extends JfxFSM<PressFSM.PressFSMHandler> {
	public PressFSM() {
		super();
	}

	@Override
	protected void buildFSM(final PressFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new PressureTransition(initState, pressed) {
			@Override
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof MouseEvent) {
					dataHandler.initToPress((MouseEvent) event);
				}
			}
		};
	}

	interface PressFSMHandler extends FSMDataHandler {
		void initToPress(final MouseEvent event);
	}
}
