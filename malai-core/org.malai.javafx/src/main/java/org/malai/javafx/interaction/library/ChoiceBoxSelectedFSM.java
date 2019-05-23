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
import org.malai.javafx.interaction.JfxChoiceBoxTransition;
import org.malai.javafx.interaction.JfxFSM;

public class ChoiceBoxSelectedFSM extends JfxFSM<ChoiceBoxSelectedFSM.ChoiceBoxSelectedFSMHandler> {
	public ChoiceBoxSelectedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ChoiceBoxSelectedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> selected = new TerminalState<>(this, "selected");
		addState(selected);
		new JfxChoiceBoxTransition(initState, selected) {
			@Override
			public void action(final Event event) {
				if(ChoiceBoxSelectedFSM.this.dataHandler != null && event instanceof ActionEvent) {
					ChoiceBoxSelectedFSM.this.dataHandler.initToSelectedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface ChoiceBoxSelectedFSMHandler extends FSMDataHandler {
		void initToSelectedHandler(ActionEvent event);
	}
}
