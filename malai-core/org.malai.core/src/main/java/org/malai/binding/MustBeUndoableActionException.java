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
 */
public class MustBeUndoableActionException extends RuntimeException {
	/**
	 * The default constructor of the exception.
	 * @param clazz The class of the action that want to be undone/redone.
	 */
	public MustBeUndoableActionException(final Class<?> clazz) {
		super("The following action must be undoable: " + (clazz == null ? "" : " " + clazz.getName()));
	}
}
