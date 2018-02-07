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
package org.malai.javafx.interaction2.library;

import javafx.event.Event;
import javafx.scene.input.ScrollEvent;
import org.malai.javafx.interaction2.JfxInteraction;

public class Scroll extends JfxInteraction<ScrollFSM, Event> {
	private final ScrollFSM.ScrollFSMHandler handler;

	/** The scrolled node. */
	protected Object scrolledNode;

	/** The X-coordinate of the scroll position. */
	protected double px;

	/** The Y-coordinate of the scroll position. */
	protected double py;

	/** The total increment of the scrolling. */
	protected double increment;

	public Scroll() {
		super(new ScrollFSM());

		handler = new ScrollFSM.ScrollFSMHandler() {
			@Override
			public void onScroll(final ScrollEvent event) {
				Scroll.this.increment += event.getDeltaY();
				Scroll.this.px = event.getX();
				Scroll.this.py = event.getY();
				Scroll.this.scrolledNode = event.getSource();
			}

			@Override
			public void reinitData() {
				Scroll.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	/**
	 * @return The object on which the scroll is performed.
	 */
	public Object getScrolledNode() {
		return scrolledNode;
	}

	/**
	 * @return The X-coordinate of the scroll position.
	 */
	public double getPx() {
		return px;
	}

	/**
	 * @return The Y-coordinate of the scroll position.
	 */
	public double getPy() {
		return py;
	}

	/**
	 * @return The total increment of the scrolling.
	 */
	public double getIncrement() {
		return increment;
	}

	@Override
	public void reinitData() {
		super.reinitData();
		px = 0d;
		py = 0d;
		increment = 0d;
		scrolledNode = null;
	}
}
