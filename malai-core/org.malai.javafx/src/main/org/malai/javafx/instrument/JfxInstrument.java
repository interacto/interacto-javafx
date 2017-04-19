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
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.malai.action.ActionImpl;
import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.instrument.library.KeysShortcutInteractor;
import org.malai.javafx.interaction.JfxInteraction;
import org.malai.javafx.interaction.library.BoxChecked;
import org.malai.javafx.interaction.library.ButtonPressed;
import org.malai.javafx.interaction.library.ComboBoxSelected;
import org.malai.javafx.interaction.library.MenuItemPressed;
import org.malai.javafx.interaction.library.SpinnerValueChanged;
import org.malai.javafx.interaction.library.TabSelected;

/**
 * Base of an instrument for JavaFX applications.
 * @author Arnaud BLOUIN
 */
public abstract class JfxInstrument extends InstrumentImpl<JfxInteractor<?, ? extends JfxInteraction, ? extends JfxInstrument>> {
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

	protected <A extends ActionImpl> void addKeyShortcutInteractor(final Collection<KeyCode> keys, final Class<A> clazzAction,
																   final Consumer<A> initActionFct, final Node... widgets)
			throws IllegalAccessException, InstantiationException {
		addInteractor(new KeysShortcutInteractor<>(this, clazzAction, initActionFct, keys, widgets));
	}

	protected <A extends ActionImpl> void addKeyShortcutInteractor(final KeyCode key, final Class<A> clazzAction,
																   final Consumer<A> initActionFct, final Node... widgets)
			throws IllegalAccessException, InstantiationException {
		addKeyShortcutInteractor(Collections.singletonList(key), clazzAction, initActionFct, widgets);
	}

	protected <A extends ActionImpl> void addKeyShortcutInteractor(final KeyCode key, final Class<A> clazzAction,
																   final Consumer<A> initActionFct, final Window... windows)
			throws IllegalAccessException, InstantiationException {
		addKeyShortcutInteractor(Collections.singletonList(key), clazzAction, initActionFct, windows);
	}

	protected <A extends ActionImpl> void addKeyShortcutInteractor(final Collection<KeyCode> keys, final Class<A> clazzAction,
																   final Consumer<A> initActionFct, final Window... windows)
			throws IllegalAccessException, InstantiationException {
		addInteractor(new KeysShortcutInteractor<>(this, clazzAction, initActionFct, keys, windows));
	}

	protected <A extends ActionImpl> void addMenuInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final MenuItem... menus)
			throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonMenuInteractor<>(this, false, clazzAction, MenuItemPressed.class, initActionFct, menus));
	}

	protected <A extends ActionImpl, I extends JfxInteraction> void addWindowInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct,
															  final Class<I> interaction, final Window...windows)
			throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonWindowInteractor<>(this, false, clazzAction, interaction, initActionFct, windows));
	}

	protected <A extends ActionImpl> void addButtonInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final Button... buttons)
		throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, clazzAction, ButtonPressed.class, initActionFct, buttons));
	}

	protected <A extends ActionImpl> void addComboBoxInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final ComboBox<?>... cbs)
		throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, clazzAction, ComboBoxSelected.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void addCheckBoxInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final CheckBox... cbs)
		throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, clazzAction, BoxChecked.class, initActionFct, cbs));
	}

	protected <A extends ActionImpl> void addSpinnerInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final Spinner<?>... spinners)
		throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, clazzAction, SpinnerValueChanged.class, initActionFct, spinners));
	}

	protected <A extends ActionImpl> void addTabInteractor(final Class<A> clazzAction, final Consumer<A> initActionFct, final TabPane... tabs)
		throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, clazzAction, TabSelected.class, initActionFct, tabs));
	}
}
