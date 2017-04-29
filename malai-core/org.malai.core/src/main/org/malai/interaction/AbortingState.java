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

import org.malai.stateMachine.TargetableState;

/**
 * This state defines an aborting state that aborts the interaction.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class AbortingState extends StateImpl implements TargetableState {
	public AbortingState(final String name) {
		super(name);
	}


	@Override
	public void onIngoing() {
		stateMachine.onAborting();
	}
}
