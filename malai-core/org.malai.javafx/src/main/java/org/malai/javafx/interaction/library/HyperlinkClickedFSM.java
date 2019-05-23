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
import org.malai.javafx.interaction.JfxFSM;
import org.malai.javafx.interaction.JfxHyperlinkClickedTransition;

public class HyperlinkClickedFSM extends JfxFSM<HyperlinkClickedFSM.HyperlinkClickedFSMHandler> {
	public HyperlinkClickedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final HyperlinkClickedFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> clicked = new TerminalState<>(this, "clicked");
		addState(clicked);
		new JfxHyperlinkClickedTransition(initState, clicked) {
			@Override
			public void action(final Event event) {
				if(dataHandler != null && event instanceof ActionEvent) {
					dataHandler.initToClickedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface HyperlinkClickedFSMHandler extends FSMDataHandler {
		void initToClickedHandler(ActionEvent event);
	}
}
