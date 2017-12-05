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

import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.JfxWindowClosedTransition;

/**
 * A JavaFX interaction that uses a single menu item.
 * @author Arnaud BLOUIN
 */
public class WindowClosed extends JfxInteractionImpl {
	private final EventHandler<WindowEvent> winClose = evt -> onWindowClosed(evt);

	protected WindowEvent event;

	/**
	 * Creates the interaction.
	 */
	public WindowClosed() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		final TerminalState closed = new TerminalState("closed");

		addState(closed);

		new JfxWindowClosedTransition(initState, closed) {
			@Override
			public void action() {
				super.action();
				WindowClosed.this.event = this.event;
			}
		};
	}

	@Override
	public void reinit() {
		super.reinit();
		event = null;
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		window.removeEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, winClose);
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		window.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, winClose);
	}
}
