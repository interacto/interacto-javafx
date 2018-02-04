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
import javafx.scene.control.TextInputControl;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.fsm.TimeoutTransition;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.JfxTextInputChangedTransition;

public class TextInputChangedFSM extends JfxFSM<TextInputControl, TextInputChangedFSM.TextInputChangedFSMHandler> {
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
	protected void buildFSM(final TextInputChangedFSMHandler handler) {
		super.buildFSM(handler);
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
			if(event instanceof ActionEvent) {
				handler.initToChangedHandler((ActionEvent) event);
			}
		}
	}

	interface TextInputChangedFSMHandler extends FSMHandler {
		void initToChangedHandler(ActionEvent event);
	}
}
