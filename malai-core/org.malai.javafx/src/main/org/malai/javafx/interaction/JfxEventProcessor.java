package org.malai.javafx.interaction;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBoxBase;

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
	void onJfxButtonPressed(final ButtonBase button);
	
	/**
	 * Defines actions to do when a combo box is used.
	 * @param cc the combo box.
	 */
	void onJfxComboBoxSelected(final ComboBoxBase<?> cc);
}
