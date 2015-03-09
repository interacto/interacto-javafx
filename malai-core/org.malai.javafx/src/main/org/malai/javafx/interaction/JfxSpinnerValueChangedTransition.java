package org.malai.javafx.interaction;

import javafx.scene.control.Spinner;

import org.malai.interaction.TransitionImpl;
import org.malai.interaction.WidgetTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A transition based on the onAction event of spinner.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public class JfxSpinnerValueChangedTransition extends WidgetTransition<Spinner<?>> {
	/**
	 * {@link TransitionImpl#Transition(SourceableState, TargetableState)}
	 */
	public JfxSpinnerValueChangedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
