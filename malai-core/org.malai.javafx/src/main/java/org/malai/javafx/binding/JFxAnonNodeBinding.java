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
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.interaction.Interaction;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * This anonymous widget binding takes a function as a parameter that will be executed to initialise the action.
 * The goal is to avoid the creation of a specific class when the binding is quite simple.
 * @author Arnaud Blouin
 */
public class JFxAnonNodeBinding<A extends ActionImpl, I extends JfxInteraction, N extends JfxInstrument> extends JfXWidgetBinding<A, I, N> {
	private final BiConsumer<A, I> execInitAction;
	private final BiConsumer<A, I> execUpdateAction;
	private final Predicate<I> checkInteraction;
	private final Function<I, A> actionProducer;
	private final Runnable abortFct;
	private final Runnable feedbackFct;
	private final boolean asyncAction;
	private final BiConsumer<A, I> onEnd;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							  final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct,
							  final Predicate<I> check, final BiConsumer<A, I> onEndFct, final Function<I, A> actionFunction,
							  final Runnable abort, final Runnable feedback, final List<Node> widgets, Set<ObservableList<Node>> additionalWidgets,
							  final boolean async)
				throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction, widgets);
		execInitAction = initActionFct;
		execUpdateAction = updateActionFct;
		abortFct = abort;
		feedbackFct = feedback;
		actionProducer = actionFunction;
		checkInteraction = check == null ? i -> true : check;
		asyncAction = async;
		onEnd = onEndFct;

		if(additionalWidgets != null) {
			additionalWidgets.stream().filter(Objects::nonNull).forEach(elt -> interaction.registerToObservableNodeList(elt));
		}
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The windows used by the binding. Cannot be null.
	 * @param initActionFct The function that initialises the action to execute. Cannot be null.
	 * @param updateActionFct The function that updates the action. Can be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JFxAnonNodeBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							  final List<Window> widgets, final BiConsumer<A, I> initActionFct, final BiConsumer<A, I> updateActionFct,
							  final Predicate<I> check, final BiConsumer<A, I> onEndFct, final Function<I, A> actionFunction,
							  final Runnable abort, final Runnable feedback, final boolean async)
		throws InstantiationException, IllegalAccessException {
		super(ins, exec, widgets, clazzAction, interaction);
		execInitAction = initActionFct;
		execUpdateAction = updateActionFct;
		abortFct = abort;
		feedbackFct = feedback;
		actionProducer = actionFunction;
		checkInteraction = check == null ? i -> true : check;
		asyncAction = async;
		onEnd = onEndFct;
	}

	@Override
	protected A createAction() {
		if(actionProducer == null) {
			return super.createAction();
		}
		return actionProducer.apply(getInteraction());
	}

	@Override
	public void initAction() {
		if(execInitAction != null) {
			execInitAction.accept(getAction(), getInteraction());
		}
	}

	@Override
	public void updateAction() {
		if(execUpdateAction != null) {
			execUpdateAction.accept(getAction(), getInteraction());
		}
	}

	@Override
	public boolean isConditionRespected() {
		return checkInteraction == null || checkInteraction.test(getInteraction());
	}

	@Override
	public void interactionAborts(final Interaction inter) {
		super.interactionAborts(inter);
		if(abortFct != null) {
			abortFct.run();
		}
	}

	@Override
	public void interimFeedback() {
		if(feedbackFct != null) {
			feedbackFct.run();
		}
	}

	@Override
	public void interactionStops(final Interaction inter) {
		final A action = getAction();
		super.interactionStops(inter);
		if(onEnd != null) {
			onEnd.accept(action, getInteraction());
		}
	}

	@Override
	public String toString() {
		return "JFxAnonNodeBinding in " + instrument + '{' + interaction + " -> " + clazzAction + '}';
	}
}
