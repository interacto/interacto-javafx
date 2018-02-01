package org.malai.fsm;

import java.util.Collections;
import java.util.Set;

public class EpsilonTransition<E> extends Transition<E> {
	public EpsilonTransition(final OutputState<E> srcState, final InputState<E> tgtState) {
		super(srcState, tgtState);
	}

	@Override
	protected boolean accept(final E event) {
		return true;
	}

	@Override
	protected boolean isGuardOK(final E event) {
		return true;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Collections.emptySet();
	}
}
