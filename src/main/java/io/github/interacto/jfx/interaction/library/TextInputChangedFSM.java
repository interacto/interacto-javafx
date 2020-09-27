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
import io.github.interacto.fsm.TimeoutTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.JfxTextInputChangedTransition;
import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class TextInputChangedFSM extends JfxFSM<TextInputChangedFSM.TextInputChangedFSMHandler> {
	/** The time gap between the two spinner events. */
	private static long timeout = 1000L;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIMEOUT = () -> getTimeout();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeout() {
		return timeout;
	}

	public TextInputChangedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final TextInputChangedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final StdState<Event> changed = new StdState<>(this, "changed");
		final TerminalState<Event> ended = new TerminalState<>(this, "ended");
		addState(changed);
		addState(ended);
		new TextInputChangedJfxTextInputChangedTransition(initState, changed);
		new TextInputChangedJfxTextInputChangedTransition(changed, changed);
		new TimeoutTransition<>(changed, ended, SUPPLY_TIMEOUT);
	}

	private class TextInputChangedJfxTextInputChangedTransition extends JfxTextInputChangedTransition {
		TextInputChangedJfxTextInputChangedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		public void action(final ActionEvent event) {
			if(dataHandler != null) {
				dataHandler.initToChangedHandler(event);
			}
		}
	}

	interface TextInputChangedFSMHandler extends FSMDataHandler {
		void initToChangedHandler(ActionEvent event);
	}
}
