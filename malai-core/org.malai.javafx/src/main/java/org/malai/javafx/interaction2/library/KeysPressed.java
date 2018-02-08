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

import javafx.scene.input.KeyEvent;

public class KeysPressed extends MultiKeyInteraction<KeysPressedFSM> {
	private final KeysPressedFSM.KeysPressedFSMHandler handler;

	public KeysPressed() {
		super(new KeysPressedFSM());

		handler = new KeysPressedFSM.KeysPressedFSMHandler() {
			@Override
			public void onKeyPressed(final KeyEvent event) {
				setKeysData(event);
			}

			@Override
			public void reinitData() {
				KeysPressed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}
}
