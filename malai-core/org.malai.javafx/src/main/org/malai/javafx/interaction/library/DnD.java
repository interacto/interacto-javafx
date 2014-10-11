package org.malai.javafx.interaction.library;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;

import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.javafx.interaction.DragTransition;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.PressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A JavaFX Drag-And-Drop interaction (press[1]-drag[1-*]-release[1]).<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-23<br>
 * @author Arnaud BLOUIN
 */
public class DnD extends JfxInteractionImpl {
	/** The starting point of the dnd. */
	protected Optional<Point2D.Double> startPt;

	/** The ending point of the dnd. */
	protected Optional<Point2D.Double> endPt;

	/** The button of the device used to performed the dnd. */
	protected MouseButton button;

	/** The object picked at the beginning of the dnd. */
	protected Optional<Object> startObject;

	/** The object picked at the end of the dnd. */
	protected Optional<Object> endObject;

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
		released= new TerminalState("released"); //$NON-NLS-1$

		addState(pressed);
		addState(dragged);
		addState(released);

		new PressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				setLastHIDUsed(this.hid);
				DnD.this.startPt 	 = Optional.of(new Point2D.Double(this.event.getX(), this.event.getY()));
				DnD.this.endPt	 	 = Optional.of(new Point2D.Double(this.event.getX(), this.event.getY()));
				DnD.this.button  	 = this.event.getButton();
				DnD.this.startObject = Optional.ofNullable(this.event.getSource());
				DnD.this.endObject 	 = DnD.this.startObject;
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
		startPt 	= Optional.empty();
		endPt 		= Optional.empty();
		button		= MouseButton.NONE;
		startObject = Optional.empty();
		endObject 	= Optional.empty();
	}

	
	@Override
	public void registerToWidgets(List<Node> widgets) {
		widgets.stream().forEach(widget -> {
			widget.setOnMousePressed(evt -> onPressure(evt, 0));
			widget.setOnMouseReleased(evt -> onRelease(evt, 0));
			widget.setOnMouseDragged(evt -> onDrag(evt, 0));
		});
	}
	

	/**
	 * @return The starting point of the dnd.
	 */
	public Optional<Point2D.Double> getStartPt() {
		return startPt;
	}


	/**
	 * @return The ending point of the dnd.
	 */
	public Optional<Point2D.Double> getEndPt() {
		return endPt;
	}


	/**
	 * @return The button of the device used to performed the dnd.
	 */
	public MouseButton getButton() {
		return button;
	}


	/**
 	 * @return The object picked at the beginning of the dnd.
	 */
	public Optional<Object> getStartObject() {
		return startObject;
	}


	/**
	 * @return The object picked at the end of the dnd.
	 */
	public Optional<Object> getEndObjet() {
		return endObject;
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the release event.
	 */
	public class Release4DnD extends ReleaseTransition {
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
			return DnD.this.button==this.event.getButton() && DnD.this.getLastHIDUsed()==this.hid;
		}
	}


	/**
	 * A transition dedicated for the DnD interaction. Corresponds to the move events.
	 */
	public class Move4DnD extends DragTransition {
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
			DnD.this.endPt.ifPresent(pt -> pt.setLocation(event.getX(), event.getY()));
			DnD.this.endObject = Optional.ofNullable(this.event.getPickResult().getIntersectedNode());
		}
		@Override
		public boolean isGuardRespected() {
			return DnD.this.getLastHIDUsed()==this.hid;
		}
	}
}
