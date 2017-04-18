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

import java.util.ArrayList;
import java.util.List;

import org.malai.stateMachine.State;
import org.malai.stateMachine.StateMachine;
import org.malai.stateMachine.Transition;

/**
 * A state is a component of a state machine.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public abstract class StateImpl implements State {
	/** The name of the state. */
	protected final String name;

	/** The list of the transitions that leave the state. */
	protected final List<Transition> transitions;

	/** The state machine that contains the state. */
	protected Interaction stateMachine;


	/**
	 * Creates the state.
	 * @param name The name of the state.
	 * @throws IllegalArgumentException If the given name is null.
	 * @since 0.1
	 */
	public StateImpl(final String name) {
		super();

		if(name==null)
			throw new IllegalArgumentException();

		this.name 	 = name;
		stateMachine = null;
		transitions  = new ArrayList<>();
	}


	@Override
	public void setStateMachine(final StateMachine sm) {
		if(sm instanceof Interaction)
			stateMachine = (Interaction)sm;
	}


	@Override
	public Interaction getStateMachine() {
		return stateMachine;
	}


	@Override
	public void addTransition(final Transition trans) {
		if(trans!=null)
			transitions.add(trans);
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public List<Transition> getTransitions() {
		return transitions;
	}


	@Override
	public Transition getTransition(final int i) {
		return i<0 || i>=transitions.size() ? null : transitions.get(i);
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append(getClass().getCanonicalName()).append('[').append(name).append(',').append(' ');

		for(final Transition t : transitions)
			sb.append(t).append(',').append(' ');

		sb.append(']');

		return sb.toString();
	}
}
