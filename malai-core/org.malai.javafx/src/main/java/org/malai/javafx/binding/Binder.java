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
import org.malai.command.CommandImpl;
import org.malai.interaction.InteractionData;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.logging.LogLevel;

/**
 * The base class that defines the concept of binding builder (called binder).
 * @param <W> The type of the widgets.
 * @param <C> The type of the action to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
public abstract class Binder<W, C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData, B extends Binder<W, C, I, D, B>> {
	protected BiConsumer<D, C> initCmd;
	protected Predicate<D> checkConditions;
	protected final Function<D, C> cmdProducer;
	protected final List<W> widgets;
	protected final I interaction;
	protected final JfxInstrument instrument;
	protected boolean async;
	protected BiConsumer<D, C> onEnd;
	protected List<ObservableList<? extends Node>> additionalWidgets;
	protected Set<LogLevel> logLevels;
	protected HelpAnimation helpAnimation;
	protected boolean withHelp;
	protected DoubleProperty progressProp;
	protected StringProperty msgProp;
	protected Button cancel;

	public Binder(final I interaction, final Function<D, C> cmdCreation, final JfxInstrument ins) {
		super();
		cmdProducer = Objects.requireNonNull(cmdCreation);
		this.interaction = Objects.requireNonNull(interaction);
		widgets = new ArrayList<>();
		instrument = Objects.requireNonNull(ins);
		async = false;
		checkConditions = null;
		initCmd = null;
		onEnd = null;
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
	 * Specifies the initialisation of the command when the interaction starts.
	 * Each time the interaction starts, an instance of the command is created and configured by the given callback.
	 * @param initCmdFct The callback method that initialises the command.
	 * This callback takes as arguments the command to configure.
	 * @return The builder to chain the building configuration.
	 */
	public B first(final Consumer<C> initCmdFct) {
		if(initCmdFct != null) {
			initCmd = (i, c) -> initCmdFct.accept(c);
		}
		return (B) this;
	}

	/**
	 * Specifies the initialisation of the command when the interaction starts.
	 * Each time the interaction starts, an instance of the command is created and configured by the given callback.
	 * @param initCmdFct The callback method that initialises the command.
	 * This callback takes as arguments both the command and interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	public B first(final BiConsumer<D, C> initCmdFct) {
		initCmd = initCmdFct;
		return (B) this;
	}

	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the command while the interaction is running.
	 * @param checkCmd The predicate that checks whether the command can be initialised, updated, or executed.
	 * This predicate takes as arguments the ongoing user interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	public B when(final Predicate<D> checkCmd) {
		checkConditions = checkCmd;
		return (B) this;
	}

	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the command while the interaction is running.
	 * @param checkCmd The predicate that checks whether the command can be initialised, updated, or executed.
	 * @return The builder to chain the building configuration.
	 */
	public B when(final BooleanSupplier checkCmd) {
		checkConditions = i -> checkCmd.getAsBoolean();
		return (B) this;
	}

	/**
	 * Specifies that the command will be executed in a separated threads.
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
	 * the interaction is reinitialised and the command finally executed and discarded / saved).
	 * @param onEndFct The callback method to specify what to do when an interaction ends.
	 * @return The builder to chain the building configuration.
	 */
	public B end(final BiConsumer<D, C> onEndFct) {
		onEnd = onEndFct;
		return (B) this;
	}

	/**
	 * Specifies the loggings to use.
	 * Several call to 'log' can be done to log different parts:
	 * log(LogLevel.INTERACTION).log(LogLevel.COMMAND)
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
	 * @throws IllegalArgumentException On issues while creating the commands.
	 */
	public JfXWidgetBinding<C, I, ?, D> bind() {
		final JFxAnonNodeBinding<C, I, JfxInstrument, D> binding = new JFxAnonNodeBinding<>(instrument, false, interaction, initCmd,
			null, checkConditions, onEnd, cmdProducer, null, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async, false, 0L, logLevels,
			withHelp, helpAnimation);
		binding.setProgressBarProp(progressProp);
		binding.setProgressMsgProp(msgProp);
		binding.setCancelCmdButton(cancel);
		instrument.addBinding(binding);
		return binding;
	}
}
