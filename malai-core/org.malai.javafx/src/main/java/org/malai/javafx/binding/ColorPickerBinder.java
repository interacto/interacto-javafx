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

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ColorPicked;

/**
 * The binding builder to create bindings between a color picker interaction and a given action.
 * @param <A> The type of the action to produce.
 * @author Arnaud Blouin
 */
public class ColorPickerBinder<A extends ActionImpl> extends Binder<ColorPicker, A, ColorPicked> {
	public ColorPickerBinder(final Class<A> action, final JfxInstrument instrument) {
		super(action, new ColorPicked(), instrument);
	}

	@Override
	public ColorPickerBinder<A> on(final ColorPicker... widget) {
		super.on(widget);
		return this;
	}

	@Override
	public ColorPickerBinder<A> on(final ObservableList<Node> widgets) {
		super.on(widgets);
		return this;
	}

	@Override
	public ColorPickerBinder<A> map(final Function<ColorPicked, A> actionFunction) {
		actionProducer = actionFunction;
		return this;
	}

	@Override
	public ColorPickerBinder<A> first(final Consumer<A> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ColorPickerBinder<A> first(final BiConsumer<A, ColorPicked> initActionFct) {
		super.first(initActionFct);
		return this;
	}

	@Override
	public ColorPickerBinder<A> when(final Predicate<ColorPicked> checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ColorPickerBinder<A> when(final BooleanSupplier checkAction) {
		super.when(checkAction);
		return this;
	}

	@Override
	public ColorPickerBinder<A> async() {
		super.async();
		return this;
	}

	@Override
	public ColorPickerBinder<A> end(final BiConsumer<A, ColorPicked> onEndFct) {
		super.end(onEndFct);
		return this;
	}

	@Override
	public JfXWidgetBinding<A, ColorPicked, ?> bind() throws IllegalAccessException, InstantiationException {
		final JFxAnonNodeBinding<A, ColorPicked, JfxInstrument> binding = new JFxAnonNodeBinding<>(instrument, false,
			actionClass, interaction, initAction, null, checkConditions, onEnd, actionProducer, null, null,
			widgets.stream().map(w -> (Node) w).collect(Collectors.toList()), additionalWidgets, async);
		instrument.addBinding(binding);
		return binding;
	}
}
