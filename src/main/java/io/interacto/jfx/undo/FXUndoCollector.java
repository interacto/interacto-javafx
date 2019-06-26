/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.interacto.jfx.undo;

import io.interacto.undo.UndoCollector;
import io.interacto.undo.UndoHandler;
import io.interacto.undo.Undoable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

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

	void updateLastUndoRedo() {
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
