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

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class Click extends PointInteraction<ClickFSM, Node> {
	private final ClickFSM.ClickFSMHandler handler;

	protected Click(final ClickFSM fsm) {
		super(fsm);

		handler = new ClickFSM.ClickFSMHandler() {
			@Override
			public void initToClicked(final MouseEvent event) {
				setPointData(event);
			}

			@Override
			public void reinitData() {
				Click.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	public Click() {
		this(new ClickFSM());
	}
}
