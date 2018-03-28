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
package org.malai.javafx.undo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.malai.undo.UndoCollector;
import org.malai.undo.UndoHandler;
import org.malai.undo.Undoable;

/**
 * Additional routines to handle undoable redoable commands in JavaFX.
 */
public final class FXUndoCollector {
	/** The singleton. */
	public static final FXUndoCollector INSTANCE = new FXUndoCollector();

	private final ObjectProperty<Undoable> lastUndo;
	private final ObjectProperty<Undoable> lastRedo;

	private FXUndoCollector() {
		super();
		lastUndo = new SimpleObjectProperty<>();
		lastRedo = new SimpleObjectProperty<>();
		UndoCollector.INSTANCE.addHandler(new FXUndoHander());
	}

	public ReadOnlyObjectProperty<Undoable> lastUndoProperty() {
		return lastUndo;
	}

	public ReadOnlyObjectProperty<Undoable> lastRedoProperty() {
		return lastRedo;
	}

	private void updateLastUndoRedo() {
		lastUndo.set(UndoCollector.INSTANCE.getLastUndo().orElse(null));
		lastRedo.set(UndoCollector.INSTANCE.getLastRedo().orElse(null));
	}

	class FXUndoHander implements UndoHandler {
		@Override
		public void onUndoableCleared() {
			updateLastUndoRedo();
		}

		@Override
		public void onUndoableAdded(final Undoable undoable) {
			updateLastUndoRedo();
		}

		@Override
		public void onUndoableUndo(final Undoable undoable) {
			updateLastUndoRedo();
		}

		@Override
		public void onUndoableRedo(final Undoable undoable) {
			updateLastUndoRedo();
		}
	}
}
