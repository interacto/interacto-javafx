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
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import org.malai.interaction.AbortingState;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;

/**
 * An abortable DnD interaction.
 * @author Arnaud Blouin
 */
public class AbortableDnD extends DnD {
	/**
	 * Creates the interaction.
	 */
	public AbortableDnD() {
		super();
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		super.initStateMachine();

		AbortingState aborted = new AbortingState("aborted"); //$NON-NLS-1$
		addState(aborted);

		new EscapeKeyPressureTransition(pressed, aborted);
		new EscapeKeyPressureTransition(dragged, aborted);
		pressed.getTransitions().stream().filter(t -> t instanceof ReleaseTransition).findFirst().ifPresent(t -> pressed.getTransitions().remove(t));
		new Release4DnD(pressed, aborted);
	}


	@Override
	public void registerToNodes(Collection<Node> widgets) {
		super.registerToNodes(widgets);
		widgets.forEach(widget -> {
			widget.addEventHandler(KeyEvent.KEY_PRESSED, evt -> onKeyPressure(evt, 0));
			widget.addEventHandler(KeyEvent.KEY_RELEASED, evt -> onKeyRelease(evt, 0));
		});
	}

	@Override
	public void registerToWindows(Collection<Window> windows) {
		super.registerToWindows(windows);
		windows.forEach(widget -> {
			widget.addEventHandler(KeyEvent.KEY_PRESSED, evt -> onKeyPressure(evt, 0));
			widget.addEventHandler(KeyEvent.KEY_RELEASED, evt -> onKeyRelease(evt, 0));
		});
	}
}
