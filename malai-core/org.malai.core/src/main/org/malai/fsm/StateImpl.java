package org.malai.fsm;

abstract class StateImpl<E> implements State<E> {
	protected final FSM<E> fsm;
	protected final String name;

	protected StateImpl(final FSM<E> stateMachine, final String stateName) {
		super();
		fsm = stateMachine;
		name = stateName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public FSM<E> getFSM() {
		return fsm;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{name='" + name + "\'}";
	}
}
