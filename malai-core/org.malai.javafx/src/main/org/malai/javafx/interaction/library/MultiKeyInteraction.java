package org.malai.javafx.interaction.library;

import javafx.scene.input.KeyCode;
import org.malai.javafx.interaction.KeyReleaseTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import java.util.ArrayList;
import java.util.List;

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
	protected List<KeyCode> keyCodes;

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

		if(keyCodes == null)
			keyCodes = new ArrayList<>();
		else
			keyCodes.clear();
	}

	/**
	 * @return The key code used by the interaction.
	 */
	public List<KeyCode> getKeyCodes() {
		return keyCodes;
	}

	/**
	 * @return The keys used by the interaction.
	 */
	public List<String> getKeys() {
		return keys;
	}

	/**
	 * @param key The key pressed to add.
	 */
	protected void addKey(final String key) {
		keys.add(key);
	}

	/**
	 * @param keycode The key pressed to add.
	 */
	protected void addKeyCode(final KeyCode keycode) {
		if(keycode!=null)
			keyCodes.add(keycode);
	}

	/**
	 * Defines a key pressure transition modifying the key attribute of the interaction.
	 */
	public class MultiKeyInteractionKeyPressedTransition extends KeyInteractionKeyPressedTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The srcObject state of the transition.
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
		 * @param outputState The srcObject state of the transition.
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
