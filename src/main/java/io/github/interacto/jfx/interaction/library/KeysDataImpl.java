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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeysDataImpl implements KeysData {
	/** The key pressed. */
	protected final List<String> keys;
	/** The code of the key. */
	protected final List<KeyCode> keyCodes;
	/** The object that produced the interaction. */
	protected Object object;

	boolean shift;
	boolean ctrl;
	boolean alt;
	boolean shortcut;
	boolean meta;

	protected KeysDataImpl() {
		super();
		keys = new ArrayList<>();
		keyCodes = new ArrayList<>();
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
	public boolean isShortcutDown() {
		return shortcut;
	}

	@Override
	public boolean isMetaDown() {
		return meta;
	}

	@Override
	public boolean isAltDown() {
		return alt;
	}

	@Override
	public boolean isShiftDown() {
		return shift;
	}

	@Override
	public boolean isCtrlDown() {
		return ctrl;
	}

	@Override
	public Object getObject() {
		return object;
	}

	protected void addKeysData(final KeyEvent event) {
		if(!event.getCode().isModifierKey()) {
			keys.add(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
			keyCodes.add(event.getCode());
		}
		object = event.getSource();
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			setModifiers(event);
		}
	}

	protected void removeKeysData(final KeyEvent event) {
		keys.remove(KeyEvent.CHAR_UNDEFINED.equals(event.getCharacter()) ? event.getText() : event.getCharacter());
		keyCodes.remove(event.getCode());
		setModifiers(event);
	}

	private void setModifiers(final KeyEvent event) {
		shortcut = event.isShortcutDown();
		alt = event.isAltDown();
		shift = event.isShiftDown();
		ctrl = event.isControlDown();
		meta = event.isMetaDown();
	}

	@Override
	public void flush() {
		keys.clear();
		keyCodes.clear();
		object = null;
		shift = false;
		shortcut = false;
		ctrl = false;
		alt = false;
		meta = false;
	}
}
