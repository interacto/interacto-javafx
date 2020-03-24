/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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

import io.github.interacto.command.Command;
import io.github.interacto.error.ErrorCatcher;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.api.LogLevel;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;

abstract class JfxAnonBinding<C extends Command, I extends JfxInteraction<D, ?>, D extends InteractionData> extends JfxWidgetBinding<C, I, D> {
	private final BiConsumer<D, C> execInitCmd;
	private final BiConsumer<D, C> execUpdateCmd;
	private final Predicate<D> checkInteraction;
	private final Consumer<D> cancelFct;
	private final Consumer<D> endOrCancelFct;
	private final BiConsumer<D, C> hadEffectsFct;
	private final BiConsumer<D, C> hadNoEffectFct;
	private final BiConsumer<D, C> cannotExecFct;
	private final BiConsumer<D, C> onEnd;
	private final boolean strictStart;


	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param continuousExec Specifies whether the command must be executed on each evolution of the interaction.
	 * @param interaction The user interaction of the binding.
	 * @param initCmdFct The function that initialises the command to execute. Cannot be null.
	 * @param updateCmdFct The function that updates the command. Can be null.
	 * @param cmdFunction The function that produces the command.
	 */
	JfxAnonBinding(final boolean continuousExec, final I interaction, final BiConsumer<D, C> initCmdFct, final BiConsumer<D, C> updateCmdFct,
		final Predicate<D> check, final BiConsumer<D, C> onEndFct, final Function<D, C> cmdFunction, final Consumer<D> cancel,
		final Consumer<D> endOrCancel, final boolean asyncExec, final boolean strict, final long timeoutThrottle, final Set<LogLevel> loggers,
		final boolean help, final HelpAnimation animation, final BiConsumer<D, C> hadNoEffectFct, final BiConsumer<D, C> hadEffectsFct,
		final BiConsumer<D, C> cannotExecFct, final boolean consumeEvents) {
		super(continuousExec, interaction, cmdFunction, help, animation);
		execInitCmd = initCmdFct;
		execUpdateCmd = updateCmdFct;
		cancelFct = cancel;
		endOrCancelFct = endOrCancel;
		checkInteraction = check == null ? i -> true : check;
		this.hadNoEffectFct = hadNoEffectFct;
		this.hadEffectsFct = hadEffectsFct;
		this.cannotExecFct = cannotExecFct;
		async = asyncExec;
		onEnd = onEndFct;
		strictStart = strict;
		interaction.setThrottleTimeout(timeoutThrottle);
		interaction.setConsumeEvents(consumeEvents);
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
	public void first() {
		if(execInitCmd != null) {
			try {
				execInitCmd.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void then() {
		if(execUpdateCmd != null) {
			try {
				execUpdateCmd.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void end() {
		if(onEnd != null) {
			try {
				onEnd.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void cancel() {
		if(cancelFct != null) {
			try {
				cancelFct.accept(getInteraction().getData());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void endOrCancel() {
		if(endOrCancelFct != null) {
			try {
				endOrCancelFct.accept(getInteraction().getData());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void ifCmdHadNoEffect() {
		if(hadNoEffectFct != null) {
			try {
				hadNoEffectFct.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void ifCmdHadEffects() {
		if(hadEffectsFct != null) {
			try {
				hadEffectsFct.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public void ifCannotExecuteCmd() {
		if(cannotExecFct != null) {
			try {
				cannotExecFct.accept(getInteraction().getData(), getCommand());
			}catch(final Exception ex) {
				ErrorCatcher.getInstance().reportError(ex);
			}
		}
	}

	@Override
	public boolean when() {
		try {
			final boolean ok = checkInteraction == null || checkInteraction.test(getInteraction().getData());
			if(loggerBinding != null) {
				loggerBinding.log(Level.INFO, "Checking condition: " + ok);
			}
			return ok;
		}catch(final Exception ex) {
			ErrorCatcher.getInstance().reportError(ex);
			return false;
		}
	}

	@Override
	public String toString() {
		return "JFxAnonBinding {" + interaction + " -> " + cmdProducer + '}';
	}
}
