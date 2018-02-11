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

import javafx.scene.input.KeyEvent;

public class KeysScroll extends MultiKeyInteraction<KeysScrollFSM> {
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

	public ScrollData getScrollData() {
		return scroll;
	}
}
