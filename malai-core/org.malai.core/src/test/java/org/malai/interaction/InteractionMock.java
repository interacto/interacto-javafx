package org.malai.interaction;

import org.malai.fsm.FSM;
import org.malai.fsm.OutputState;

public class InteractionMock extends InteractionImpl<Object, FSM<Object>> {
	public InteractionMock() {
		super(new FSM<>());
	}

	@Override
	protected void updateEventsRegistered(final OutputState<Object> newState, final OutputState<Object> oldState) {
	}

	@Override
	protected void reinitData() {
	}
}
