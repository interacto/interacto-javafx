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
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.DragTransition;
import org.malai.javafx.interaction.ReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A JavaFX Drag-And-Drop interaction (press[1]-drag[1-*]-release[1]).
 * @author Arnaud BLOUIN
 */
public class DnD extends PointInteraction {
	/** The ending srcPoint of the dnd. */
	protected Optional<Point3D> endPt;

	/** The object picked at the beginning of the dnd. */
	protected Optional<Node> endObject;

	protected IntermediaryState pressed;

	protected IntermediaryState dragged;

	protected TerminalState released;


	/**
	 * Creates the interaction.
	 */
	public DnD() {
		super();
		initStateMachine();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		dragged = new IntermediaryState("dragged"); //$NON-NLS-1$
		released = new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(dragged);
		addState(released);

		new PointPressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				setLastHIDUsed(this.hid);
				DnD.this.endPt = Optional.of(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				DnD.this.endObject = DnD.this.srcObject;
			}
		};

		new Move4DnD(pressed, dragged);
		new Move4DnD(dragged, dragged);
		new Release4DnD(dragged, released);
		new Release4DnD(pressed, released);
	}


	@Override
	public void reinit() {
		super.reinit();
		endPt = Optional.empty();
		endObject = Optional.empty();
	}


	@Override
	public void registerToNodes(final Collection<Node> widgets) {
		widgets.forEach(widget -> {
			widget.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_DRAGGED, evt -> onDrag(evt, 0));
		});
	}

	@Override
	public void registerToWindows(final Collection<Window> windows) {
		windows.forEach(widget -> {
			widget.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> onPressure(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_RELEASED, evt -> onRelease(evt, 0));
			widget.addEventHandler(MouseEvent.MOUSE_DRAGGED, evt -> onDrag(evt, 0));
		});
	}


	/**
	 * @return The ending srcPoint of the dnd.
	 */
	public Optional<Point3D> getEndPt() {
		return endPt;
	}


	/**
	 * @return The object picked at the end of the dnd.
	 */
	public Optional<Node> getEndObjet() {
		return endObject;
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the release event.
	 */
	class Release4DnD extends ReleaseTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public Release4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public boolean isGuardRespected() {
			return DnD.this.button.isPresent() && DnD.this.button.get() == this.event.getButton() && DnD.this.getLastHIDUsed() == this.hid;
		}

		@Override
		public void action() {
			super.action();
			DnD.this.altPressed = this.event.isAltDown();
			DnD.this.shiftPressed = this.event.isShiftDown();
			DnD.this.ctrlPressed = this.event.isControlDown();
			DnD.this.metaPressed = this.event.isMetaDown();
		}
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the move events.
	 */
	class Move4DnD extends DragTransition {
		/**
		 * Creates the transition.
		 * @param inputState The input state of the transition.
		 * @param outputState The output state of the transition.
		 */
		public Move4DnD(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			super.action();
			DnD.this.endPt = Optional.of(new Point3D(event.getX(), event.getY(), event.getZ()));
			DnD.this.endObject = Optional.ofNullable(this.event.getPickResult().getIntersectedNode());
			DnD.this.altPressed = this.event.isAltDown();
			DnD.this.shiftPressed = this.event.isShiftDown();
			DnD.this.ctrlPressed = this.event.isControlDown();
			DnD.this.metaPressed = this.event.isMetaDown();
		}

		@Override
		public boolean isGuardRespected() {
			return DnD.this.getLastHIDUsed() == this.hid;
		}
	}
}
