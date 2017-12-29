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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.action.ActionsRegistry;
import org.malai.action.AutoUnbind;
import org.malai.error.ErrorCatcher;
import org.malai.instrument.Instrument;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustCancelStateMachineException;
import org.malai.undo.Undoable;

/**
 * The base class to do widget bindings, i.e. bindings between user interactions and (undoable) actions.
 * @param <A> The type of the action that will produce this widget binding.
 * @param <I> The type of the interaction that will use this widget binding.
 * @param <N> The type of the instrument that will contain this widget binding.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetBindingImpl<A extends ActionImpl, I extends Interaction, N extends Instrument<?>> implements WidgetBinding {
	protected Logger loggerBinding;

	protected Logger loggerAction;

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

	/** The action class to instantiate. */
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

	public void logBinding(final boolean log) {
		if(log) {
			if(loggerBinding == null) {
				loggerBinding = Logger.getLogger(getClass().getName());
			}
		}else {
			loggerBinding = null;
		}
	}

	public void logAction(final boolean log) {
		if(log) {
			if(loggerAction == null) {
				loggerAction = Logger.getLogger(getClass().getName());
			}
		}else {
			loggerAction = null;
		}
	}

	public void logInteraction(final boolean log) {
		interaction.log(log);
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
	 * creates the action of the widget binding. If the attribute 'action' is not null, nothing will be done.
	 * @return The created action or null if problems occured.
	 */
	protected A createAction() {
		try {
			return clazzAction.newInstance();
		}catch(final IllegalAccessException | InstantiationException ex) {
			ErrorCatcher.INSTANCE.reportError(ex);
			return null;
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
	public boolean isStrictStart() {
		return false;
	}

	protected void unbindActionAttributes() {
		if(action != null) {
			unbindActionAttributesClass(action.getClass());
			if(loggerAction != null) {
				loggerAction.log(Level.INFO, "Action unbound: " + action);
			}
		}
	}

	private void unbindActionAttributesClass(final Class<? extends ActionImpl> clazz) {
		Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(AutoUnbind.class) &&
			Property.class.isAssignableFrom(field.getType())).forEach(field -> {
			try {
				final boolean access = field.isAccessible();
				field.setAccessible(true);
				final Object o = field.get(action);
				if(o instanceof Property<?>) {
					((Property<?>) o).unbind();
				}
				field.setAccessible(access);
			}catch(final IllegalAccessException ex) {
				ex.printStackTrace();
			}
		});

		final Class<?> superClass = clazz.getSuperclass();
		if(superClass != null && superClass != ActionImpl.class && ActionImpl.class.isAssignableFrom(superClass)) {
			unbindActionAttributesClass((Class<? extends ActionImpl>) superClass);
		}
	}

	@Override
	public void interactionCancels() {
		if(action != null) {
			if(loggerBinding != null) {
				loggerBinding.log(Level.INFO, "Binding cancelled");
			}

			action.cancel();
			if(loggerAction != null) {
				loggerAction.log(Level.INFO, "Action cancelled");
			}
			unbindActionAttributes();

			// The instrument is notified about the cancel of the action.
			instrument.onActionCancelled(action);

			if(isExecute()) {
				if(action instanceof Undoable) {
					((Undoable) action).undo();
					if(loggerAction != null) {
						loggerAction.log(Level.INFO, "Action undone");
					}
				}else {
					throw new MustBeUndoableActionException(action.getClass());
				}
			}

			action = null;
			instrument.interimFeedback();
		}
	}


	@Override
	public void interactionStarts() throws MustCancelStateMachineException {
		final boolean ok = action == null && isActivated() && isConditionRespected();

		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Starting binding: " + ok);
		}

		if(ok) {
			action = createAction();
			initAction();
			if(loggerAction != null) {
				loggerAction.log(Level.INFO, "Action created and init: " + action);
			}
			interimFeedback();
		}else {
			if(isStrictStart()) {
				if(loggerBinding != null) {
					loggerBinding.log(Level.INFO, "Cancelling starting interaction: " + interaction);
				}
				throw new MustCancelStateMachineException();
			}
		}
	}


	@Override
	public void interactionStops() {
		final boolean ok = isConditionRespected();
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding stops with condition: " + ok);
		}
		if(ok) {
			if(action == null) {
				action = createAction();
				initAction();
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Action created and init: " + action);
				}
			}

			if(!execute) {
				updateAction();
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Action updated: " + action);
				}
			}

			executeAction(action);
			unbindActionAttributes();
			action = null;
			instrument.interimFeedback();
		}else {
			if(action != null) {
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Cancelling the action: " + action);
				}
				action.cancel();
				unbindActionAttributes();
				instrument.onActionCancelled(action);
				action = null;
				instrument.interimFeedback();
			}
		}
	}


	private void executeAction(final Action act) {
		boolean ok = act.doIt();
		if(loggerAction != null) {
			loggerAction.log(Level.INFO, "Action execution did it: " + ok);
		}

		if(ok) {
			instrument.onActionExecuted(act);
			act.done();
			instrument.onActionDone(act);
		}

		ok = act.hadEffect();

		if(loggerAction != null) {
			loggerAction.log(Level.INFO, "Action execution had effect: " + ok);
		}
		if(ok) {
			if(act.getRegistrationPolicy() != Action.RegistrationPolicy.NONE) {
				ActionsRegistry.INSTANCE.addAction(act, instrument);
				instrument.onActionAdded(act);
			}else {
				ActionsRegistry.INSTANCE.unregisterActions(act);
			}
			act.followingActions().forEach(this::executeAction);
		}
	}


	@Override
	public void interactionUpdates() {
		final boolean ok = isConditionRespected();

		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding updates with condition: " + ok);
		}

		if(ok) {
			if(action == null) {
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Action creation");
				}
				action = createAction();
				initAction();
			}

			if(loggerAction != null) {
				loggerAction.log(Level.INFO, "Action update");
			}

			updateAction();

			if(execute && action.canDo()) {
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Action execution");
				}
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
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Binding Activated: " + activ);
		}

		interaction.setActivated(activ);
	}


	@Override
	public N getInstrument() {
		return instrument;
	}
}
