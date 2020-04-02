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

import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.scene.input.KeyEvent;

/**
 * A user interaction for typing several keyboard touches.
 * One can type on multiple keys. The interaction ends after a timeout (a delay after the latest
 * typed key).
 */
public class KeysTyped extends JfxInteraction<KeysData, KeysTypedFSM> {
	private final KeysTypedFSM.KeysTypedFSMHandler handler;

	/**
	 * Creates the user interaction.
	 */
	public KeysTyped() {
		super(new KeysTypedFSM());

		handler = new KeysTypedFSM.KeysTypedFSMHandler() {
			@Override
			public void onKeyTyped(final KeyEvent event) {
				((KeysDataImpl) data).addKeysData(event);
			}

			@Override
			public void reinitData() {
				KeysTyped.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected KeysData createDataObject() {
		return new KeysDataImpl();
	}
}
