/*
 * This abstract interaction defines an interaction used by pointing devices. <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * Malai is distributed without any warranty; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.PressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import javafx.event.EventTarget;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;

/**
 * @author Arnaud BLOUIN
 */
public abstract class PointInteraction extends JfxInteractionImpl {
	/** The pressed position. */
	protected Point3D point;

	/** The button used for the pressure. */
	protected MouseButton button;

	/** The object picked at the pressed position. */
	protected EventTarget target;
	
	protected boolean altPressed;
	
	protected boolean ctrlPressed;
	
	protected boolean shiftPressed;
	
	/**
	 * Creates the interaction.
	 */
	public PointInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();
		point = null;
		target = null;
		altPressed = false;
		ctrlPressed = false;
		shiftPressed = false;
	}

	/**
	 * @return True: the alt key is pressed.
	 */
	public boolean isAltPressed() {
		return altPressed;
	}
	
	/**
	 * @return True: the control key is pressed.
	 */

	public boolean isCtrlPressed() {
		return ctrlPressed;
	}
	
	/**
	 * @return True: the shift key is pressed.
	 */

	public boolean isShiftPressed() {
		return shiftPressed;
	}

	/**
	 * @return The pressed position.
	 */
	public Point3D getPoint() {
		return point;
	}

	/**
	 * @return The button used for the pressure.
	 */
	public MouseButton getButton() {
		return button;
	}

	/**
	 * @return The object picked at the pressed position.
	 */
	public EventTarget getTarget() {
		return target;
	}

	class PointPressureTransition extends PressureTransition {
		PointPressureTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			PointInteraction.this.point = new Point3D(this.event.getX(), this.event.getY(), this.event.getZ());
			PointInteraction.this.button = this.event.getButton();
			PointInteraction.this.target = this.event.getTarget();
			PointInteraction.this.altPressed = this.event.isAltDown();
			PointInteraction.this.shiftPressed = this.event.isShiftDown();
			PointInteraction.this.ctrlPressed = this.event.isControlDown();
//			PointInteraction.this.setLastHIDUsed(this.hid);
		}
	}
}
