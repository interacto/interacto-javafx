/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.binding;

import io.github.interacto.command.CommandImpl;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the command.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonNodeBinding<C extends CommandImpl, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
			extends JfXWidgetBinding<C, I, D> {
	private final BiConsumer<D, C> execInitCmd;
	private final BiConsumer<D, C> execUpdateCmd;
	private final Predicate<D> checkInteraction;
	private final Consumer<D> cancelFct;
	private final Consumer<D> endOrCancelFct;
	private final Consumer<D> onEnd;
	private final boolean strictStart;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param cmdFunction The function that produces the command.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final boolean exec, final I interaction, final BiConsumer<D, C> initCmdFct,
							final BiConsumer<D, C> updateCmdFct, final Predicate<D> check, final Consumer<D> onEndFct, final Function<D, C> cmdFunction,
							final Consumer<D> cancel, final Consumer<D> endOrCancel, final List<Node> widgets,
							final List<ObservableList<? extends Node>> additionalWidgets, final boolean asyncExec, final boolean strict,
							final long timeoutThrottle, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation) {
		super(exec, interaction, cmdFunction, widgets, help, animation);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		interaction.setThrottleTimeout(timeoutThrottle);
		configureLoggers(loggers);

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
		}
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param exec Specifies if the command must be execute or update on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The windows used by the binding. Cannot be null.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final boolean exec, final I interaction, final List<Window> widgets,
							final BiConsumer<D, C> initCmdFct, final BiConsumer<D, C> updateCmdFct, final Predicate<D> check, final Consumer<D> onEndFct,
							final Function<D, C> cmdFunction, final Consumer<D> cancel, final Consumer<D> endOrCancel,
							final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers, final boolean help,
							final HelpAnimation animation) {
		super(exec, widgets, interaction, cmdFunction, animation, help);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		checkInteraction = check == null ? i -> true : check;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
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
	protected C createCommand() {
		final C currentCmd;

		if(cmdProducer == null) {
			currentCmd = super.createCommand();
		}else {
			currentCmd = cmdProducer.apply(getInteraction().getData());
		}

		return currentCmd;
	}

	@Override
	public void first() {
		if(execInitCmd != null) {
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
	public void end() {
		if(onEnd != null) {
			onEnd.accept(getInteraction().getData());
		}
	}

	@Override
	public void cancel() {
		if(cancelFct != null) {
			cancelFct.accept(getInteraction().getData());
		}
	}

	@Override
	public void endOrCancel() {
		if(endOrCancelFct != null) {
			endOrCancelFct.accept(getInteraction().getData());
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
	public String toString() {
		return "JFxAnonNodeBinding {" + interaction + " -> " + cmdProducer + '}';
	}
}
