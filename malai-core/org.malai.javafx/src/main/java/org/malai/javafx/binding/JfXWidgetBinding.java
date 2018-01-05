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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.binding.WidgetBindingImpl;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.help.HelpAnimation;
import org.malai.javafx.interaction.help.HelpAnimationPlayer;

/**
 * Base of a widget binding for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfXWidgetBinding<A extends ActionImpl, I extends JfxInteraction, N extends JfxInstrument> extends WidgetBindingImpl<A, I, N> {
	protected final BooleanProperty activation;
	protected boolean withHelp;
	protected HelpAnimation customAnimation;

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction,
							final List<Node> widgets, final boolean help, final HelpAnimation animation) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		initActivation();
		withHelp = help;
		customAnimation = animation;
		interaction.registerToNodes(widgets);
	}

	/**
	 * Creates a widget binding. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies whether the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param widgets The widgets concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final Class<A> clazzAction, final I interaction, final boolean help,
							final HelpAnimation animation, final Node... widgets) throws InstantiationException, IllegalAccessException {
		this(ins, exec, clazzAction, interaction, Arrays.asList(widgets), help, animation);
	}

	/**
	 * Creates a widget binding for windows. This constructor must initialise the interaction. The binding is (de-)activated if the given
	 * instrument is (de-)activated.
	 * @param ins The instrument that contains the widget binding.
	 * @param exec Specifies if the action must be execute or update on each evolution of the interaction.
	 * @param clazzAction The type of the action that will be created. Used to instantiate the action by reflexivity.
	 * The class must be public and must have a constructor with no parameter.
	 * @param interaction The user interaction of the binding.
	 * @param windows The windows concerned by the binding. Cannot be null.
	 * @throws IllegalAccessException If no free-parameter constructor is available.
	 * @throws InstantiationException If an error occurs during instantiation of the interaction/action.
	 * @throws IllegalArgumentException If the given interaction or instrument is null.
	 */
	public JfXWidgetBinding(final N ins, final boolean exec, final List<Window> windows, final Class<A> clazzAction,
							final I interaction, final HelpAnimation animation, final boolean help) throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, interaction);
		activation = new SimpleBooleanProperty(isActivated());
		withHelp = help;
		customAnimation = animation;
		initActivation();
		interaction.registerToWindows(windows);
	}

	@Override
	public void setActivated(final boolean activ) {
		if(activation != null && !activation.isBound()) {
			activation.set(activ);
		}
		if(activation != null && withHelp) {
			final HelpAnimation anim = customAnimation == null ? interaction.getHelpAnimation().orElse(null) : customAnimation;
			if(anim != null) {
				if(activation.get()) {//TODO heuristics
					HelpAnimationPlayer.INSTANCE.add(anim);
				}else {
					HelpAnimationPlayer.INSTANCE.stop(anim);
				}
			}
		}
	}

	private void initActivation() {
		activation.addListener((observable, oldValue, newValue) -> {
			if(oldValue != newValue) {
				interaction.setActivated(newValue);
			}
		});
	}

	@Override
	public boolean isConditionRespected() {
		return true;
	}

	public BooleanProperty activationProperty() {
		return activation;
	}
}
