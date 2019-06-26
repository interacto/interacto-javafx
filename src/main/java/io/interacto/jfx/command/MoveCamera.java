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
package io.interacto.jfx.command;

import io.interacto.command.library.PositionCommand;
import javafx.scene.control.ScrollPane;

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
		return scrollPane != null && super.canDo();
	}

	/**
	 * Sets the scroll panel to modify.
	 * @param scrollp The scroll panel to modify.
	 */
	public void setScrollPane(final ScrollPane scrollp) {
		scrollPane = scrollp;
	}
}
