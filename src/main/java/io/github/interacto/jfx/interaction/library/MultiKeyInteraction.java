/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.FSM;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class MultiKeyInteraction<D extends KeysData, F extends FSM<Event>> extends JfxInteraction<D, F, Event> implements KeysData {
	/** The key pressed. */
	protected final List<String> keys;
	/** The code of the key. */
	protected final List<KeyCode> keyCodes;
	/** The object that produced the interaction. */
	protected Object object;

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
		object = null;
	}

	@Override
	public List<KeyCode> getKeyCodes() {
		return keyCodes;
	}

	@Override
	public List<String> getKeys() {
		return keys;
	}

	@Override
	public Object getObject() {
		return object;
	}

	protected void addKeysData(final KeyEvent event) {
		keys.add(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
		keyCodes.add(event.getCode());
		object = event.getSource();
	}

	protected void removeKeysData(final KeyEvent event) {
		keys.remove(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
		keyCodes.remove(event.getCode());
	}
}
