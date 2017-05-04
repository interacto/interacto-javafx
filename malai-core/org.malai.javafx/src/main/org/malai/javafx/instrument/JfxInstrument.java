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

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.binding.JFxAnonBinding;
import org.malai.javafx.binding.JFxAnonMenuBinding;
import org.malai.javafx.binding.JFxAnonWindowBinding;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.binding.KeysShortcutBinding;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.BoxChecked;
import org.malai.javafx.interaction.library.ButtonPressed;
import org.malai.javafx.interaction.library.ComboBoxSelected;
import org.malai.javafx.interaction.library.MenuItemPressed;
import org.malai.javafx.interaction.library.SpinnerValueChanged;
import org.malai.javafx.interaction.library.TabSelected;
import org.malai.javafx.interaction.library.ToggleButtonPressed;

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

	protected <A extends ActionImpl> void bindKeyShortcut(final Collection<KeyCode> keys, final Class<A> clazzAction,
														  final Consumer<A> initActionFct, final Node... widgets)
			throws IllegalAccessException, InstantiationException {
		addBinding(new KeysShortcutBinding<>(this, clazzAction, initActionFct, keys, widgets));
	}

	protected <A extends ActionImpl> void bindKeyShortcut(final KeyCode key, final Class<A> clazzAction,
														  final Consumer<A> initActionFct, final Node... widgets)
			throws IllegalAccessException, InstantiationException {
		bindKeyShortcut(Collections.singletonList(key), clazzAction, initActionFct, widgets);
	}

	protected <A extends ActionImpl> void bindKeyShortcut(final KeyCode key, final Class<A> clazzAction,
														  final Consumer<A> initActionFct, final Window... windows)
			throws IllegalAccessException, InstantiationException {
		bindKeyShortcut(Collections.singletonList(key), clazzAction, initActionFct, windows);
	}

	protected <A extends ActionImpl> void bindKeyShortcut(final Collection<KeyCode> keys, final Class<A> clazzAction,
														  final Consumer<A> initActionFct, final Window... windows)
			throws IllegalAccessException, InstantiationException {
		addBinding(new KeysShortcutBinding<>(this, clazzAction, initActionFct, keys, windows));
	}

	protected <A extends ActionImpl> void bindMenu(final Class<A> clazzAction, final Consumer<A> initActionFct, final MenuItem... menus)
			throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonMenuBinding<>(this, false, clazzAction, MenuItemPressed.class, initActionFct, menus));
	}

	protected <A extends ActionImpl> void bindMenu(final Class<A> clazzAction, final BiConsumer<A, MenuItemPressed> initActionFct, final MenuItem... menus)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonMenuBinding<>(this, false, clazzAction, MenuItemPressed.class, initActionFct, menus));
	}

	protected <A extends ActionImpl, I extends JfxInteraction> void bindWindow(final Class<A> clazzAction, final Consumer<A> initActionFct,
																			   final Class<I> interaction, final Window...windows)
			throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonWindowBinding<>(this, false, clazzAction, interaction, initActionFct, windows));
	}

	protected <A extends ActionImpl, I extends JfxInteraction> void bindWindow(final Class<A> clazzAction, final BiConsumer<A, I> initActionFct,
																			   final Class<I> interaction, final Window...windows)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonWindowBinding<>(this, false, clazzAction, interaction, initActionFct, windows));
	}

	protected <A extends ActionImpl> void bindButton(final Class<A> clazzAction, final Consumer<A> initActionFct, final Button... buttons)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ButtonPressed.class, initActionFct, buttons));
	}

	protected <A extends ActionImpl> void bindButton(final Class<A> clazzAction, final BiConsumer<A, ButtonPressed> initActionFct, final Button... buttons)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ButtonPressed.class, initActionFct, buttons));
	}

	protected <A extends ActionImpl> void bindToggleButton(final Class<A> clazzAction, final Consumer<A> initActionFct, final ToggleButton... buttons)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ToggleButtonPressed.class, initActionFct, buttons));
	}

	protected <A extends ActionImpl> void bindToggleButton(final Class<A> clazzAction, final BiConsumer<A, ToggleButtonPressed> initActionFct, final ToggleButton... buttons)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ToggleButtonPressed.class, initActionFct, buttons));
	}

	protected <A extends ActionImpl> void bindComboBox(final Class<A> clazzAction, final Consumer<A> initActionFct, final ComboBox<?>... cbs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ComboBoxSelected.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void bindComboBox(final Class<A> clazzAction, final BiConsumer<A, ComboBoxSelected> initActionFct, final ComboBox<?>... cbs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, ComboBoxSelected.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void bindCheckbox(final Class<A> clazzAction, final Consumer<A> initActionFct, final CheckBox... cbs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, BoxChecked.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void bindCheckbox(final Class<A> clazzAction, final BiConsumer<A, BoxChecked> initActionFct, final CheckBox... cbs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, BoxChecked.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void bindSpinner(final Class<A> clazzAction, final Consumer<A> initActionFct, final Spinner<?>... spinners)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, SpinnerValueChanged.class, initActionFct, spinners));
	}

	protected <A extends ActionImpl> void bindSpinner(final Class<A> clazzAction, final BiConsumer<A, SpinnerValueChanged> initActionFct, final Spinner<?>... spinners)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, SpinnerValueChanged.class, initActionFct, spinners));
	}

	protected <A extends ActionImpl> void bindTab(final Class<A> clazzAction, final Consumer<A> initActionFct, final TabPane... tabs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, TabSelected.class, initActionFct, tabs));
	}

	protected <A extends ActionImpl> void bindTab(final Class<A> clazzAction, final BiConsumer<A, TabSelected> initActionFct, final TabPane... tabs)
		throws IllegalAccessException, InstantiationException {
		addBinding(new JFxAnonBinding<>(this, false, clazzAction, TabSelected.class, initActionFct, tabs));
	}
}
