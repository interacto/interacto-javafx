package org.malai.jinput.interaction;

import net.java.games.input.Component;

/**
 * This interface can be used for object that want to gather Jinput events (button pressed, etc.) produced by HIDs.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-02-26<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public interface JinputEventHandler {
	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.2
	 */
	void onButtonPressed(final Component.Identifier.Button button);
}
