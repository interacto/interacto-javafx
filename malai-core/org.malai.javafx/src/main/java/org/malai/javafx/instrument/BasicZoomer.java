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
import org.malai.action.library.Zoom;
import org.malai.javafx.interaction.library.KeyPressureNoModifier;
import org.malai.javafx.interaction.library.KeysScrolling;
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
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		if(withKeys) {
			nodeBinder(Zoom.class, new KeyPressureNoModifier()).on(zoomable).
				first((a, i) -> i.getKey().ifPresent(key -> {
					a.setZoomable(getZoomable());
					if("+".equals(key)) {
						a.setZoomLevel(zoomable.getZoom() + zoomable.getZoomIncrement());
					}else {
						a.setZoomLevel(zoomable.getZoom() - zoomable.getZoomIncrement());
					}
					a.setPx(-1d);
					a.setPy(-1d);
				})).
				when(i -> i.getKey().map(code -> "+".equals(code) || "-".equals(code)).orElse(false)).bind();
		}

		nodeBinder(Zoom.class, new KeysScrolling()).on(zoomable).
			first(a -> a.setZoomable(zoomable)).
			then((a, i) -> {
				a.setZoomLevel(zoomable.getZoom() + (i.getIncrement() > 0 ? zoomable.getZoomIncrement() : -zoomable.getZoomIncrement()));
				a.setPx(i.getPx());
				a.setPy(i.getPy());
			}).
			when(i -> i.getKeys().size() == 1 && i.getKeys().get(0) == KeyCode.CONTROL).bind();
	}
}
