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

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.logging.LogLevel;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonNodeBinding<A extends ActionImpl, I extends JfxInteraction<?, ?>, N extends JfxInstrument> extends JfXWidgetBinding<A, I, N> {
	private final BiConsumer<A, I> execInitAction;
	private final BiConsumer<A, I> execUpdateAction;
	private final Predicate<I> checkInteraction;
	private final Function<I, A> actionProducer;
	private final BiConsumer<A, I> cancelFct;
	private final BiConsumer<A, I> endOrCancelFct;
	private final Runnable feedbackFct;
	private final BiConsumer<A, I> onEnd;
	private final boolean strictStart;
	/** Used rather than 'action' to catch the action during its creation.
	 * Sometimes (eg onInteractionStops) can create the action, execute it, and forget it.
	 */
	protected A currentAction;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							  final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct,
							  final Predicate<I> check, final BiConsumer<A, I> onEndFct, final Function<I, A> actionFunction,
							  final BiConsumer<A, I> cancel, final BiConsumer<A, I> endOrCancel, final Runnable feedback, final List<Node> widgets,
							  final List<ObservableList<? extends Node>> additionalWidgets, final boolean asyncExec, final boolean strict, final Set<LogLevel> loggers,
							  final boolean help, final HelpAnimation animation)
				throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction, widgets, help, animation);
		execInitAction = initActionFct;
		execUpdateAction = updateActionFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		actionProducer = actionFunction;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentAction = null;
		configureLoggers(loggers);

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
		}
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							  final List<Window> widgets, final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct,
							  final Predicate<I> check, final BiConsumer<A, I> onEndFct, final Function<I, A> actionFunction,
							  final BiConsumer<A, I> cancel, final BiConsumer<A, I> endOrCancel, final Runnable feedback, final boolean asyncExec,
							  final boolean strict, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation)
		throws InstantiationException, IllegalAccessException {
		super(ins, exec, widgets, clazzAction, interaction, animation, help);
		execInitAction = initActionFct;
		execUpdateAction = updateActionFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		actionProducer = actionFunction;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentAction = null;
		configureLoggers(loggers);
	}

	private void configureLoggers(final Set<LogLevel> loggers) {
		if(loggers != null) {
			logAction(loggers.contains(LogLevel.ACTION));
			logBinding(loggers.contains(LogLevel.BINDING));
			interaction.log(loggers.contains(LogLevel.INTERACTION));
		}
	}

	@Override
	public boolean isStrictStart() {
		return strictStart;
	}

	@Override
	protected A map() {
		if(actionProducer == null) {
			currentAction = super.map();
		}else {
			currentAction = actionProducer.apply(getInteraction());
		}
		return currentAction;
	}

	@Override
	public void first() {
		if(execInitAction != null && currentAction != null) {
			execInitAction.accept(getAction(), getInteraction());
		}
	}

	@Override
	public void then() {
		if(execUpdateAction != null) {
			execUpdateAction.accept(getAction(), getInteraction());
		}
	}

	@Override
	public boolean when() {
		final boolean ok = checkInteraction == null || checkInteraction.test(getInteraction());
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Checking condition: " + ok);
		}
		return ok;
	}

	@Override
	public void fsmCancels() {
		if(endOrCancelFct != null && currentAction != null) {
			endOrCancelFct.accept(action, interaction);
		}
		if(cancelFct != null && currentAction != null) {
			cancelFct.accept(action, interaction);
		}
		super.fsmCancels();
		currentAction = null;
	}

	@Override
	public void feedback() {
		if(feedbackFct != null) {
			if(loggerBinding != null) {
				loggerBinding.log(Level.INFO, "Feedback");
			}
			feedbackFct.run();
		}
	}

	@Override
	public void fsmStops() {
		super.fsmStops();
		if(endOrCancelFct != null && currentAction != null) {
			endOrCancelFct.accept(currentAction, getInteraction());
		}
		if(onEnd != null && currentAction != null) {
			onEnd.accept(currentAction, getInteraction());
		}
		currentAction = null;
	}

	@Override
	public String toString() {
		return "JFxAnonNodeBinding in " + instrument + '{' + interaction + " -> " + clazzAction + '}';
	}
}
