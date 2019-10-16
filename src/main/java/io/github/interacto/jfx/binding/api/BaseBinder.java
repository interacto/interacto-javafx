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
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface BaseBinder<W> extends BaseBinderBuilder<W> {
	@Override
	BaseBinder<W> on(final W... widgets);

	@Override
	BaseBinder<W> on(final ObservableList<? extends W> widgets);

	@Override
	BaseBinder<W> when(final BooleanSupplier whenPredicate);

	@Override
	BaseBinder<W> log(final LogLevel level);

	@Override
	BaseBinder<W> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	BaseBinder<W> help(final HelpAnimation animation);

	@Override
	BaseBinder<W> help(final Pane helpPane);

	<C extends Command> CmdBinder<W, C> toProduce(final Supplier<C> cmdSupplier);

	<I extends JfxInteraction<D, ?, ?>, D extends InteractionData> InteractionBinder<W, I, D> usingInteraction(final Supplier<I> interactionSupplier);
}
