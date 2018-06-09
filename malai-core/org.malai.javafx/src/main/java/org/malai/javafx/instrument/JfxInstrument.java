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

import java.util.function.Function;
import java.util.function.Supplier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.stage.Window;
import org.malai.command.CommandImpl;
import org.malai.instrument.InstrumentImpl;
import org.malai.interaction.InteractionData;
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
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.KeysData;
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
	public void setActivated(final boolean toBeActivated) {
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
	 * @throws IllegalArgumentException If the given class is null.
	 */
	protected <D extends InteractionData, W extends Node, I extends JfxInteraction<D, ?, ?>> AnonCmdBinder<W, I, D>
				anonCmdBinder(final I interaction, final Runnable cmd) {
		return new AnonCmdBinder<>(interaction, cmd, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> KeyNodeBinder<A> keyNodeBinder(final Supplier<A> cmdCreation) {
		return new KeyNodeBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that creates the commands.
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> KeyNodeBinder<A> keyNodeBinder(final Function<KeysData, A> cmdCreation) {
		return new KeyNodeBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> KeyWindowBinder<A> keyWindowBinder(final Supplier<A> cmdCreation) {
		return new KeyWindowBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> KeyWindowBinder<A> keyWindowBinder(final Function<KeysData, A> cmdCreation) {
		return new KeyWindowBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ButtonBinder<A> buttonBinder(final Supplier<A> cmdCreation) {
		return new ButtonBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ButtonBinder<A> buttonBinder(final Function<WidgetData<Button>, A> cmdCreation) {
		return new ButtonBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ToggleButtonBinder<A> toggleButtonBinder(final Supplier<A> cmdCreation) {
		return new ToggleButtonBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ToggleButtonBinder<A> toggleButtonBinder(final Function<WidgetData<ToggleButton>, A> cmdCreation) {
		return new ToggleButtonBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> CheckBoxBinder<A> checkboxBinder(final Supplier<A> cmdCreation) {
		return new CheckBoxBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> CheckBoxBinder<A> checkboxBinder(final Function<WidgetData<CheckBox>, A> cmdCreation) {
		return new CheckBoxBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ColorPickerBinder<A> colorPickerBinder(final Supplier<A> cmdCreation) {
		return new ColorPickerBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ColorPickerBinder<A> colorPickerBinder(final Function<WidgetData<ColorPicker>, A> cmdCreation) {
		return new ColorPickerBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> SpinnerBinder<A> spinnerBinder(final Supplier<A> cmdCreation) {
		return new SpinnerBinder<>(cmdCreation, this);
	}


	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> SpinnerBinder<A> spinnerBinder(final Function<WidgetData<Spinner<?>>, A> cmdCreation) {
		return new SpinnerBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a text input interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl, W extends TextInputControl> TextInputBinder<A, W> textInputBinder(final Supplier<A> cmdCreation) {
		return new TextInputBinder<>(cmdCreation, this);
	}


	/**
	 * Creates binding builder to build a binding between a text input interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl, W extends TextInputControl> TextInputBinder<A, W> textInputBinder(final Function<WidgetData<TextInputControl>, A> cmdCreation) {
		return new TextInputBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> MenuItemBinder<A> menuItemBinder(final Supplier<A> cmdCreation) {
		return new MenuItemBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> MenuItemBinder<A> menuItemBinder(final Function<WidgetData<MenuItem>, A> cmdCreation) {
		return new MenuItemBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ComboBoxBinder<A> comboboxBinder(final Supplier<A> cmdCreation) {
		return new ComboBoxBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> ComboBoxBinder<A> comboboxBinder(final Function<WidgetData<ComboBox<?>>, A> cmdCreation) {
		return new ComboBoxBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> TabBinder<A> tabBinder(final Supplier<A> cmdCreation) {
		return new TabBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <A> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl> TabBinder<A> tabBinder(final Function<WidgetData<TabPane>, A> cmdCreation) {
		return new TabBinder<>(cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on windows
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<A, I> windowBinder(final I interaction,
																														  final Supplier<A> cmdCreation) {
		return new WindowBinder<>(interaction, cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on windows
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <A extends CommandImpl, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<A, I> windowBinder(final I interaction,
																										  final Function<WidgetData<Window>, A> cmdCreation) {
		return new WindowBinder<>(interaction, cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on nodes
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <D extends InteractionData, A extends CommandImpl, I extends JfxInteraction<D, ?, ?>> NodeBinder<A, I, D>
				nodeBinder(final I interaction, final Supplier<A> cmdCreation) {
		return new NodeBinder<>(interaction, cmdCreation, this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on nodes
	 * @param <A> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	protected <D extends InteractionData, A extends CommandImpl, I extends JfxInteraction<D, ?, ?>> NodeBinder<A, I, D>
				nodeBinder(final I interaction, final Function<D, A> cmdCreation) {
		return new NodeBinder<>(interaction, cmdCreation, this);
	}
}
