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
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.JfxTabSelectedTransition;
import javafx.event.Event;

public class TabSelectedFSM extends JfxFSM<TabSelectedFSM.TabSelectedFSMHandler> {
	public TabSelectedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final TabSelectedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> selected = new TerminalState<>(this, "selected");
		addState(selected);
		new JfxTabSelectedTransition(initState, selected) {
			@Override
			public void action(final Event event) {
				if(dataHandler != null && event instanceof TabEvent) {
					dataHandler.initToSelectedHandler((TabEvent) event);
				}
			}
		};
	}

	interface TabSelectedFSMHandler extends FSMDataHandler {
		void initToSelectedHandler(TabEvent event);
	}
}
