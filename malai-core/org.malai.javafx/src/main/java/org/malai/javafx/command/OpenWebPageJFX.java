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
package org.malai.javafx.command;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import javafx.application.Application;
import org.malai.command.CommandImpl;

/**
 * This command opens a URI.
 * @author Arnaud BLOUIN
 */
public class OpenWebPageJFX extends CommandImpl {
	/** The URI to open. */
	protected String uri;

	protected Application app;

	private boolean browsed;

	/**
	 * Creates the command.
	 * @since 0.2
	 */
	public OpenWebPageJFX() {
		this(null, null);
	}

	public OpenWebPageJFX(final Application app, final String uri) {
		super();
		this.app = app;
		this.uri = uri;
		browsed = false;
	}

	@Override
	public void flush() {
		uri = null;
		app = null;
	}


	@Override
	protected void doCmdBody() {
		HostServicesFactory.getInstance(app).showDocument(uri);
	}


	@Override
	public boolean canDo() {
		return uri != null && app != null;
	}

	@Override
	public boolean hadEffect() {
		return super.hadEffect() && browsed;
	}

	/**
	 * @param uri The URI to open.
	 * @since 0.2
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}

	public void setApp(final Application app) {
		this.app = app;
	}
}
