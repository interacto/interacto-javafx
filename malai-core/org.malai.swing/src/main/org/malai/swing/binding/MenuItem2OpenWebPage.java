package org.malai.swing.binding;

import java.net.URI;

import org.malai.action.library.OpenWebPage;
import org.malai.binding.WidgetBindingImpl;
import org.malai.swing.instrument.SwingInstrument;
import org.malai.swing.interaction.library.MenuItemPressed;
import org.malai.swing.widget.MMenuItem;


/**
 * This widget binding binds an OpenWebPage action to a menu item interaction.
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/11/2011<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MenuItem2OpenWebPage extends WidgetBindingImpl<OpenWebPage, MenuItemPressed, SwingInstrument> {
	/** The menu item that will be uses to create the action. */
	protected MMenuItem menuItem;

	/** The URI to open. */
	protected URI uri;

	/**
	 * Creates the widget binding.
	 * @param ins The instrument that will contain the widget binding.
	 * @param menuItem he menu item that will be uses to create the action.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @since 0.2
	 */
	public MenuItem2OpenWebPage(final SwingInstrument ins, final MMenuItem menuItem,
								final URI uri) throws InstantiationException, IllegalAccessException {
		super(ins, false, OpenWebPage.class, MenuItemPressed.class);

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
		return interaction.getMenuItem()==menuItem;
	}
}
