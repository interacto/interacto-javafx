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

/**
 * This interface defines methods for an unary relation that may contain a value of type T (cardinality 0..1).
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <T> The type of the element contained by the 0-1 relation.
 */
public interface IUnary<T> {
	/**
	 * @return The value of the singleton.
	 * @since 0.2
	 */
	T getValue();


	/**
	 * Sets a new value to the singleton.
	 * @param value The new value.
	 * @since 0.2
	 */
	void setValue(final T value);
}
