/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.action;

import javafx.scene.control.ScrollPane;
import org.malai.action.library.PositionAction;

/**
 * This action moves a camera by moving the scroll bars of a pane.
 * @author Arnaud Blouin
 */
public class MoveCamera extends PositionAction {
	/** The scroll panel to modify. */
	protected ScrollPane scrollPane;

	/**
	 * Creates the action.
	 */
	public MoveCamera() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		scrollPane.setHvalue(px);
		scrollPane.setVvalue(py);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && scrollPane != null;
	}

	/**
	 * Sets the scroll panel to modify.
	 * @param scrollp The scroll panel to modify.
	 */
	public void setScrollPane(final ScrollPane scrollp) {
		scrollPane = scrollp;
	}
}
