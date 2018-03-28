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
 * This exception must be launched when a command which is not undoable want to be undone or redone.
 * @author Arnaud BLOUIN
 */
public class MustBeUndoableCmdException extends RuntimeException {
	/**
	 * The default constructor of the exception.
	 * @param clazz The class of the command that want to be undone/redone.
	 */
	public MustBeUndoableCmdException(final Class<?> clazz) {
		super("The following command must be undoable: " + (clazz == null ? "" : " " + clazz.getName()));
	}
}
