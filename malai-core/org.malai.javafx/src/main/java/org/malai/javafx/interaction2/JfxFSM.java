/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction2;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.FSM;
import org.malai.interaction.InitState;

public abstract class JfxFSM<H extends FSMDataHandler> extends FSM<Event> {
	protected H dataHandler;

	protected void buildFSM(final H dataHandler) {
		if(states.size() > 1) {
			return;
		}
		this.dataHandler = dataHandler;
	}

	@Override
	public void reinit() {
		super.reinit();
		if(dataHandler != null && !inner) {
			dataHandler.reinitData();
		}
	}

	@Override
	public boolean process(final Event event) {
		// Removing the possible corresponding and pending key pressed event
		if(event.getEventType() == KeyEvent.KEY_RELEASED && event instanceof KeyEvent) {
			removeKeyEvent(((KeyEvent) event).getCode());
		}

		// Processing the event
		final boolean processed = super.process(event);

		// Recycling events
		if(processed && event.getEventType() == KeyEvent.KEY_PRESSED && !(currentState instanceof InitState) && event instanceof KeyEvent &&
			(eventsToProcess == null || eventsToProcess.stream().noneMatch(evt -> ((KeyEvent) evt).getCode() == ((KeyEvent) event).getCode()))) {
			addRemaningEventsToProcess(event);
		}

		return processed;
	}

	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param key The key code of the event to remove.
	 */
	private void removeKeyEvent(final KeyCode key) {
		if(eventsToProcess == null) {
			return;
		}

		synchronized(eventsToProcess) {
			boolean removed = false;
			Event event;

			for(int i = 0, size = eventsToProcess.size(); i < size && !removed; i++) {
				event = eventsToProcess.get(i);

				if(event instanceof KeyEvent && ((KeyEvent) event).getCode() == key) {
					removed = true;
					eventsToProcess.remove(i);
				}
			}
		}
	}

	public H getDataHandler() {
		return dataHandler;
	}
}
