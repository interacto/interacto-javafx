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
 * An active unary relation is an object that may contain a value (cardinalities 0..1). When the value is modified,
 * the mapping registry is notified and the corresponding mappings executed.
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <T> The type of the element contained by the active 0-1 relation.
 */
public class ActiveUnary<T> implements IUnary<T> {
	/** The value of the singleton. */
	protected T value;


	/**
	 * Creates an empty singleton.
	 * @since 0.2
	 */
	public ActiveUnary() {
		this(null);
	}
	
	
	/**
	 * Creates a singleton with a given value.
	 * @param value The value of the singleton.
	 * @since 0.2
	 */
	public ActiveUnary(final T value) {
		super();
		
		this.value = value;
	}


	@Override
	public T getValue() {
		return value;
	}


	@Override
	public void setValue(final T value) {
		final T replacedValue 	= this.value;
		this.value 				= value;
		MappingRegistry.REGISTRY.onObjectReplaced(this, replacedValue);
	}
}
