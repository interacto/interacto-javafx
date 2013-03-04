package org.malai.wiimote.interaction;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;

/**
 * This interface can be used for object that want to gather Wiimote events (button pressed, moves, etc.) produced by HIDs.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Tom Demulier--Chevret, Juliette Gourlaouen, Maxime Lorant, Liantsoa Rasata-Manantena <br>
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
 * @author Tom Demulier--Chevret
 * @author Juliette Gourlaouen
 * @author Maxime Lorant
 * @author Liantsoa Rasata-Manantena
 * @since 0.2
 */
public interface WiimoteEventHandler {
	
	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.2
	 */
	void onButtonPressed(final ButtonsEvent button);
}
