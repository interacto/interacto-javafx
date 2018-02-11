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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import javafx.scene.input.KeyCode;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysPressed;

/**
 * The base binding builder to create bindings between a keys pressure interaction and a given action.
 * @param <A> The type of the action to produce.
 * @param <W> The type of the widget to bind.
 * @author Arnaud Blouin
 */
public abstract class KeyBinder<W, A extends ActionImpl, B extends KeyBinder<W, A, B>> extends Binder<W, A, KeysPressed, B> {
	final Collection<KeyCode> codes;
	final Predicate<KeysPressed> checkCode;

	public KeyBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new KeysPressed(), instrument);
		codes = new ArrayList<>();

		checkCode = interaction -> {
			final List<KeyCode> keys = interaction.getKeyCodes();

			return (codes.isEmpty() || codes.size() == keys.size() && codes.stream().allMatch(code -> keys.contains(code))) &&
				(checkConditions == null || checkConditions.test(interaction));
		};
	}

	/**
	 * Defines key code the widget binding will check. On a key interaction, the typed keys will be check against
	 * the given key code. The set of typed codes must matches the given key codes.
	 * @param codes The key codes to match.
	 * @return The builder.
	 */
	public B with(final KeyCode... codes) {
		this.codes.addAll(Arrays.asList(codes));
		return (B) this;
	}
}
