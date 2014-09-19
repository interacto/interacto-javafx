package org.malai.javafx.interaction.library;

import javafx.scene.control.ButtonBase;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxButtonPressedTransition;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * A ButtonPressed interaction occurs when a button is pressed.<br>
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
 * 2014-09-18<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public class ButtonPressed extends JfxInteraction {
	/** The pressed button. */
	protected ButtonBase button;

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

		new JfxButtonPressedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ButtonPressed.this.button = this.widget;
			}
		};
	}


	/**
	 * @return The pressed button.
	 */
	public ButtonBase getButton() {
		return button;
	}
}
