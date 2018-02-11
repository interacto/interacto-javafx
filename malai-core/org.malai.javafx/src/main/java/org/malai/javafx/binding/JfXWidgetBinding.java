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
package org.malai.javafx.binding;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.binding.WidgetBindingImpl;
import org.malai.error.ErrorCatcher;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.help.HelpAnimationPlayer;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * Base of a widget binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfXWidgetBinding<A extends ActionImpl, I extends JfxInteraction<?, ?>, N extends JfxInstrument> extends WidgetBindingImpl<A, I, N> {
	/** The executor service used to execute action async. Do not access directly (lazy instantiation). Use its private getter instead. */
	private static ExecutorService executorService = null;

	private static ExecutorService getActionExecutor() {
		if(executorService == null) {
			executorService = Executors.newSingleThreadExecutor();
		}
		return executorService;
	}

	protected final BooleanProperty activation;
	protected boolean withHelp;
	protected HelpAnimation customAnimation;
	/** The property used to displayed a message while executing an action async. */
	protected StringProperty progressMsgProp;
	/** The property used to make a progress bar progressing while executing an action async. */
	protected DoubleProperty progressBarProp;
	/** The button used to stop the action executed async. May be null. */
	protected Button cancelActionButton;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							final List<Node> widgets, final boolean help, final HelpAnimation animation) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		initActivation();
		withHelp = help;
		customAnimation = animation;
		interaction.registerToNodes(widgets);
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction, final boolean help,
							final HelpAnimation animation, final Node... widgets) throws InstantiationException, IllegalAccessException {
		this(ins, exec, clazzAction, interaction, Arrays.asList(widgets), help, animation);
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param windows The windows concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final List<Window> windows, final Class<A> clazzAction,
							final I interaction, final HelpAnimation animation, final boolean help) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		withHelp = help;
		customAnimation = animation;
		initActivation();
		interaction.registerToWindows(windows);
	}

	/**
	 * The property used to displayed a message while executing an action async.
	 * @param progressMsgProp The string property to be bound. Nothing done if null or already bound.
	 */
	public void setProgressMsgProp(final StringProperty progressMsgProp) {
		this.progressMsgProp = progressMsgProp;
	}

	/**
	 * The property used to make a progress bar progressing while executing an action async.
	 * @param progressBarProp The double property to be bound. Nothing done if null or already bound.
	 */
	public void setProgressBarProp(final DoubleProperty progressBarProp) {
		this.progressBarProp = progressBarProp;
	}

	/**
	 * The button used to stop the action executed async.
	 * @param cancelActionButton The cancel button. May be null.
	 */
	public void setCancelActionButton(final Button cancelActionButton) {
		this.cancelActionButton = cancelActionButton;
	}

	@Override
	public void setActivated(final boolean activ) {
		if(activation != null && !activation.isBound()) {
			activation.set(activ);
		}
		if(activation != null && withHelp) {
			final HelpAnimation anim = customAnimation == null ? interaction.getHelpAnimation().orElse(null) : customAnimation;
			if(anim != null) {
				if(activation.get()) {//TODO heuristics
					HelpAnimationPlayer.INSTANCE.add(anim);
				}else {
					HelpAnimationPlayer.INSTANCE.stop(anim);
				}
			}
		}
	}


	@Override
	protected void executeActionAsync(final Action act) {
		final BindingTask task = new BindingTask(act);
		final boolean progressBound = progressBarProp != null && !progressBarProp.isBound();
		final boolean msgBound = progressMsgProp != null && !progressMsgProp.isBound();
		final EventHandler<ActionEvent> cancelEvent = evt -> task.cancel();

		if(progressBound) {
			progressBarProp.bind(task.progressProperty());
		}

		if(msgBound) {
			progressMsgProp.bind(task.messageProperty());
		}

		if(cancelActionButton != null) {
			cancelActionButton.addEventHandler(ActionEvent.ACTION, cancelEvent);
			cancelActionButton.setVisible(true);
		}

		getActionExecutor().submit(task);

		new Thread(() -> {
			boolean ok;

			try {
				ok = task.get();
			}catch(final CancellationException | InterruptedException ex1) {
				if(loggerAction != null) {
					loggerAction.log(Level.INFO, "Action execution cancelled: " + act);
				}
				ok = false;
			}catch(final ExecutionException ex2) {
				ErrorCatcher.INSTANCE.reportError(ex2);
				ok = false;
			}

			if(cancelActionButton != null) {
				cancelActionButton.removeEventHandler(ActionEvent.ACTION, cancelEvent);
				cancelActionButton.setVisible(false);
			}

			if(progressBound) {
				progressBarProp.unbind();
			}

			if(msgBound) {
				progressMsgProp.unbind();
			}

			afterActionExecuted(act, ok);
		}).start();
	}

	private void initActivation() {
		activation.addListener((observable, oldValue, newValue) -> {
			if(oldValue != newValue) {
				interaction.setActivated(newValue);
			}
		});
	}

	@Override
	public boolean when() {
		return true;
	}

	public BooleanProperty activationProperty() {
		return activation;
	}
}
