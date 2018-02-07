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
package org.malai.undo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * A collector of undone/redone objects.
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public final class UndoCollector {
	/** The default undo/redo collector. */
	public static final UndoCollector INSTANCE = new UndoCollector();

	/** The standard text for redo. */
	public static final String EMPTY_REDO = "redo";

	/** The standard text for undo. */
	public static final String EMPTY_UNDO = "undo";
	/** The Null object for UndoHandler. To avoid the use of null in the stacks. */
	private static final UndoHandler STUB_UNDO_HANDLER = new EmptyUndoHandler();
	/** Contains the handler of each undoable of the undo stack */
	private final Deque<UndoHandler> undoHandlers;
	/** Contains the handler of each undoable of the redo stack */
	private final Deque<UndoHandler> redoHandlers;
	/** Contains the undoable objects. */
	private final Deque<Undoable> undo;
	/** Contains the redoable objects. */
	private final Deque<Undoable> redo;
	/** The maximal number of undo. */
	private int sizeMax;
	/** The handler that handles the collector. */
	private final List<UndoHandler> handlers;


	/**
	 * Creates the undo collector.
	 */
	private UndoCollector() {
		super();

		handlers = new ArrayList<>();
		undo = new ArrayDeque<>();
		redo = new ArrayDeque<>();
		undoHandlers = new ArrayDeque<>();
		redoHandlers = new ArrayDeque<>();
		sizeMax = 30;
	}


	/**
	 * Adds a handler to the collector.
	 * @param handler The handler to add. Must not be null.
	 */
	public void addHandler(final UndoHandler handler) {
		if(handler != null) {
			handlers.add(handler);
		}
	}


	/**
	 * Removes the given handler from the collector.
	 * @param handler The handler to remove. Must not be null.
	 */
	public void removeHandler(final UndoHandler handler) {
		if(handler != null) {
			handlers.remove(handler);
		}
	}

	public List<UndoHandler> getHandlers() {
		return Collections.unmodifiableList(handlers);
	}

	public void clearHandlers() {
		handlers.clear();
	}

	/**
	 * Removes all the undoable objects of the collector.
	 */
	public void clear() {
		undo.clear();
		redo.clear();
		undoHandlers.clear();
		redoHandlers.clear();
		for(final UndoHandler h : handlers) {
			h.onUndoableCleared();
		}
	}


	/**
	 * Adds an undoable object to the collector.
	 * @param undoable The undoable object to add.
	 * @param undoHandler The handler that produced or is associated to the undoable object.
	 */
	public void add(final Undoable undoable, final UndoHandler undoHandler) {
		if(undoable != null && sizeMax > 0) {
			if(undo.size() == sizeMax) {
				undo.removeLast();
				undoHandlers.removeLast();
			}

			undo.push(undoable);
			// When undo handler is null, a fake object is added instead of using null.
			if(undoHandler == null) {
				undoHandlers.push(STUB_UNDO_HANDLER);
			}else {
				undoHandlers.push(undoHandler);
			}
			redo.clear(); /* The redoable objects must be removed. */
			redoHandlers.clear();

			for(final UndoHandler handler : handlers) {
				handler.onUndoableAdded(undoable);
			}
		}
	}


	/**
	 * Undoes the last undoable object.
	 */
	public void undo() {
		if(!undo.isEmpty()) {
			final Undoable undoable = undo.pop();
			final UndoHandler undoHandler = undoHandlers.pop();

			undoable.undo();
			redo.push(undoable);
			redoHandlers.push(undoHandler);
			undoHandler.onUndoableUndo(undoable);

			for(final UndoHandler handler : handlers) {
				handler.onUndoableUndo(undoable);
			}
		}
	}


	/**
	 * Redoes the last undoable object.
	 */
	public void redo() {
		if(!redo.isEmpty()) {
			final Undoable undoable = redo.pop();
			final UndoHandler redoHandler = redoHandlers.pop();

			undoable.redo();
			undo.push(undoable);
			undoHandlers.push(redoHandler);
			redoHandler.onUndoableRedo(undoable);

			for(final UndoHandler handler : handlers) {
				handler.onUndoableRedo(undoable);
			}
		}
	}


	/**
	 * @return The last undoable object name or null if there is no last object.
	 */
	public Optional<String> getLastUndoMessage() {
		return undo.isEmpty() ? Optional.empty() : Optional.ofNullable(undo.peek().getUndoName());
	}


	/**
	 * @return The last redoable object name or null if there is no last object.
	 */
	public Optional<String> getLastRedoMessage() {
		return redo.isEmpty() ? Optional.empty() : Optional.ofNullable(redo.peek().getUndoName());
	}


	/**
	 * @return The last undoable object or null if there is no last object.
	 */
	public Optional<Undoable> getLastUndo() {
		return undo.isEmpty() ? Optional.empty() : Optional.ofNullable(undo.peek());
	}


	/**
	 * @return The last redoable object or null if there is no last object.
	 */
	public Optional<Undoable> getLastRedo() {
		return redo.isEmpty() ? Optional.empty() : Optional.ofNullable(redo.peek());
	}


	/**
	 * @return The max number of saved undoable objects.
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * @param max The max number of saved undoable objects. Must be great than 0.
	 */
	public void setSizeMax(final int max) {
		if(max >= 0) {
			for(int i = 0, nb = undo.size() - max; i < nb; i++) {
				undo.removeLast();
				undoHandlers.removeLast();
			}
			this.sizeMax = max;
		}
	}

	/**
	 * @return The stack of saved undoable objects.
	 * @since 0.1
	 */
	public Deque<Undoable> getUndo() {
		return undo;
	}

	/**
	 * @return The stack of saved redoable objects
	 * @since 0.1
	 */
	public Deque<Undoable> getRedo() {
		return redo;
	}
}
