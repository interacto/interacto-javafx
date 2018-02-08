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
package org.malai.javafx.interaction2.library;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.FSM;
import org.malai.javafx.interaction2.JfxInteraction;

public abstract class MultiKeyInteraction<F extends FSM<Event>> extends JfxInteraction<F, Event> {
	/** The key pressed. */
	protected final List<String> keys;
	/** The code of the key. */
	protected final List<KeyCode> keyCodes;

	protected MultiKeyInteraction(final F fsm) {
		super(fsm);
		keys = new ArrayList<>();
		keyCodes = new ArrayList<>();
	}

	@Override
	public void reinitData() {
		super.reinitData();
		keys.clear();
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

	protected void addKeysData(final KeyEvent event) {
		keys.add(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
		keyCodes.add(event.getCode());
	}

	protected void removeKeysData(final KeyEvent event) {
		keys.remove(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
		keyCodes.remove(event.getCode());
	}
}
