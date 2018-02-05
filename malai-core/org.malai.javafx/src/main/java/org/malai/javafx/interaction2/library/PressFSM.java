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
import javafx.scene.input.MouseEvent;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.PressureTransition;

public class PressFSM extends JfxFSM<PressFSM.PressFSMHandler> {
	public PressFSM() {
		super();
	}

	@Override
	protected void buildFSM(final PressFSMHandler handler) {
		super.buildFSM(handler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new PressureTransition(initState, pressed) {
			@Override
			protected void action(final Event event) {
				if(handler != null && event instanceof MouseEvent) {
					handler.initToPress((MouseEvent) event);
				}
			}
		};
	}

	interface PressFSMHandler extends FSMHandler {
		void initToPress(final MouseEvent event);
	}
}
