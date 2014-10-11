package org.malai.javafx.interaction;

import javafx.scene.input.MouseEvent;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a drag (move after a pressure) using a pointing device.<br>
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
 * 2014-10-11<br>
 * @author Arnaud BLOUIN
 */
public class DragTransition extends PointingDeviceTransition<MouseEvent> {
	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public DragTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
