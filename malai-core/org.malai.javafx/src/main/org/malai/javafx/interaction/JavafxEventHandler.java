package org.malai.javafx.interaction;

import javafx.scene.control.ButtonBase;

/**
 * This interface can be used for object that want to gather Javafx events (button pressed, etc.) produced by HIDs.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public interface JavafxEventHandler {
	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.1
	 */
	void onButtonPressed(final ButtonBase button);
}
