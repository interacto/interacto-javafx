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
package org.malai.javafx.action;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import org.malai.action.ActionImpl;

/**
 * This action opens an URI in the default browser.
 * @author Arnaud BLOUIN
 */
public class OpenWebPageJFX extends ActionImpl {
	/** The URI to open. */
	protected URI uri;

	private boolean browsed;

	/**
	 * Creates the action.
	 * @since 0.2
	 */
	public OpenWebPageJFX() {
		super();
		browsed = false;
	}

	@Override
	public void flush() {
		uri = null;
	}


	@Override
	protected void doActionBody() {
		new Thread(() -> {
			try {
				Desktop.getDesktop().browse(uri);
				browsed = true;
			}catch(IOException e) {
				browsed = false;
			}
		}).start();
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
	 * @param uri The URI to open.
	 * @since 0.2
	 */
	public void setUri(final URI uri) {
		this.uri = uri;
	}
}
