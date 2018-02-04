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
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.ClickTransition;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;

public class ClickFSM extends JfxFSM<Node, ClickFSM.ClickFSMHandler> {
	public ClickFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ClickFSM.ClickFSMHandler handler) {
		super.buildFSM(handler);
		final TerminalState<Event> clicked = new TerminalState<>(this, "clicked");
		addState(clicked);
		new ClickTransition(initState, clicked) {
			@Override
			public void action(final Event event) {
				if(event instanceof MouseEvent) {
					handler.initToClicked((MouseEvent) event);
				}
			}
		};
	}

	interface ClickFSMHandler extends FSMHandler {
		void initToClicked(final MouseEvent event);
	}
}
