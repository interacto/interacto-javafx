package org.malai.javafx.interaction;

import org.malai.interaction.TransitionImpl;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a scroll event.<br>
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
public class ScrollTransition extends TransitionImpl {
	protected double deltaX;
	protected double deltaY;
	protected double multiplierX;
	protected double multiplierY;
	protected int nbTouchPoints;

	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public ScrollTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return the horizontal scroll amount.
	 */
	public double getDeltaX() {
		return deltaX;
	}

	/**
	 * @param deltaX the horizontal scroll amount.
	 */
	public void setDeltaX(final double deltaX) {
		this.deltaX = deltaX;
	}

	/**
	 * @return the deltaY The vertical scroll amount.
	 */
	public double getDeltaY() {
		return deltaY;
	}

	/**
	 * @param deltaY The vertical scroll amount.
	 */
	public void setDeltaY(final double deltaY) {
		this.deltaY = deltaY;
	}

	/**
	 * @return The multiplier used to convert mouse wheel rotation units to pixels
	 */
	public double getMultiplierX() {
		return multiplierX;
	}

	/**
	 * @param multiplierX The multiplier used to convert mouse wheel rotation units to pixels
	 */
	public void setMultiplierX(final double multiplierX) {
		this.multiplierX = multiplierX;
	}

	/**
	 * @return the multiplierY The multiplier used to convert mouse wheel rotation units to pixels
	 */
	public double getMultiplierY() {
		return multiplierY;
	}

	/**
	 * @param multiplierY The multiplier used to convert mouse wheel rotation units to pixels
	 */
	public void setMultiplierY(final double multiplierY) {
		this.multiplierY = multiplierY;
	}

	/**
	 * @return the nbTouchPoints The number of touch points that caused this event.
	 */
	public int getNbTouchPoints() {
		return nbTouchPoints;
	}

	/**
	 * @param nbTouchPoints The number of touch points that caused this event.
	 */
	public void setNbTouchPoints(final int nbTouchPoints) {
		this.nbTouchPoints = nbTouchPoints;
	}
}
