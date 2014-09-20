package org.malai.javafx.interaction;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * Defines all the events an JavaFX interaction must manage.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 * @date 2014-09-19
 * @version 2.0
 */
public interface JfxEventProcessor {
	/**
	 * Defines actions to do when a button is triggered.
	 * @param button The pressed button.
	 */
	void onJfxButtonPressed(final Button button);
	
	/**
	 * Defines actions to do when a checkbox is used.
	 * @param cb The pressed button.
	 */
	void onJfxCheckBoxUsed(final CheckBox cb);
	
	/**
	 * Defines actions to do when a combo box is used.
	 * @param cc the combo box.
	 */
	void onJfxComboBoxSelected(final ComboBox<?> cc);
	
	/**
	 * Defines actions to do when a date is picked.
	 * @param cc the combo box.
	 */
	void onJfxDatePicked(final DatePicker picker);
	
	/**
	 * Defines actions to do when a colour is picked.
	 * @param cc the combo box.
	 */
	void onJfxColorPicked(final ColorPicker picker);
	
	/**
	 * Defines actions to do when a text are set within a text field.
	 * @param tf The involved text field.
	 */
	void onTextChanged(final TextField tf);

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
	 * @param button The pressed menu button.
	 */
	void onJfxMenuItemPressed(final MenuItem item);

	/**
	 * Defines actions to do when a toggle button is clicked.
	 * @param button The pressed toggle button.
	 */
	void onJfxToggleButtonsPressed(ToggleButton button);
}
