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
package org.malai.javafx.interaction2.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for text input controls.
 * @author Arnaud BLOUIN
 */
public class TextInputChanged extends JfxInteraction<TextInputChangedFSM, TextInputControl> {
	private static final EventHandler<? super KeyEvent> HANDLER_KEY_ACTION = evt -> {
		final KeyCode code = evt.getCode();
		if(!code.isFunctionKey() && !code.isMediaKey() && !code.isModifierKey() && !code.isArrowKey() && !code.isNavigationKey() && evt.getSource() instanceof
			Node) {
			((Node) evt.getSource()).fireEvent(new ActionEvent(evt.getSource(), null));
		}
	};

	private final TextInputChangedFSM.TextInputChangedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public TextInputChanged() {
		super(new TextInputChangedFSM());

		handler = new TextInputChangedFSM.TextInputChangedFSMHandler() {
			@Override
			public void initToChangedHandler(final ActionEvent event) {
				if(event.getSource() instanceof TextInputControl) {
					widget = (TextInputControl) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				TextInputChanged.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof TextInputControl) {
			registerActionHandler(node);
			node.removeEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
			node.addEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof TextInputControl) {
			unregisterActionHandler(node);
			node.removeEventHandler(KeyEvent.KEY_PRESSED, HANDLER_KEY_ACTION);
		}
	}
}
