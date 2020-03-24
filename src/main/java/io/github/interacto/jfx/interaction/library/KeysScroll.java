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

public class KeysScroll extends JfxInteraction<KeysScrollData, KeysScrollFSM> {
	private final Scroll scroll;
	private final KeysScrollFSM.KeysScrollFSMHandler handler;

	public KeysScroll() {
		super(new KeysScrollFSM());
		scroll = new Scroll(fsm.scrollFSM);
		((KeysScrollDataImpl) data).setScrollData(scroll.getData());

		handler = new KeysScrollFSM.KeysScrollFSMHandler() {
			@Override
			public void reinitData() {
				KeysScroll.this.reinitData();
			}

			@Override
			public void onKeyPressed(final KeyEvent event) {
				((KeysScrollDataImpl) data).addKeysData(event);
			}

			@Override
			public void onKeyReleased(final KeyEvent event) {
				((KeysScrollDataImpl) data).removeKeysData(event);
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected KeysScrollData createDataObject() {
		return new KeysScrollDataImpl();
	}

	@Override
	public void reinitData() {
		super.reinitData();
		scroll.reinitData();
	}
}
