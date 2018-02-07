package org.malai.fsm;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SubFSMTransition<E> extends Transition<E> {
	private final FSM<E> subFSM;
	private final FSMHandler subFSMHandler;

	public SubFSMTransition(final OutputState<E> srcState, final InputState<E> tgtState, final FSM<E> fsm) {
		super(srcState, tgtState);

		if(fsm == null) {
			throw new IllegalArgumentException("sub fsm cannot be null");
		}

		subFSM = fsm;
		subFSM.setInner(true);
		subFSMHandler = new FSMHandler() {
			@Override
			public void fsmStarts() {
			}

			@Override
			public void fsmUpdates() throws CancelFSMException {
				src.getFSM().currentState = subFSM.currentState;
				src.getFSM().onUpdating();
			}

			@Override
			public void fsmStops() throws CancelFSMException {
				action(null);
				subFSM.removeHandler(subFSMHandler);
				src.getFSM().currentSubFSM = null;
				if(tgt instanceof TerminalState) {
					tgt.enter();
					return;
				}
				if(tgt instanceof CancellingState) {
					fsmCancels();
					return;
				}
				if(tgt instanceof OutputState) {
					src.getFSM().currentState = (OutputState<E>) tgt;
					tgt.enter();
				}
			}

			@Override
			public void fsmCancels() {
				subFSM.removeHandler(subFSMHandler);
				src.getFSM().currentSubFSM = null;
				src.getFSM().onCancelling();
			}
		};
	}

	@Override
	public Optional<InputState<E>> execute(final E event) throws CancelFSMException {
		if(isGuardOK(event)) {
			src.getFSM().stopCurrentTimeout();
			final Optional<Transition<E>> transition = findTransition(event);
			src.exit();
			subFSM.addHandler(subFSMHandler);
			src.getFSM().currentSubFSM = subFSM;
			subFSM.process(event);
			return Optional.of(transition.get().tgt);
		}

		return Optional.empty();
	}

	@Override
	protected boolean accept(final E event) {
		return findTransition(event).isPresent();
	}

	@Override
	protected boolean isGuardOK(final E event) {
		return findTransition(event).filter(tr -> tr.isGuardOK(event)).isPresent();
	}

	private Optional<Transition<E>> findTransition(final E event) {
		return subFSM.initState.transitions.stream().filter(tr -> tr.accept(event)).findFirst();
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return subFSM.initState.getTransitions().stream().map(tr -> tr.getAcceptedEvents()).flatMap(s -> s.stream()).collect(Collectors.toSet());
	}
}
