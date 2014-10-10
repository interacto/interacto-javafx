package org.malai.javafx.interaction;

import javafx.scene.input.MouseEvent;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a pressure of a button of a pointing device.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-23<br>
 * @author Arnaud BLOUIN
 */
public class PressureTransition extends PointingDeviceTransition<MouseEvent> {
	/**
	 * Creates the transition.
	 */
	public PressureTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
