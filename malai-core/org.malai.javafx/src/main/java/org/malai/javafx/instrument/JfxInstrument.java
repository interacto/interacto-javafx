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

import javafx.scene.Node;
import org.malai.action.ActionImpl;
import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.binding.AnonActionBinder;
import org.malai.javafx.binding.ButtonBinder;
import org.malai.javafx.binding.CheckBoxBinder;
import org.malai.javafx.binding.ComboBoxBinder;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.binding.KeyNodeBinder;
import org.malai.javafx.binding.KeyWindowBinder;
import org.malai.javafx.binding.MenuItemBinder;
import org.malai.javafx.binding.NodeBinder;
import org.malai.javafx.binding.SpinnerBinder;
import org.malai.javafx.binding.TabBinder;
import org.malai.javafx.binding.TextFieldBinder;
import org.malai.javafx.binding.ToggleButtonBinder;
import org.malai.javafx.binding.WindowBinder;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * Base of an instrument for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInstrument extends InstrumentImpl<JfXWidgetBinding<?, ? extends JfxInteraction, ? extends JfxInstrument>> {
	/**
	 * Creates the instrument.
	 */
	public JfxInstrument() {
		super();
	}

	/**
	 * Activates or deactivates the instrument. This operation has a second parameter to define whether the widgets of the
	 * instrument must be hidden: in some cases, deactivating the instrument just implies disabling its widgets
	 * (but they are still visible); in some others cases, deactivating the instrument means hiding the widgets.
	 * Should be overridden to define the process of hiding the widgets.
	 * @param activ True = activation.
	 * @param hide If true the widgets will be hidden. Only while deactivating the instrument.
	 */
	public void setActivated(final boolean activ, final boolean hide) {
		setActivated(activ);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param action The anonymous action to produce.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <W extends Node, I extends JfxInteraction> AnonActionBinder<W, I> anonActionBinder(final Runnable action, final I interaction) {
		return new AnonActionBinder<>(action, interaction, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> KeyNodeBinder<A> keyNodeBinder(final Class<A> actionClass) {
		return new KeyNodeBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> KeyWindowBinder<A> keyWindowBinder(final Class<A> actionClass) {
		return new KeyWindowBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> ButtonBinder<A> buttonBinder(final Class<A> actionClass) {
		return new ButtonBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> ToggleButtonBinder<A> toggleButtonBinder(final Class<A> actionClass) {
		return new ToggleButtonBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> CheckBoxBinder<A> checkboxBinder(final Class<A> actionClass) {
		return new CheckBoxBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> SpinnerBinder<A> spinnerBinder(final Class<A> actionClass) {
		return new SpinnerBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a text field interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> TextFieldBinder<A> textfieldBinder(final Class<A> actionClass) {
		return new TextFieldBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> MenuItemBinder<A> menuItemBinder(final Class<A> actionClass) {
		return new MenuItemBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> ComboBoxBinder<A> comboboxBinder(final Class<A> actionClass) {
		return new ComboBoxBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given action type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param actionClass The action to produce.
	 * @param <A> The type of the action.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl> TabBinder<A> tabBinder(final Class<A> actionClass) {
		return new TabBinder<>(actionClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given action type.
	 * This builder is dedicated to bind window interactions to actions.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param action The action to produce.
	 * @param interaction The user interaction to perform on windows
	 * @param <A> The type of the action.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl, I extends JfxInteraction> WindowBinder<A, I> windowBinder(final Class<A> action, final I interaction) {
		return new WindowBinder<>(action, interaction, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given action type.
	 * This builder is dedicated to bind node interactions to actions.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param action The action to produce.
	 * @param interaction The user interaction to perform on nodes
	 * @param <A> The type of the action.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends ActionImpl, I extends JfxInteraction> NodeBinder<A, I> nodeBinder(final Class<A> action, final I interaction) {
		return new NodeBinder<>(action, interaction, this);
	}
}
