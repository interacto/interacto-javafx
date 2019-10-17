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

import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.function.BooleanSupplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public interface BaseBinderBuilder<W> {
	BaseBinderBuilder<W> on(final W... widgets);

	/**
	 * Specifies the observable list that will contain the widgets on which the binding must operate.
	 * When a widget is added to this list, the added widget is binded to this binding.
	 * When widget is removed from this list, this widget is unbinded from this binding.
	 * @param widgets The observable list of the widgets involved in the bindings.
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> on(final ObservableList<? extends W> widgets);

	/**
	 * Specifies the conditions to fulfill to initialise, update, or execute the command while the interaction is running.
	 * @param whenPredicate The predicate that checks whether the command can be initialised, updated, or executed.
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> when(final BooleanSupplier whenPredicate);

	/**
	 * Specifies the loggings to use.
	 * Several call to 'log' can be done to log different parts:
	 * log(LogLevel.INTERACTION).log(LogLevel.COMMAND)
	 * @param level The logging level to use.
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> log(final LogLevel... level);

	/**
	 * Specifies that the command will be executed in a separated threads.
	 * Beware of UI modifications: UI changes must be done in the JFX UI thread.
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> async(final Button cancel, final DoubleProperty progressProp, final StringProperty msgProp);

	/**
	 * Uses the given animation to explain how the binding works.
	 * @param animation The animation to play. If null, the default animation of the user interaction is used (if defined).
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> help(final HelpAnimation animation);

	/**
	 * Uses the default help animation of the user interaction to explain how the binding works.
	 * @param helpPane The pane where the animation will be played.
	 * @return The builder to chain the building configuration.
	 */
	BaseBinderBuilder<W> help(final Pane helpPane);
}
