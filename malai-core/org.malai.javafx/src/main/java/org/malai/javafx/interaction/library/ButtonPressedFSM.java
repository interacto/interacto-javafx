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

import javafx.event.ActionEvent;
import javafx.event.Event;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction.FSMDataHandler;
import org.malai.javafx.interaction.JfxButtonPressedTransition;
import org.malai.javafx.interaction.JfxFSM;

public class ButtonPressedFSM extends JfxFSM<ButtonPressedFSM.ButtonPressedFSMHandler> {
	public ButtonPressedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ButtonPressedFSMHandler dataHandler) {
		if(states.size() > 1) return;
		super.buildFSM(dataHandler);
		final TerminalState<Event> pressed = new TerminalState<>(this, "pressed");
		addState(pressed);
		new JfxButtonPressedTransition(initState, pressed) {
			@Override
			public void action(final Event event) {
				if(dataHandler != null && event instanceof ActionEvent) {
					dataHandler.initToPressedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface ButtonPressedFSMHandler extends FSMDataHandler {
		void initToPressedHandler(ActionEvent event);
	}
}
