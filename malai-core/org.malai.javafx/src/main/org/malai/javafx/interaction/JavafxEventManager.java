package org.malai.javafx.interaction;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;

import org.malai.interaction.BasicEventManager;

/**
 * A Javafx event manager gathers Javafx events produces by widgets and transfers them handlers.<br>
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
public class JavafxEventManager extends BasicEventManager<Node> {
	protected ActionEvtHandler actionHandler;
	protected List<JavafxEventHandler> javafxHandlers;
	
	/**
	 * Creates a event manager that gathers Javafx events and transfers them to handlers.
	 */
	public JavafxEventManager() {
		super();
		actionHandler = new ActionEvtHandler();
		javafxHandlers = new ArrayList<>();
	}

	
	public EventHandler<ActionEvent> actionHandler() {
		return actionHandler;
	}

	@Override
	public void detachForm(Node comp) {
		if(comp==null) return;
		if(comp instanceof ButtonBase) {
			final ButtonBase but = (ButtonBase) comp;
			but.removeEventHandler(ActionEvent.ACTION, actionHandler);
		}
	}


	@Override
	public void attachTo(Node comp) {
		if(comp==null) return;
		if(comp instanceof ButtonBase) {
			final ButtonBase but = (ButtonBase) comp;
			but.setOnAction(actionHandler);
		}
	}
	
	private class ActionEvtHandler implements EventHandler<ActionEvent> {
		public ActionEvtHandler() {
			super();
		}

		@Override
		public void handle(ActionEvent evt) {
			final Object src = evt.getSource();
			if(src instanceof ButtonBase) {
				final ButtonBase but = (ButtonBase)src;
				for(final JavafxEventHandler h: javafxHandlers)
					h.onButtonPressed(but);
			}
		}
	}
}
