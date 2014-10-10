package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxButtonPressedTransition;

/**
 * This interaction occurs when a button is pressed.<br>
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
public class ButtonPressed extends WidgetInteraction<ButtonBase> {
	/**
	 * Creates the interaction.
	 */
	public ButtonPressed() {
		super();
		initStateMachine();
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
				ButtonPressed.this.widget = this.widget;
			}
		};
	}
	
	@Override
	public void registerToWidgets(List<Node> widgets) {
		widgets.stream().filter(w -> w instanceof ButtonBase).forEach(w -> {
			if(w instanceof Button) ((Button)w).setOnAction(evt -> onJfxButtonPressed((Button)evt.getSource()));
			else if(w instanceof CheckBox) ((CheckBox)w).setOnAction(evt -> onJfxCheckBoxUsed((CheckBox)evt.getSource()));
			else if(w instanceof ToggleButton) ((ToggleButton)w).setOnAction(evt -> onJfxToggleButtonsPressed((ToggleButton)evt.getSource()));
			else if(w instanceof Hyperlink) ((Hyperlink)w).setOnAction(evt -> onJfxHyperlinkClicked((Hyperlink)evt.getSource()));
			else if(w instanceof MenuButton) ((MenuButton)w).setOnAction(evt -> onJfxMenuButtonPressed((MenuButton)evt.getSource()));
		});
	}
}
