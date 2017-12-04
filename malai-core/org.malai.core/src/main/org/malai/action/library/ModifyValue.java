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
package org.malai.action.library;

import org.malai.action.ActionImpl;

/**
 * The goal of this abstract action is to modify an object. This object can be for instance
 * a property of an object.
 * The use of this action can be made when an object has a lot of properties which modification
 * follow the same process. Thus, a same action can be used to modify all the properties. To do
 * so, a enumeration of the properties can be defined and used into the action to specify which
 * property will be modified by the current action instance.
 * @author Arnaud Blouin
 */
public abstract class ModifyValue extends ActionImpl {
	/** The new value of the property. */
	protected Object value;

	/**
	 * Initialises the action.
	 */
	public ModifyValue() {
		super();
	}

	/**
	 * Initialises the action with the value to set.
	 * @param value The value to set.
	 */
	public ModifyValue(final Object value) {
		this.value = value;
	}

	@Override
	public void flush() {
		super.flush();
		value = null;
	}


	@Override
	public boolean canDo() {
		return value != null && isValueMatchesProperty();
	}


	/**
	 * Sets the new value of the parameter to change.
	 * @param newValue The new value.
	 */
	public void setValue(final Object newValue) {
		value = newValue;
	}


	/**
	 * This method executes the job of methods undo, redo, and do
	 * @param obj The value to set. Must not be null.
	 * @throws NullPointerException If the given value is null.
	 */
	protected abstract void applyValue(final Object obj);


	/**
	 * @return True: the object to modified supports the selected property.
	 */
	protected abstract boolean isValueMatchesProperty();
}
