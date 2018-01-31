package org.malai.fsm;

public class StdState<E> extends OutputStateImpl<E> implements InputState<E> {
	public StdState(final FSM<E> stateMachine, final String stateName) {
		super(stateMachine, stateName);
	}

	@Override
	public void enter() throws CancelFSMException {
		fsm.enterStdState(this);
	}

	@Override
	public void exit() {

	}
}
