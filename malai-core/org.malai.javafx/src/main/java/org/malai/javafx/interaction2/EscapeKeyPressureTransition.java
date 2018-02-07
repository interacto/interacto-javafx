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
package org.malai.javafx.interaction2;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;

/**
 * This transition should be used to cancel an interaction using key ESCAPE.
 * @author Arnaud BLOUIN
 */
public class EscapeKeyPressureTransition extends KeyPressureTransition {
	public EscapeKeyPressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	public boolean isGuardOK(final Event event) {
		return event instanceof KeyEvent && ((KeyEvent) event).getCode() == KeyCode.ESCAPE;
	}
}
