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
package org.malai.javafx.instrument;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.malai.command.library.Zoom;
import org.malai.javafx.interaction.library.KeyPressed;
import org.malai.javafx.interaction.library.KeysScroll;
import org.malai.properties.Zoomable;

/**
 * A base JFX instrument to zoom.
 * @author Arnaud Blouin
 */
public class BasicZoomer<T extends Node & Zoomable> extends JfxInstrument {
	/** The node to zoom in/out. */
	protected T zoomable;

	/** True: the keys + and - will be used to zoom in and out. */
	protected boolean withKeys;


	/**
	 * Creates and initialises the zoomer.
	 */
	public BasicZoomer() {
		super();
	}

	public void setZoomable(final T zoomable) {
		this.zoomable = zoomable;
	}

	public void setWithKeys(final boolean withKeys) {
		this.withKeys = withKeys;
	}

	/**
	 * @return The object to zoom in/out.
	 */
	public T getZoomable() {
		return zoomable;
	}


	@Override
	protected void configureBindings() {
		if(withKeys) {
			nodeBinder(new KeyPressed(false), Zoom::new).on(zoomable).
				first((i, c) -> {
					final String key = i.getKey();
					c.setZoomable(getZoomable());
					if("+".equals(key)) {
						c.setZoomLevel(zoomable.getZoom() + zoomable.getZoomIncrement());
					}else {
						c.setZoomLevel(zoomable.getZoom() - zoomable.getZoomIncrement());
					}
					c.setPx(-1d);
					c.setPy(-1d);
				}).
				when(i -> "+".equals(i.getKey()) || "-".equals(i.getKey())).bind();
		}

		nodeBinder(new KeysScroll(), Zoom::new).on(zoomable).
			first(c -> c.setZoomable(zoomable)).
			then((i, c) -> {
				c.setZoomLevel(zoomable.getZoom() + (i.getIncrement() > 0 ? zoomable.getZoomIncrement() : -zoomable.getZoomIncrement()));
				c.setPx(i.getPx());
				c.setPy(i.getPy());
			}).
			when(i -> i.getKeys().size() == 1 && i.getKeyCodes().get(0) == KeyCode.CONTROL).bind();
	}
}
