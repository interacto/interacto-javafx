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
package org.malai.fsm;

abstract class StateImpl<E> implements State<E> {
	protected final FSM<E> fsm;
	protected final String name;

	protected StateImpl(final FSM<E> stateMachine, final String stateName) {
		super();
		fsm = stateMachine;
		name = stateName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FSM<E> getFSM() {
		return fsm;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{name='" + name + "\'}";
	}
}
