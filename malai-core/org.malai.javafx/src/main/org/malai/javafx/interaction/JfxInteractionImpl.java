package org.malai.javafx.interaction;

import java.util.ArrayList;

import org.malai.interaction.InteractionImpl;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * The core class for defining interactions using the JavaFX library.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. <br>
 * Malai is distributed without any warranty; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2014-09-18<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public abstract class JfxInteractionImpl extends InteractionImpl implements JfxInteraction {
	/**
	 * Creates a JavaFX interaction.
	 */
	public JfxInteractionImpl() {
		super();
	}

	@Override
	protected void processEvent(final Event event) {
		if(event instanceof MousePressEvent) {
			final MousePressEvent press = (MousePressEvent)event;
			onPressure(press.evt, press.getIdHID());
		}else if(event instanceof KeyPressEvent) {
			final KeyPressEvent key = (KeyPressEvent)event;
			onKeyPressure(key.evt, key.getIdHID());
		}
	}
	
	@Override
	public void onKeyPressure(final KeyEvent event, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyPressureTransition).filter(tr -> {
			final KeyPressureTransition pt =  (KeyPressureTransition)tr;
			pt.setEvent(event);
			pt.setHid(idHID);
			boolean found = checkTransition(tr);
			
			if(found && !stillInProgressContainsKey(idHID, event.getCode())) {
				// Adding an event 'still in process'
				addEvent(new KeyPressEvent(idHID, event));
			}
			return found;
		}).findFirst();
	}
	
	@Override
	public void onKeyRelease(final KeyEvent event, final int idHID) {
		if(!isActivated()) return ;
		boolean found = getCurrentState().getTransitions().stream().filter(tr -> tr instanceof KeyReleaseTransition).filter(tr -> {
			final KeyReleaseTransition pt =  (KeyReleaseTransition)tr;
			pt.setEvent(event);
			pt.setHid(idHID);
			
			if(tr.isGuardRespected()) {
				// Removing from the 'still in process' list
				removeKeyEvent(idHID, event.getCode());
				return checkTransition(tr);
			}
			return false;
		}).findFirst().isPresent();
		
		if(!found) {
			removeKeyEvent(idHID, event.getCode());
		}
	}
	
	@Override
	public void onPressure(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		getCurrentState().getTransitions().stream().filter(tr -> tr instanceof PressureTransition).filter(tr -> {
			final PressureTransition pt = (PressureTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			boolean found = checkTransition(tr);
			
			if(found) {
				// Adding an event 'still in process'
				addEvent(new MousePressEvent(idHID, evt));
			}
			
			return found;
		}).findFirst();
	}
	
	@Override
	public void onRelease(final MouseEvent evt, final int idHID) {
		if(!isActivated()) return ;
		boolean found = getCurrentState().getTransitions().stream().filter(tr -> tr instanceof ReleaseTransition).filter(tr -> {
			final ReleaseTransition pt = (ReleaseTransition)tr;
			pt.setEvent(evt);
			pt.setHid(idHID);
			
			if(tr.isGuardRespected()) {
				// Removing from the 'still in process' list
				removePressEvent(idHID);
				return checkTransition(tr);
			}
			return false;
		}).findFirst().isPresent();
		
		if(!found) {
			removePressEvent(idHID);
		}
	}
	
	private void addEvent(final Event event) {
		if(stillProcessingEvents==null)
			stillProcessingEvents = new ArrayList<>();

		synchronized(stillProcessingEvents) {
			stillProcessingEvents.add(event);
		}
	}
	
	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param idHID The identifier of the HID which produced the event.
	 * @param key The key code of the event to remove.
	 * @since 0.2
	 */
	private void removeKeyEvent(final int idHID, final KeyCode key) {
		if(stillProcessingEvents==null) return ;

		synchronized(stillProcessingEvents) {
			boolean removed = false;
			Event event;
			
			for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
				event = stillProcessingEvents.get(i);
	
				if(event.getIdHID()==idHID && event instanceof KeyPressEvent && ((KeyPressEvent)event).evt.getCode()==key) {
					removed = true;
					stillProcessingEvents.remove(i);
				}
			}
		}
	}
	
	private void removePressEvent(final int idHID) {
		if(stillProcessingEvents==null) return ;

		boolean removed = false;
		Event event;

		synchronized(stillProcessingEvents) {
			for(int i=0, size=stillProcessingEvents.size(); i<size && !removed; i++) {
				event = stillProcessingEvents.get(i);
	
				if(event.getIdHID()==idHID && event instanceof MousePressEvent) {
					removed = true;
					stillProcessingEvents.remove(i);
				}
			}
		}
	}

	/**
	 * Checks that the list stillProcessingEvents does not contains a keyEvent corresponding to the given one.
	 */
	private boolean stillInProgressContainsKey(final int idHID, final KeyCode key) {
		if(stillProcessingEvents == null)
			return false;
		synchronized(stillProcessingEvents) {
			return stillProcessingEvents.stream().filter(evt -> idHID == evt.getIdHID() && evt instanceof KeyPressEvent && ((KeyPressEvent)evt).evt.getCode() == key).
					findFirst().isPresent();
		}
	}

	private static class MousePressEvent extends Event {
		MouseEvent evt;

		MousePressEvent(final int idHID, final MouseEvent evt) {
			super(idHID);
			this.evt = evt;
		}

		@Override
		public String toString() {
			return "MousePressEvent [evt=" + evt + "]";
		}
	}

	private static class KeyPressEvent extends Event {
		KeyEvent evt;

		KeyPressEvent(final int idHID, final KeyEvent evt) {
			super(idHID);
			this.evt = evt;
		}

		@Override
		public String toString() {
			return "KeyPressEvent [evt=" + evt + "]";
		}
	}
}
