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

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.FSM;
import org.malai.javafx.interaction.JfxInteraction;

public abstract class KeyInteraction<D extends KeyData, F extends FSM<Event>, T> extends JfxInteraction<D, F, T> implements KeyData {
	/** The key pressed. */
	protected String key;
	/** The code of the key. */
	protected KeyCode keyCode;
	/** The object that produced the interaction. */
	protected Object object;

	protected KeyInteraction(final F fsm) {
		super(fsm);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		object = null;
		key = null;
		keyCode = null;
	}

	protected void setKeyData(final KeyEvent event) {
		object = event.getSource();
		key = KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter();
		keyCode = event.getCode();
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public KeyCode getKeyCode() {
		return keyCode;
	}
}
