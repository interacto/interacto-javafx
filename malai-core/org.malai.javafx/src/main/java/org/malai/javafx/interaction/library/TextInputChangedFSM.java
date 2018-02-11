/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.event.Event;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.fsm.TimeoutTransition;
import org.malai.javafx.interaction.FSMDataHandler;
import org.malai.javafx.interaction.JfxFSM;
import org.malai.javafx.interaction.JfxTextInputChangedTransition;

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
		if(states.size() > 1) return;
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
		public TextInputChangedJfxTextInputChangedTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		public void action(final Event event) {
			if(dataHandler != null && event instanceof ActionEvent) {
				dataHandler.initToChangedHandler((ActionEvent) event);
			}
		}
	}

	interface TextInputChangedFSMHandler extends FSMDataHandler {
		void initToChangedHandler(ActionEvent event);
	}
}
