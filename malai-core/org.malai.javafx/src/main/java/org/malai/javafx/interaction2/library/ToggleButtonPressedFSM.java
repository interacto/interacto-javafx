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

import javafx.event.ActionEvent;
import javafx.event.Event;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.JfxToggleButtonPressedTransition;

public class ToggleButtonPressedFSM extends JfxFSM<ToggleButtonPressedFSM.ToggleButtonPressedFSMHandler> {
	public ToggleButtonPressedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ToggleButtonPressedFSMHandler handler) {
		super.buildFSM(handler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new JfxToggleButtonPressedTransition(initState, pressed) {
			@Override
			public void action(final Event event) {
				if(handler != null && event instanceof ActionEvent) {
					handler.initToPressedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface ToggleButtonPressedFSMHandler extends FSMHandler {
		void initToPressedHandler(ActionEvent event);
	}
}
