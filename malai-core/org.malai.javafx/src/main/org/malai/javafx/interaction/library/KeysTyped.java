package org.malai.javafx.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;

/**
 * A KeyTyped interaction occurs when several keys are typed. It stops when the defined timeout
 * expired.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * Malai is distributed without any warranty; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public class KeysTyped extends MultiKeyInteraction {
	/** The timeout transition. Used to set the timeout value. */
	protected TimeoutTransition timeoutTransition;

	/**
	 * Creates the interaction.
	 * @since 0.2
	 */
	public KeysTyped() {
		super();
		initStateMachine();
	}

	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final TerminalState ended = new TerminalState("ended"); //$NON-NLS-1$

		addState(pressed);
		addState(ended);

		new MultiKeyInteractionKeyPressedTransition(initState, pressed);

		new MultiKeyInteractionKeyPressedTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid == KeysTyped.this.getLastHIDUsed();
			}
		};

		new MultiKeyReleaseTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid == KeysTyped.this.getLastHIDUsed();
			}
		};

		timeoutTransition = new TimeoutTransition(pressed, ended, 1000);
	}

	/**
	 * @return the current timeout.
	 * @since 0.2
	 */
	public int getTimeout() {
		return timeoutTransition.getTimeout();
	}

	/**
	 * @param timeout the timeout to set. Must be greater than 0.
	 * @since 0.2
	 */
	public void setTimeout(final int timeout) {
		if(timeout > 0)
			timeoutTransition.setTimeout(timeout);
	}
}
