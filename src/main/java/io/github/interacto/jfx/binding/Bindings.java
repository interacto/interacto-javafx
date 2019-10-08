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
package io.github.interacto.jfx.binding;

import io.github.interacto.command.Command;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import io.github.interacto.jfx.interaction.library.KeysData;
import io.github.interacto.jfx.interaction.library.WidgetData;
import java.util.function.Function;
import java.util.function.Supplier;
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
 * Utility class for building widget bindings.
 */
public final class Bindings {
	private Bindings() {
		super();
	}


	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmd The anonymous command to produce.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given class is null.
	 */
	public static <D extends InteractionData, W extends Node, I extends JfxInteraction<D, ?, ?>> AnonCmdBinder<W, I, D>
	anonCmdBinder(final I interaction, final Runnable cmd) {
		return new AnonCmdBinder<>(interaction, cmd, null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> KeyNodeBinder<C> keyNodeBinder(final Supplier<C> cmdCreation) {
		return new KeyNodeBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that creates the commands.
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> KeyNodeBinder<C> keyNodeBinder(final Function<KeysData, C> cmdCreation) {
		return new KeyNodeBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> KeyWindowBinder<C> keyWindowBinder(final Supplier<C> cmdCreation) {
		return new KeyWindowBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> KeyWindowBinder<C> keyWindowBinder(final Function<KeysData, C> cmdCreation) {
		return new KeyWindowBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ButtonBinder<C> buttonBinder(final Supplier<C> cmdCreation) {
		return new ButtonBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ButtonBinder<C> buttonBinder(final Function<WidgetData<Button>, C> cmdCreation) {
		return new ButtonBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ToggleButtonBinder<C> toggleButtonBinder(final Supplier<C> cmdCreation) {
		return new ToggleButtonBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a toggle button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ToggleButtonBinder<C> toggleButtonBinder(final Function<WidgetData<ToggleButton>, C> cmdCreation) {
		return new ToggleButtonBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> CheckBoxBinder<C> checkboxBinder(final Supplier<C> cmdCreation) {
		return new CheckBoxBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a checkbox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> CheckBoxBinder<C> checkboxBinder(final Function<WidgetData<CheckBox>, C> cmdCreation) {
		return new CheckBoxBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ColorPickerBinder<C> colorPickerBinder(final Supplier<C> cmdCreation) {
		return new ColorPickerBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a color picker interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ColorPickerBinder<C> colorPickerBinder(final Function<WidgetData<ColorPicker>, C> cmdCreation) {
		return new ColorPickerBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> SpinnerBinder<C> spinnerBinder(final Supplier<C> cmdCreation) {
		return new SpinnerBinder<>(cmdCreation, null);
	}


	/**
	 * Creates binding builder to build a binding between a spinner interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> SpinnerBinder<C> spinnerBinder(final Function<WidgetData<Spinner<?>>, C> cmdCreation) {
		return new SpinnerBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a text input interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command, W extends TextInputControl> TextInputBinder<C, W> textInputBinder(final Supplier<C> cmdCreation) {
		return new TextInputBinder<>(cmdCreation, null);
	}


	/**
	 * Creates binding builder to build a binding between a text input interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command, W extends TextInputControl> TextInputBinder<C, W> textInputBinder(final Function<WidgetData<TextInputControl>, C> cmdCreation) {
		return new TextInputBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> MenuItemBinder<C> menuItemBinder(final Supplier<C> cmdCreation) {
		return new MenuItemBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> MenuItemBinder<C> menuItemBinder(final Function<WidgetData<MenuItem>, C> cmdCreation) {
		return new MenuItemBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ComboBoxBinder<C> comboboxBinder(final Supplier<C> cmdCreation) {
		return new ComboBoxBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a combobox interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> ComboBoxBinder<C> comboboxBinder(final Function<WidgetData<ComboBox<?>>, C> cmdCreation) {
		return new ComboBoxBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> TabBinder<C> tabBinder(final Supplier<C> cmdCreation) {
		return new TabBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a tab interaction (on tabs of a TabPane) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param <C> The type of the command.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command> TabBinder<C> tabBinder(final Function<WidgetData<TabPane>, C> cmdCreation) {
		return new TabBinder<>(cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on windows
	 * @param <C> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<C, I> windowBinder(final I interaction,
		final Supplier<C> cmdCreation) {
		return new WindowBinder<>(interaction, cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on windows
	 * @param <C> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <C extends Command, I extends JfxInteraction<WidgetData<Window>, ?, ?>> WindowBinder<C, I> windowBinder(final I interaction,
		final Function<WidgetData<Window>, C> cmdCreation) {
		return new WindowBinder<>(interaction, cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on nodes
	 * @param <C> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <D extends InteractionData, C extends Command, I extends JfxInteraction<D, ?, ?>> NodeBinder<C, I, D>
	nodeBinder(final I interaction, final Supplier<C> cmdCreation) {
		return new NodeBinder<>(interaction, cmdCreation, null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmdCreation The function that produces the commands
	 * @param interaction The user interaction to perform on nodes
	 * @param <C> The type of the command.
	 * @param <I> The type of the user interaction.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given lambda is null.
	 */
	public static <D extends InteractionData, C extends Command, I extends JfxInteraction<D, ?, ?>> NodeBinder<C, I, D>
	nodeBinder(final I interaction, final Function<D, C> cmdCreation) {
		return new NodeBinder<>(interaction, cmdCreation, null);
	}
}
