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
import io.github.interacto.jfx.interaction.library.ColorPicked;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.control.ColorPicker;

/**
 * The binding builder to create bindings between a color picker interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class ColorPickerBinder<C extends Command> extends Binder<ColorPicker, C, ColorPicked, WidgetData<ColorPicker>, ColorPickerBinder<C>> {
	public ColorPickerBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		super(new ColorPicked(), i -> cmdClass.get(), instrument);
	}

	public ColorPickerBinder(final Function<WidgetData<ColorPicker>, C> cmdCreation, final JfxInstrument instrument) {
		super(new ColorPicked(), cmdCreation, instrument);
	}
}
