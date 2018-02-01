package org.malai.fsm;

public class CancellingState<E> extends StateImpl<E> implements InputState<E> {
	public CancellingState(final FSM<E> stateMachine, final String stateName) {
		super(stateMachine, stateName);
	}

	@Override
	public void enter() {
		fsm.onCancelling();
	}
}
