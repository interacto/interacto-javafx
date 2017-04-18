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
import org.malai.undo.UndoCollector;

/**
 * Defines an action that undoes an action.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class Undo extends ActionImpl {
	/**
	 * Initialises an Undo action.
	 * @since 0.2
	 */
	public Undo() {
		super();
	}


	@Override
	public boolean canDo() {
		return UndoCollector.INSTANCE.getLastUndo()!=null;
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		UndoCollector.INSTANCE.undo();
		done();
	}
}
