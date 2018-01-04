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

import java.util.List;
import javafx.scene.Node;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.library.SpinnerValueChanged;

/**
 * A widget binding for spinners.
 * @param <A> The action to produce.
 * @param <I> The instrument.
 * @author Arnaud Blouin
 */
public abstract class SpinnerBinding<A extends ActionImpl, I extends JfxInstrument> extends JfXWidgetBinding<A, SpinnerValueChanged, I> {
	/**
	 * Creates a spinner binding.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public SpinnerBinding(final I ins, final boolean exec, final Class<A> clazzAction, final List<Node> widgets) throws InstantiationException,
		IllegalAccessException {
		this(ins, exec, clazzAction, widgets, false, null);
	}

	/**
	 * Creates a spinner binding.
	 * @param ins The instrument that contains the binding.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public SpinnerBinding(final I ins, final boolean exec, final Class<A> clazzAction, final List<Node> widgets,
						  final boolean help, final HelpAnimation animation) throws InstantiationException,
		IllegalAccessException {
		super(ins, exec, clazzAction, new SpinnerValueChanged(), widgets, help, animation);
	}
}
