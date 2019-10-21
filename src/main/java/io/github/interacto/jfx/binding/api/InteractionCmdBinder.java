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
import io.github.interacto.jfx.binding.JfXWidgetBinding;
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

public interface InteractionCmdBinder<W, C extends Command, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
		extends CmdBinderBuilder<W, C>, InteractionBinderBuilder<W, I, D> {
	/**
	 * Specifies the initialisation of the command when the interaction starts.
	 * Each time the interaction starts, an instance of the command is created and configured by the given callback.
	 * @param initCmdFct The callback method that initialises the command.
	 * This callback takes as arguments both the command and interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	InteractionCmdBinder<W, C, I, D> first(final BiConsumer<D, C> initCmdFct);

	InteractionCmdBinder<W, C, I, D> ifHadEffects(final BiConsumer<D, C> hadEffectFct);

	InteractionCmdBinder<W, C, I, D> ifHadNoEffect(final BiConsumer<D, C> noEffectFct);

	/**
	 * Specifies what to do end when an interaction ends (when the last event of the interaction has occured, but just after
	 * the interaction is reinitialised and the command finally executed and discarded / saved).
	 * @param onEnd The callback method to specify what to do when an interaction ends.
	 * @return The builder to chain the building configuration.
	 */
	InteractionCmdBinder<W, C, I, D> end(final BiConsumer<D, C> onEnd);

	@Override
	InteractionCmdBinder<W, C, I, D> first(final Consumer<C> initCmdFct);

	@Override
	InteractionCmdBinder<W, C, I, D> on(final W... widgets);

	@Override
	InteractionCmdBinder<W, C, I, D> end(final Runnable endFct);

	@Override
	InteractionCmdBinder<W, C, I, D> on(final ObservableList<? extends W> widgets);

	@Override
	InteractionCmdBinder<W, C, I, D> when(final BooleanSupplier whenPredicate);

	@Override
	InteractionCmdBinder<W, C, I, D> log(final LogLevel... level);

	@Override
	InteractionCmdBinder<W, C, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	InteractionCmdBinder<W, C, I, D> help(final HelpAnimation animation);

	@Override
	InteractionCmdBinder<W, C, I, D> help(final Pane helpPane);

	@Override
	InteractionCmdBinder<W, C, I, D> when(final Predicate<D> whenPredicate);

	@Override
	InteractionCmdBinder<W, C, I, D> end(final Consumer<C> onEnd);

	/**
	 * Executes the builder to create and install the binding on the instrument.
	 * @throws IllegalArgumentException On issues while creating the binding.
	 */
	JfXWidgetBinding<C, I, D> bind();
}
