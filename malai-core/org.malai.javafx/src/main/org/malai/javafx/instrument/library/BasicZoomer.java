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
package org.malai.javafx.instrument.library;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.malai.action.library.Zoom;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.instrument.JfxInteractor;
import org.malai.javafx.interaction.library.KeyPressure;
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
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		if(withKeys) {
			addInteractor(new KeysZoom(this));
		}
		addInteractor(new Scroll2Zoom(this));
	}


	/**
	 * This interactor maps a key pressure interaction to a zoom action.
	 */
	protected static class KeysZoom extends JfxInteractor<Zoom, KeyPressure, BasicZoomer<?>> {
		protected KeysZoom(final BasicZoomer<?> ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, Zoom.class, KeyPressure.class, ins.zoomable);
		}

		@Override
		public void initAction() {
			interaction.getKeyCode().ifPresent(key -> {
				action.setZoomable(instrument.getZoomable());
				action.setZoomLevel(instrument.zoomable.getZoom() +
					(isZoomInKey(key) ? instrument.zoomable.getZoomIncrement() : -instrument.zoomable.getZoomIncrement()));
				action.setPx(-1d);
				action.setPy(-1d);
			});
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getKeyCode().map(code -> isZoomInKey(code) || isZoomOutKey(code)).orElse(false);
		}

		private boolean isZoomInKey(final KeyCode key) {
			return key == KeyCode.PLUS;
		}

		private boolean isZoomOutKey(final KeyCode key) {
			return key == KeyCode.MINUS;
		}
	}


	/**
	 * This interactor maps a scroll interaction to a zoom action.
	 */
	protected static class Scroll2Zoom extends JfxInteractor<Zoom, KeysScrolling, BasicZoomer<?>> {
		/**
		 * Creates the action.
		 */
		protected Scroll2Zoom(final BasicZoomer<?> ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, Zoom.class, KeysScrolling.class, ins.zoomable);
		}

		@Override
		public void initAction() {
			action.setZoomable(instrument.zoomable);
		}

		@Override
		public void updateAction() {
			action.setZoomLevel(instrument.zoomable.getZoom() +
				(interaction.getIncrement() > 0 ? instrument.zoomable.getZoomIncrement() : -instrument.zoomable.getZoomIncrement()));
			action.setPx(interaction.getPx());
			action.setPy(interaction.getPy());
		}

		@Override
		public boolean isConditionRespected() {
			final List<KeyCode> keys = interaction.getKeys();
			return keys.size() == 1 && keys.get(0) == KeyCode.CONTROL;
		}
	}
}
