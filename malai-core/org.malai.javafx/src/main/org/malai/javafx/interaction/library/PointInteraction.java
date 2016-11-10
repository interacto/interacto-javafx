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

import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.PressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * @author Arnaud BLOUIN
 */
public abstract class PointInteraction extends JfxInteractionImpl {
	/** The pressed position. */
	protected Optional<Point3D> srcPoint;

	/** The button used for the pressure. */
	protected Optional<MouseButton> button;

	/** The object picked at the pressed position. */
	protected Optional<Node> srcObject;
	
	protected boolean altPressed;
	
	protected boolean ctrlPressed;
	
	protected boolean shiftPressed;

	protected boolean metaPressed;

	/**
	 * Creates the interaction.
	 */
	public PointInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();
		srcPoint = Optional.empty();
		srcObject = Optional.empty();
		button = Optional.empty();
		altPressed = false;
		ctrlPressed = false;
		shiftPressed = false;
		metaPressed = false;
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
	 * @return True: the meta key is pressed.
	 */

	public boolean isMetaPressed() {
		return metaPressed;
	}

	/**
	 * @return The pressed position.
	 */
	public Optional<Point3D> getSrcPoint() {
		return srcPoint;
	}

	/**
	 * @return The button used for the pressure.
	 */
	public Optional<MouseButton> getButton() {
		return button;
	}

	/**
	 * @return The object picked at the pressed position.
	 */
	public Optional<Node> getSrcObject() {
		return srcObject;
	}

	class PointPressureTransition extends PressureTransition {
		PointPressureTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			PointInteraction.this.srcPoint = Optional.of(new Point3D(this.event.getX(), this.event.getY(), this.event.getZ()));
			PointInteraction.this.button = Optional.of(this.event.getButton());
			PointInteraction.this.srcObject = Optional.of(this.event.getPickResult().getIntersectedNode());
			PointInteraction.this.altPressed = this.event.isAltDown();
			PointInteraction.this.shiftPressed = this.event.isShiftDown();
			PointInteraction.this.ctrlPressed = this.event.isControlDown();
			PointInteraction.this.metaPressed = this.event.isMetaDown();
//			PointInteraction.this.setLastHIDUsed(this.hid);
		}
	}
}
