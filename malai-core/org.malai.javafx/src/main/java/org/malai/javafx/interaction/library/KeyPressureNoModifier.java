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

import org.malai.interaction.TerminalState;

/**
 * A KeyPressure interaction occurs when a key (but not a modifier one) of a keyboard is pressed.
 * @author Arnaud BLOUIN
 */
public class KeyPressureNoModifier extends SingleKeyInteraction {
	/**
	 * Creates the interaction.
	 */
	public KeyPressureNoModifier() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		final TerminalState pressed = new TerminalState("pressed");

		addState(pressed);

		new SingleKeyInteractionKeyPressedTransition(initState, pressed) {
			@Override
			public boolean isGuardRespected() {
				return !event.getCode().isModifierKey() && !event.getCode().isFunctionKey() && !event.getCode().isMediaKey();
			}
		};
	}
}
