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
package org.malai.javafx.interaction2.library;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.PressureTransition;

public class PressFSM extends JfxFSM<PressFSM.PressFSMHandler> {
	public PressFSM() {
		super();
	}

	@Override
	protected void buildFSM(final PressFSMHandler dataHandler) {
		if(states.size() > 1) return;
		super.buildFSM(dataHandler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new PressureTransition(initState, pressed) {
			@Override
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof MouseEvent) {
					dataHandler.initToPress((MouseEvent) event);
				}
			}
		};
	}

	interface PressFSMHandler extends FSMDataHandler {
		void initToPress(final MouseEvent event);
	}
}
