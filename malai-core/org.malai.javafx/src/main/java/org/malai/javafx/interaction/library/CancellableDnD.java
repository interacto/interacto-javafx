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

import org.malai.interaction.CancellingState;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.ReleaseTransition;

/**
 * A cancellable DnD interaction.
 * @author Arnaud Blouin
 */
public class CancellableDnD extends DnD {
	/**
	 * Creates the interaction.
	 * @param updateSrcOnUpdate If true, the source point and object will take the latest end point and object
	 * at each update, just before these end point and object will be updated.
	 */
	public CancellableDnD(final boolean updateSrcOnUpdate) {
		super(updateSrcOnUpdate);
	}

	/**
	 * Creates the interaction.
	 */
	public CancellableDnD() {
		this(false);
	}

	@Override
	protected void initStateMachine() {
		super.initStateMachine();

		final CancellingState aborted = new CancellingState("cancelled");
		addState(aborted);

		new EscapeKeyPressureTransition(pressed, aborted);
		new EscapeKeyPressureTransition(dragged, aborted);
		pressed.getTransitions().stream().filter(t -> t instanceof ReleaseTransition).findFirst().ifPresent(t -> pressed.getTransitions().remove(t));
		new Release4DnD(pressed, aborted);
	}
}
