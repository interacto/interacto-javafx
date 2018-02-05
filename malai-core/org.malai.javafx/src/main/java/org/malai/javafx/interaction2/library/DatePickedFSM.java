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
import javafx.scene.control.DatePicker;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxDatePickedTransition;
import org.malai.javafx.interaction2.JfxFSM;

public class DatePickedFSM extends JfxFSM<DatePickedFSM.DatePickedFSMHandler> {
	public DatePickedFSM() {
		super();
	}

	@Override
	protected void buildFSM(final DatePickedFSMHandler handler) {
		super.buildFSM(handler);
		final TerminalState<Event> picked = new TerminalState<>(this, "picked");
		addState(picked);
		new JfxDatePickedTransition(initState, picked) {
			@Override
			public void action(final Event event) {
				if(handler != null && event instanceof ActionEvent) {
					handler.initToPickedHandler((ActionEvent) event);
				}
			}
		};
	}

	interface DatePickedFSMHandler extends FSMHandler {
		void initToPickedHandler(ActionEvent event);
	}
}
