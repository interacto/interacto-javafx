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

import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface InteractionBinderBuilder<W, I extends JfxInteraction<D, ?, ?>, D extends InteractionData> extends BaseBinderBuilder<W> {
	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the command while the interaction is running.
	 * @param whenPredicate The predicate that checks whether the command can be initialised, updated, or executed.
	 * This predicate takes as arguments the ongoing user interaction involved in the binding.
	 * @return The builder to chain the building configuration.
	 */
	InteractionBinderBuilder<W, I, D> when(final Predicate<D> whenPredicate);


	/**
	 * Specifies what to do end when an interaction ends (when the last event of the interaction has occured, but just after
	 * the interaction is reinitialised and the command finally executed and discarded / saved).
	 * @param onEnd The callback method to specify what to do when an interaction ends.
	 * @return The builder to chain the building configuration.
	 */
	InteractionBinderBuilder<W, I, D> end(final Consumer<D> onEnd);

	@Override
	InteractionBinderBuilder<W, I, D> on(final W... widgets);

	@Override
	InteractionBinderBuilder<W, I, D> on(final ObservableList<? extends W> widgets);

	@Override
	InteractionBinderBuilder<W, I, D> when(final BooleanSupplier whenPredicate);

	@Override
	InteractionBinderBuilder<W, I, D> log(final LogLevel... level);

	@Override
	InteractionBinderBuilder<W, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	InteractionBinderBuilder<W, I, D> help(final HelpAnimation animation);

	@Override
	InteractionBinderBuilder<W, I, D> help(final Pane helpPane);
}
