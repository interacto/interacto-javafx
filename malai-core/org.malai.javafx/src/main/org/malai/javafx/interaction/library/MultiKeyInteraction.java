package org.malai.javafx.interaction.library;

import java.util.ArrayList;
import java.util.List;

import org.malai.javafx.interaction.KeyReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import javafx.scene.input.KeyCode;

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
public abstract class MultiKeyInteraction extends KeyInteraction {
	/** The key pressed. */
	protected List<String> keys;

	/** The code of the key. */
	protected List<KeyCode> keyCode;

	/**
	 * Creates the interaction.
	 */
	public MultiKeyInteraction() {
		super();
	}

	@Override
	public void reinit() {
		super.reinit();

		if(keys == null)
			keys = new ArrayList<>();
		else
			keys.clear();

		if(keyCode == null)
			keyCode = new ArrayList<>();
		else
			keyCode.clear();
	}

	/**
	 * @return The key code used by the interaction.
	 */
	public List<KeyCode> getKeyCode() {
		return keyCode;
	}

	/**
	 * @return The key used by the interaction.
	 */
	public List<String> getKey() {
		return keys;
	}

	/**
	 * @param key The key pressed.
	 */
	protected void addKey(final String key) {
		keys.add(key);
	}

	/**
	 * @param keys The key pressed.
	 */
	protected void addKeyCode(final KeyCode keycode) {
		keyCode.add(keycode);
	}

	/**
	 * Defines a key pressure transition modifying the key attribute of the interaction.
	 */
	public class MultiKeyInteractionKeyPressedTransition extends KeyInteractionKeyPressedTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The target state of the transition.
		 */
		public MultiKeyInteractionKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			super.action();
			MultiKeyInteraction.this.addKey(event.getCharacter());
			MultiKeyInteraction.this.addKeyCode(event.getCode());
		}
	}

	/**
	 * Defines a key release transition modifying the key attribute of the interaction.
	 */
	public class MultiKeyReleaseTransition extends KeyReleaseTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The target state of the transition.
		 */
		public MultiKeyReleaseTransition(SourceableState inputState, TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			MultiKeyInteraction.this.keys.remove(this.event.getCharacter());
		}
	}
}
