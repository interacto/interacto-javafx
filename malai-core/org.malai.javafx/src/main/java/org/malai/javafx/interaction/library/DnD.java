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

import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
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
	/** The ending local point of the dnd. */
	protected final ObjectProperty<Point3D> endLocalPt;
	/** The ending scene point of the dnd. */
	protected final ObjectProperty<Point3D> endScenePt;

	/** The object picked at the beginning of the dnd. */
	protected final ObjectProperty<Node> endObject;

	protected IntermediaryState pressed;

	protected IntermediaryState dragged;

	protected TerminalState released;

	protected final boolean updateSrcOnUpdate;


	/**
	 * Creates the interaction.
	 * @param updateSrcOnUpdate If true, the source point and object will take the latest end point and object
	 * at each update, just before these end point and object will be updated.
	 */
	public DnD(final boolean updateSrcOnUpdate) {
		super();
		initStateMachine();
		endLocalPt = new SimpleObjectProperty<>();
		endScenePt = new SimpleObjectProperty<>();
		endObject = new SimpleObjectProperty<>();
		this.updateSrcOnUpdate = updateSrcOnUpdate;
	}

	/**
	 * Creates the interaction.
	 */
	public DnD() {
		this(false);
	}


	@Override
	protected void initStateMachine() {
		pressed = new IntermediaryState("pressed");
		dragged = new IntermediaryState("dragged");
		released = new TerminalState("released");

		addState(pressed);
		addState(dragged);
		addState(released);

		new PointPressureTransition(initState, pressed) {
			@Override
			public void action() {
				super.action();
				setLastHIDUsed(this.hid);
				DnD.this.endLocalPt.set(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				DnD.this.endScenePt.set(new Point3D(this.event.getSceneX(), this.event.getSceneY(), this.event.getZ()));
				DnD.this.endObject.set(DnD.this.srcObject.get());
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
		endLocalPt.setValue(null);
		endScenePt.setValue(null);
		endObject.set(null);
	}

	/**
	 * @return The ending local point of the dnd.
	 */
	public Point3D getEndLocalPt() {
		return endLocalPt.get();
	}

	/**
	 * @return The ending scene point of the dnd.
	 */
	public Point3D getEndScenePt() {
		return endScenePt.get();
	}


	/**
	 * @return The object picked at the end of the dnd.
	 */
	public Optional<Node> getEndObjet() {
		return Optional.ofNullable(endObject.get());
	}

	public ReadOnlyObjectProperty<Point3D> endLocalPtProperty() {
		return endLocalPt;
	}

	public ReadOnlyObjectProperty<Point3D> endScenePtProperty() {
		return endScenePt;
	}

	public Optional<Node> getEndObject() {
		return Optional.ofNullable(endObject.get());
	}

	public ReadOnlyObjectProperty<Node> endObjectProperty() {
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
			return DnD.this.button == this.event.getButton() && DnD.this.getLastHIDUsed() == this.hid;
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

			if(updateSrcOnUpdate) {
				DnD.this.srcLocalPoint.set(endLocalPt.get());
				DnD.this.srcScenePoint.set(endScenePt.get());
				DnD.this.srcObject.set(DnD.this.endObject.get());
			}

			DnD.this.endLocalPt.set(new Point3D(event.getX(), event.getY(), event.getZ()));
			DnD.this.endScenePt.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
			DnD.this.endObject.set(this.event.getPickResult().getIntersectedNode());
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
