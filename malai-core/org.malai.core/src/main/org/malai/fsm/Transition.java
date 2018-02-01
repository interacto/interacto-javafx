package org.malai.fsm;

import java.util.Optional;
import java.util.Set;

public abstract class Transition<E> {
	protected final OutputState<E> src;
	protected final InputState<E> tgt;

	protected Transition(final OutputState<E> srcState, final InputState<E> tgtState) {
		super();

		if(srcState == null || tgtState == null) {
			throw new IllegalArgumentException("States cannot be null");
		}

		src = srcState;
		tgt = tgtState;

		src.addTransition(this);
	}

	public Optional<InputState<E>> execute(final E event) throws CancelFSMException {
		if(accept(event) && isGuardOK(event)) {
			src.getFSM().stopCurrentTimeout();
			src.exit();
			action(event);
			tgt.enter();
			return Optional.of(tgt);
		}

		return Optional.empty();
	}

	protected void action(final E event) {
	}

	protected abstract boolean accept(final E event);

	protected abstract boolean isGuardOK(final E event);

	public abstract Set<Object> getAcceptedEvents();
}
