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
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.scene.input.KeyCode;
import org.malai.command.CommandImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysData;
import org.malai.javafx.interaction.library.KeysPressed;

/**
 * The base binding builder to create bindings between a keys pressure interaction and a given command.
 * @param <C> The type of the command to produce.
 * @param <W> The type of the widget to bind.
 * @author Arnaud Blouin
 */
public abstract class KeyBinder<W, C extends CommandImpl, B extends KeyBinder<W, C, B>> extends Binder<W, C, KeysPressed, KeysData, B> {
	final Collection<KeyCode> codes;
	final Predicate<KeysData> checkCode;

	public KeyBinder(final Function<KeysData, C> cmdCreation, final JfxInstrument instrument) {
		super(new KeysPressed(), cmdCreation, instrument);
		codes = new ArrayList<>();

		checkCode = interaction -> {
			final List<KeyCode> keys = interaction.getKeyCodes();

			return (codes.isEmpty() || codes.size() == keys.size() && keys.containsAll(codes)) && (checkConditions == null || checkConditions.test(interaction));
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
