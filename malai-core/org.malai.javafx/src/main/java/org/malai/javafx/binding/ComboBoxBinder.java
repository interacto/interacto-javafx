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

import javafx.scene.control.ComboBox;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ComboBoxSelected;

/**
 * The binding builder to create bindings between a combobox interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class ComboBoxBinder<A extends ActionImpl> extends Binder<ComboBox<?>, A, ComboBoxSelected, ComboBoxBinder<A>> {
	public ComboBoxBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new ComboBoxSelected(), instrument);
	}
}
