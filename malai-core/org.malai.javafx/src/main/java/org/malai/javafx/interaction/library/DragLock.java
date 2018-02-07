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

import javafx.geometry.Point3D;
import org.malai.interaction.CancellingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.TerminalState;
import org.malai.interaction.TimeoutTransition;
import org.malai.javafx.interaction.MoveTransition;
import org.malai.javafx.interaction.ReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

public class DragLock extends DnD {
	public DragLock() {
		this(false);
	}

	public DragLock(final boolean updateSrcOnUpdate) {
		super(updateSrcOnUpdate);
	}

	@Override
	protected void initStateMachine() {
		final CancellingState cancelled = new CancellingState("cancelled");
		pressed = new IntermediaryState("pressed1a");
		final IntermediaryState released1a = new IntermediaryState("released1a");
		final IntermediaryState pressed1b = new IntermediaryState("released1b");
		final IntermediaryState released1b = new IntermediaryState("released1b");
		dragged = new IntermediaryState("moved");

		new PointPressureTransition(initState, pressed){
			@Override
			public void action() {
				super.action();
				setLastHIDUsed(this.hid);
				DragLock.this.endLocalPt.set(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
				DragLock.this.endScenePt.set(new Point3D(this.event.getSceneX(), this.event.getSceneY(), this.event.getZ()));
				DragLock.this.endObject.set(DragLock.this.srcObject.get());
			}
		};
		new MoveTransition(pressed, cancelled);
		new ReleaseTransition(pressed, released1a);
		new MoveTransition(released1a, cancelled);
		new TimeoutTransition(released1a, cancelled, () -> 1000L);
		new PointPressureTransition(released1a, pressed1b);
		new MoveTransition(pressed1b, cancelled);
		new ReleaseTransition(pressed1b, released1b);

		new Move4DragLock(released1b, dragged);
		new Move4DragLock(dragged, dragged);

		final IntermediaryState pressed2a = new IntermediaryState("pressed2a");
		final IntermediaryState released2a = new IntermediaryState("released2a");
		final IntermediaryState pressed2b = new IntermediaryState("pressed2b");
		released = new TerminalState("released2b");

		new PointPressureTransition(dragged, pressed2a);
		new MoveTransition(pressed2a, dragged);
		new ReleaseTransition(pressed2a, released2a);
		new MoveTransition(released2a, dragged);
		new TimeoutTransition(released2a, dragged, () -> 1000L);
		new PointPressureTransition(released2a, pressed2b);
		new MoveTransition(pressed2b, dragged);
		new ReleaseTransition(pressed2b, released);

		addState(cancelled);
		addState(pressed);
		addState(pressed1b);
		addState(pressed2a);
		addState(pressed2b);
		addState(dragged);
		addState(released);
		addState(released1a);
		addState(released1b);
		addState(released2a);
	}

	class Move4DragLock extends MoveTransition {
		public Move4DragLock(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			super.action();

			if(updateSrcOnUpdate) {
				DragLock.this.srcLocalPoint.set(endLocalPt.get());
				DragLock.this.srcScenePoint.set(endScenePt.get());
				DragLock.this.srcObject.set(DragLock.this.endObject.get());
			}

			DragLock.this.endLocalPt.set(new Point3D(event.getX(), event.getY(), event.getZ()));
			DragLock.this.endScenePt.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
			DragLock.this.endObject.set(this.event.getPickResult().getIntersectedNode());
			DragLock.this.altPressed = this.event.isAltDown();
			DragLock.this.shiftPressed = this.event.isShiftDown();
			DragLock.this.ctrlPressed = this.event.isControlDown();
			DragLock.this.metaPressed = this.event.isMetaDown();
		}

		@Override
		public boolean isGuardRespected() {
			return DragLock.this.getLastHIDUsed() == this.hid;
		}
	}
}
