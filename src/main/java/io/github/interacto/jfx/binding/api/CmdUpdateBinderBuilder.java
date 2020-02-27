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
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface CmdUpdateBinderBuilder<W, C extends Command> extends CmdBinderBuilder<W, C>, BaseUpdateBinderBuilder<W> {
	/**
	 * Specifies the update of the command on interaction updates.
	 * @param update The callback method that updates the command.
	 * This callback takes as arguments the command to update.
	 * @return The builder to chain the building configuration.
	 */
	CmdUpdateBinderBuilder<W, C> then(final Consumer<C> update);

	@Override
	CmdUpdateBinderBuilder<W, C> continuousExecution();

	@Override
	CmdUpdateBinderBuilder<W, C> strictStart();

	@Override
	CmdUpdateBinderBuilder<W, C> throttle(final long timeout);

	@Override
	CmdUpdateBinderBuilder<W, C> first(final Consumer<C> initCmdFct);

	@Override
	CmdUpdateBinderBuilder<W, C> on(final W... widgets);

	@Override
	CmdUpdateBinderBuilder<W, C> end(final Consumer<C> onEnd);

	@Override
	CmdUpdateBinderBuilder<W, C> end(final Runnable endFct);

	@Override
	CmdUpdateBinderBuilder<W, C> consume();

	@Override
	CmdUpdateBinderBuilder<W, C> on(final ObservableList<? extends W> widgets);

	@Override
	CmdUpdateBinderBuilder<W, C> when(final BooleanSupplier whenPredicate);

	@Override
	CmdUpdateBinderBuilder<W, C> log(final LogLevel... level);

	@Override
	CmdUpdateBinderBuilder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	CmdUpdateBinderBuilder<W, C> help(final HelpAnimation animation);

	@Override
	CmdUpdateBinderBuilder<W, C> help(final Pane helpPane);
}
