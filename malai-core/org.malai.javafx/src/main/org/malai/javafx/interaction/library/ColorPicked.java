package org.malai.javafx.interaction.library;

import javafx.scene.control.ColorPicker;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxColorPickedTransition;

/**
 * An interaction dedicated to the use of colour pickers.<br>
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
public class ColorPicked extends JfxWidgetInteraction<ColorPicker> {
	/**
	 * Creates the interaction.
	 */
	public ColorPicked() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new JfxColorPickedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ColorPicked.this.widget = this.widget;
			}
		};
	}
}