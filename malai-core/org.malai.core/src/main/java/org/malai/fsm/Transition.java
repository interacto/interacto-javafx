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

import java.util.Optional;
import java.util.Set;

public abstract class Transition<E> {
	protected final OutputState<E> src;
	protected final InputState<E> tgt;

	protected Transition(final OutputState<E> srcState, final InputState<E> tgtState) {
		super();

		if(srcState == null || tgtState == null) {
			throw new IllegalArgumentException("States cannot be null");
		}

		src = srcState;
		tgt = tgtState;

		src.addTransition(this);
	}

	public Optional<InputState<E>> execute(final E event) throws CancelFSMException {
		if(accept(event) && isGuardOK(event)) {
			src.getFSM().stopCurrentTimeout();
			action(event);
			src.exit();
			tgt.enter();
			return Optional.of(tgt);
		}

		return Optional.empty();
	}

	protected void action(final E event) {
	}

	protected abstract boolean accept(final E event);

	protected abstract boolean isGuardOK(final E event);

	public abstract Set<Object> getAcceptedEvents();

	/**
	 * Clean the transition when not used anymore.
	 */
	public void uninstall() {
	}
}
