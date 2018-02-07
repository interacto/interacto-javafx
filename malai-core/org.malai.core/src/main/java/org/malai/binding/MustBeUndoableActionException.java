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
package org.malai.binding;

/**
 * This exception must be launched when an action which is not undoable want to be undone or redone.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class MustBeUndoableActionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/** The class of the action that want to be undone/redone. */
	protected final Class<?> clazz;


	/**
	 * The default constructor of the exception.
	 * @param theClass The class of the action that want to be undone/redone.
	 * @since 0.1
	 */
	public MustBeUndoableActionException(final Class<?> theClass) {
		super();
		clazz = theClass;
	}


	@Override
	public String toString() {
		return super.toString() + (clazz == null ? "" : " " + clazz.getSimpleName()); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
