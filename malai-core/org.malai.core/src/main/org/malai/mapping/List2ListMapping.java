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
 * Defines a mapping established between two lists.
 * @param <E> The type of the source element of the mapping.
 * @param <F> The type of the target element of the mapping.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class List2ListMapping<E, F> extends List2ObjectMapping<E, List<F>> {
	/**
	 * Creates the mapping.
	 * @param source The source list.
	 * @param target The target list.
	 * @throws IllegalArgumentException If one of the given lists is null or if they are the same object.
	 * @since 0.2
	 */
	public List2ListMapping(final List<E> source, final List<F> target) {
		super(source, target);
	}
}
