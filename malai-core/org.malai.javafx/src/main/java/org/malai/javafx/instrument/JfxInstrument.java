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
package org.malai.javafx.instrument;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.stage.Window;
import org.malai.command.CommandImpl;
import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.binding.AnonCmdBinder;
import org.malai.javafx.binding.ButtonBinder;
import org.malai.javafx.binding.CheckBoxBinder;
import org.malai.javafx.binding.ColorPickerBinder;
import org.malai.javafx.binding.ComboBoxBinder;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.binding.KeyNodeBinder;
import org.malai.javafx.binding.KeyWindowBinder;
import org.malai.javafx.binding.MenuItemBinder;
import org.malai.javafx.binding.NodeBinder;
import org.malai.javafx.binding.SpinnerBinder;
import org.malai.javafx.binding.TabBinder;
import org.malai.javafx.binding.TextInputBinder;
import org.malai.javafx.binding.ToggleButtonBinder;
import org.malai.javafx.binding.WindowBinder;
import org.malai.javafx.interaction.InteractionData;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.WidgetData;

/**
 * Base of an instrument for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInstrument extends InstrumentImpl<JfXWidgetBinding<?, ? extends JfxInteraction<?, ?, ?>, ? extends JfxInstrument, ?>> {
	protected final BooleanProperty activatedProp;

	/**
	 * Creates the instrument.
	 */
	public JfxInstrument() {
		super();
		activatedProp = new SimpleBooleanProperty(activated);
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

	@Override
	public void uninstallBindings() {
		activatedProp.unbind();
		super.uninstallBindings();
	}

	@Override
	public void setActivated(boolean toBeActivated) {
		super.setActivated(toBeActivated);
		if(!activatedProp.isBound()) {
			activatedProp.set(activated);
		}
	}

	/**
	 * @return The property corresponding to the activation of the instrument. Cannot be null.
	 */
	public BooleanProperty activatedProperty() {
		return activatedProp;
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmd The anonymous command to produce.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <D extends InteractionData, W extends Node, I extends JfxInteraction<D, ?, ?>> AnonCmdBinder<W, I, D>
				anonCmdBinder(final I interaction, final Runnable cmd) {
		return new AnonCmdBinder<>(interaction, cmd, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> KeyNodeBinder<A> keyNodeBinder(final Class<A> cmdClass) {
		return new KeyNodeBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> KeyWindowBinder<A> keyWindowBinder(final Class<A> cmdClass) {
		return new KeyWindowBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> ButtonBinder<A> buttonBinder(final Class<A> cmdClass) {
		return new ButtonBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> ToggleButtonBinder<A> toggleButtonBinder(final Class<A> cmdClass) {
		return new ToggleButtonBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> CheckBoxBinder<A> checkboxBinder(final Class<A> cmdClass) {
		return new CheckBoxBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> ColorPickerBinder<A> colorPickerBinder(final Class<A> cmdClass) {
		return new ColorPickerBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> SpinnerBinder<A> spinnerBinder(final Class<A> cmdClass) {
		return new SpinnerBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a text input interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl, W extends TextInputControl> TextInputBinder<A, W> textInputBinder(final Class<A> cmdClass) {
		return new TextInputBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> MenuItemBinder<A> menuItemBinder(final Class<A> cmdClass) {
		return new MenuItemBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> ComboBoxBinder<A> comboboxBinder(final Class<A> cmdClass) {
		return new ComboBoxBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl> TabBinder<A> tabBinder(final Class<A> cmdClass) {
		return new TabBinder<>(cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param interaction The user interaction to perform on windows
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <A extends CommandImpl, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<A, I> windowBinder(final I interaction,
																														  final Class<A> cmdClass) {
		return new WindowBinder<>(interaction, cmdClass, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdClass The command to produce.
	 * @param interaction The user interaction to perform on nodes
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws NullPointerException If the given class is null.
	 */
	protected <D extends InteractionData, A extends CommandImpl, I extends JfxInteraction<D, ?, ?>> NodeBinder<A, I, D>
				nodeBinder(final I interaction, final Class<A> cmdClass) {
		return new NodeBinder<>(interaction, cmdClass, this);
	}
}
