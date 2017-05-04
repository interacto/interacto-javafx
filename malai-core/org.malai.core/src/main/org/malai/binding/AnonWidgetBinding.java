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
package org.malai.binding;

import java.util.Objects;
import java.util.function.Consumer;
import org.malai.action.ActionImpl;
import org.malai.instrument.Instrument;
import org.malai.interaction.Interaction;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the widget binding is quite simple.
 * @author Arnaud Blouin
 */
public class AnonWidgetBinding<A extends ActionImpl, I extends Interaction, N extends Instrument> extends WidgetBindingImpl<A, I, N> {
	final Consumer<A> execInitAction;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The widget binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param fctInitAction The function executed to initialise the action. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 * @since 0.2
	 */
	public AnonWidgetBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
							 final Consumer<A> fctInitAction) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction);
		execInitAction = Objects.requireNonNull(fctInitAction);
	}

	@Override
	public void initAction() {
		execInitAction.accept(getAction());
	}

	@Override
	public boolean isConditionRespected() {
		return true;
	}
}
