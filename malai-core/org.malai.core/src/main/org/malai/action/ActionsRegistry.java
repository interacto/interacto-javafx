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
package org.malai.action;

import java.util.ArrayList;
import java.util.List;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * A register of actions.
 * This is a singleton. It automatically collects the executed actions when the actions is executed by an instrument.
 * The register has a limited size that can be changed.
 * It can notify handlers about changes in the registry.
 * @author Arnaud Blouin
 * @since 0.1
 */
public final class ActionsRegistry {
	/** The singleton. */
	public static final ActionsRegistry INSTANCE = new ActionsRegistry();
	/** The saved actions. */
	private final List<Action> actions;
	/** The actions handlers. */
	private final List<ActionHandler> handlers;
	/** The max number of actions that can contain the register. */
	private int sizeMax;


	/**
	 * Creates and initialises a register.
	 * @since 0.1
	 */
	private ActionsRegistry() {
		super();
		actions = new ArrayList<>();
		handlers = new ArrayList<>();
		sizeMax = 30;
	}


	/**
	 * Notifies handlers that an action has been executed.
	 * @param action The executed action.
	 * @since 0.2
	 */
	public void onActionExecuted(final Action action) {
		if(action != null) {
			handlers.forEach(handler -> handler.onActionExecuted(action));
		}
	}


	/**
	 * Notifies handlers that an action ends.
	 * @param action The ending action.
	 * @since 0.2
	 */
	public void onActionDone(final Action action) {
		if(action != null) {
			handlers.forEach(handler -> handler.onActionDone(action));
		}
	}


	/**
	 * @return The stored actions. Cannot be null.
	 * @since 0.1
	 */
	public List<Action> getActions() {
		return actions;
	}


	/**
	 * Removes and flushes the actions from the register that use the given action type.
	 * @see Action::unregisteredBy
	 * @param action The action that may cancels others.
	 * @since 0.1
	 */
	public void unregisterActions(final Action action) {
		if(action == null) return;

		int i = 0;

		while(i < actions.size()) {
			if(actions.get(i).unregisteredBy(action)) {
				final Action act = actions.remove(i);
				handlers.forEach(handler -> handler.onActionCancelled(act));
				act.flush();
			}else {
				i++;
			}
		}
	}


	/**
	 * Adds an action to the register. Before being added, the given action is used to cancel actions
	 * already added. Handlers are notified of the add of the given action. If Undoable, the action is
	 * added to the undo collector as well.
	 * @param action The action to add. If null, nothing is done.
	 * @param actionHandler The handler that produced or is associated to the action. If null, nothing is done.
	 * @since 0.2
	 */
	public void addAction(final Action action, final ActionHandler actionHandler) {
		if(action != null && actionHandler != null && !actions.contains(action) && sizeMax > 0) {
			unregisterActions(action);

			// If there is too many actions in the register, the oldest action is removed and flushed.
			if(actions.size() == sizeMax) {
				actions.remove(0).flush();
			}

			actions.add(action);

			handlers.forEach(handler -> handler.onActionAdded(action));

			if(action instanceof Undoable) {
				UndoCollector.INSTANCE.add((Undoable) action, actionHandler);
			}
		}
	}


	/**
	 * Removes the action from the register. The action is then flushed.
	 * @param action The action to remove.
	 * @since 0.1
	 */
	public void removeAction(final Action action) {
		if(action == null) return;

		actions.remove(action);
		action.flush();
	}


	/**
	 * Adds an action handler.
	 * @param handler The handler to add.
	 * @since 0.1
	 */
	public void addHandler(final ActionHandler handler) {
		if(handler != null) {
			handlers.add(handler);
		}
	}


	/**
	 * Removes the given handler.
	 * @param handler The handler to remove.
	 * @since 0.2
	 */
	public void removeHandler(final ActionHandler handler) {
		if(handler != null) {
			handlers.remove(handler);
		}
	}


	/**
	 * Removes all the action handlers.
	 * @since 0.2
	 */
	public void removeAllHandlers() {
		handlers.clear();
	}


	/**
	 * Flushes and removes all the stored actions.
	 * @since 0.2
	 */
	public void clear() {
		actions.forEach(action -> action.flush());
		actions.clear();
	}


	/**
	 * Aborts the given action, i.e. the action is aborted and removed from the register.
	 * Handlers are then notified. The action is finally flushed.
	 * @param action The action to abort.
	 * @since 0.1
	 */
	public void abortAction(final Action action) {
		if(action != null) {
			action.abort();
			actions.remove(action);
			handlers.forEach(handler -> handler.onActionAborted(action));
			action.flush();
		}
	}


	/**
	 * @return The maximal number of actions that the register can contain.
	 * @since 0.2
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * Changes the number of actions that the register can contain.
	 * In the case that actions have to be removed (because the new size is smaller than the old one),
	 * the necessary number of the oldest actions are flushed and removed from the register.
	 * @param newSizeMax The max number of actions that can contain the register. Must be equal or greater than 0.
	 * @since 0.2
	 */
	public void setSizeMax(final int newSizeMax) {
		if(newSizeMax >= 0) {
			// If there is too many actions in the register, they are removed.
			for(int i = 0, nb = actions.size() - newSizeMax; i < nb; i++) {
				actions.remove(0).flush();
			}

			sizeMax = newSizeMax;
		}
	}
}
