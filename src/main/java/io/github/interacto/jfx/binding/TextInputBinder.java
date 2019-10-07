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
import io.github.interacto.jfx.interaction.library.TextInputChanged;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.control.TextInputControl;

/**
 * The binding builder to create bindings between a text input interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class TextInputBinder<C extends Command, W extends TextInputControl> extends UpdateBinder<W, C, TextInputChanged, WidgetData<TextInputControl>,
			TextInputBinder<C, W>> {
	public TextInputBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(i -> cmdClass.get(), instrument);
	}

	public TextInputBinder(final Function<WidgetData<TextInputControl>, C> cmdCreation, final JfxInstrument instrument) {
		super(new TextInputChanged(), cmdCreation, instrument);
	}
}
