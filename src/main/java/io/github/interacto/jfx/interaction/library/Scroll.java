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
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.event.Event;
import javafx.scene.input.ScrollEvent;

public class Scroll extends JfxInteraction<ScrollData, ScrollFSM, Event> implements ScrollData {
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
		this(new ScrollFSM());
	}

	protected Scroll(final ScrollFSM fsm) {
		super(fsm);

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

	@Override
	public Object getScrolledNode() {
		return scrolledNode;
	}

	@Override
	public double getPx() {
		return px;
	}

	@Override
	public double getPy() {
		return py;
	}

	@Override
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

	@Override
	public ScrollData getData() {
		return this;
	}
}
