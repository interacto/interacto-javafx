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

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import org.malai.action.ActionImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemInteraction;

/**
 * This anonymous widget binding for menus takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the widget binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonMenuBinding<A extends ActionImpl, I extends MenuItemInteraction<MenuItem>, N extends JfxInstrument> extends JfxMenuItemBinding<A, I, N> {
	private final BiConsumer<A, I> execInitAction;
	private final Predicate<I> checkInteraction;
	private final BiConsumer<A, I> onEnd;
	private final Function<I, A> actionProducer;
	/** Used rather than 'action' to catch the action during its creation.
	 * Sometimes (eg onInteractionStops) can create the action, execute it, and forget it.
	 */
	protected A currentAction;

	/**
	 * Creates a menu item binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction to use.
	 * The class must be public and must have a constructor with no parameter.
	 * @param menus The menus used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonMenuBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							  final BiConsumer<A, I> initActionFct, final Predicate<I> check, final BiConsumer<A, I> onEndFct,
							  final Function<I, A> actionFct, final List<MenuItem> menus, List<ObservableList<? extends MenuItem>> additionalMenus)
								throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction, menus);
		execInitAction = initActionFct == null ? (a, i) -> {} : initActionFct;
		checkInteraction = check == null ? i -> true : check;
		onEnd = onEndFct;
		actionProducer = actionFct;
		currentAction = null;

		if(additionalMenus != null) {
			additionalMenus.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableMenuList(elt));
		}
	}

	@Override
	public void first() {
		execInitAction.accept(getAction(), getInteraction());
	}

	@Override
	public boolean when() {
		return checkInteraction == null || checkInteraction.test(getInteraction());
	}

	@Override
	protected A map() {
		if(actionProducer == null) {
			currentAction = super.map();
		}else {
			currentAction = actionProducer.apply(getInteraction());
		}
		return currentAction;
	}


	@Override
	public void interactionStops() {
		super.interactionStops();
		if(onEnd != null) {
			onEnd.accept(currentAction, getInteraction());
		}
		currentAction = null;
	}

	@Override
	public void interactionCancels() {
		super.interactionCancels();
		currentAction = null;
	}

	@Override
	public String toString() {
		return "JFxAnonMenuBinding in " + instrument + '{' + interaction + " -> " + clazzAction + '}';
	}
}
