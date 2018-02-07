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
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxComboBoxTransition;
import org.malai.javafx.interaction2.JfxFSM;

public class ComboBoxSelectedFSM extends JfxFSM<ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler> {
	public ComboBoxSelectedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ComboBoxSelectedFSM.ComboBoxSelectedFSMHandler dataHandler) {
		if(states.size() > 1) return;
		super.buildFSM(dataHandler);
		final TerminalState<Event> selected = new TerminalState<>(this, "selected");
		addState(selected);
		new JfxComboBoxTransition(initState, selected) {
			@Override
			public void action(final Event event) {
				if(dataHandler != null && event instanceof ActionEvent) {
					dataHandler.initToSelectedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface ComboBoxSelectedFSMHandler extends FSMDataHandler {
		void initToSelectedHandler(ActionEvent event);
	}
}
