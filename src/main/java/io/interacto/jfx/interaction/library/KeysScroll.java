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

public class KeysScroll extends MultiKeyInteraction<KeysScrollData, KeysScrollFSM> implements KeysScrollData {
	private final Scroll scroll;
	private final KeysScrollFSM.KeysScrollFSMHandler handler;

	public KeysScroll() {
		super(new KeysScrollFSM());
		scroll = new Scroll(fsm.scrollFSM);

		handler = new KeysScrollFSM.KeysScrollFSMHandler() {
			@Override
			public void reinitData() {
				KeysScroll.this.reinitData();
			}

			@Override
			public void onKeyPressed(final KeyEvent event) {
				addKeysData(event);
			}

			@Override
			public void onKeyReleased(final KeyEvent event) {
				removeKeysData(event);
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		scroll.reinitData();
	}

	@Override
	public KeysScrollData getData() {
		return this;
	}

	@Override
	public Object getScrolledNode() {
		return scroll.getData().getScrolledNode();
	}

	@Override
	public double getPx() {
		return scroll.getData().getPx();
	}

	@Override
	public double getPy() {
		return scroll.getData().getPy();
	}

	@Override
	public double getIncrement() {
		return scroll.getData().getIncrement();
	}
}
