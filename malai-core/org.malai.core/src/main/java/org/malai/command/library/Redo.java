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
package org.malai.command.library;

import org.malai.command.CommandImpl;
import org.malai.undo.UndoCollector;

/**
 * A command that redoes a command.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class Redo extends CommandImpl {
	/**
	 * Initialises a Redo command.
	 * @since 0.2
	 */
	public Redo() {
		super();
	}


	@Override
	public boolean canDo() {
		return UndoCollector.INSTANCE.getLastRedo().isPresent();
	}


	@Override
	protected void doCmdBody() {
		UndoCollector.INSTANCE.redo();
		done();
	}
}
