/*
 * This file is part of Malai
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
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
	/**
	 * Creates the interaction.
	 */
	public DoubleClick() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
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
		new PointPressureTransition(released1, pressed2) {
			@Override
			public boolean isGuardRespected() {
				return hid == DoubleClick.this.getLastHIDUsed() && DoubleClick.this.getButton().orElse(MouseButton.NONE) == event.getButton();
			}

			@Override
			public void action() {
				super.action();
				DoubleClick.this.altPressed = event.isAltDown();
				DoubleClick.this.shiftPressed = event.isShiftDown();
				DoubleClick.this.ctrlPressed = event.isControlDown();
				DoubleClick.this.metaPressed = event.isMetaDown();
			}
		};
		new MoveTransition(pressed2, aborted);
		new ReleaseTransition4DoubleClick(pressed2, released2);
	}

	@Override
	public void registerToNodes(final List<Node> widgets) {
		widgets.forEach(widget -> {
			widget.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_DRAGGED, evt -> onDrag(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> onDrag(evt, 0));
		});
	}


	class ReleaseTransition4DoubleClick extends ReleaseTransition {
		protected ReleaseTransition4DoubleClick(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public boolean isGuardRespected() {
			return hid == DoubleClick.this.getLastHIDUsed() && DoubleClick.this.getButton().orElse(MouseButton.NONE) == event.getButton();
		}

		@Override
		public void action() {
			super.action();
			DoubleClick.this.altPressed = event.isAltDown();
			DoubleClick.this.shiftPressed = event.isShiftDown();
			DoubleClick.this.ctrlPressed = event.isControlDown();
			DoubleClick.this.metaPressed = event.isMetaDown();
		}
	}
}
