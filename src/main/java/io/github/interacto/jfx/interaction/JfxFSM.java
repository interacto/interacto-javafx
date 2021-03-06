/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction;

import io.github.interacto.fsm.FSM;
import io.github.interacto.fsm.InitState;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The base class for JavaFX FSMs.
 * @param <H> The type of handler notified during the running of the FSM
 */
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
	protected void onTimeout() {
		if(Platform.isFxApplicationThread()) {
			super.onTimeout();
		}else {
			Platform.runLater(() -> super.onTimeout());
		}
	}

	@Override
	public boolean process(final Event event) {
		// Removing the possible corresponding and pending key pressed event
		if(event instanceof KeyEvent && event.getEventType() == KeyEvent.KEY_RELEASED) {
			removeKeyEvent(((KeyEvent) event).getCode());
		}

		// Processing the event
		final boolean processed = super.process(event);

		// Recycling events
		if(processed
			&& event instanceof KeyEvent
			&& event.getEventType() == KeyEvent.KEY_PRESSED
			&& !(getCurrentState() instanceof InitState)
			&& eventsToProcess.stream().noneMatch(evt -> ((KeyEvent) evt).getCode() == ((KeyEvent) event).getCode())) {
			addRemaningEventsToProcess((KeyEvent) event);
		}

		return processed;
	}


	protected void addRemaningEventsToProcess(final KeyEvent evt) {
		synchronized(this.eventsToProcess) {
			// We override the method not to clone the event:
			// the key modifiers must not be cloned as we are interesting in its key code
			this.eventsToProcess.add(new KeyEvent(evt.getSource(), evt.getTarget(), evt.getEventType(), evt.getCharacter(),
				evt.getText(), evt.getCode(), false, false, false, false));
		}
	}


	/**
	 * Removes the given KeyPress event from the events 'still in process' list.
	 * @param key The key code of the event to remove.
	 */
	private void removeKeyEvent(final KeyCode key) {
		synchronized(eventsToProcess) {
			boolean removed = false;
			Event event;
			int i = 0;

			while(i < eventsToProcess.size() && !removed) {
				event = eventsToProcess.get(i);
				if(event instanceof KeyEvent && ((KeyEvent) event).getCode() == key) {
					removed = true;
					eventsToProcess.remove(i);
				}else {
					i++;
				}
			}
		}
	}

	public H getDataHandler() {
		return dataHandler;
	}

	@Override
	public void uninstall() {
		super.uninstall();
		dataHandler = null;
	}
}
