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
package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.command.OpenWebPageJFX;
import io.github.interacto.jfx.interaction.library.MenuItemPressed;
import java.util.Collections;
import javafx.application.HostServices;
import javafx.scene.control.MenuItem;

/**
 * A widget binding that opens an URI using a menu item.
 * @author Arnaud BLOUIN
 */
public class MenuItem2OpenWebPage extends JfxMenuItemBinding<OpenWebPageJFX, MenuItemPressed> {
	/** The URI to open. */
	protected String uri;
	protected HostServices serv;

	/**
	 * Creates the widget binding.
	 * @param menuItem he menu item that will be uses to create the command.
	 * @param uri The URI to open.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 */
	public MenuItem2OpenWebPage(final MenuItem menuItem, final String uri, final HostServices services) {
		super(false, new MenuItemPressed(), i -> new OpenWebPageJFX(), Collections.singletonList(menuItem));

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
