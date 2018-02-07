/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.function.LongSupplier;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;

/**
 * A KeyTyped interaction occurs when several keys are typed. It stops when the defined timeout expired.
 * @author Arnaud BLOUIN
 */
public class KeysTyped extends MultiKeyInteraction {
	/** The time gap to wait after the latest key event. */
	private static long timeGap = 1000L;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap to wait after the latest key event.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap to wait after the latest key event.
	 * @param timeGapBetweenClicks The time gap to wait after the latest key event. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	@Override
	public void reinit() {
		super.reinit();
		if(stillProcessingEvents != null) {
			stillProcessingEvents.clear();
		}
	}

	/**
	 * Creates the interaction.
	 */
	public KeysTyped() {
		super();
		initStateMachine();
	}

	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed");
		final TerminalState ended = new TerminalState("ended");

		addState(pressed);
		addState(ended);

		new MultiKeyInteractionKeyPressedTransition(initState, pressed);
		new MultiKeyInteractionKeyPressedTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid == KeysTyped.this.getLastHIDUsed();
			}
		};
		new TimeoutTransition(pressed, ended, SUPPLY_TIME_GAP);
	}
}
