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
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.ConcurrentFSM;
import io.github.interacto.fsm.FSM;
import java.util.Set;
import javafx.event.Event;

public abstract class JfxConcurrFSM<H extends FSMDataHandler, F extends FSM<Event>> extends ConcurrentFSM<Event, F> {
	protected H dataHandler;

	public JfxConcurrFSM(final Set<F> fsms) {
		super(fsms);
	}

	protected void buildFSM(final H dataHandler) {
		if(states.size() > 1) {
			return;
		}
		this.dataHandler = dataHandler;
	}

	@Override
	public void reinit() {
		super.reinit();
		if(dataHandler != null && !inner) {
			dataHandler.reinitData();
		}
	}

	public H getDataHandler() {
		return dataHandler;
	}

	@Override
	public void uninstall() {
		super.uninstall();
		dataHandler = null;
	}
}
