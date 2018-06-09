package org.malai.interaction;

import org.malai.fsm.FSM;
import org.malai.fsm.OutputState;

public class InteractionMock extends InteractionImpl<InteractionData, Object, FSM<Object>> {
	public InteractionMock() {
		super(new FSM<>());
	}

	@Override
	public InteractionData getData() {
		return null;
	}

	@Override
	protected void updateEventsRegistered(final OutputState<Object> newState, final OutputState<Object> oldState) {
	}

	@Override
	protected boolean isEventsOfSameType(final Object evt1, final Object evt2) {
		return false;
	}

	@Override
	protected void runInUIThread(final Runnable cmd) {
		cmd.run();
	}

	@Override
	protected void reinitData() {
	}
}
