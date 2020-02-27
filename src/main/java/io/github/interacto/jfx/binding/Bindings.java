/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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

import io.github.interacto.command.AnonCommand;
import io.github.interacto.command.Command;
import io.github.interacto.jfx.binding.api.BaseBinder;
import io.github.interacto.jfx.binding.api.BaseUpdateBinder;
import io.github.interacto.jfx.binding.api.CmdBinder;
import io.github.interacto.jfx.binding.api.InteractionBinder;
import io.github.interacto.jfx.binding.api.InteractionCmdBinder;
import io.github.interacto.jfx.binding.api.InteractionUpdateBinder;
import io.github.interacto.jfx.binding.api.KeyInteractionBinder;
import io.github.interacto.jfx.command.OpenWebPageJFX;
import io.github.interacto.jfx.command.ShowNode;
import io.github.interacto.jfx.command.ShowStage;
import io.github.interacto.jfx.instrument.JfxInstrument;
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
import java.util.function.Supplier;
import javafx.application.HostServices;
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
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Utility class for building widget bindings.
 */
public final class Bindings {
	private static BindingsObserver observer = null;

	public static void setBindingObserver(final BindingsObserver obs) {
		observer = obs;
	}

	private Bindings() {
		super();
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmd The anonymous command to produce.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given cmd is null.
	 */
	public static <W extends Node> CmdBinder<W, AnonCommand> anonCmdBinder(final Runnable cmd) {
		return anonCmdBinder(cmd, null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Node) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @param cmd The anonymous command to produce.
	 * @param ins The instrument that uses the bindings. May be null.
	 * @return The binding builder. Cannot be null.
	 * @throws IllegalArgumentException If the given cmd is null.
	 */
	public static <W extends Node> CmdBinder<W, AnonCommand> anonCmdBinder(final Runnable cmd, final JfxInstrument ins) {
		return new AnonCmdBinder<>(cmd, ins, observer);
	}

	/**
	 * Creates binding builder to build a binding between a button interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionBinder<Button, ButtonPressed, WidgetData<Button>> buttonBinder() {
		return buttonBinder(null);
	}

	public static <C extends Command> InteractionBinder<Button, ButtonPressed, WidgetData<Button>> buttonBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<Button, C, ButtonPressed, WidgetData<Button>>(ins, observer)
			.usingInteraction(ButtonPressed::new);
	}

	public static InteractionBinder<ToggleButton, ToggleButtonPressed, WidgetData<ToggleButton>> toggleButtonBinder() {
		return toggleButtonBinder(null);
	}

	public static <C extends Command> InteractionBinder<ToggleButton, ToggleButtonPressed, WidgetData<ToggleButton>> toggleButtonBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<ToggleButton, C, ToggleButtonPressed, WidgetData<ToggleButton>>(ins, observer)
			.usingInteraction(ToggleButtonPressed::new);
	}

	public static InteractionBinder<CheckBox, BoxChecked, WidgetData<CheckBox>> checkboxBinder() {
		return checkboxBinder(null);
	}

	public static <C extends Command> InteractionBinder<CheckBox, BoxChecked, WidgetData<CheckBox>> checkboxBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<CheckBox, C, BoxChecked, WidgetData<CheckBox>>(ins, observer)
			.usingInteraction(BoxChecked::new);
	}

	public static InteractionBinder<ColorPicker, ColorPicked, WidgetData<ColorPicker>> colorPickerBinder() {
		return colorPickerBinder(null);
	}

	public static <C extends Command> InteractionBinder<ColorPicker, ColorPicked, WidgetData<ColorPicker>> colorPickerBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<ColorPicker, C, ColorPicked, WidgetData<ColorPicker>>(ins, observer)
			.usingInteraction(ColorPicked::new);
	}

	public static InteractionUpdateBinder<Spinner<?>, SpinnerChanged, WidgetData<Spinner<?>>> spinnerBinder() {
		return spinnerBinder(null);
	}

	public static <C extends Command> InteractionUpdateBinder<Spinner<?>, SpinnerChanged, WidgetData<Spinner<?>>> spinnerBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<Spinner<?>, C, SpinnerChanged, WidgetData<Spinner<?>>>(ins, observer)
			.usingInteraction(SpinnerChanged::new);
	}

	public static <W extends TextInputControl> InteractionUpdateBinder<W, TextInputChanged, WidgetData<TextInputControl>> textInputBinder() {
		return textInputBinder(null);
	}

	public static <C extends Command, W extends TextInputControl> InteractionUpdateBinder<W, TextInputChanged, WidgetData<TextInputControl>> textInputBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<W, C, TextInputChanged, WidgetData<TextInputControl>>(ins, observer)
			.usingInteraction(TextInputChanged::new);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionBinder<MenuItem, MenuItemPressed, WidgetData<MenuItem>> menuItemBinder() {
		return menuItemBinder(null);
	}

	/**
	 * Creates binding builder to build a binding between a menu item interaction and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionBinder<MenuItem, MenuItemPressed, WidgetData<MenuItem>> menuItemBinder(final JfxInstrument ins) {
		return new MenuItemBinder<>(ins, observer);
	}

	public static InteractionBinder<ComboBox<?>, ComboBoxSelected, WidgetData<ComboBox<?>>> comboboxBinder() {
		return comboboxBinder(null);
	}

	public static <C extends Command> InteractionBinder<ComboBox<?>, ComboBoxSelected, WidgetData<ComboBox<?>>> comboboxBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<ComboBox<?>, C, ComboBoxSelected, WidgetData<ComboBox<?>>>(ins, observer)
			.usingInteraction(ComboBoxSelected::new);
	}

	public static InteractionBinder<TabPane, TabSelected, WidgetData<TabPane>> tabBinder() {
		return tabBinder(null);
	}

	public static <C extends Command> InteractionBinder<TabPane, TabSelected, WidgetData<TabPane>> tabBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<TabPane, C, TabSelected, WidgetData<TabPane>>(ins, observer)
			.usingInteraction(TabSelected::new);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static BaseBinder<Window> windowBinder() {
		return windowBinder(null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind window interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static BaseBinder<Window> windowBinder(final JfxInstrument ins) {
		return new WindowBinder<>(ins, observer);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static <W extends Node> BaseUpdateBinder<W> nodeBinder() {
		return nodeBinder(null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static <W extends Node> BaseUpdateBinder<W> nodeBinder(final JfxInstrument ins) {
		return new NodeUpdateBinder<>(ins, observer);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static KeyInteractionBinder<Node, KeysPressed, KeysData> shortcutBinder() {
		return shortcutBinder(null);
	}

	/**
	 * Creates binding builder to build a binding between a given interaction and the given command type.
	 * This builder is dedicated to bind node interactions to commands.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static KeyInteractionBinder<Node, KeysPressed, KeysData> shortcutBinder(final JfxInstrument ins) {
		return new KeysNodeBinder<>(ins, observer);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static KeyInteractionBinder<Window, KeysPressed, KeysData> shortcutWinBinder() {
		return shortcutWinBinder(null);
	}

	/**
	 * Creates binding builder to build a binding between a KeysPressure interaction (done on a Window) and the given command type.
	 * Do not forget to call bind() at the end of the build to execute the builder.
	 * @return The binding builder. Cannot be null.
	 */
	public static KeyInteractionBinder<Window, KeysPressed, KeysData> shortcutWinBinder(final JfxInstrument ins) {
		return new KeysWindowBinder<>(ins, observer);
	}

	/**
	 * A widget binding that opens a URL using a menu item.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionCmdBinder<MenuItem, OpenWebPageJFX, MenuItemPressed, WidgetData<MenuItem>>
			menuItem2OpenWebPage(final String uri, final HostServices services, final JfxInstrument ins) {
		return new MenuItemBinder<>(ins, observer)
			.toProduce(() -> new OpenWebPageJFX())
			.first((i, c) -> {
				c.setUri(uri);
				c.setServices(services);
			});
	}

	/**
	 * A widget binding that opens a stage using a menu item.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionCmdBinder<MenuItem, ShowStage, MenuItemPressed, WidgetData<MenuItem>>
			menuItem2OpenStage(final Supplier<Stage> stageLazy, final boolean toshow, final JfxInstrument ins) {
		return new MenuItemBinder<>(ins, observer)
			.toProduce(() -> new ShowStage())
			.first((i, c) -> {
				c.setWidget(stageLazy.get());
				c.setVisible(toshow);
			});
	}

	/**
	 * A widget binding that shows a node using a menu item.
	 * @return The binding builder. Cannot be null.
	 */
	public static InteractionCmdBinder<MenuItem, ShowNode, MenuItemPressed, WidgetData<MenuItem>>
			menuItem2ShowNode(final Node node, final boolean toshow, final JfxInstrument ins) {
		return new MenuItemBinder<>(ins, observer)
			.toProduce(() -> new ShowNode())
			.first((i, c) -> {
				c.setWidget(node);
				c.setVisible(toshow);
			});
	}
}
