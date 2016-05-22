/*
 * This interaction permits to mouse press with key pressures (eg modifiers).<br>
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
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.List;

import org.malai.interaction.TerminalState;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * @author Arnaud BLOUIN
 */
public class Press extends PointInteraction {
	/**
	 * Creates the interaction.
	 */
	public Press() {
		super();
		initStateMachine();
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState end = new TerminalState("ended"); //$NON-NLS-1$
		addState(end);
		new PointPressureTransition(initState, end);
	}

	@Override
	public void registerToNodes(final List<Node> widgets) {
		widgets.forEach(widget -> {
			widget.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
		});
	}
}
