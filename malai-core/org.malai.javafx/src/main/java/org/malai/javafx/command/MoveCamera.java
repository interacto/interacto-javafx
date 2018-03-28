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
package org.malai.javafx.command;

import javafx.scene.control.ScrollPane;
import org.malai.command.library.PositionCommand;

/**
 * This command moves a camera by moving the scroll bars of a pane.
 * @author Arnaud Blouin
 */
public class MoveCamera extends PositionCommand {
	/** The scroll panel to modify. */
	protected ScrollPane scrollPane;

	/**
	 * Creates the action.
	 */
	public MoveCamera() {
		super();
	}

	@Override
	protected void doCmdBody() {
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
