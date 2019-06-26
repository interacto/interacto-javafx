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
package io.interacto.jfx.binding;

import io.interacto.jfx.command.ShowNode;
import io.interacto.jfx.instrument.JfxInstrument;
import io.interacto.jfx.interaction.library.MenuItemPressed;
import java.util.Collections;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

/**
 * A wdget binding that opens a URL using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2ShowNode extends JfxMenuItemBinding<ShowNode, MenuItemPressed, JfxInstrument> {
	protected Node nodeToShow;

	protected boolean show;

	/**
	 * Creates the widget binding.
	 * @param ins The instrument that will contain the binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param node The node to show or hide
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 2.0
	 */
	public MenuItem2ShowNode(final JfxInstrument ins, final MenuItem menuItem, final Node node, final boolean toshow) {
		super(ins, false, new MenuItemPressed(), i -> new ShowNode(), Collections.singletonList(menuItem));

		if(node == null) {
			throw new IllegalArgumentException();
		}

		nodeToShow = node;
		show = toshow;
	}

	@Override
	public void first() {
		cmd.setWidget(nodeToShow);
		cmd.setVisible(show);
	}
}
