/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.instrument;

import io.github.interacto.command.AnonCommand;
import io.github.interacto.command.Command;
import io.github.interacto.instrument.InstrumentImpl;
import io.github.interacto.jfx.binding.Bindings;
import io.github.interacto.jfx.binding.JfXWidgetBinding;
import io.github.interacto.jfx.binding.api.BaseBinder;
import io.github.interacto.jfx.binding.api.BaseUpdateBinder;
import io.github.interacto.jfx.binding.api.CmdBinder;
import io.github.interacto.jfx.binding.api.InteractionBinder;
import io.github.interacto.jfx.binding.api.InteractionUpdateBinder;
import io.github.interacto.jfx.binding.api.KeyInteractionBinder;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.library.BoxChecked;
import io.github.interacto.jfx.interaction.library.ButtonPressed;
import io.github.interacto.jfx.interaction.library.ColorPicked;
import io.github.interacto.jfx.interaction.library.ComboBoxSelected;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.KeysPressed;
import io.github.interacto.jfx.interaction.library.MenuItemPressed;
import io.github.interacto.jfx.interaction.library.SpinnerChanged;
import io.github.interacto.jfx.interaction.library.TabSelected;
import io.github.interacto.jfx.interaction.library.TextInputChanged;
import io.github.interacto.jfx.interaction.library.ToggleButtonPressed;
import io.github.interacto.jfx.interaction.library.WidgetData;
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

