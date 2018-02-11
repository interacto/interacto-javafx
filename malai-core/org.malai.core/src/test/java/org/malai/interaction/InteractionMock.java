package org.malai.interaction;

import org.malai.fsm.FSM;
import org.malai.fsm.OutputState;
import org.malai.interaction2.Interaction;

public class InteractionMock extends Interaction<Object, FSM<Object>> {
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
