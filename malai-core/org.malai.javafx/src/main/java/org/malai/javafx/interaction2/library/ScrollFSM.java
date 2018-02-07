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
import javafx.scene.input.ScrollEvent;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.ScrollTransition;

public class ScrollFSM extends JfxFSM<ScrollFSM.ScrollFSMHandler> {
	public ScrollFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ScrollFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> scrolled = new TerminalState<>(this, "scrolled");
		addState(scrolled);
		new ScrollTransition(initState, scrolled) {
			@Override
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof ScrollEvent) {
					dataHandler.onScroll((ScrollEvent) event);
				}
			}
		};
	}

	interface ScrollFSMHandler extends FSMDataHandler {
		void onScroll(final ScrollEvent event);
	}
}
