package org.malai.wiimote.interaction;

import java.util.ArrayList;
import java.util.List;

import org.malai.error.AttachWiimoteException;
import org.malai.interaction.BasicEventManager;
import org.malai.interaction.EventHandler;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

/**
 * An android event manager gathers Wiimote events produces by the controller and transfers them handlers.<br>
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
public class WiimoteEventManager extends BasicEventManager<Wiimote> implements WiimoteListener {

	/** A subset of the set 'handlers' corresponding to the Wiimote Handlers. */
	private List<WiimoteEventHandler> wiimoteHandlers;
	
	private Wiimote[] wiimotes;
	
	private static WiimoteEventManager instance;
	
	public static WiimoteEventManager getInstance() {
		return instance;
	}

	public static void init(int nbWiimotes) throws AttachWiimoteException {
		WiimoteEventManager.instance = new WiimoteEventManager(nbWiimotes);
	}

	/**
	 * Creates a event manager that gathers Wiimote events and transfers them to handlers.
	 * @throws AttachWiimoteException
	 * @since 0.2
	 */
	private WiimoteEventManager(int nbWiimotes) throws AttachWiimoteException {
		super();
		
		wiimotes = WiiUseApiManager.getWiimotes(nbWiimotes, false);
		
		// Check if every wiimotes has been connected
		if(wiimotes.length != nbWiimotes) {
			throw new AttachWiimoteException();
		}
		
		// For each wiimotes, attach listener
		for(int i = 0; i < nbWiimotes; i++) {
			wiimotes[i].addWiiMoteEventListeners(this);
			this.attachTo(wiimotes[i]);
		}
		
		wiimoteHandlers = null;
	}
	
	/**
	 * Attachs this listener to a new wiimote controller
	 * @TODO
	 */
	public void attachTo(final Wiimote controller) {
		if(controller != null) {
			controller.addWiiMoteEventListeners(this);
		}
	}

	/**
	 * Disconnect a wiimote
	 */
	public void detachForm(final Wiimote controller) {
		if(controller != null) {
			controller.addWiiMoteEventListeners(null);
		}
	}
	
	/**
	 * Disconnect a wiimote, via its wiimote ID
	 */
	public void detachForm(int id) {
		if(wiimotes.length < id) {
			wiimotes[id].addWiiMoteEventListeners(null);
		}
	}
	
	@Override
	public void addHandlers(final EventHandler h) {
		super.addHandlers(h);
		if(h instanceof WiimoteEventHandler) {
			if(wiimoteHandlers==null) 
				wiimoteHandlers = new ArrayList<WiimoteEventHandler>();
			wiimoteHandlers.add((WiimoteEventHandler)h);
		}
	}


	@Override
	public void removeHandler(final EventHandler h) {
		super.removeHandler(h);
		if(h != null && wiimoteHandlers!=null) 
			wiimoteHandlers.remove(h);
	}
	
	/**
	 * Active IR Event for all wiimotes
	 */
	public void enableIREvent() {
		for(Wiimote w : wiimotes)
			w.activateIRTRacking();
	}
	
	/**
	 * Desactive IR Event for all wiimotes
	 */
	public void disableIREvent() {
		for(Wiimote w : wiimotes)
			w.deactivateIRTRacking();
	}
	
	/**
	 * Active Motion Sensing Event for all wiimotes
	 */
	public void enableMotionEvent() {
		for(Wiimote w : wiimotes) {
			w.activateMotionSensing();
		}
	}
	
	/**
	 * Desactive Motion Sensing Event for all wiimotes
	 */
	public void disableMotionEvent() {
		for(Wiimote w : wiimotes)
			w.deactivateMotionSensing();
	}
	
	/**
	 * Event receives when a button is pressed on the wiimote or a plugged controller
	 * @param button Event object which contains info about the button pressed
	 * @since 0.2
	 */
	public void onButtonsEvent(WiimoteButtonsEvent button) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onButtonPressed(button);
		}
	}

	/**
	 * Event received when a wiimote is disconnected (no battery left, 
	 * connection dropped or controller has been turn off)
	 * @since 0.2
	 */
	public void onDisconnectionEvent(DisconnectionEvent disconnection) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onDisconnection(disconnection);
		}
	}

	/**
	 * Infra Red Event. Returns information about the position of the wiimote
	 * relative to the sensor bar
	 * @param ir
	 * @since 0.2
	 */
	public void onIrEvent(IREvent ir) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onIrEvent(ir);
		}
	}

	/**
	 * Motion Sensing Event. Returns information about movements made with the
	 * wiimote (orientation, force)
	 * @param motion
	 * @since 0.2 
	 */
	public void onMotionSensingEvent(MotionSensingEvent motion) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onMotionSensing(motion);
		}
	}



	/** 
	 * Called on a status event : when a expansion controller is (un)plugged
	 * Return many information about wiimote setup
	 * @param status Data about wiimote
	 * @since 0.2
	 */
	public void onStatusEvent(StatusEvent status) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onStatus(status);
		}
	}
	
	
	public void onExpansionEvent(ExpansionEvent arg0) {
		
		// Nunchuk management
		if (arg0 instanceof NunchukEvent) {
			NunchukEvent nunchuk = (NunchukEvent) arg0;
			NunchukButtonsEvent button = nunchuk.getButtonsEvent();
			JoystickEvent joystick = nunchuk.getNunchukJoystickEvent(); 
               
			
			if(wiimoteHandlers != null) {
				for(final WiimoteEventHandler handler : wiimoteHandlers)
					handler.onJoystickMove(joystick);
				
				for(final WiimoteEventHandler handler : wiimoteHandlers)
					handler.onButtonPressed(button);
			}
		}
	}
	
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Event receives when the classic controller is connected to the current Wiimote
	 * @param 
	 * @since 0.2
	 */
	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Event receives when the classic controller is removed
	 * @param 
	 * @since 0.2
	 */
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub	
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub	
	}

}
