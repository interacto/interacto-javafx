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
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.malai.interaction.TerminalState;

/**
 * This interaction permits to mouse press with key pressures (eg modifiers).
 * @author Arnaud BLOUIN
 */
public class Press extends PointInteraction {
	private final EventHandler<MouseEvent> pressure;
	private final EventHandler<MouseEvent> release;
	/**
	 * Creates the interaction.
	 */
	public Press() {
		super();
		initStateMachine();
		pressure = evt -> onPressure(evt, 0);
		release = evt -> onRelease(evt, 0);
	}

	@Override
	protected void initStateMachine() {
		final TerminalState end = new TerminalState("ended");
		addState(end);
		new PointPressureTransition(initState, end);
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		node.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		node.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		window.removeEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		window.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		window.addEventHandler(MouseEvent.MOUSE_PRESSED, pressure);
		window.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
	}
}
