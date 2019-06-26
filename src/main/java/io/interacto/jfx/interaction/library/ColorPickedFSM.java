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
import io.interacto.jfx.interaction.JfxColorPickedTransition;
import io.interacto.jfx.interaction.JfxFSM;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class ColorPickedFSM extends JfxFSM<ColorPickedFSM.ColorPickedFSMFSMHandler> {
	public ColorPickedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ColorPickedFSM.ColorPickedFSMFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> picked = new TerminalState<>(this, "picked");
		addState(picked);
		new JfxColorPickedTransition(initState, picked) {
			@Override
			public void action(final Event event) {
				if(dataHandler != null && event instanceof ActionEvent) {
					dataHandler.initToPickedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface ColorPickedFSMFSMHandler extends FSMDataHandler {
		void initToPickedHandler(ActionEvent event);
	}
}
