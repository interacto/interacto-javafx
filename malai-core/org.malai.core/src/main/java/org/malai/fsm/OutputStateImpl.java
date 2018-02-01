package org.malai.fsm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class OutputStateImpl<E> extends StateImpl<E> implements OutputState<E> {
	protected final List<Transition<E>> transitions;

	protected OutputStateImpl(final FSM<E> stateMachine, final String stateName) {
		super(stateMachine, stateName);
		transitions = new ArrayList<>();
	}


	@Override
	public List<Transition<E>> getTransitions() {
		return Collections.unmodifiableList(transitions);
	}

	@Override
	public void addTransition(final Transition<E> tr) {
		if(tr != null) {
			transitions.add(tr);
		}
	}
}
