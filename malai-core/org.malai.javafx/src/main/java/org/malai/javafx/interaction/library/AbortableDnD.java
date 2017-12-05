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
	private final EventHandler<KeyEvent> keyPress;
	private final EventHandler<KeyEvent> keyRelease;

	/**
	 * Creates the interaction.
	 * @param updateSrcOnUpdate If true, the source point and object will take the latest end point and object
	 * at each update, just before these end point and object will be updated.
	 */
	public AbortableDnD(final boolean updateSrcOnUpdate) {
		super(updateSrcOnUpdate);
		keyPress = evt -> onKeyPressure(evt, 0);
		keyRelease = evt -> onKeyRelease(evt, 0);
	}

	/**
	 * Creates the interaction.
	 */
	public AbortableDnD() {
		this(false);
	}

	@Override
	protected void initStateMachine() {
		super.initStateMachine();

		final AbortingState aborted = new AbortingState("aborted");
		addState(aborted);

		new EscapeKeyPressureTransition(pressed, aborted);
		new EscapeKeyPressureTransition(dragged, aborted);
		pressed.getTransitions().stream().filter(t -> t instanceof ReleaseTransition).findFirst().ifPresent(t -> pressed.getTransitions().remove(t));
		new Release4DnD(pressed, aborted);
	}

	@Override
	public void onNewNodeRegistered(final Node node) {
		super.onNewNodeRegistered(node);
		node.addEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		node.addEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}

	@Override
	public void onNewWindowRegistered(final Window window) {
		super.onNewWindowRegistered(window);
		window.addEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		window.addEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		super.onNodeUnregistered(node);
		node.removeEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		node.removeEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		super.onWindowUnregistered(window);
		window.removeEventHandler(KeyEvent.KEY_PRESSED, keyPress);
		window.removeEventHandler(KeyEvent.KEY_RELEASED, keyRelease);
	}
}
