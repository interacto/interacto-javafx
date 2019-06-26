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

import io.interacto.fsm.InputState;
import io.interacto.fsm.OutputState;
import io.interacto.fsm.StdState;
import io.interacto.fsm.TerminalState;
import io.interacto.fsm.TimeoutTransition;
import io.interacto.jfx.interaction.FSMDataHandler;
import io.interacto.jfx.interaction.JfxFSM;
import io.interacto.jfx.interaction.JfxSpinnerChangedTransition;
import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class SpinnerChangedFSM extends JfxFSM<SpinnerChangedFSM.SpinnerChangedFSMHandler> {
	/** The time gap between the two spinner events. */
	private static long timeGap = 300;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap between the two spinner events.
	 * @param timeGapBetweenClicks The time gap between the two spinner events. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	public SpinnerChangedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final SpinnerChangedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final StdState<Event> changed = new StdState<>(this, "valueChanged");
		final TerminalState<Event> ended = new TerminalState<>(this, "ended");
		addState(changed);
		addState(ended);
		new SpinnerChangedJfxSpinnerChangedTransition(initState, changed);
		new SpinnerChangedJfxSpinnerChangedTransition(changed, changed);
		new TimeoutTransition<>(changed, ended, SUPPLY_TIME_GAP);
	}

	private class SpinnerChangedJfxSpinnerChangedTransition extends JfxSpinnerChangedTransition {
		SpinnerChangedJfxSpinnerChangedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		public void action(final Event event) {
			if(dataHandler != null && event instanceof ActionEvent) {
				dataHandler.initToChangedHandler((ActionEvent) event);
			}
		}
	}

	interface SpinnerChangedFSMHandler extends FSMDataHandler {
		void initToChangedHandler(ActionEvent event);
	}
}
