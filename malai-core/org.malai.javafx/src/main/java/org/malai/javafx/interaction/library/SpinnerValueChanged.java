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
import java.util.function.LongSupplier;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.javafx.interaction.JfxSpinnerValueChangedTransition;

/**
 * An interaction dedicated to set values using a spinner.
 * @author Arnaud BLOUIN
 */
public class SpinnerValueChanged extends NodeInteraction<Spinner<?>> {
	/** The time gap between the two spinner events. */
	private static long timeGap = 300;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap between the two spinner events.
	 * @param timeGapBetweenClicks The time gap between the two spinner events. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	/**
	 * Creates the interaction.
	 */
	public SpinnerValueChanged() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState changed = new IntermediaryState("valueChanged");
		final TerminalState ended = new TerminalState("ended");

		addState(changed);
		addState(ended);

		new JfxSpinnerValueChangedTransition(initState, changed) {
			@Override
			public void action() {
				super.action();
				SpinnerValueChanged.this.widget = this.widget;
			}
		};

		new JfxSpinnerValueChangedTransition(changed, changed);

		new TimeoutTransition(changed, ended, SUPPLY_TIME_GAP);
	}

	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		super.registerToNodes(widgets);
		widgets.stream().filter(w -> w instanceof Spinner<?>).forEach(w ->
			w.addEventHandler(ActionEvent.ACTION, evt -> onJfxSpinnerValueChanged((Spinner<?>) w)));
	}
}
