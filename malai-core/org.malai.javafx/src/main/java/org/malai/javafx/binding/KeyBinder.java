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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.input.KeyCode;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.KeysPressure;

/**
 * The base binding builder to create bindings between a keys pressure interaction and a given action.
 * @param <A> The type of the action to produce.
 * @param <W> The type of the widget to bind.
 * @author Arnaud Blouin
 */
public abstract class KeyBinder<W, A extends ActionImpl> extends Binder<W, A, KeysPressure> {
	final Collection<KeyCode> codes;
	final Predicate<KeysPressure> checkCode;

	public KeyBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, KeysPressure.class, instrument);
		codes = new ArrayList<>();

		checkCode = interaction -> {
			final List<KeyCode> keys = interaction.getKeyCodes();
			return codes.size() == keys.size() && codes.stream().allMatch(code -> keys.contains(code)) &&
				(checkConditions == null || checkConditions.test(interaction));
		};
	}

	public KeyBinder<W, A> with(final KeyCode... code) {
		codes.addAll(Arrays.asList(code));
		return this;
	}

	@Override
	public KeyBinder<W, A> on(final W... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public KeyBinder<W, A> async() {
		super.async();
		return this;
	}

	@Override
	public KeyBinder<W, A> init(final Consumer<A> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public KeyBinder<W, A> init(final BiConsumer<A, KeysPressure> initActionFct) {
		super.init(initActionFct);
		return this;
	}

	@Override
	public KeyBinder<W, A> check(final Predicate<KeysPressure> checkAction) {
		super.check(checkAction);
		return this;
	}
}
