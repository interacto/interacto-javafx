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

import io.github.interacto.command.Command;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.api.BaseBinder;
import io.github.interacto.jfx.binding.api.CmdBinder;
import io.github.interacto.jfx.binding.api.InteractionBinder;
import io.github.interacto.jfx.binding.api.InteractionCmdBinder;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * The base class that defines the concept of binding builder (called binder).
 * @param <W> The type of the widgets.
 * @param <C> The type of the action to produce.
 * @param <I> The type of the user interaction to bind.
 * @author Arnaud Blouin
 */
abstract class Binder<W, C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
		implements CmdBinder<W, C>, InteractionBinder<W, I, D>, InteractionCmdBinder<W, C, I, D>, BaseBinder<W> {
	protected BiConsumer<D, C> initCmd;
	protected Predicate<D> checkConditions;
	protected Function<D, C> cmdProducer;
	protected List<W> widgets;
	protected Supplier<I> interactionSupplier;
	protected JfxInstrument instrument;
	protected boolean async;
	protected Consumer<D> onEnd;
	protected List<ObservableList<? extends W>> additionalWidgets;
	protected EnumSet<LogLevel> logLevels;
	protected HelpAnimation helpAnimation;
	protected boolean withHelp;
	protected DoubleProperty progressProp;
	protected StringProperty msgProp;
	protected Button cancel;

	Binder(final JfxInstrument ins) {
		super();
		widgets = new ArrayList<>();
		instrument = ins;
		async = false;
		checkConditions = null;
		initCmd = null;
		onEnd = null;
		additionalWidgets = null;
		helpAnimation = null;
		withHelp = false;
	}

	@Override
	public Binder<W, C, I, D> on(final W... widget) {
		widgets.addAll(Arrays.asList(widget));
		return this;
	}


	@Override
	public Binder<W, C, I, D> on(final ObservableList<? extends W> widgets) {
		if(additionalWidgets == null) {
			additionalWidgets = new ArrayList<>();
		}
		additionalWidgets.add(widgets);
		return this;
	}


	@Override
	public Binder<W, C, I, D> first(final Consumer<C> initCmdFct) {
		if(initCmdFct != null) {
			initCmd = (i, c) -> initCmdFct.accept(c);
		}
		return this;
	}

	@Override
	public Binder<W, C, I, D> first(final BiConsumer<D, C> initCmdFct) {
		initCmd = initCmdFct;
		return this;
	}

	@Override
	public Binder<W, C, I, D> when(final Predicate<D> checkCmd) {
		checkConditions = checkCmd;
		return this;
	}

	@Override
	public Binder<W, C, I, D> when(final BooleanSupplier checkCmd) {
		checkConditions = i -> checkCmd.getAsBoolean();
		return this;
	}

	@Override
	public Binder<W, C, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		async = true;
		this.progressProp = progressProp;
		this.msgProp = msgProp;
		this.cancel = cancel;
		return this;
	}

	@Override
	public Binder<W, C, I, D> end(final Consumer<D> onEndFct) {
		onEnd = onEndFct;
		return this;
	}

	@Override
	public Binder<W, C, I, D> log(final LogLevel level) {
		if(logLevels == null) {
			logLevels = EnumSet.noneOf(LogLevel.class);
		}
		logLevels.add(level);
		return this;
	}

	@Override
	public Binder<W, C, I, D> help(final HelpAnimation animation) {
		helpAnimation = animation;
		withHelp = animation != null;
		return this;
	}

	@Override
	public Binder<W, C, I, D> help(final Pane helpPane) {
		withHelp = true;
		return this;
	}

	@Override
	public <I2 extends JfxInteraction<D2, ?, ?>, D2 extends InteractionData> Binder<W, C, I2, D2> usingInteraction(final Supplier<I2> interactionSupplier) {
		this.interactionSupplier = (Supplier<I>) interactionSupplier;
		return (Binder<W, C, I2, D2>) this;
	}

	@Override
	public <C2 extends Command> Binder<W, C2, I, D> toProduce(final Supplier<C2> cmdCreation) {
		cmdProducer = i -> (C) cmdCreation.get();
		return (Binder<W, C2, I, D>) this;
	}


	@Override
	public <C2 extends Command> Binder<W, C2, I, D> toProduce(final Function<D, C2> cmdCreation) {
		cmdProducer = (Function<D, C>) cmdCreation;
		return (Binder<W, C2, I, D>) this;
	}
}
