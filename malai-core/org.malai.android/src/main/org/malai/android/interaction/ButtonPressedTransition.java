package org.malai.android.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import android.widget.Button;

/**
 * This transition must be used to use an Android button pressed event within an interaction.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-02-26<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class ButtonPressedTransition extends Transition {
	/** The pressed button. */
	protected Button button;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ButtonPressedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public Button getButton() {
		return button;
	}


	/**
	 * Sets the pressed button.
	 * @param button The pressed button. Must not be null.
	 * @since 0.2
	 */
	public void setButton(final Button button) {
		if(button!=null)
			this.button = button;
	}
}
