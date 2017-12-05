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

import java.util.function.LongSupplier;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.javafx.interaction.MoveTransition;
import org.malai.javafx.interaction.ReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This interaction defines a double click. Between the two clicks the mouse must not
 * moved. A delay of 1 second at max is permitted between the two clicks.
 * @author Arnaud BLOUIN
 */
public class DoubleClick extends PointInteraction {
	/** The time gap between the two clicks. */
	private static long timeGap = 1000L;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	private final EventHandler<MouseEvent> press;
	private final EventHandler<MouseEvent> release;
	private final EventHandler<MouseEvent> drag;

	/**
	 * @return The time gap between the two clicks.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap between the two clicks.
	 * @param timeGapBetweenClicks The time gap between the two clicks. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	/**
	 * Creates the interaction.
	 */
	public DoubleClick() {
		super();
		initStateMachine();
		press = evt -> onPressure(evt, 0);
		release = evt -> onRelease(evt, 0);
		drag = evt -> onDrag(evt, 0);
	}

	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed1 = new IntermediaryState("pressed1");
		final AbortingState aborted = new AbortingState("aborted");
		final IntermediaryState released1 = new IntermediaryState("released1");
		final IntermediaryState pressed2 = new IntermediaryState("pressed2");
		final TerminalState released2 = new TerminalState("released2");

		addState(pressed1);
		addState(aborted);
		addState(released1);
		addState(pressed2);
		addState(released2);

		new PointPressureTransition(initState, pressed1);
		new MoveTransition(pressed1, aborted);
		new ReleaseTransition4DoubleClick(pressed1, released1);
		new MoveTransition(released1, aborted);
		new TimeoutTransition(released1, aborted, SUPPLY_TIME_GAP);
		new PointPressureTransition(released1, pressed2) {
			@Override
			public boolean isGuardRespected() {
				return hid == DoubleClick.this.getLastHIDUsed() && DoubleClick.this.getButton() == event.getButton();
			}

			@Override
			public void action() {
				super.action();
				DoubleClick.this.setOnMouseEventAction(event);
			}
		};
		new MoveTransition(pressed2, aborted);
		new ReleaseTransition4DoubleClick(pressed2, released2);
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		node.removeEventHandler(MouseEvent.MOUSE_PRESSED, press);
		node.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
		node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		node.removeEventHandler(MouseEvent.MOUSE_MOVED, drag);
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		window.removeEventHandler(MouseEvent.MOUSE_PRESSED, press);
		window.removeEventHandler(MouseEvent.MOUSE_RELEASED, release);
		window.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		window.removeEventHandler(MouseEvent.MOUSE_MOVED, drag);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, press);
		node.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		node.addEventHandler(MouseEvent.MOUSE_MOVED, drag);
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		window.addEventHandler(MouseEvent.MOUSE_PRESSED, press);
		window.addEventHandler(MouseEvent.MOUSE_RELEASED, release);
		window.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
		window.addEventHandler(MouseEvent.MOUSE_MOVED, drag);
	}


	class ReleaseTransition4DoubleClick extends ReleaseTransition {
		protected ReleaseTransition4DoubleClick(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public boolean isGuardRespected() {
			return hid == DoubleClick.this.getLastHIDUsed() && DoubleClick.this.getButton() == event.getButton();
		}

		@Override
		public void action() {
			super.action();
			DoubleClick.this.setOnMouseEventAction(event);
		}
	}

	private void setOnMouseEventAction(final MouseEvent event) {
		altPressed = event.isAltDown();
		shiftPressed = event.isShiftDown();
		ctrlPressed = event.isControlDown();
		metaPressed = event.isMetaDown();
	}
}
