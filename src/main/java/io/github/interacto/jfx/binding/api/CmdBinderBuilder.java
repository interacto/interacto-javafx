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
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface CmdBinderBuilder<W, C extends Command> extends BaseBinderBuilder<W> {
	/**
	 * Specifies the initialisation of the command when the interaction starts.
	 * Each time the interaction starts, an instance of the command is created and configured by the given callback.
	 * @param initCmdFct The callback method that initialises the command.
	 * This callback takes as arguments the command to configure.
	 * @return The builder to chain the building configuration.
	 */
	CmdBinderBuilder<W, C> first(final Consumer<C> initCmdFct);

	@Override
	CmdBinderBuilder<W, C> on(final W... widgets);

	@Override
	CmdBinderBuilder<W, C> on(final ObservableList<? extends W> widgets);

	@Override
	CmdBinderBuilder<W, C> when(final BooleanSupplier whenPredicate);

	@Override
	CmdBinderBuilder<W, C> log(final LogLevel level);

	@Override
	CmdBinderBuilder<W, C> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	@Override
	CmdBinderBuilder<W, C> help(final HelpAnimation animation);

	@Override
	CmdBinderBuilder<W, C> help(final Pane helpPane);
}
