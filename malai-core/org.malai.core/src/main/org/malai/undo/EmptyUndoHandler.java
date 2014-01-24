package org.malai.undo;

/**
 * An implementation of UndoHandler that does nothing. Useful to avoid implementing all the operations.<br>
 * This file is part of libMalai.
 * Copyright (c) 2005-2014 Arnaud BLOUIN
 *
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * @author Arnaud BLOUIN
 */
public class EmptyUndoHandler implements UndoHandler {
	/**
	 * Creates an undo handler that does nothing.
	 */
	public EmptyUndoHandler() {
		super();
	}

	@Override
	public void onUndoableCleared() {
		// To override
	}

	@Override
	public void onUndoableAdded(final Undoable undoable) {
		// To override
	}

	@Override
	public void onUndoableUndo(final Undoable undoable) {
		// To override
	}

	@Override
	public void onUndoableRedo(final Undoable undoable) {
		// To override
	}
}
