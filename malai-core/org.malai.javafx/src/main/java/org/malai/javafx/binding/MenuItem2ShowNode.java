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
package org.malai.javafx.binding;

import java.util.Collections;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import org.malai.javafx.command.ShowNode;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;

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
