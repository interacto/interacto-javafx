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
package io.github.interacto.jfx.binding.api;

import io.github.interacto.command.Command;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.binding.JfxWidgetBinding;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface InteractionCmdUpdateBinder<W, C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
		extends InteractionCmdBinder<W, C, I, D>, CmdUpdateBinderBuilder<W, C>, InteractionUpdateBinderBuilder<W, I, D> {
	/**
	 * Specifies the update of the command on interaction command.
	 * @param update The callback method that updates the action.
	 * This callback takes as arguments the command to update and the ongoing interactions (and its parameters).
	 * @return The builder to chain the building configuration.
	 */
	InteractionCmdUpdateBinder<W, C, I, D> then(final BiConsumer<D, C> update);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> then(final Consumer<C> update);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> continuousExecution();

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> strictStart();

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> consume();

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> throttle(final long timeout);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> first(final BiConsumer<D, C> initCmdFct);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> first(final Consumer<C> initCmdFct);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> on(final W... widgets);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> on(final ObservableList<? extends W> widgets);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> when(final BooleanSupplier whenPredicate);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> log(final LogLevel... level);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> help(final HelpAnimation animation);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> help(final Pane helpPane);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> cancel(final Consumer<D> cancel);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> endOrCancel(final Consumer<D> endOrCancel);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> end(final Runnable endFct);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> when(final Predicate<D> whenPredicate);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> ifHadEffects(final BiConsumer<D, C> hadEffectFct);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> ifHadNoEffect(final BiConsumer<D, C> noEffectFct);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> end(final BiConsumer<D, C> onEnd);

	@Override
	InteractionCmdUpdateBinder<W, C, I, D> end(final Consumer<C> onEnd);

	@Override
	JfxWidgetBinding<C, I, D> bind();
}
