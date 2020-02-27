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
import io.github.interacto.jfx.interaction.KeyTypedTransition;
import java.util.function.LongSupplier;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

public class KeysTypedFSM extends JfxFSM<KeysTypedFSM.KeysTypedFSMHandler> {
	/** The time gap to wait after the latest key event. */
	private static long timeGap = 1000L;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap to wait after the latest key event.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap to wait after the latest key event.
	 * @param timeGapBetweenClicks The time gap to wait after the latest key event. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	public KeysTypedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final KeysTypedFSM.KeysTypedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final StdState<Event> pressed = new StdState<>(this, "pressed");
		final TerminalState<Event> ended = new TerminalState<>(this, "ended");
		addState(pressed);
		addState(ended);
		new KeysTypedKeyTypedTransition(initState, pressed);
		new KeysTypedKeyTypedTransition(pressed, pressed);
		new TimeoutTransition<>(pressed, ended, SUPPLY_TIME_GAP);
	}

	class KeysTypedKeyTypedTransition extends KeyTypedTransition {
		KeysTypedKeyTypedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected void action(final Event event) {
			if(dataHandler != null && event instanceof KeyEvent) {
				dataHandler.onKeyTyped((KeyEvent) event);
			}
		}
	}

	interface KeysTypedFSMHandler extends FSMDataHandler {
		void onKeyTyped(final KeyEvent event);
	}
}
