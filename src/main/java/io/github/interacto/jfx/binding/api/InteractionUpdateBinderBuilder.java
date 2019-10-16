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

public interface InteractionUpdateBinderBuilder<W, I extends JfxInteraction<D, ?, ?>, D extends InteractionData>
		extends InteractionBinderBuilder<W, I, D> {
	/**
	 * Defines what to do when a command is cancelled (because the interaction is cancelled).
	 * The undoable command is automatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the building configuration.
	 */
	InteractionUpdateBinderBuilder<W, I, D> cancel(final Consumer<D> cancel);

	/**
	 * Defines what to do when a command is cancelled (because the interaction is cancelled).
	 * The undoable command is automatically cancelled so that nothing must be done on the command.
	 * @return The builder to chain the building configuration.
	 */
	InteractionUpdateBinderBuilder<W, I, D> endOrCancel(final Consumer<D> endOrCancel);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> when(final Predicate<D> whenPredicate);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> end(final Consumer<D> onEnd);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> on(final W... widgets);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> on(final ObservableList<? extends W> widgets);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> when(final BooleanSupplier whenPredicate);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> log(final LogLevel level);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> help(final HelpAnimation animation);

	@Override
	InteractionUpdateBinderBuilder<W, I, D> help(final Pane helpPane);
}
