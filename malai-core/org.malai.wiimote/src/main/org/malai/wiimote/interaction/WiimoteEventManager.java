package org.malai.wiimote.interaction;

import java.util.List;

import org.malai.interaction.BasicEventManager;

import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
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
	
	/**
	 * Creates a event manager that gathers Wiimote events and transfers them to handlers.
	 * @since 0.2
	 */
	public WiimoteEventManager() {
		super();
		wiimoteHandlers = null;
	}
	
	public void attachTo(final Wiimote controller) {
		if(controller != null) {
			controller.addWiiMoteEventListeners(this);
		}
		
	}


	public void detachForm(final Wiimote controller) {
		if(controller != null) {
			controller.disconnect();
		}
		
	}
	
	
	public void onButtonsEvent(WiimoteButtonsEvent button) {
		if(wiimoteHandlers != null) {
			for(final WiimoteEventHandler handler : wiimoteHandlers)
				handler.onButtonPressed(button);
		}
	}

	public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onExpansionEvent(ExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onIrEvent(IREvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
