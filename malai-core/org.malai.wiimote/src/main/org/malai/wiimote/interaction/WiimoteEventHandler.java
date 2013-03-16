package org.malai.wiimote.interaction;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;

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
	 * Defines action to do when a wiimote is disconnected/lost
	 * @param disconnection The disconnection event.
	 * @since 0.2
	 */
	void onDisconnection(final DisconnectionEvent disconnection);

	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.2
	 */
	void onButtonPressed(final ButtonsEvent button);
	
	/**
	 * Defines actions to do when joystick position data are received
	 * @param joystick
	 */
	void onJoystickMove(final JoystickEvent joystick);
	
	/**
	 * Defines actions to do when motion sensing data are received
	 * @param motion
	 */
	void onMotionSensing(final MotionSensingEvent motion);
	
	/**
	 * Defines actions to do when IR data are received
	 * @param ir
	 */
	void onIrEvent(final IREvent ir);
	
	
}
