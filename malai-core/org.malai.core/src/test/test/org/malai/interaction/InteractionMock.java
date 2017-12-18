package test.org.malai.interaction;

import org.malai.interaction.InteractionImpl;
import org.malai.stateMachine.State;

public class InteractionMock extends InteractionImpl {
	public InteractionMock() {
		super();
	}

	@Override
	protected void changeEventsRegistered(final State oldState) {

	}

	@Override
	protected void initStateMachine() {
		//
	}


	@Override
	protected void processEvent(final Event event) {
		//
	}
}
