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
package org.malai.javafx.interaction.library;

import java.util.Optional;
import org.malai.javafx.interaction.JfxInteractionImpl;
import org.malai.javafx.interaction.KeyPressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * An abstract interaction for defining JavaFX interactions based on key events.
 * @author Arnaud BLOUIN
 */
public abstract class KeyInteraction extends JfxInteractionImpl {
	/** The object that produced the interaction. */
	protected Optional<Object> object;

	/**
	 * Creates the interaction.
	 */
	public KeyInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();
		object = Optional.empty();
	}

	/**
	 * @return The object that produced the interaction.
	 */
	public Optional<Object> getObject() {
		return object;
	}

	/**
	 * @param object The object that produced the interaction.
	 */
	protected void setObject(final Object object) {
		this.object = Optional.ofNullable(object);
	}


	/**
	 * Defines a transition modifying the key attribute of the interaction.
	 */
	public class KeyInteractionKeyPressedTransition extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The srcObject state of the transition.
		 */
		public KeyInteractionKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			KeyInteraction.this.setObject(event.getSource());
			KeyInteraction.this.setLastHIDUsed(this.hid);
		}
	}
}
