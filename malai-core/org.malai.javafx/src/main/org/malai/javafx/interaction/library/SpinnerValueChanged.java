package org.malai.javafx.interaction.library;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Spinner;

import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxSpinnerValueChangedTransition;

/**
 * An interaction dedicated to set values using a spinner.<br>
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
 * 2014-12-21<br>
 * @author Arnaud BLOUIN
 */
public class SpinnerValueChanged extends WidgetInteraction<Spinner<?>> {
	/**
	 * Creates the interaction.
	 */
	public SpinnerValueChanged() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("valueChanged"); //$NON-NLS-1$

		addState(pressed);

		new JfxSpinnerValueChangedTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				SpinnerValueChanged.this.widget = this.widget;
			}
		};
	}
	
	@Override
	public void registerToWidgets(final List<Node> widgets) {
		widgets.stream().filter(w -> w instanceof Spinner<?>).forEach(w -> 
			((Spinner<?>)w).valueProperty().addListener((observable, oldValue, newValue) -> onJfxSpinnerValueChanged((Spinner<?>)w)));
	}
}
