package org.malai.javafx.interaction2;

import javafx.event.Event;
import org.malai.fsm.FSM;

public abstract class JfxFSM<H extends FSMDataHandler> extends FSM<Event> {
	protected H dataHandler;

	protected void buildFSM(final H dataHandler) {
		if(states.size() > 1) return;
		this.dataHandler = dataHandler;
	}

	@Override
	public void reinit() {
		super.reinit();
		if(dataHandler != null && !inner) {
			dataHandler.reinitData();
		}
	}

	public H getDataHandler() {
		return dataHandler;
	}
}
