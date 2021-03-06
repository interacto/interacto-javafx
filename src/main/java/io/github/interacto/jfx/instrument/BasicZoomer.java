/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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
package io.github.interacto.jfx.instrument;

import io.github.interacto.command.library.Zoom;
import io.github.interacto.jfx.interaction.library.KeyPressed;
import io.github.interacto.jfx.interaction.library.Scroll;
import io.github.interacto.properties.Zoomable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

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


	private void configureKeyBindings() {
		final var baseBinder =
			nodeBinder()
				.usingInteraction(() -> new KeyPressed(false))
				.toProduce(() -> new Zoom(getZoomable()))
				.on(zoomable);

		baseBinder
			.first(c -> {
				c.setZoomLevel(zoomable.getZoom() + zoomable.getZoomIncrement());
				c.setPx(-1d);
				c.setPy(-1d);
			})
			.when(i -> i.getKeyCode() == KeyCode.ADD || i.getKeyCode() == KeyCode.PLUS || "+".equals(i.getKey()))
			.bind();

		baseBinder
			.first(c -> {
				c.setZoomLevel(zoomable.getZoom() - zoomable.getZoomIncrement());
				c.setPx(-1d);
				c.setPy(-1d);
			})
			.when(i -> i.getKeyCode() == KeyCode.MINUS || i.getKeyCode() == KeyCode.SUBTRACT || "-".equals(i.getKey()))
			.bind();
	}

	@Override
	protected void configureBindings() {
		if(withKeys) {
			configureKeyBindings();
		}

		nodeBinder()
			.usingInteraction(Scroll::new)
			.toProduce(() -> new Zoom(getZoomable()))
			.on(zoomable)
			.then((i, c) -> {
				c.setZoomLevel(zoomable.getZoom() + (i.getIncrement() > 0 ? zoomable.getZoomIncrement() : -zoomable.getZoomIncrement()));
				c.setPx(i.getPx());
				c.setPy(i.getPy());
			})
			.when(i -> i.isShortcutDown() && i.getIncrement() != 0)
			.bind();
	}
}
