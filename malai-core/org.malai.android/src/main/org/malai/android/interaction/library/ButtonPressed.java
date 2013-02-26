package org.malai.android.interaction.library;

import org.malai.android.interaction.AndroidInteraction;
import org.malai.android.interaction.ButtonPressedTransition;
import org.malai.interaction.TerminalState;

import android.widget.Button;

/**
 * A ButtonPressed interaction occurs when an Android button is pressed.<br>
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
 * 2012-02-26<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class ButtonPressed extends AndroidInteraction {
	/** The pressed button. */
	protected Button button;

	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		button = null;
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new ButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ButtonPressed.this.button = this.button;
			}
		};
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public Button getButton() {
		return button;
	}
}
