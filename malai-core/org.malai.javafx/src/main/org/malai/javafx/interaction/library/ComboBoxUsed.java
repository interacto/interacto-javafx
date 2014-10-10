package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxComboBoxUsedTransition;

/**
 * A ButtonPressed interaction occurs when a combo box is used.<br>
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
public class ComboBoxUsed extends WidgetInteraction<ComboBoxBase<?>> {
	/**
	 * Creates the interaction.
	 */
	public ComboBoxUsed() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed"); //$NON-NLS-1$

		addState(pressed);

		new JfxComboBoxUsedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				ComboBoxUsed.this.widget = this.widget;
			}
		};
	}
	
	@Override
	public void registerToWidgets(List<Node> widgets) {
		widgets.stream().filter(w -> w instanceof ComboBoxBase<?>).forEach(w -> {
			if(w instanceof ComboBox<?>) ((ComboBox<?>)w).setOnAction(evt -> onJfxComboBoxSelected((ComboBox<?>)evt.getSource()));
			else if(w instanceof ColorPicker) ((ColorPicker)w).setOnAction(evt -> onJfxColorPicked((ColorPicker)evt.getSource()));
			else if(w instanceof DatePicker) ((DatePicker)w).setOnAction(evt -> onJfxDatePicked((DatePicker)evt.getSource()));
		});
	}
}
