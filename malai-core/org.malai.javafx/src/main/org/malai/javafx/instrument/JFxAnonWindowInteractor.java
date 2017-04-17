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
package org.malai.javafx.instrument;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * This anonymous interactor for windows takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the interactor is quite simple.
 */
public class JFxAnonWindowInteractor<A extends ActionImpl, I extends JfxInteraction, N extends JfxInstrument> extends JfxInteractor<A, I, N> {
	final Consumer<A> execInitAction;

	/**
	 * Creates an interactor. This constructor must initialise the interaction. The interactor is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the interactor.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the interactor. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 */
	public JFxAnonWindowInteractor(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
								   final Consumer<A> initActionFct, final Window... windows) throws InstantiationException, IllegalAccessException {
		super(ins, exec, Arrays.asList(windows), clazzAction, clazzInteraction);
		execInitAction = Objects.requireNonNull(initActionFct);
	}

	@Override
	public void initAction() {
		execInitAction.accept(getAction());
	}
}