/**
 * Base of an instrument for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInstrument extends InstrumentImpl<JfXWidgetBinding<?, ? extends JfxInteraction<?, ?, ?>, ?>> {
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
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmd The anonymous command to produce.
	 * @return The binding builder. Cannot be null.
	 */
	protected <W extends Node> CmdBinder<W, AnonCommand> anonCmdBinder(final Runnable cmd) {
		return Bindings.anonCmdBinder(cmd);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected KeyInteractionBinder<Node, KeysPressed, KeysData> shortcutBinder() {
		return Bindings.shortcutBinder(this);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected KeyInteractionBinder<Window, KeysPressed, KeysData> shortcutWindowBinder() {
		return Bindings.shortcutWinBinder(this);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected InteractionBinder<Button, ButtonPressed, WidgetData<Button>> buttonBinder() {
		return Bindings.buttonBinder(this);
	}
//	/**
//	 * Creates binding builder to build a binding between a button interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<Button, C, ButtonPressed, WidgetData<Button>> buttonBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseButtonBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a button interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<Button, C, ButtonPressed, WidgetData<Button>> buttonBinder(final Function<WidgetData<Button>, C> cmdCreation) {
//		return Bindings.baseButtonBinder(this).toProduce(cmdCreation);
//	}

//	/**
//	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ToggleButton, C, ToggleButtonPressed, WidgetData<ToggleButton>> toggleButtonBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseToggleButtonBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ToggleButton, C, ToggleButtonPressed, WidgetData<ToggleButton>> toggleButtonBinder(final Function<WidgetData<ToggleButton>, C> cmdCreation) {
//		return Bindings.baseToggleButtonBinder(this).toProduce(cmdCreation);
//	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected InteractionBinder<ToggleButton, ToggleButtonPressed, WidgetData<ToggleButton>> toggleButtonBinder() {
		return Bindings.toggleButtonBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<CheckBox, C, BoxChecked, WidgetData<CheckBox>> checkboxBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseCheckboxBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<CheckBox, C, BoxChecked, WidgetData<CheckBox>> checkboxBinder(final Function<WidgetData<CheckBox>, C> cmdCreation) {
//		return Bindings.baseCheckboxBinder(this).toProduce(cmdCreation);
//	}

	protected InteractionBinder<CheckBox, BoxChecked, WidgetData<CheckBox>> checkboxBinder() {
		return Bindings.checkboxBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ColorPicker, C, ColorPicked, WidgetData<ColorPicker>> colorPickerBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseColorPickerBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ColorPicker, C, ColorPicked, WidgetData<ColorPicker>> colorPickerBinder(final Function<WidgetData<ColorPicker>, C> cmdCreation) {
//		return Bindings.baseColorPickerBinder(this).toProduce(cmdCreation);
//	}

	protected InteractionBinder<ColorPicker, ColorPicked, WidgetData<ColorPicker>> colorPickerBinder() {
		return Bindings.colorPickerBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdUpdateBinder<Spinner<?>, C, SpinnerChanged, WidgetData<Spinner<?>>> spinnerBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseSpinnerBinder(this).toProduce(cmdCreation);
//	}
//
//
//	/**
//	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdUpdateBinder<Spinner<?>, C, SpinnerChanged, WidgetData<Spinner<?>>> spinnerBinder(final Function<WidgetData<Spinner<?>>, C> cmdCreation) {
//		return Bindings.baseSpinnerBinder(this).toProduce(cmdCreation);
//	}

	protected <C extends Command> InteractionUpdateBinder<Spinner<?>, SpinnerChanged, WidgetData<Spinner<?>>> spinnerBinder() {
		return Bindings.spinnerBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a text input interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 * @throws IllegalArgumentException If the given lambda is null.
//	 */
//	protected  <C extends Command> InteractionCmdUpdateBinder<TextInputControl, C, TextInputChanged, WidgetData<TextInputControl>>
//			textInputBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseTextInputBinder(null).toProduce(cmdCreation);
//	}
//
//
//	/**
//	 * Creates binding builder to build a binding between a text input interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 * @throws IllegalArgumentException If the given lambda is null.
//	 */
//	protected  <C extends Command> InteractionCmdUpdateBinder<TextInputControl, C, TextInputChanged, WidgetData<TextInputControl>>
//			textInputBinder(final Function<WidgetData<TextInputControl>, C> cmdCreation) {
//		return Bindings.baseTextInputBinder(null).toProduce(cmdCreation);
//	}

	protected <W extends TextInputControl> InteractionUpdateBinder<W, TextInputChanged, WidgetData<TextInputControl>> textInputBinder() {
		return Bindings.textInputBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<MenuItem, C, MenuItemPressed, WidgetData<MenuItem>> menuItemBinder(final Supplier<C> cmdCreation) {
//		return new MenuItemBinder<C>(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<MenuItem, C, MenuItemPressed, WidgetData<MenuItem>> menuItemBinder(final Function<WidgetData<MenuItem>, C> cmdCreation) {
//		return new MenuItemBinder<C>(this).toProduce(cmdCreation);
//	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected InteractionBinder<MenuItem, MenuItemPressed, WidgetData<MenuItem>> menuItemBinder() {
		return Bindings.menuItemBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ComboBox<?>, C, ComboBoxSelected, WidgetData<ComboBox<?>>> comboboxBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseComboBoxBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<ComboBox<?>, C, ComboBoxSelected, WidgetData<ComboBox<?>>> comboboxBinder(final Function<WidgetData<ComboBox<?>>, C> cmdCreation) {
//		return Bindings.baseComboBoxBinder(this).toProduce(cmdCreation);
//	}

	protected InteractionBinder<ComboBox<?>, ComboBoxSelected, WidgetData<ComboBox<?>>> comboboxBinder() {
		return Bindings.comboboxBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<TabPane, C, TabSelected, WidgetData<TabPane>> tabBinder(final Supplier<C> cmdCreation) {
//		return Bindings.baseTabBinder(this).toProduce(cmdCreation);
//	}
//
//	/**
//	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param <C> The type of the command.
//	 * @return The binding builder. Cannot be null.
//	 */
//	protected <C extends Command> InteractionCmdBinder<TabPane, C, TabSelected, WidgetData<TabPane>> tabBinder(final Function<WidgetData<TabPane>, C> cmdCreation) {
//		return Bindings.baseTabBinder(this).toProduce(cmdCreation);
//	}

	protected InteractionBinder<TabPane, TabSelected, WidgetData<TabPane>> tabBinder() {
		return Bindings.tabBinder(this);
	}



//	/**
//	 * Creates binding builder to build a binding between a given interaction and the given command type.
//	 * This builder is dedicated to bind window interactions to commands.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param <C> The type of the command.
//	 * @param <I> The type of the user interaction.
//	 * @return The binding builder. Cannot be null.
//	 * @throws IllegalArgumentException If the given lambda is null.
//	 */
//	protected <C extends Command, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<C, I> windowBinder() {
//		return new WindowBinder<>(this);
//	}

	/**
	 * Creates a widget binding builder for JFX nodes.
	 * @return The binding builder. Cannot be null.
	 */
	protected BaseUpdateBinder<Node> nodeBinder() {
		return Bindings.nodeBinder(this);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	protected BaseBinder<Window> windowBinder() {
		return Bindings.windowBinder(this);
	}

//	/**
//	 * Creates binding builder to build a binding between a given interaction and the given command type.
//	 * This builder is dedicated to bind node interactions to commands.
//	 * Do not forget to call bind() at the end of the build to execute the builder.
//	 * @param cmdCreation The function that produces the commands
//	 * @param interaction The user interaction to perform on nodes
//	 * @param <C> The type of the command.
//	 * @param <I> The type of the user interaction.
//	 * @return The binding builder. Cannot be null.
//	 * @throws IllegalArgumentException If the given lambda is null.
//	 */
//	protected <D extends InteractionData, C extends Command, I extends JfxInteraction<D, ?, ?>> NodeUpdateBinder<Node, C, I, D>
//				nodeBinder(final Supplier<I> interaction, final Function<D, C> cmdCreation) {
//		return new NodeUpdateBinder<>(interaction, cmdCreation, this);
//	}
}
