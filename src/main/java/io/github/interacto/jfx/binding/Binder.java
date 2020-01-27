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
import java.util.Collections;
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
	protected BiConsumer<D, C> hadEffectsFct;
	protected BiConsumer<D, C> hadNoEffectFct;
	protected BiConsumer<D, C> cannotExecFct;
	protected BiConsumer<D, C> onEnd;
	protected List<ObservableList<? extends W>> additionalWidgets;
	protected EnumSet<LogLevel> logLevels;
	protected HelpAnimation helpAnimation;
	protected boolean withHelp;
	protected DoubleProperty progressProp;
	protected StringProperty msgProp;
	protected Button cancel;
	protected BindingsObserver observer;

	Binder(final JfxInstrument ins, final BindingsObserver observer) {
		this(null, null, null, Collections.emptyList(), null,
			ins, false, null, Collections.emptyList(), EnumSet.noneOf(LogLevel.class),
			null, false, null, null, null, null, null, null, observer);
	}

	Binder(final BiConsumer<D, C> initCmd, final Predicate<D> checkConditions, final Function<D, C> cmdProducer, final List<W> widgets,
			final Supplier<I> interactionSupplier, final JfxInstrument instrument, final boolean async, final BiConsumer<D, C> onEnd,
			final List<ObservableList<? extends W>> additionalWidgets, final EnumSet<LogLevel> logLevels, final HelpAnimation helpAnimation,
			final boolean withHelp, final DoubleProperty progressProp, final StringProperty msgProp, final Button cancel,
			final BiConsumer<D, C> hadNoEffectFct, final BiConsumer<D, C> hadEffectsFct, final BiConsumer<D, C> cannotExecFct,
			final BindingsObserver observer) {
		super();
		this.initCmd = initCmd;
		this.checkConditions = checkConditions;
		this.cmdProducer = cmdProducer;
		this.widgets = widgets;
		this.interactionSupplier = interactionSupplier;
		this.instrument = instrument;
		this.async = async;
		this.onEnd = onEnd;
		this.hadEffectsFct = hadEffectsFct;
		this.hadNoEffectFct = hadNoEffectFct;
		this.cannotExecFct = cannotExecFct;
		this.additionalWidgets = additionalWidgets;
		this.logLevels = logLevels;
		this.helpAnimation = helpAnimation;
		this.withHelp = withHelp;
		this.progressProp = progressProp;
		this.msgProp = msgProp;
		this.cancel = cancel;
		this.observer = observer;
	}

	protected abstract Binder<W, C, I, D> duplicate();

	@Override
	public Binder<W, C, I, D> on(final W... widget) {
		final List<W> w;

		if(widgets.isEmpty()) {
			w = List.of(widget);
		}else {
			w = new ArrayList<>(widgets);
			w.addAll(Arrays.asList(widget));
		}

		final Binder<W, C, I, D> dup = duplicate();
		dup.widgets = w;
		return dup;
	}


	@Override
	public Binder<W, C, I, D> on(final ObservableList<? extends W> widgets) {
		final List<ObservableList<? extends W>> adds;

		if(additionalWidgets == null) {
			adds = List.of(widgets);
		}else {
			adds = new ArrayList<>(additionalWidgets);
			adds.add(widgets);
		}

		final Binder<W, C, I, D> dup = duplicate();
		dup.additionalWidgets = adds;
		return dup;
	}


	@Override
	public Binder<W, C, I, D> first(final Consumer<C> initCmdFct) {
		return first((i, c) -> initCmdFct.accept(c));
	}

	@Override
	public Binder<W, C, I, D> first(final BiConsumer<D, C> initCmdFct) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.initCmd = initCmdFct;
		return dup;
	}

	@Override
	public Binder<W, C, I, D> when(final Predicate<D> checkCmd) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.checkConditions = checkCmd;
		return dup;
	}

	@Override
	public Binder<W, C, I, D> when(final BooleanSupplier checkCmd) {
		return when(i -> checkCmd.getAsBoolean());
	}

	@Override
	public Binder<W, C, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.async = true;
		dup.progressProp = progressProp;
		dup.msgProp = msgProp;
		dup.cancel = cancel;
		return dup;
	}

	@Override
	public InteractionCmdBinder<W, C, I, D> ifHadEffects(final BiConsumer<D, C> hadEffectFct) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.hadEffectsFct = hadEffectsFct;
		return dup;
	}

	@Override
	public InteractionCmdBinder<W, C, I, D> ifHadNoEffect(final BiConsumer<D, C> noEffectFct) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.hadNoEffectFct = noEffectFct;
		return dup;
	}

	@Override
	public InteractionCmdBinder<W, C, I, D> end(final Consumer<C> onEnd) {
		return end((i, c) -> onEnd.accept(c));
	}

	@Override
	public Binder<W, C, I, D> end(final Runnable endFct) {
		return end((i, c) -> endFct.run());
	}

	@Override
	public Binder<W, C, I, D> end(final BiConsumer<D, C> onEndFct) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.onEnd = onEndFct;
		return dup;
	}

	@Override
	public Binder<W, C, I, D> log(final LogLevel... level) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.logLevels = EnumSet.copyOf(Arrays.asList(level));
		return dup;
	}

	@Override
	public Binder<W, C, I, D> help(final HelpAnimation animation) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.helpAnimation = animation;
		dup.withHelp = animation != null;
		return dup;
	}

	@Override
	public Binder<W, C, I, D> help(final Pane helpPane) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.withHelp = true;
		return dup;
	}

	@Override
	public <I2 extends JfxInteraction<D2, ?, ?>, D2 extends InteractionData> Binder<W, C, I2, D2> usingInteraction(final Supplier<I2> interactionSupplier) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.interactionSupplier = (Supplier<I>) interactionSupplier;
		return (Binder<W, C, I2, D2>) dup;
	}

	@Override
	public <C2 extends Command> Binder<W, C2, I, D> toProduce(final Supplier<C2> cmdCreation) {
		return toProduce(i -> cmdCreation.get());
	}


	@Override
	public <C2 extends Command> Binder<W, C2, I, D> toProduce(final Function<D, C2> cmdCreation) {
		final Binder<W, C, I, D> dup = duplicate();
		dup.cmdProducer = (Function<D, C>) cmdCreation;
		return (Binder<W, C2, I, D>) dup;
	}
}
