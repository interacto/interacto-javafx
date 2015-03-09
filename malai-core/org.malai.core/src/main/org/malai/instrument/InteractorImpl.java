package org.malai.instrument;

import org.malai.action.Action;
import org.malai.action.ActionsRegistry;
import org.malai.error.ErrorCatcher;
import org.malai.interaction.Eventable;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.malai.undo.Undoable;

/**
 * In the Malai interaction model, an instrument links interactions to actions.
 * Thus, an instrument is composed of Link definitions: each interactor links an interaction
 * to an action. A Link manages the life cycle of an action following the life cycle
 * of the interaction (started, aborted, etc.).<br>
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
 * <br>
 * 05/10/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 * @param <A> The type of the action that will produce this interactor.
 * @param <I> The type of the interaction that will use this interactor.
 * @param <N> The type of the instrument that will contain this interactor.
 */
public abstract class InteractorImpl<A extends Action, I extends Interaction, N extends Instrument> implements Interactor {
	/** The source interaction. */
	protected final I interaction;

	/** The target action. */
	protected A action;

	/** The instrument that contains the interactor. */
	protected final N instrument;

	/** Specifies if the action must be execute or update
	 * on each evolution of the interaction. */
	protected boolean execute;

	protected final Class<A> clazzAction;


	/**
	 * Creates a interactor. This constructor must initialise the interaction. The interactor is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the interactor.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @since 0.2
	 */
	public InteractorImpl(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
		super();

		if(ins==null || clazzAction==null || clazzInteraction==null)
			throw new IllegalArgumentException();

		this.clazzAction = clazzAction;
		interaction = clazzInteraction.newInstance();
		action		= null;
		instrument  = ins;
		execute		= exec;
		interaction.addHandler(this);
		setActivated(ins.isActivated());
	}



	@Override
	public void addEventable(final Eventable eventable) {
		if(eventable!=null && interaction!=null)
			interaction.linkToEventable(eventable);
	}


	@Override
	public void clearEvents() {
		interaction.clearEvents();
	}


	/**
	 * Initialises the action of the interactor. If the attribute 'action' is
	 * not null, nothing will be done.
	 * @since 0.2
	 */
	protected void createAction() {
		try{
			action = clazzAction.newInstance();
		}catch(final IllegalAccessException | InstantiationException e){
			ErrorCatcher.INSTANCE.reportError(e);
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


//	/**
//	* Indicates if the interactor can be run. To be run, no interactor, of the instrument, that produces the
//	* same type of action must be running.
//	* @return True: The interactor can be run.
//	*/
//	public boolean isRunnable() {
//		for(final Link<?, ?, ?> link : instrument.links)
//			if(link!=this && link.isRunning() && link.clazzAction==clazzAction) {
//				System.out.println("Not RUNNABLE: " + this + " because of " + link);
//				return true;
//			//	return false;
//			}
//
//		return true;
//	}



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
		if(action!=null && inter==interaction) {
			action.abort();

			// The instrument is notified about the aborting of the action.
			instrument.onActionAborted(action);

			if(isExecute())
				if(action instanceof Undoable)
					((Undoable)action).undo();
				else throw new MustBeUndoableActionException(action.getClass());

			action = null;
			instrument.interimFeedback();
		}
	}


	@Override
	public void interactionStarts(final Interaction inter) throws MustAbortStateMachineException {
		if(inter==interaction && isInteractionMustBeAborted())
			throw new MustAbortStateMachineException();
//isRunnable() &&
		if(action==null && inter==interaction && isActivated() && isConditionRespected()) {
			createAction();
			initAction();
			interimFeedback();
		}
	}


	@Override
	public void interactionStops(final Interaction inter) {
		if(interaction==inter)
			if(isConditionRespected()) {
				if(action==null) {
					createAction();
					initAction();
				}

    			if(!execute)
    				updateAction();

    			if(action.doIt()) {
    				instrument.onActionExecuted(action);
    				action.done();
    				instrument.onActionDone(action);
    			}

    			executeAction(action);
    			action = null;
    			instrument.interimFeedback();
			}
			else
				if(action!=null) {
					action.abort();
					instrument.onActionAborted(action);
					action = null;
					instrument.interimFeedback();
				}
	}


	private void executeAction(final Action act) {
		if(act.doIt()) {
			instrument.onActionExecuted(act);
			act.done();
			instrument.onActionDone(act);
		}

		if(act.hadEffect()) {
			if(act.isRegisterable()) {
				 ActionsRegistry.INSTANCE.addAction(act, instrument);
				 instrument.onActionAdded(act);
			}
			else {
				ActionsRegistry.INSTANCE.cancelActions(act);
				instrument.onActionCancelled(act);
			}
			for(final Action followAction : act.followingActions())
				executeAction(followAction);
		}
	}


	@Override
	public void interactionUpdates(final Interaction inter) {
		if(inter==interaction && isConditionRespected()) {
			if(action==null) {
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
