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
package org.malai.picking;

/**
 * Defines an interface for objects that contains pickable and picker objects.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public interface Picker {
	/**
	 * @param x The x-coordinate of the position used to get the pickable object.
	 * @param y The y-coordinate of the position used to get the pickable object.
	 * @return The pickable object at the given position.
	 * @since 0.1
	 */
	Pickable getPickableAt(final double x, final double y);

	/**
	 * @param x The x-coordinate of the position used to get the picker object.
	 * @param y The y-coordinate of the position used to get the picker object.
	 * @return The pickable object at the given position.
	 * @since 0.1
	 */
	Picker getPickerAt(final double x, final double y);

	/**
	 * Tests if the given object is contained by the calling picker.
	 * @param obj The object to test.
	 * @return True: the given object is contained by the calling picker.
	 * @since 0.1
	 */
	boolean contains(final Object obj);
}
