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
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.logging.LogLevel;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonNodeBinding<C extends CommandImpl, I extends JfxInteraction<?, ?>, N extends JfxInstrument> extends JfXWidgetBinding<C, I, N> {
	private final BiConsumer<C, I> execInitCmd;
	private final BiConsumer<C, I> execUpdateCmd;
	private final Predicate<I> checkInteraction;
	private final Function<I, C> cmdProducer;
	private final BiConsumer<C, I> cancelFct;
	private final BiConsumer<C, I> endOrCancelFct;
	private final Runnable feedbackFct;
	private final BiConsumer<C, I> onEnd;
	private final boolean strictStart;
	/** Used rather than 'command' to catch the command during its creation.
	 * Sometimes (eg onInteractionStops) can create the command, execute it, and forget it.
	 */
	protected C currentCmd;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param clazzCmd The type of the command that will be created. Used to instantiate the command by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<C> clazzCmd, final I interaction,
							  final BiConsumer<C, I> initCmdFct, final BiConsumer<C, I> updateCmdFct,
							  final Predicate<I> check, final BiConsumer<C, I> onEndFct, final Function<I, C> cmdFunction,
							  final BiConsumer<C, I> cancel, final BiConsumer<C, I> endOrCancel, final Runnable feedback, final List<Node> widgets,
							  final List<ObservableList<? extends Node>> additionalWidgets, final boolean asyncExec, final boolean strict, final Set<LogLevel> loggers,
							  final boolean help, final HelpAnimation animation) {
		super(ins, exec, clazzCmd, interaction, widgets, help, animation);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		cmdProducer = cmdFunction;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentCmd = null;
		configureLoggers(loggers);

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
		}
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param clazzCmd The type of the command that will be created. Used to instantiate the command by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The windows used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<C> clazzCmd, final I interaction,
							  final List<Window> widgets, final BiConsumer<C, I> initCmdFct, final BiConsumer<C, I> updateCmdFct,
							  final Predicate<I> check, final BiConsumer<C, I> onEndFct, final Function<I, C> cmdFunction,
							  final BiConsumer<C, I> cancel, final BiConsumer<C, I> endOrCancel, final Runnable feedback, final boolean asyncExec,
							  final boolean strict, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation) {
		super(ins, exec, widgets, clazzCmd, interaction, animation, help);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		cmdProducer = cmdFunction;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentCmd = null;
		configureLoggers(loggers);
	}

	private void configureLoggers(final Set<LogLevel> loggers) {
		if(loggers != null) {
			logCmd(loggers.contains(LogLevel.COMMAND));
			logBinding(loggers.contains(LogLevel.BINDING));
			interaction.log(loggers.contains(LogLevel.INTERACTION));
		}
	}

	@Override
	public boolean isStrictStart() {
		return strictStart;
	}

	@Override
	protected C map() {
		if(cmdProducer == null) {
			currentCmd = super.map();
		}else {
			currentCmd = cmdProducer.apply(getInteraction());
		}
		return currentCmd;
	}

	@Override
	public void first() {
		if(execInitCmd != null && currentCmd != null) {
			execInitCmd.accept(getCommand(), getInteraction());
		}
	}

	@Override
	public void then() {
		if(execUpdateCmd != null) {
			execUpdateCmd.accept(getCommand(), getInteraction());
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
		if(endOrCancelFct != null && currentCmd != null) {
			endOrCancelFct.accept(cmd, interaction);
		}
		if(cancelFct != null && currentCmd != null) {
			cancelFct.accept(cmd, interaction);
		}
		super.fsmCancels();
		currentCmd = null;
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
		if(endOrCancelFct != null && currentCmd != null) {
			endOrCancelFct.accept(currentCmd, getInteraction());
		}
		if(onEnd != null && currentCmd != null) {
			onEnd.accept(currentCmd, getInteraction());
		}
		currentCmd = null;
	}

	@Override
	public String toString() {
		return "JFxAnonNodeBinding in " + instrument + '{' + interaction + " -> " + clazzCmd + '}';
	}
}
