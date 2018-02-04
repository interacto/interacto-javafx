package org.malai.javafx.interaction2;

import javafx.event.Event;
import org.malai.fsm.FSM;

public abstract class JfxFSM<W, H extends FSMHandler> extends FSM<Event> {
	protected H handler;

	protected void buildFSM(final H handler) {
		this.handler = handler;
	}

	@Override
	public void reinit() {
		super.reinit();
		if(handler != null) {
			handler.reinitData();
		}
	}
}
