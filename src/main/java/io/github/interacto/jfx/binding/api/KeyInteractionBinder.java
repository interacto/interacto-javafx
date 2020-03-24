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
package io.github.interacto.jfx.binding.api;

import io.github.interacto.command.Command;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public interface KeyInteractionBinder<W, I extends JfxInteraction<D, ?>, D extends InteractionData> extends KeyInteractionBinderBuilder<W, I, D> {
	@Override
	KeyInteractionBinder<W, I, D> when(final Predicate<D> whenPredicate);

	@Override
	KeyInteractionBinder<W, I, D> on(final W... widgets);

	@Override
	KeyInteractionBinder<W, I, D> on(final ObservableList<? extends W> widgets);

	@Override
	KeyInteractionBinder<W, I, D> when(final BooleanSupplier whenPredicate);

	@Override
	KeyInteractionBinder<W, I, D> log(final LogLevel... level);

	@Override
	KeyInteractionBinder<W, I, D> consume();

	@Override
	KeyInteractionBinder<W, I, D> end(final Runnable endFct);

	@Override
	KeyInteractionBinder<W, I, D> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	KeyInteractionBinder<W, I, D> help(final HelpAnimation animation);

	@Override
	KeyInteractionBinder<W, I, D> help(final Pane helpPane);

	@Override
	KeyInteractionBinder<W, I, D> with(final KeyCode... codes);

	<C extends Command> KeyInteractionCmdBinder<W, C, I, D> toProduce(final Supplier<C> cmdSupplier);

	<C extends Command> KeyInteractionCmdBinder<W, C, I, D> toProduce(final Function<D, C> cmdSupplier);
}
