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
import org.malai.interaction.InteractionData;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.logging.LogLevel;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonNodeBinding<C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, N extends JfxInstrument, D extends InteractionData>
			extends JfXWidgetBinding<C, I, N, D> {
	private final BiConsumer<D, C> execInitCmd;
	private final BiConsumer<D, C> execUpdateCmd;
	private final Predicate<D> checkInteraction;
	private final BiConsumer<D, C> cancelFct;
	private final BiConsumer<D, C> endOrCancelFct;
	private final Runnable feedbackFct;
	private final BiConsumer<D, C> onEnd;
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
	 * @param interaction The user interaction of the binding.
	 * @param cmdFunction The function that produces the command.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final I interaction, final BiConsumer<D, C> initCmdFct,
							  final BiConsumer<D, C> updateCmdFct, final Predicate<D> check, final BiConsumer<D, C> onEndFct, final Function<D, C> cmdFunction,
							  final BiConsumer<D, C> cancel, final BiConsumer<D, C> endOrCancel, final Runnable feedback, final List<Node> widgets,
							  final List<ObservableList<? extends Node>> additionalWidgets, final boolean asyncExec, final boolean strict,
							  final long timeoutThrottle, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation) {
		super(ins, exec, interaction, cmdFunction, widgets, help, animation);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentCmd = null;
		interaction.setThrottleTimeout(timeoutThrottle);
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
	 * @param interaction The user interaction of the binding.
	 * @param widgets The windows used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final I interaction, final List<Window> widgets,
							  final BiConsumer<D, C> initCmdFct, final BiConsumer<D, C> updateCmdFct, final Predicate<D> check, final BiConsumer<D, C> onEndFct,
							  final Function<D, C> cmdFunction, final BiConsumer<D, C> cancel, final BiConsumer<D, C> endOrCancel, final Runnable feedback,
							  final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers, final boolean help,
							  final HelpAnimation animation) {
		super(ins, exec, widgets, interaction, cmdFunction, animation, help);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		feedbackFct = feedback;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		currentCmd = null;
		interaction.setThrottleTimeout(timeoutThrottle);
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
			currentCmd = cmdProducer.apply(getInteraction().getData());
		}
		return currentCmd;
	}

	@Override
	public void first() {
		if(execInitCmd != null && currentCmd != null) {
			execInitCmd.accept(getInteraction().getData(), getCommand());
		}
	}

	@Override
	public void then() {
		if(execUpdateCmd != null) {
			execUpdateCmd.accept(getInteraction().getData(), getCommand());
		}
	}

	@Override
	public boolean when() {
		final boolean ok = checkInteraction == null || checkInteraction.test(getInteraction().getData());
		if(loggerBinding != null) {
			loggerBinding.log(Level.INFO, "Checking condition: " + ok);
		}
		return ok;
	}

	@Override
	public void fsmCancels() {
		if(endOrCancelFct != null && currentCmd != null) {
			endOrCancelFct.accept(interaction.getData(), cmd);
		}
		if(cancelFct != null && currentCmd != null) {
			cancelFct.accept(interaction.getData(), cmd);
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
			endOrCancelFct.accept(getInteraction().getData(), currentCmd);
		}
		if(onEnd != null && currentCmd != null) {
			onEnd.accept(getInteraction().getData(), currentCmd);
		}
		currentCmd = null;
	}

	@Override
	public String toString() {
		return "JFxAnonNodeBinding in " + instrument + '{' + interaction + " -> " + cmdProducer + '}';
	}
}
