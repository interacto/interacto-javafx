package org.malai.javafx.interaction;

import javafx.scene.input.MouseButton;
import javafx.scene.input.PickResult;

import org.malai.interaction.TransitionImpl;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This abstract transition defines a model for transitions based on pointing device events.<br>
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
public abstract class PointingDeviceTransition extends TransitionImpl {
	/** The X-coordinate of the pointing device. */
	protected int x;

	/** The Y-coordinate of the pointing device. */
	protected int y;
	
	/** The Y-coordinate of the pointing device. */
	protected int z;
	
	protected int sceneX;
	
	protected int sceneY;
	
	protected int screenX;
	
	protected int screenY;

	/** The button used. */
	protected MouseButton button;

	/** The object picked at the position (x, y). */
	protected Object source;
	
	/** The picked node. */
	protected PickResult picking;


	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public PointingDeviceTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The button used.
	 */
	public MouseButton getButton() {
		return button;
	}

	/**
	 * @return The object picked at the position (x, y).
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * Sets the object picked at the position (x, y).
	 * @param source The object picked at the position (x, y).
	 */
	public void setSource(final Object source) {
		this.source = source;
	}

	/**
	 * Sets the button of the pointing device used by the event.
	 * @param button The button used.
	 */
	public void setButton(final MouseButton button) {
		this.button = button;
	}


	/**
	 * @return The X-coordinate of the pointing device.
	 */
	public int getX() {
		return x;
	}


	/**
	 * Sets the X-coordinate of the pointing device.
	 * @param x The X-coordinate of the pointing device.
	 */
	public void setX(final int x) {
		this.x = x;
	}


	/**
	 * @return The Y-coordinate of the pointing device.
	 */
	public int getY() {
		return y;
	}


	/**
	 * @param z the z to set
	 */
	public void setZ(final int z) {
		this.z = z;
	}


	/**
	 * @param sceneX the sceneX to set
	 */
	public void setSceneX(final int sceneX) {
		this.sceneX = sceneX;
	}
	


	/**
	 * @param sceneY the sceneY to set
	 */
	public void setSceneY(final int sceneY) {
		this.sceneY = sceneY;
	}
	


	/**
	 * @param screenX the screenX to set
	 */
	public void setScreenX(final int screenX) {
		this.screenX = screenX;
	}
	


	/**
	 * @param screenY the screenY to set
	 */
	public void setScreenY(final int screenY) {
		this.screenY = screenY;
	}
	


	/**
	 * @param picking the picking to set
	 */
	public void setPicking(final PickResult picking) {
		this.picking = picking;
	}
	


	/**
	 * Sets the Y-coordinate of the pointing device.
	 * @param y The Y-coordinate of the pointing device.
	 * @since 0.1
	 */
	public void setY(final int y) {
		this.y = y;
	}
}
