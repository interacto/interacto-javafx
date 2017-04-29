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
package org.malai.action.library;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import org.malai.action.ActionImpl;
import org.malai.error.ErrorCatcher;

/**
 * This action opens an URI in the default browser.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class OpenWebPage extends ActionImpl {
	/** The URI to open. */
	protected URI uri;

	protected boolean browsed;

	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public OpenWebPage() {
		super();
		browsed = false;
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	public void flush() {
		uri = null;
	}


	@Override
	protected void doActionBody() {
		try {
			Desktop.getDesktop().browse(uri);
			browsed = true;
		}catch(final IOException exception) {
			ErrorCatcher.INSTANCE.reportError(exception);
			browsed = false;
		}
	}


	@Override
	public boolean canDo() {
		return uri != null && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
	}

	@Override
	public boolean hadEffect() {
		return super.hadEffect() && browsed;
	}

	/**
	 * @param newURI The URI to open.
	 * @since 0.2
	 */
	public void setUri(final URI newURI) {
		uri = newURI;
	}
}
