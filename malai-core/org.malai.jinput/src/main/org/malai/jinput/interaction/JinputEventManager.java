package org.malai.jinput.interaction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.malai.interaction.BasicEventManager;
import org.malai.interaction.EventHandler;

import net.java.games.input.*;

/**
 * A Jinput event manager gathers Jinput events produces by widgets and transfers them handlers.<br>
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
public class JinputEventManager extends BasicEventManager<Controller> implements ControllerListener {
	/** A subset of the set 'handlers' corresponding to the Jinput Handlers. */
	private List<JinputEventHandler> jinputHandlers;
	private ControllerEnvironment env = ControllerEnvironment.getDefaultEnvironment() ;
	
	
	/**
	 * Creates a event manager that gathers Jinput events and transfers them to handlers.
	 * @since 0.2
	 */
	public JinputEventManager() {
		super();
		jinputHandlers = null;
	}
	
	
	/**
	 * Attaches the JinputEventManager to the component to listen.
	 * @param controller The Component to listen.
	 */
	// C'est plutôt : ajoute le listener à la liste des listeners (globaux) de l'environnement
	public void attachTo(final Controller controller) {
		if (controller != null)
			env.addControllerListener(this) ;
	}
	

	public void detachForm(final Controller controller) {
		if(controller!=null) {
			env.removeControllerListener(this);
		}
	}
	
	
	public void addHandlers(final EventHandler h) {
		super.addHandlers(h);
		if(h instanceof JinputEventHandler) {
			if(jinputHandlers==null) jinputHandlers = new CopyOnWriteArrayList<JinputEventHandler>();
			jinputHandlers.add((JinputEventHandler)h);
		}
	}


	public void removeHandler(final EventHandler h) {
		super.removeHandler(h);
		if(h!=null && jinputHandlers!=null) 
			jinputHandlers.remove(h);
	}
	
	
	public void onButton(final Component.Identifier.Button button) {
		if(jinputHandlers!=null) {
			for(final JinputEventHandler handler : jinputHandlers)
				handler.onButtonPressed(button);
		}
	}

	public void controllerRemoved(ControllerEvent ce) {
		detachForm(ce.getController()) ;
		
	}


	public void controllerAdded(ControllerEvent ce) {
		attachTo(ce.getController()) ;
	}
}
