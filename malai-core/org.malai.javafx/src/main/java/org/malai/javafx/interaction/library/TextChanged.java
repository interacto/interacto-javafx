/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.Collection;
import java.util.function.LongSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.javafx.interaction.JfxTextChangedTransition;


/**
 * This interaction is performed when the text of a text field is modified.
 * @author Arnaud Blouin
 */
public class TextChanged extends NodeInteraction<TextInputControl> {
	/** The time gap between the two spinner events. */
	private static long timeout = 1000;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIMEOUT = () -> getTimeout();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeout() {
		return timeout;
	}

	final ObjectProperty<String> txt;

	/**
	 * Creates the interaction.
	 */
	public TextChanged() {
		super();
		txt = new SimpleObjectProperty<>("");
		initStateMachine();
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState changed = new IntermediaryState("textChanged");
		final TerminalState ended = new TerminalState("ended");

		addState(changed);
		addState(ended);

		new JfxTextChangedTransition(initState, changed) {
			@Override
			public void action() {
				super.action();
				TextChanged.this.widget = widget;
				txt.setValue(widget.getText());
			}
		};

		new JfxTextChangedTransition(changed, changed) {
			@Override
			public void action() {
				super.action();
				txt.setValue(widget.getText());
			}
		};

		new TimeoutTransition(changed, ended, SUPPLY_TIMEOUT);
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		super.registerToNodes(widgets);

		if(widgets != null) {
			widgets.stream().filter(w -> w instanceof TextInputControl).
				forEach(w -> w.addEventHandler(ActionEvent.ACTION, evt -> onTextChanged((TextInputControl) evt.getSource())));
		}
	}

	/**
	 * @return The text of the text widget during the interaction.
	 */
	public String getTxt() {
		return txt.get();
	}

	/**
	 * @return The text property that corresponds to the text of the text widget during the interaction.
	 * This is not the text property of the text widget.
	 */
	public ObjectProperty<String> txtProperty() {
		return txt;
	}
}
