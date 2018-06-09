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
import javafx.scene.control.Button;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ButtonPressed;
import org.malai.javafx.interaction.library.WidgetData;

/**
 * The binding builder to create bindings between a button interaction and a given command.
 * @param <C> The type of the command to produce.
 * @author Arnaud Blouin
 */
public class ButtonBinder<C extends CommandImpl> extends Binder<Button, C, ButtonPressed, WidgetData<Button>, ButtonBinder<C>> {
	public ButtonBinder(final Supplier<C> cmdClass, final JfxInstrument instrument) {
		this(i -> cmdClass.get(), instrument);
	}

	public ButtonBinder(final Function<WidgetData<Button>, C> cmdCreation, final JfxInstrument instrument) {
		super(new ButtonPressed(), cmdCreation, instrument);
	}
}
