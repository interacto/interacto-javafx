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
package org.malai.interaction;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;
import org.malai.stateMachine.Transition;

/**
 * A transition links two states of a state machine if a given condition is respected.
 * Actions can be performed when executing the transition.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class TransitionImpl implements Transition {
	/** The source state. */
	protected final SourceableState inputState;

	/** The target state. */
	protected final TargetableState outputState;

	/** The ID of the HID that produced the transition. */
	protected int hid;


	/**
	 * Defines a transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The target state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 * @since 0.1
	 */
	public TransitionImpl(final SourceableState inputState, final TargetableState outputState) {
		if(inputState == null || outputState == null) throw new IllegalArgumentException();

		this.inputState = inputState;
		this.outputState = outputState;
		this.inputState.addTransition(this);
	}


	@Override
	public void action() {
		//
	}


	@Override
	public boolean isGuardRespected() {
		return true;
	}


	@Override
	public SourceableState getInputState() {
		return inputState;
	}


	@Override
	public TargetableState getOutputState() {
		return outputState;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + inputState.getName() + ">>" + outputState.getName() + ']'; //$NON-NLS-1$
	}


	@Override
	public int getHid() {
		return hid;
	}


	@Override
	public void setHid(final int hid) {
		this.hid = hid;
	}
}
