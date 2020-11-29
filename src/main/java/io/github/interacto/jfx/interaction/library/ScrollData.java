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

import io.github.interacto.interaction.InteractionData;

/**
 * Scrolling interaction data
 */
public interface ScrollData extends InteractionData, ModifierData {
	/**
	 * @return The object on which the scroll is performed.
	 */
	Object getScrolledNode();

	/**
	 * @return The X-coordinate of the scroll position.
	 */
	double getPx();

	/**
	 * @return The Y-coordinate of the scroll position.
	 */
	double getPy();

	/**
	 * @return The total increment of the scrolling.
	 */
	double getIncrement();
}
