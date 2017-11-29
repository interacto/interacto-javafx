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
package org.malai.binding;

import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.action.ActionsRegistry;
import org.malai.error.ErrorCatcher;
import org.malai.instrument.Instrument;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.undo.Undoable;

/**
 * The base class to do widget bindings, i.e. bindings between user interactions and (undoable) actions.
 * @param <A> The type of the action that will produce this widget binding.
 * @param <I> The type of the interaction that will use this widget binding.
 * @param <N> The type of the instrument that will contain this widget binding.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetBindingImpl<A extends ActionImpl, I extends Interaction, N extends Instrument<?>> implements WidgetBinding {
	/** The source interaction. */
	protected final I interaction;

	/** The current action in progress. */
	protected A action;

	/** The instrument that contains the widget binding. */
	protected final N instrument;

	/** Specifies if the action must be execute or update * on each evolution of the interaction. */
	protected final boolean execute;

	/** Defines whether the action must be executed in a specific thread. */
	protected boolean async;

	protected final Class<A> clazzAction;


	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The widget binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param actionClass The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @since 0.2
	 */
	public WidgetBindingImpl(final N ins, final boolean exec, final Class<A> actionClass, final I interaction) throws InstantiationException, IllegalAccessException {
		super();

		if(ins == null || actionClass == null || interaction == null) {
			throw new IllegalArgumentException();
		}

		clazzAction = actionClass;
		this.interaction = interaction;
		action = null;
		instrument = ins;
		execute = exec;
		this.interaction.addHandler(this);
		setActivated(ins.isActivated());
		async = false;
	}

	/**
	 * Whether the action must be executed in a specific thread.
	 * @return True: the action will be executed asynchronously.
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * Sets wether the action must be executed in a specific thread.
	 * @param asyncAction True: the action will be executed asynchronously.
	 */
	public void setAsync(final boolean asyncAction) {
		async = asyncAction;
	}

	@Override
	public void clearEvents() {
		interaction.clearEvents();
	}


	/**
	 * Initialises the action of the widget binding. If the attribute 'action' is
	 * not null, nothing will be done.
	 * @since 0.2
	 */
	protected void createAction() {
		try {
			action = clazzAction.newInstance();
		}catch(final IllegalAccessException | InstantiationException ex) {
			ErrorCatcher.INSTANCE.reportError(ex);
		}
	}


	@Override
	public abstract void initAction();


	@Override
	public void updateAction() {
		// to override.
	}


	@Override
	public abstract boolean isConditionRespected();


	@Override
	public I getInteraction() {
		return interaction;
	}


	@Override
	public A getAction() {
		return action;
	}


	@Override
	public boolean isActivated() {
		return instrument.isActivated();
	}

	@Override
	public boolean isRunning() {
		return interaction.isRunning();
	}


	@Override
	public boolean isInteractionMustBeAborted() {
		return false;
	}


	@Override
	public void interactionAborts(final Interaction inter) {
		if(action != null && inter == interaction) {
			action.abort();

			// The instrument is notified about the aborting of the action.
			instrument.onActionAborted(action);

			if(isExecute()) {
				if(action instanceof Undoable) {
					((Undoable) action).undo();
				}else {
					throw new MustBeUndoableActionException(action.getClass());
				}
			}

			action = null;
			instrument.interimFeedback();
		}
	}


	@Override
	public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
		if(inter == interaction && isInteractionMustBeAborted()) throw new MustAbortStateMachineException();
		if(action == null && inter == interaction && isActivated() && isConditionRespected()) {
			createAction();
			initAction();
			interimFeedback();
		}
	}


	@Override
	public void interactionStops(final Interaction inter) {
		if(interaction == inter) {
			if(isConditionRespected()) {
				if(action == null) {
					createAction();
					initAction();
				}

				if(!execute) {
					updateAction();
				}

				if(action.doIt()) {
					instrument.onActionExecuted(action);
					action.done();
					instrument.onActionDone(action);
				}

				executeAction(action);
				action = null;
				instrument.interimFeedback();
			}else {
				if(action != null) {
					action.abort();
					instrument.onActionAborted(action);
					action = null;
					instrument.interimFeedback();
				}
			}
		}
	}


	private void executeAction(final Action act) {
		if(act.doIt()) {
			instrument.onActionExecuted(act);
			act.done();
			instrument.onActionDone(act);
		}

		if(act.hadEffect()) {
			if(act.getRegistrationPolicy() != Action.RegistrationPolicy.NONE) {
				ActionsRegistry.INSTANCE.addAction(act, instrument);
				instrument.onActionAdded(act);
			}else {
				ActionsRegistry.INSTANCE.unregisterActions(act);
				instrument.onActionCancelled(act);
			}
			act.followingActions().forEach(this::executeAction);
		}
	}


	@Override
	public void interactionUpdates(final Interaction inter) {
		if(inter == interaction && isConditionRespected()) {
			if(action == null) {
				createAction();
				initAction();
			}

			updateAction();

			if(execute && action.canDo()) {
				action.doIt();
				instrument.onActionExecuted(action);
			}

			interimFeedback();
		}
	}


	@Override
	public boolean isExecute() {
		return execute;
	}


	@Override
	public void interimFeedback() {
		//
	}


	@Override
	public void setActivated(final boolean activ) {
		interaction.setActivated(activ);
	}


	@Override
	public N getInstrument() {
		return instrument;
	}
}
