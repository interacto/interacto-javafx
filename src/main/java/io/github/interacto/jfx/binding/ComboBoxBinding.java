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
package io.github.interacto.jfx.binding;

import io.github.interacto.command.Command;
import io.github.interacto.jfx.instrument.JfxInstrument;
import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.jfx.interaction.library.ComboBoxSelected;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.List;
import java.util.function.Function;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

/**
 * A widget binding for combo-boxes.
 * @param <C> The command to produce.
 * @param <I> The instrument.
 * @author Arnaud Blouin
 */
public abstract class ComboBoxBinding<C extends Command, I extends JfxInstrument> extends JfXWidgetBinding<C, ComboBoxSelected, WidgetData<ComboBox<?>>> {
	/**
	 * Creates a combo-box binding.
	 * @param cmdCreation The function that produces the commands
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public ComboBoxBinding(final Function<WidgetData<ComboBox<?>>, C> cmdCreation, final List<Node> widgets) {
		this(cmdCreation, widgets, false, null);
	}

	/**
	 * Creates a combo-box binding.
	 * @param cmdCreation The function that produces the commands
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public ComboBoxBinding(final Function<WidgetData<ComboBox<?>>, C> cmdCreation, final List<Node> widgets,
						final boolean help, final HelpAnimation animation) {
		super(false, new ComboBoxSelected(), cmdCreation, widgets, help, animation);
	}
}
