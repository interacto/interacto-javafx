package org.malai.javafx.interaction.library;

import javafx.scene.input.KeyCode;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import java.util.Optional;

/**
 * This abstract interaction should be used to define JavaFX interactions based on keyboards.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * Malai is distributed without any warranty; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2014-09-22<br>
 * @author Arnaud BLOUIN
 */
public abstract class SingleKeyInteraction extends KeyInteraction {
	/** The key pressed. */
	protected Optional<String> key;

	/** The code of the key. */
	protected Optional<KeyCode> keyCode;

	/**
	 * Creates the interaction.
	 */
	public SingleKeyInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();
		key = Optional.empty();
		keyCode = Optional.empty();
	}

	/**
	 * @return The key code used by the interaction.
	 */
	public Optional<KeyCode> getKeyCode() {
		return keyCode;
	}

	/**
	 * @return The key used by the interaction.
	 */
	public Optional<String> getKey() {
		return key;
	}

	/**
	 * @param key The key pressed.
	 */
	protected void setKey(final String key) {
		this.key = Optional.ofNullable(key);
	}

	/**
	 * @param keycode The key pressed.
	 */
	protected void setKeyCode(final KeyCode keycode) {
		this.keyCode = Optional.ofNullable(keycode);
	}

	/**
	 * Defines a transition modifying the key attribute of the interaction.
	 */
	public class SingleKeyInteractionKeyPressedTransition extends KeyInteractionKeyPressedTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The srcObject state of the transition.
		 */
		public SingleKeyInteractionKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			super.action();
			SingleKeyInteraction.this.setKey(event.getCharacter());
			SingleKeyInteraction.this.setKeyCode(event.getCode());
		}
	}
}
