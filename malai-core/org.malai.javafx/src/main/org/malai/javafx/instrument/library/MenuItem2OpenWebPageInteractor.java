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
package org.malai.javafx.instrument.library;

import javafx.scene.control.MenuItem;
import org.malai.javafx.action.library.OpenWebPageJFX;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxMenuItemInteractor;
import org.malai.javafx.interaction.library.MenuItemPressed;

import java.net.URI;


/**
 * An interactor that opens a URL using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2OpenWebPageInteractor extends JfxMenuItemInteractor<OpenWebPageJFX, MenuItemPressed, JfxInstrument> {
	/** The URI to open. */
	protected URI uri;

	/**
	 * Creates the interactor.
	 * @param ins The instrument that will contain the interactor.
	 * @param menuItem he menu item that will be uses to create the action.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @since 2.0
	 */
	public MenuItem2OpenWebPageInteractor(final JfxInstrument ins, final MenuItem menuItem,
										  final URI uri) throws InstantiationException, IllegalAccessException {
		super(ins, false, OpenWebPageJFX.class, MenuItemPressed.class, menuItem);

		if(uri==null)
			throw new IllegalArgumentException();

		this.uri = uri;
	}


	@Override
	public void initAction() {
		action.setUri(uri);
	}
}
