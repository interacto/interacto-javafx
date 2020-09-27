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

import io.github.interacto.fsm.StdState;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.TouchMoveTransition;
import io.github.interacto.jfx.interaction.TouchPressureTransition;
import io.github.interacto.jfx.interaction.TouchReleaseTransition;
import javafx.event.Event;
import javafx.scene.input.TouchEvent;

/**
 * The FSM that defines a touch interaction (that works like a DnD)
 */
public class TouchDnDFSM extends JfxFSM<TouchDnDFSM.TouchDnDFSMHandler> {
	int touchID;

	/**
	 * Creates the FSM.
	 */
	public TouchDnDFSM() {
		super();
		touchID = -1;
	}

	@Override
	protected void buildFSM(final TouchDnDFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}

		super.buildFSM(dataHandler);

		final StdState<Event> touched = new StdState<>(this, "touched");
		final TerminalState<Event> released = new TerminalState<>(this, "released");

		addState(touched);
		addState(released);

		new TouchPressureTransition(initState, touched) {
			@Override
			protected void action(final TouchEvent event) {
				touchID = event.getTouchPoint().getId();
				if(dataHandler != null) {
					dataHandler.onTouch(event);
				}
			}
		};

		new TouchMoveTransition(touched, touched) {
			@Override
			protected boolean isGuardOK(final TouchEvent event) {
				return event.getTouchPoint().getId() == touchID;
			}

			@Override
			protected void action(final TouchEvent event) {
				if(dataHandler != null) {
					dataHandler.onMove(event);
				}
			}
		};

		new TouchReleaseTransition(touched, released) {
			@Override
			protected boolean isGuardOK(final TouchEvent event) {
				return event.getTouchPoint().getId() == touchID;
			}

			@Override
			protected void action(final TouchEvent event) {
				if(dataHandler != null) {
					dataHandler.onRelease(event);
				}
			}
		};

		super.buildFSM(dataHandler);
	}

	@Override
	public void reinit() {
		super.reinit();
		touchID = -1;
	}

	interface TouchDnDFSMHandler extends FSMDataHandler {
		void onTouch(final TouchEvent event);

		void onMove(final TouchEvent event);

		void onRelease(final TouchEvent event);
	}
}
