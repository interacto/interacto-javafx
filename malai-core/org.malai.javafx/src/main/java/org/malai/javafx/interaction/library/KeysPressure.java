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

/**
 * This interaction permits to define combo a key pressed that can be used to define shortcuts, etc.
 * @author Arnaud BLOUIN
 */
public class KeysPressure extends MultiKeyInteraction {
	/**
	 * Creates the interaction.
	 */
	public KeysPressure() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed");
		final TerminalState end = new TerminalState("ended");

		addState(pressed);
		addState(end);

		new MultiKeyInteractionKeyPressedTransition(initState, pressed);
		new MultiKeyInteractionKeyPressedTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid == KeysPressure.this.getLastHIDUsed();
			}
		};

		// The interaction stops once one of the key pressed is released. The other key pressed
		// events will be recycled.
		new MultiKeyReleaseTransition(pressed, end) {
			@Override
			public boolean isGuardRespected() {
				return this.hid == KeysPressure.this.getLastHIDUsed() && KeysPressure.this.keyCodes.contains(this.event.getCode());
			}
		};
	}
}
