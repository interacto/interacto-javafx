package org.malai.action;

import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

import java.util.ArrayList;
import java.util.List;

/**
 * A register of actions.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud Blouin
 * @since 0.1
 */
public final class ActionsRegistry {
	/** The saved actions. */
	private final List<Action> IActions;

	/** The actions handlers. */
	private final List<ActionHandler> handlers;

	/** The max number of actions that can contains the register. */
	private int sizeMax;

	/** The register of actions. */
	public static final ActionsRegistry INSTANCE = new ActionsRegistry();



	/**
	 * Creates and initialises a register.
	 * @since 0.1
	 */
	private ActionsRegistry() {
		super();
		IActions = new ArrayList<>();
		handlers 	= new ArrayList<>();
		sizeMax		= 30;
	}



	/**
	 * Notifies handlers that an action has been executed.
	 * @param IAction The executed action.
	 * @since 0.2
	 */
	public void onActionExecuted(final Action IAction) {
		if(IAction !=null)
			for(final ActionHandler handler : handlers)
				handler.onActionExecuted(IAction);
	}



	/**
	 * Notifies handlers that an action ends.
	 * @param IAction The action that ends.
	 * @since 0.2
	 */
	public void onActionDone(final Action IAction) {
		if(IAction !=null)
			for(final ActionHandler handler : handlers)
				handler.onActionDone(IAction);
	}



	/**
	 * @return The stored actions. Cannot be null.
	 * @since 0.1
	 */
	public List<Action> getActions() {
		return IActions;
	}



	/**
	 * Removes and flushes actions from the register using a given action.
	 * @param IAction The action that may cancels others.
	 * @since 0.1
	 */
	public void cancelActions(final Action IAction) {
		if(IAction ==null)
			return;

		int i=0;

		while(i< IActions.size())
			if(IActions.get(i).cancelledBy(IAction)) {
				final Action act = IActions.remove(i);

				for(final ActionHandler handler : handlers)
					handler.onActionCancelled(act);

				act.flush();
			}
			else
				i++;
	}



	/**
	 * Adds an action to the register. Before being added, the given action is used to cancel actions
	 * already added. Handlers are notified of the add of the given action. If Undoable, the action is
	 * added to the undo collector as well.
	 * @param IAction The action to add. Cannot be null.
	 * @param actionHanndler The handler that produced or is associated to the action. Cannot be null.
	 * @since 0.2
	 */
	public void addAction(final Action IAction, final ActionHandler actionHanndler) {
		if(IAction !=null && actionHanndler!=null && !IActions.contains(IAction) && sizeMax>0) {
			cancelActions(IAction);

			// If there is too many actions in the register, the oldest action is removed and flushed.
			if(IActions.size()==sizeMax)
				IActions.remove(0).flush();

			IActions.add(IAction);

			for(final ActionHandler handler : handlers)
				handler.onActionAdded(IAction);

			if(IAction instanceof Undoable)
				UndoCollector.INSTANCE.add((Undoable) IAction, actionHanndler);
		}
	}



	/**
	 * Removes the action from the register. The action is then flushes.
	 * @param IAction The action to remove.
	 * @since 0.1
	 */
	public void removeAction(final Action IAction) {
		if(IAction ==null)
			return;

		IActions.remove(IAction);
		IAction.flush();
	}



	/**
	 * Adds an action handler.
	 * @param handler The handler to add.
	 * @since 0.1
	 */
	public void addHandler(final ActionHandler handler) {
		if(handler!=null)
			handlers.add(handler);
	}


	/**
	 * Removes the given handler.
	 * @param handler The handler to remove.
	 * @since 0.2
	 */
	public void removeHandler(final ActionHandler handler) {
		if(handler!=null)
			handlers.remove(handler);
	}


	/**
	 * Removes all the action handlers.
	 * @since 0.2
	 */
	public void removeAllHandlers() {
		handlers.clear();
	}


	/**
	 * Removes all the stored actions.
	 * @since 0.2
	 */
	public void clear() {
		while(!IActions.isEmpty())
			IActions.remove(0).flush();
	}


	/**
	 * @param clazz The reference class.
	 * @return The first action of the exact same class of the given class.
	 * @since 0.1
	 */
	@SafeVarargs
	public final <T extends ActionImpl> T getAction(final Class<? extends T>... clazz) {
		T action = null;

		if(clazz!=null) {
			for(int j=0; j<clazz.length && action == null; j++) {
				for(int i = 0, size = IActions.size(); i < size && action == null; i++)
					if(IActions.get(i).getClass() == clazz[j])
						action = clazz[j].cast(IActions.get(i));
			}
		}

		return action;
	}



	/**
	 * Aborts the given action, i.e. the action is aborted then remove from
	 * the register. Handlers are then notified. The action is finally flushes.
	 * @param IAction The action to abort.
	 * @since 0.1
	 */
	public void abortAction(final Action IAction) {
		if(IAction !=null) {
			IAction.abort();
			IActions.remove(IAction);

			for(final ActionHandler handler : handlers)
				handler.onActionAborted(IAction);

			IAction.flush();
		}
	}


	/**
	 * @return The max number of actions that can contains the register.
	 * @since 0.2
	 */
	public int getSizeMax() {
		return sizeMax;
	}


	/**
	 * @param sizeMax The max number of actions that can contains the register. Must be equal or greater than 0.
	 * @since 0.2
	 */
	public void setSizeMax(final int sizeMax) {
		if(sizeMax>=0) {
			// If there is too many actions in the register, they are removed.
			for(int i = 0, nb = IActions.size()-sizeMax; i<nb; i++)
				IActions.remove(0).flush();

			this.sizeMax = sizeMax;
		}
	}
}
