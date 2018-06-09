/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.control.TextInputControl;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.TextInputChanged;
import org.malai.javafx.interaction.library.WidgetData;

/**
 * The binding builder to create bindings between a text input interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class TextInputBinder<C extends CommandImpl, W extends TextInputControl> extends UpdateBinder<W, C, TextInputChanged, WidgetData<TextInputControl>,
			TextInputBinder<C, W>> {
	public TextInputBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(i -> cmdClass.get(), instrument);
	}

	public TextInputBinder(final Function<WidgetData<TextInputControl>, C> cmdCreation, final JfxInstrument instrument) {
		super(new TextInputChanged(), cmdCreation, instrument);
	}
}
