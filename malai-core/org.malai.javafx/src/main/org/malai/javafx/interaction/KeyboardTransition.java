package org.malai.javafx.interaction;

import javafx.scene.input.KeyCode;

import org.malai.interaction.TransitionImpl;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 *Defines a transition based on keyboard events.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-23<br>
 * @author Arnaud BLOUIN
 */
public abstract class KeyboardTransition extends TransitionImpl {
	/** The pressed key. */
	protected String key;

	/** The object that produced the event. */
	protected Object source;
	
	/** The code of the key.*/
	protected KeyCode keyCode;


	/**
	 * Creates the transition.
	 */
	public KeyboardTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
		key = "";
	}


	/**
	 * @return The pressed key.
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Sets the pressed key.
	 * @param key The pressed key.
	 */
	public void setKey(final String key) {
		this.key = key;
	}


	/**
	 * @return The object that produced the event.
	 */
	public Object getSource() {
		return source;
	}


	/**
	 * @param source The object that produced the event.
	 */
	public void setSource(final Object source) {
		this.source = source;
	}


	/**
	 * @return The code corresponding to the key pressed.
	 */
	public KeyCode getKeyCode() {
		return keyCode;
	}


	/**
	 * @param keyCode The code corresponding to the key pressed.
	 */
	public void setKeyCode(final KeyCode keyCode) {
		this.keyCode = keyCode;
	}
}
