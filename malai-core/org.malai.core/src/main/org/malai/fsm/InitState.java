package org.malai.fsm;

public class InitState<E> extends OutputStateImpl<E> {
	protected InitState(final FSM<E> stateMachine, final String stateName) {
		super(stateMachine, stateName);
	}

	@Override
	public void exit() throws CancelFSMException {
		fsm.onStarting();
	}
}
