/*
 * This interaction permits to mouse press with key pressures (eg modifiers).<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2017 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Defines all the events a JavaFX interaction must manage.
 * @author Arnaud BLOUIN
 */
public interface JfxEventProcessor {
	/**
	 * Defines action to do when the button of a mouse or something equivalent is pressed.
	 * @param idHID The identifier of the used HID.
	 * @since 2.0
	 */
	void onPressure(final MouseEvent evt, final int idHID);

	/**
	 * Defines action to do when the button of a mouse or something equivalent is released.
	 * @param idHID The identifier of the used HID.
	 * @since 2.0
	 */
	void onRelease(final MouseEvent evt, final int idHID);

	/**
	 * Defines action to do when a mouse or something equivalent is moved.
	 * @param idHID The identifier of the used HID.
	 * @since 2.0
	 */
	void onMove(final MouseEvent evt, final int idHID);

	/**
	 * Defines action to do when a mouse or something equivalent is dragged.
	 * @param idHID The identifier of the used HID.
	 * @since 2.0
	 */
	void onDrag(final MouseEvent evt, final int idHID);

	/**
	 * Defines actions to do when a key of a keyboard is pressed.
	 * @param idHID The identifier of the HID that produced the event.
	 * @since 2.0
	 */
	void onKeyPressure(final KeyEvent event, final int idHID);

	/**
	 * Defines actions to do when a key of a keyboard is released.
	 * @param idHID The identifier of the HID that produced the event.
	 * @since 2.0
	 */
	void onKeyRelease(final KeyEvent event, final int idHID);

	/**
	 * Defines actions to do when a button is triggered.
	 * @param button The pressed button.
	 */
	void onJfxButtonPressed(final Button button);

	/**
	 * Defines actions to do when a checkbox is used.
	 * @param cb The pressed button.
	 */
	void onJfxBoxChecked(final CheckBox cb);

	/**
	 * Defines actions to do when a combo box is used.
	 * @param cc the combo box.
	 */
	void onJfxComboBoxSelected(final ComboBox<?> cc);

	/**
	 * Defines actions to do when a date is picked.
	 * @param picker the date picker.
	 */
	void onJfxDatePicked(final DatePicker picker);

	/**
	 * Defines actions to do when a colour is picked.
	 * @param picker The colour picker.
	 */
	void onJfxColorPicked(final ColorPicker picker);

	/**
	 * Defines actions to do when a text are set within a text field.
	 * @param tf The involved text field.
	 */
	void onTextChanged(final TextInputControl tf);

	/**
	 * Defines actions to do when an hyperlink is clicked.
	 * @param link The pressed button.
	 */
	void onJfxHyperlinkClicked(final Hyperlink link);

	/**
	 * Defines actions to do when a menu button is clicked.
	 * @param button The pressed menu button.
	 */
	void onJfxMenuButtonPressed(final MenuButton button);

	/**
	 * Defines actions to do when a menu item is clicked.
	 * @param item The pressed menu button.
	 */
	void onJfxMenuItemPressed(final MenuItem item);

	/**
	 * Defines actions to do when a toggle button is clicked.
	 * @param button The pressed toggle button.
	 */
	void onJfxToggleButtonPressed(final ToggleButton button);

	/**
	 * Defines actions to do when the value of a spinner has changed.
	 * @param spinner The spinner.
	 */
	void onJfxSpinnerValueChanged(final Spinner<?> spinner);

	/**
	 * Defines actions to do when a tab is selected.
	 * @param tabPane The tab pane.
	 */
	void onJfXTabSelected(final TabPane tabPane);

	/**
	 * Actions to do on scroll.
	 * @param evt The scroll event.
	 * @param idHID The ID of the HID that produced the event.
	 */
	void onScroll(final ScrollEvent evt, final int idHID);
}
