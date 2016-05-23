package org.malai.javafx.instrument.library;

import javafx.scene.control.MenuItem;
import org.malai.javafx.action.library.OpenWebPageJFX;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxMenuItemInteractor;
import org.malai.javafx.interaction.library.MenuItemPressed;

import java.net.URI;


/**
 * An interactor that opens a URL using a menu item.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2016 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2016/05/22<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public class MenuItem2OpenWebPageInteractor extends JfxMenuItemInteractor<OpenWebPageJFX, MenuItemPressed, JfxInstrument> {
	/** The menu item that will be uses to create the action. */
	protected MenuItem menuItem;

	/** The URI to open. */
	protected URI uri;

	/**
	 * Creates the link.
	 * @param ins The instrument that will contain the link.
	 * @param menuItem he menu item that will be uses to create the action.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @since 0.2
	 */
	public MenuItem2OpenWebPageInteractor(final JfxInstrument ins, final MenuItem menuItem,
										  final URI uri) throws InstantiationException, IllegalAccessException {
		super(ins, false, OpenWebPageJFX.class, MenuItemPressed.class, menuItem);

		if(menuItem==null || uri==null)
			throw new IllegalArgumentException();

		this.uri		= uri;
		this.menuItem 	= menuItem;
	}


	@Override
	public void initAction() {
		action.setUri(uri);
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getWidget()==menuItem;
	}
}
