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
import java.util.Collections;
import java.util.List;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

/**
 * A register of actions.
 * This is a singleton. It automatically collects the executed actions when the action is executed by an instrument.
 * The register has a limited size that can be changed.
 * It can notify handler about changes in the registry.
 * @author Arnaud Blouin
 */
public final class ActionsRegistry {
	/** The singleton. */
	public static final ActionsRegistry INSTANCE = new ActionsRegistry();
	/** The saved actions. */
	private final List<Action> actions;
	/** The actions handler. */
	private final List<ActionHandler> handlers;
	/** The max number of cleanable actions (cf. Action::getRegistrationPolicy) that can contain the register. */
	private int sizeMax;


	/**
	 * Creates and initialises a register.
	 */
	private ActionsRegistry() {
		super();
		actions = new ArrayList<>();
		handlers = new ArrayList<>();
		sizeMax = 50;
	}

	public List<ActionHandler> getHandlers() {
		return Collections.unmodifiableList(handlers);
	}

	/**
	 * Notifies handler that an action has been executed.
	 * @param action The executed action.
	 */
	public void onActionExecuted(final Action action) {
		if(action != null) {
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onActionExecuted(action));
			}
		}
	}


	/**
	 * Notifies handler that an action ends.
	 * @param action The ending action.
	 */
	public void onActionDone(final Action action) {
		if(action != null) {
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onActionDone(action));
			}
		}
	}


	/**
	 * @return The stored actions. Cannot be null. Because of concurrency, you should not modify this list.
	 */
	public List<Action> getActions() {
		return actions;
	}


	/**
	 * Removes and flushes the actions from the register that use the given action type.
	 * @see Action::unregisteredBy
	 * @param action The action that may cancels others.
	 */
	public void unregisterActions(final Action action) {
		if(action == null) return;

		int i = 0;

		synchronized(actions) {
			while(i < actions.size()) {
				if(actions.get(i).unregisteredBy(action)) {
					actions.remove(i).flush();
				}else {
					i++;
				}
			}
		}
	}


	/**
	 * Adds an action to the register. Before being added, the given action is used to cancel actions
	 * already added. Handlers are notified of the add of the given action. If Undoable, the action is
	 * added to the undo collector as well.
	 * @param action The action to add. If null, nothing is done.
	 * @param actionHandler The handler that produced or is associated to the action. If null, nothing is done.
	 */
	public void addAction(final Action action, final ActionHandler actionHandler) {
		synchronized(actions) {
			if(action != null && actionHandler != null && !actions.contains(action) &&
				(sizeMax > 0 || action.getRegistrationPolicy() == Action.RegistrationPolicy.UNLIMITED)) {
				unregisterActions(action);

				// If there is too many actions in the register, the oldest removable action is removed and flushed.
				if(actions.size() >= sizeMax) {
					actions.stream().filter(act -> act.getRegistrationPolicy() != Action.RegistrationPolicy.UNLIMITED).findFirst().
						ifPresent(act -> {
							actions.remove(act);
							act.flush();
						});
				}

				actions.add(action);

				synchronized(handlers) {
					handlers.forEach(handler -> handler.onActionAdded(action));
				}

				if(action instanceof Undoable) {
					UndoCollector.INSTANCE.add((Undoable) action, actionHandler);
				}
			}
		}
	}


	/**
	 * Removes the action from the register. The action is then flushed.
	 * @param action The action to remove.
	 */
	public void removeAction(final Action action) {
		if(action != null) {
			synchronized(actions) {
				actions.remove(action);
			}
			action.flush();
		}
	}


	/**
	 * Adds an action handler.
	 * @param handler The handler to add.
	 */
	public void addHandler(final ActionHandler handler) {
		if(handler != null) {
			synchronized(handlers) {
				handlers.add(handler);
			}
		}
	}


	/**
	 * Removes the given handler.
	 * @param handler The handler to remove.
	 */
	public void removeHandler(final ActionHandler handler) {
		if(handler != null) {
			synchronized(handlers) {
				handlers.remove(handler);
			}
		}
	}


	/**
	 * Removes all the action handler.
	 */
	public void removeAllHandlers() {
		synchronized(handlers) {
			handlers.clear();
		}
	}


	/**
	 * Flushes and removes all the stored actions.
	 */
	public void clear() {
		synchronized(actions) {
			actions.forEach(action -> action.flush());
			actions.clear();
		}
	}


	/**
	 * Aborts the given action, i.e. the action is cancelled and removed from the register.
	 * Handlers are then notified. The action is finally flushed.
	 * @param action The action to cancel.
	 */
	public void cancelAction(final Action action) {
		if(action != null) {
			action.cancel();
			synchronized(actions) {
				actions.remove(action);
			}
			synchronized(handlers) {
				handlers.forEach(handler -> handler.onActionCancelled(action));
			}
			action.flush();
		}
	}


	/**
	 * @return The maximal number of actions that the register can contain.
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * Changes the number of actions that the register can contain.
	 * In the case that actions have to be removed (because the new size is smaller than the old one),
	 * the necessary number of the oldest and cleanable actions (cf. Action::getRegistrationPolicy)
	 * are flushed and removed from the register.
	 * @param newSizeMax The max number of actions that can contain the register. Must be equal or greater than 0.
	 */
	public void setSizeMax(final int newSizeMax) {
		if(newSizeMax >= 0) {
			synchronized(actions) {
				int i = 0;
				int nb = 0;
				final int toRemove = actions.size() - newSizeMax;

				while(nb < toRemove && i < actions.size()) {
					if(actions.get(i).getRegistrationPolicy() != Action.RegistrationPolicy.UNLIMITED) {
						actions.remove(i).flush();
						nb++;
					}else {
						i++;
					}
				}
			}
			sizeMax = newSizeMax;
		}
	}
}
