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
import org.malai.javafx.interaction.JfxInteraction;

public class DoubleClick extends JfxInteraction<DoubleClickFSM, Node> {
	private final Click firstClick;

	public DoubleClick() {
		this(new DoubleClickFSM());
	}

	protected DoubleClick(final DoubleClickFSM fsm) {
		super(fsm);
		firstClick = new Click(fsm.firstClickFSM);
		fsm.buildFSM(this);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		firstClick.reinitData();
	}

	public PointInteractionData getClickData() {
		return firstClick;
	}
}
