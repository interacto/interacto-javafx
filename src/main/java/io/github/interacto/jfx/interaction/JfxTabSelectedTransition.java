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
package io.github.interacto.jfx.interaction;

import io.github.interacto.jfx.interaction.library.TabEvent;
import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import java.util.Collections;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.control.TabPane;

/**
 * An FSM transition for tabs.
 * @author Arnaud BLOUIN
 */
public class JfxTabSelectedTransition extends JfxWidgetTransition<TabPane> {
	public JfxTabSelectedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	public boolean accept(final Event e) {
		return e != null && e.getEventType() == TabEvent.SELECTED;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.singleton(TabEvent.SELECTED);
	}
}
