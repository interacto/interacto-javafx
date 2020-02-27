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
package io.github.interacto.jfx.command;

import javafx.scene.Node;

/**
 * This command shows or hides a JComponent.
 * @author Arnaud BLOUIN
 */
public class ShowNode extends WidgetCommand<Node> {
	/** Defines if the component must be shown or hidden. */
	protected boolean visible;


	/**
	 * Creates the command.
	 */
	public ShowNode() {
		super();
	}

	@Override
	protected void doCmdBody() {
		widget.setVisible(visible);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && widget.isVisible() != visible;
	}


	/**
	 * @param visible Defines if the component must be shown or hidden.
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
