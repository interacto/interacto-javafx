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
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface CmdBinder<W, C extends Command> extends CmdBinderBuilder<W, C> {
	@Override
	CmdBinder<W, C> first(final Consumer<C> initCmdFct);

	@Override
	CmdBinder<W, C> end(final Consumer<C> onEnd);

	@Override
	CmdBinder<W, C> end(final Runnable endFct);

	@Override
	CmdBinder<W, C> on(final W... widgets);

	@Override
	CmdBinder<W, C> on(final ObservableList<? extends W> widgets);

	@Override
	CmdBinder<W, C> consume();

	@Override
	CmdBinder<W, C> when(final BooleanSupplier whenPredicate);

	@Override
	CmdBinder<W, C> log(final LogLevel... level);

	@Override
	CmdBinder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	CmdBinder<W, C> help(final HelpAnimation animation);

	@Override
	CmdBinder<W, C> help(final Pane helpPane);

	<I extends JfxInteraction<D, ?>, D extends InteractionData> InteractionCmdBinder<W, C, I, D> usingInteraction(final Supplier<I> interactionSupplier);
}
