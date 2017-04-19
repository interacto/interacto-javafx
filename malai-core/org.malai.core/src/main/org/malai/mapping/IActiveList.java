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
package org.malai.mapping;

import java.util.List;

/**
 * This interface completes the List interface with necessary methods.
 * @param <E> The type of the elements contained in the active list.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface IActiveList<E> extends List<E> {
	/**
	 * Moves the object located at the position srcIndex to the location specified
	 * by the targetIndex.
	 * @param srcIndex The position of the object to move.
	 * @param targetIndex The final position of the moved object.
	 * @throws ArrayIndexOutOfBoundsException if one of the given index is not valid.
	 * @since 0.2
	 */
	void move(final int srcIndex, final int targetIndex);
}
