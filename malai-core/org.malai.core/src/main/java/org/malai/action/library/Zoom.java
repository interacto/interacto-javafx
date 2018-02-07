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
package org.malai.action.library;

import org.malai.properties.Zoomable;

/**
 * An action for zooming in/out a zoomable object.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class Zoom extends PositionAction {
	/** The object to zoom. */
	protected Zoomable zoomable;

	/** The zooming level. */
	protected double zoomLevel;


	/**
	 * Initialises a Zoom action.
	 * @since 0.2
	 */
	public Zoom() {
		super();

		zoomLevel = Double.NaN;
		zoomable = null;
	}


	@Override
	public void flush() {
		super.flush();
		zoomable = null;
	}


	@Override
	public boolean canDo() {
		return zoomable != null && zoomLevel >= zoomable.getMinZoom() && zoomLevel <= zoomable.getMaxZoom();
	}


	@Override
	protected void doActionBody() {
		zoomable.setZoom(px, py, zoomLevel);
	}


	/**
	 * @param newZoomable the zoomable to set.
	 * @since 0.2
	 */
	public void setZoomable(final Zoomable newZoomable) {
		zoomable = newZoomable;
	}


	/**
	 * @param newZoomLevel the zoomLevel to set.
	 * @since 0.2
	 */
	public void setZoomLevel(final double newZoomLevel) {
		zoomLevel = newZoomLevel;
	}
}
