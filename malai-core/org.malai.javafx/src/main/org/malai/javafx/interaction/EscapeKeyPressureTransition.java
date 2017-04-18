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
package org.malai.javafx.interaction;

import javafx.scene.input.KeyCode;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition should be used to cancel an interaction using key ESCAPE.
 * @author Arnaud BLOUIN
 */
public class EscapeKeyPressureTransition extends KeyPressureTransition {
	/**
	 * Creates the transition.
	 * @param inputState The source state.
	 * @param outputState The srcObject state.
	 */
	public EscapeKeyPressureTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	@Override
	public boolean isGuardRespected() {
		return event.getCode()==KeyCode.ESCAPE;
	}
}
