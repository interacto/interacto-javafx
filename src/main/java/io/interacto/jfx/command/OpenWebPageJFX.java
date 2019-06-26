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

import io.interacto.command.CommandImpl;
import javafx.application.HostServices;

/**
 * This command opens a URI.
 * @author Arnaud BLOUIN
 */
public class OpenWebPageJFX extends CommandImpl {
	/** The URI to open. */
	protected String uri;

	protected HostServices services;

	private boolean browsed;

	/**
	 * Creates the command.
	 * @since 0.2
	 */
	public OpenWebPageJFX() {
		this(null, null);
	}

	public OpenWebPageJFX(final HostServices services, final String uri) {
		super();
		this.services = services;
		this.uri = uri;
		browsed = false;
	}

	@Override
	public void flush() {
		uri = null;
		services = null;
	}


	@Override
	protected void doCmdBody() {
		services.showDocument(uri);
	}


	@Override
	public boolean canDo() {
		return uri != null && services != null;
	}

	@Override
	public boolean hadEffect() {
		return browsed && super.hadEffect();
	}

	/**
	 * @param uri The URI to open.
	 * @since 0.2
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}

	public void setServices(final HostServices services) {
		this.services = services;
	}
}
