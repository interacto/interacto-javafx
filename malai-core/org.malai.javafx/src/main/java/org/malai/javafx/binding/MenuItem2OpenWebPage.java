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
import javafx.application.HostServices;
import javafx.scene.control.MenuItem;
import org.malai.javafx.command.OpenWebPageJFX;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;

/**
 * A widget binding that opens a URI using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2OpenWebPage extends JfxMenuItemBinding<OpenWebPageJFX, MenuItemPressed, JfxInstrument> {
	/** The URI to open. */
	protected String uri;
	protected HostServices serv;

	/**
	 * Creates the widget binding.
	 * @param ins The instrument that will contain the binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 2.0
	 */
	public MenuItem2OpenWebPage(final JfxInstrument ins, final MenuItem menuItem, final String uri, final HostServices services) {
		super(ins, false, new MenuItemPressed(), i -> new OpenWebPageJFX(), Collections.singletonList(menuItem));

		if(uri == null) {
			throw new IllegalArgumentException();
		}

		this.uri = uri;
		serv = services;
	}

	@Override
	public void first() {
		cmd.setUri(uri);
		cmd.setServices(serv);
	}
}
