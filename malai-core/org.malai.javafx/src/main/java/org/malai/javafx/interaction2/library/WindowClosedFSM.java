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

import javafx.event.Event;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.JfxWindowClosedTransition;

public class WindowClosedFSM extends JfxFSM<Window, WindowClosedFSM.WindowClosedHandler> {
	public WindowClosedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final WindowClosedHandler handler) {
		super.buildFSM(handler);
		final TerminalState<Event> closed = new TerminalState<>(this, "closed");
		addState(closed);
		new JfxWindowClosedTransition(initState, closed) {
			@Override
			public void action(final Event event) {
				if(event instanceof WindowEvent) {
					handler.initToClosedHandler((WindowEvent) event);
				}
			}
		};
	}

	interface WindowClosedHandler extends FSMHandler {
		void initToClosedHandler(WindowEvent event);
	}
}
