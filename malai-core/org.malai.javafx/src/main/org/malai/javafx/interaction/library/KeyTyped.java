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

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.KeyReleaseTransition;

/**
 * A KeyTyped interaction occurs when a key of a keyboard is pressed and then released.
 * @author Arnaud BLOUIN
 */
public class KeyTyped extends SingleKeyInteraction {
	/**
	 * Creates the interaction.
	 */
	public KeyTyped() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final TerminalState released = new TerminalState("released"); //$NON-NLS-1$
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$

		addState(pressed);
		addState(released);

		new SingleKeyInteractionKeyPressedTransition(initState, pressed);
		new KeyReleaseTransition(pressed, released) {
			@Override
			public boolean isGuardRespected() {
				return KeyTyped.this.keyCode.orElse(null) == this.event.getCode() && KeyTyped.this.getLastHIDUsed() == this.hid;
			}
		};
	}
}
