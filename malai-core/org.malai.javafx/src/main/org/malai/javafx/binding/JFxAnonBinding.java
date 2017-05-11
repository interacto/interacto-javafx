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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonBinding<A extends ActionImpl, I extends JfxInteraction, N extends JfxInstrument> extends JfXWidgetBinding<A, I, N> {
	private final BiConsumer<A, I> execInitAction;
	private final BiConsumer<A, I> execUpdateAction;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final List<Node> widgets, final Consumer<A> initActionFct) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction, widgets);
		Objects.requireNonNull(initActionFct);
		execInitAction = (a, i) -> initActionFct.accept(a);
		execUpdateAction = null;
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final Consumer<A> initActionFct, final Consumer<A> updateActionFct, final Node... widgets)
							throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction, widgets);
		Objects.requireNonNull(initActionFct);
		execInitAction = (a, i) -> initActionFct.accept(a);
		execUpdateAction = updateActionFct == null ? null : (a, i) -> updateActionFct.accept(a);
	}


	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, List<Window> windows, final Class<A> clazzAction,
						  final Class<I> clazzInteraction, final Consumer<A> initActionFct) throws InstantiationException, IllegalAccessException {
		super(ins, exec, windows, clazzAction, clazzInteraction);
		Objects.requireNonNull(initActionFct);
		execInitAction = (a, i) -> initActionFct.accept(a);
		execUpdateAction = null;
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final Consumer<A> initActionFct, final Window...windows) throws InstantiationException, IllegalAccessException {
		this(ins, exec, Arrays.asList(windows), clazzAction, clazzInteraction, initActionFct);
	}

	/**
	 * Creates an binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final List<Node> widgets, final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct)
			throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction, widgets);
		execInitAction = Objects.requireNonNull(initActionFct);
		execUpdateAction = updateActionFct;
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 * @throws NullPointerException If the given function is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct, final Node... widgets)
				throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction, widgets);
		execInitAction = Objects.requireNonNull(initActionFct);
		execUpdateAction = updateActionFct;
	}


	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, List<Window> windows, final Class<A> clazzAction,
						  final Class<I> clazzInteraction, final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct)
			throws InstantiationException, IllegalAccessException {
		super(ins, exec, windows, clazzAction, clazzInteraction);
		execInitAction = Objects.requireNonNull(initActionFct);
		execUpdateAction = updateActionFct;
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param clazzInteraction The type of the interaction that will be created. Used to instantiate the interaction by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param windows The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonBinding(final N ins, final boolean exec, final Class<A> clazzAction, final Class<I> clazzInteraction,
						  final BiConsumer<A, I> initActionFct,  final BiConsumer<A, I> updateActionFct, final Window...windows)
						throws InstantiationException, IllegalAccessException {
		this(ins, exec, Arrays.asList(windows), clazzAction, clazzInteraction, initActionFct, updateActionFct);
	}

	@Override
	public void initAction() {
		execInitAction.accept(getAction(), getInteraction());
	}

	@Override
	public void updateAction() {
		if(execUpdateAction != null) {
			execUpdateAction.accept(getAction(), getInteraction());
		}
	}
}
