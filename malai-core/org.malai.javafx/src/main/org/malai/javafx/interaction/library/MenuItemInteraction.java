package org.malai.javafx.interaction.library;

import javafx.scene.control.MenuItem;
import org.malai.javafx.interaction.JfxInteractionImpl;

import java.util.List;

/**
 * A JavaFX interaction that uses a single menu item.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2014-09-20<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public abstract class MenuItemInteraction<T extends MenuItem> extends JfxInteractionImpl {
	/** The widget used during the interaction. */
	protected T widget;

	/**
	 * Creates the interaction.
	 */
	public MenuItemInteraction() {
		super();
	}


	@Override
	public void reinit() {
		super.reinit();
		widget = null;
	}
	

	/**
	 * @return The widget used during the interaction.
	 */
	public T getWidget() {
		return widget;
	}

	public void registerToMenuItems(final List<MenuItem> widgets) {
		// Should be overriden.
	}
}
