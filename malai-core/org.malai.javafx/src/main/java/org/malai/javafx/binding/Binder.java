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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.logging.LogLevel;

/**
 * The base class that defines the concept of binding builder (called binder).
 * @param <W> The type of the widgets.
 * @param <A> The type of the action to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
public abstract class Binder<W, A extends ActionImpl, I extends JfxInteraction<?, ?>, B extends Binder<W, A, I, B>> {
	protected BiConsumer<A, I> initAction;
	protected Predicate<I> checkConditions;
	protected Function<I, A> actionProducer;
	protected final List<W> widgets;
	protected final Class<A> actionClass;
	protected final I interaction;
	protected final JfxInstrument instrument;
	protected boolean async;
	protected BiConsumer<A, I> onEnd;
	protected List<ObservableList<? extends Node>> additionalWidgets;
	protected Set<LogLevel> logLevels;
	protected HelpAnimation helpAnimation;
	protected boolean withHelp;
	protected DoubleProperty progressProp;
	protected StringProperty msgProp;
	protected Button cancel;

	public Binder(final Class<A> action, final I interaction, final JfxInstrument ins) {
		super();
		actionClass = Objects.requireNonNull(action);
		this.interaction = Objects.requireNonNull(interaction);
		widgets = new ArrayList<>();
		instrument = Objects.requireNonNull(ins);
		async = false;
		checkConditions = null;
		initAction = null;
		onEnd = null;
		actionProducer = null;
		additionalWidgets = null;
		helpAnimation = null;
		withHelp = false;
	}

	/**
	 * Specifies the widgets on which the binding must operate.
	 * @param widget The widgets involve in the bindings.
	 * @return The builder to chain the building configuration.
	 */
	@SafeVarargs
	public final B on(final W... widget) {
		widgets.addAll(Arrays.asList(widget));
		return (B) this;
	}


	/**
	 * Specifies the observable list that will contain the widgets on which the binding must operate.
	 * When a widget is added to this list, the added widget is binded to this binding.
	 * When widget is removed from this list, this widget is unbinded from this binding.
	 * @param widgets The observable list of the widgets involved in the bindings.
	 * @return The builder to chain the building configuration.
	 */
	public B on(final ObservableList<? extends Node> widgets) {
		if(additionalWidgets == null) {
			additionalWidgets = new ArrayList<>();
		}
		additionalWidgets.add(widgets);
		return (B) this;
	}


	/**
	 * Specifies how the action is created from the user interaction.
	 * Called a single time per interaction executed (just before 'first' that can still be called to, somehow, configure the action).
	 * Each time the interaction starts, an instance of the action is created and configured by the given callback.
	 * @param actionFunction The function that creates and initialises the action.
	 * This callback takes as arguments the current user interaction.
	 * @return The builder to chain the building configuration.
	 */
	public B map(final Function<I, A> actionFunction) {
		actionProducer = actionFunction;
		return (B) this;
	}

	/**
	 * Specifies the initialisation of the action when the interaction starts.
	 * Each time the interaction starts, an instance of the action is created and configured by the given callback.
	 * @param initActionFct The callback method that initialises the action.
	 * This callback takes as arguments the action to configure.
	 * @return The builder to chain the building configuration.
	 */
	public B first(final Consumer<A> initActionFct) {
		if(initActionFct != null) {
			initAction = (a, i) -> initActionFct.accept(a);
		}
		return (B) this;
	}

	/**
	 * Specifies the initialisation of the action when the interaction starts.
	 * Each time the interaction starts, an instance of the action is created and configured by the given callback.
	 * @param initActionFct The callback method that initialises the action.
	 * This callback takes as arguments both the action and interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	public B first(final BiConsumer<A, I> initActionFct) {
		initAction = initActionFct;
		return (B) this;
	}

	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the action while the interaction is running.
	 * @param checkAction The predicate that checks whether the action can be initialised, updated, or executed.
	 * This predicate takes as arguments the ongoing user interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	public B when(final Predicate<I> checkAction) {
		checkConditions = checkAction;
		return (B) this;
	}

	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the action while the interaction is running.
	 * @param checkAction The predicate that checks whether the action can be initialised, updated, or executed.
	 * @return The builder to chain the building configuration.
	 */
	public B when(final BooleanSupplier checkAction) {
		checkConditions = i -> checkAction.getAsBoolean();
		return (B) this;
	}

	/**
	 * Specifies that the action will be executed in a separated threads.
	 * Beware of UI modifications: UI changes must be done in the JFX UI thread.
	 * @return The builder to chain the building configuration.
	 */
	public B async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		async = true;
		this.progressProp = progressProp;
		this.msgProp = msgProp;
		this.cancel = cancel;
		return (B) this;
	}

	/**
	 * Specifies what to do end when an interaction ends (when the last event of the interaction has occured, but just after
	 * the interaction is reinitialised and the action finally executed and discarded / saved).
	 * @param onEndFct The callback method to specify what to do when an interaction ends.
	 * @return The builder to chain the building configuration.
	 */
	public B end(final BiConsumer<A, I> onEndFct) {
		onEnd = onEndFct;
		return (B) this;
	}

	/**
	 * Specifies the loggings to use.
	 * Several call to 'log' can be done to log different parts:
	 * log(LogLevel.INTERACTION).log(LogLevel.ACTION)
	 * @param level The logging level to use.
	 * @return The builder to chain the building configuration.
	 */
	public B log(final LogLevel level) {
		if(logLevels == null) {
			logLevels = new HashSet<>();
		}
		logLevels.add(level);
		return (B) this;
	}

	/**
	 * Uses the given animation to explain how the binding works.
	 * @param animation The animation to play. If null, the default animation of the user interaction is used (if defined).
	 * @return The builder to chain the building configuration.
	 */
	public B help(final HelpAnimation animation) {
		helpAnimation = animation;
		withHelp = animation != null;
		return (B) this;
	}

	/**
	 * Uses the default help animation of the user interaction to explain how the binding works.
	 * @param helpPane The pane where the animation will be played.
	 * @return The builder to chain the building configuration.
	 */
	public B help(final Pane helpPane) {
		withHelp = true;
		return (B) this;
	}

	/**
	 * Executes the builder to create and install the binding on the instrument.
	 * @throws IllegalArgumentException On issues while creating the actions.
	 */
	public JfXWidgetBinding<A, I, ?> bind() {
		final JFxAnonNodeBinding<A, I, JfxInstrument> binding = new JFxAnonNodeBinding<>(instrument, false,
			actionClass, interaction, initAction, null, checkConditions, onEnd, actionProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, logLevels, withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelActionButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}
