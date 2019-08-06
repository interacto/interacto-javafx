/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
