package org.malai.fsm;

public class TerminalState<E> extends StateImpl<E> implements InputState<E> {
	public TerminalState(final FSM<E> stateMachine, final String stateName) {
		super(stateMachine, stateName);
	}

	@Override
	public void enter() throws CancelFSMException {
		fsm.onTerminating();
	}
}
