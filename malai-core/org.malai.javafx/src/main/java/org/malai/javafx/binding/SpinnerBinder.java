/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.binding;

import javafx.scene.control.Spinner;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.SpinnerValueChanged;

/**
 * The binding builder to create bindings between a spinner interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class SpinnerBinder<A extends ActionImpl> extends UpdateBinder<Spinner<?>, A, SpinnerValueChanged, SpinnerBinder<A>> {
	public SpinnerBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new SpinnerValueChanged(), instrument);
	}
}
