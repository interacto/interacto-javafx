/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction2.library;

import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.event.Event;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.fsm.TimeoutTransition;
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.JfxSpinnerChangedTransition;

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
		if(states.size() > 1) return;
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
		public SpinnerChangedJfxSpinnerChangedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
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
