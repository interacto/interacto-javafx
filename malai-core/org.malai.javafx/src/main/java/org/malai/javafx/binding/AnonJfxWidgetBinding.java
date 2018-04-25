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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.command.AnonCommand;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.InteractionData;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.logging.LogLevel;

public class AnonJfxWidgetBinding<I extends JfxInteraction<D, ?, ?>, N extends JfxInstrument, D extends InteractionData>
			extends JFxAnonNodeBinding<AnonCommand, I, N, D> {
	private final Runnable anonCmd;

	public AnonJfxWidgetBinding(final N ins, final boolean exec, final I interaction, final Runnable cmd, final BiConsumer<D, AnonCommand> initCmdFct,
								final BiConsumer<D, AnonCommand> updateCmdFct, final Predicate<D> check, final BiConsumer<D, AnonCommand> onEndFct,
								final Function<D, AnonCommand> cmdFunction, final BiConsumer<D, AnonCommand> cancel,
								final BiConsumer<D, AnonCommand> endOrCancel, final Runnable feedback, final List<Node> widgets,
								final List<ObservableList<? extends Node>> additionalWidgets, final boolean async, final boolean strict,
								final long throttleTimeout, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation) {
		super(ins, exec, interaction, AnonCommand.class, initCmdFct, updateCmdFct, check, onEndFct, cmdFunction, cancel, endOrCancel, feedback,
			widgets, additionalWidgets, async, strict, throttleTimeout, loggers, help, animation);
		anonCmd = Objects.requireNonNull(cmd);
	}

	public AnonJfxWidgetBinding(final N ins, final boolean exec, final I interaction, final Runnable cmd, final List<Window> widgets,
								final BiConsumer<D, AnonCommand> initCmdFct, final BiConsumer<D, AnonCommand> updateCmdFct, final Predicate<D> check,
								final BiConsumer<D, AnonCommand> onEndFct, final Function<D, AnonCommand> cmdFunction, final BiConsumer<D, AnonCommand> cancel,
								final BiConsumer<D, AnonCommand> endOrCancel, final Runnable feedback, final boolean async,
								final boolean strict, final long throttleTimeout, final Set<LogLevel> loggers, final boolean help, final HelpAnimation animation) {
		super(ins, exec, interaction, AnonCommand.class, widgets, initCmdFct, updateCmdFct, check, onEndFct, cmdFunction, cancel, endOrCancel,
			feedback, async, strict, throttleTimeout, loggers, help, animation);
		anonCmd = Objects.requireNonNull(cmd);
	}

	@Override
	protected AnonCommand map() {
		currentCmd = new AnonCommand(anonCmd);
		return currentCmd;
	}
}
