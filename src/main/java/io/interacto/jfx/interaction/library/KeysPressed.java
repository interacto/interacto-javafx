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
package io.interacto.jfx.interaction.library;

import javafx.scene.input.KeyEvent;

public class KeysPressed extends MultiKeyInteraction<KeysData, KeysPressedFSM> {
	private final KeysPressedFSM.KeysPressedFSMHandler handler;

	public KeysPressed() {
		super(new KeysPressedFSM());

		handler = new KeysPressedFSM.KeysPressedFSMHandler() {
			@Override
			public void onKeyPressed(final KeyEvent event) {
				addKeysData(event);
			}

			@Override
			public void reinitData() {
				KeysPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	public KeysData getData() {
		return this;
	}
}
