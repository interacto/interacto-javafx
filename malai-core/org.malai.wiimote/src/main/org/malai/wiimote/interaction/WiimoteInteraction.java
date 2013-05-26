package org.malai.wiimote.interaction;

import org.malai.interaction.InitState;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.ITransition;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

/**
 * Main listener, which returns every events from the Wiimotes to the correct transition.<br>
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
public class WiimoteInteraction extends Interaction implements WiimoteEventHandler {
	
	public WiimoteInteraction() {
		super();
	}
	
	/**
	 * Initialize the interaction with a predefined InitState
	 * @param initState the initialize state.
	 */
	public WiimoteInteraction(final InitState initState) {
		super(initState);
	}
	
	/**
	 * Initialises the interaction: creates the states and the transitions.
	 */
	@Override
	protected void initStateMachine() {
		// Has to be override in subclasses
	}
		
	
	public void onButtonPressed(ButtonsEvent button) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;
		
		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);
			
			if(t instanceof ButtonPressedTransition) {
				((ButtonPressedTransition)t).setButton(button);
				again = !checkTransition(t);
			}
		}
		
	}

	public void onMotionSensing(MotionSensingEvent motion) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof MotionSensingTransition) {
				((MotionSensingTransition)t).setMotion(motion);
				again = !checkTransition(t);
			}
		}
	}

	public void onJoystickMove(JoystickEvent joystick) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;
		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);
			
			if(t instanceof JoystickMoveTransition) {
				((JoystickMoveTransition)t).setJoystick(joystick);
				again = !checkTransition(t);
			}
		}
	}
	
	public void onNunchukEvent(NunchukEvent nunchuk) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;
		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);
			if(t instanceof NunchukEvent) {
				((NunchukTransition)t).setNunchukEvent(nunchuk);
				again = !checkTransition(t);
			}
		}
	}

	public void onIrEvent(IREvent ir) {
		if(!activated) return ;

		boolean again = true;
		ITransition t;

		for(int i=0, j=currentState.getTransitions().size(); i<j && again; i++) {
			t = currentState.getTransition(i);

			if(t instanceof IRTransition) {
				((IRTransition)t).setIR(ir);
				again = !checkTransition(t);
			}
		}
	}
	
	/**
	 * 
	 */
	public void onStatus(StatusEvent status) {
		if(!activated) return ;	
		
	}
	
	/**
	 * 
	 */
	public void onDisconnection(DisconnectionEvent disconnection) {
		if(!activated) return ;		
	}
}
