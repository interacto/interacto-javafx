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

public interface CmdUpdateBinder<W, C extends Command> extends CmdUpdateBinderBuilder<W, C> {
	@Override
	CmdUpdateBinder<W, C> then(final Consumer<C> update);

	@Override
	CmdUpdateBinder<W, C> continuousExecution();

	@Override
	CmdUpdateBinder<W, C> strictStart();

	@Override
	CmdUpdateBinder<W, C> throttle(final long timeout);

	@Override
	CmdUpdateBinder<W, C> first(final Consumer<C> initCmdFct);

	@Override
	CmdUpdateBinder<W, C> on(final W... widgets);

	@Override
	CmdUpdateBinder<W, C> on(final ObservableList<? extends W> widgets);

	@Override
	CmdUpdateBinder<W, C> consume();

	@Override
	CmdUpdateBinder<W, C> when(final BooleanSupplier whenPredicate);

	@Override
	CmdUpdateBinder<W, C> log(final LogLevel... level);

	@Override
	CmdUpdateBinder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	CmdUpdateBinder<W, C> help(final HelpAnimation animation);

	@Override
	CmdUpdateBinder<W, C> help(final Pane helpPane);

	@Override
	CmdUpdateBinder<W, C> end(final Consumer<C> onEnd);

	@Override
	CmdUpdateBinder<W, C> end(final Runnable endFct);

	<I extends JfxInteraction<D, ?>, D extends InteractionData> InteractionCmdUpdateBinder<W, C, I, D> usingInteraction(final Supplier<I> interactionSupplier);
}
