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

import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private static long timeout = 1000L;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIMEOUT = () -> getTimeout();
	private static final EventHandler<? super KeyEvent> HANDLER_KEY_ACTION = evt -> {
		final KeyCode code = evt.getCode();
		if(!code.isFunctionKey() && !code.isMediaKey() && !code.isModifierKey() && !code.isArrowKey() && !code.isNavigationKey() && evt.getSource() instanceof Node) {
			((Node) evt.getSource()).fireEvent(new ActionEvent(evt.getSource(), null));
		}
	};

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeout() {
		return timeout;
	}

	private final EventHandler<ActionEvent> event;

	/**
	 * Creates the interaction.
	 */
	public TextChanged() {
		super();
		initStateMachine();
		event = evt -> onTextChanged((TextInputControl) evt.getSource());
	}

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
			}
		};

		new JfxTextChangedTransition(changed, changed) {
			@Override
			public boolean isGuardRespected() {
				return super.isGuardRespected() && widget == TextChanged.this.widget;
			}
		};

		new TimeoutTransition(changed, ended, SUPPLY_TIMEOUT);
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		node.removeEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
		node.removeEventHandler(ActionEvent.ACTION, event);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		node.removeEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
		node.addEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
		node.addEventHandler(ActionEvent.ACTION, event);
	}
}
