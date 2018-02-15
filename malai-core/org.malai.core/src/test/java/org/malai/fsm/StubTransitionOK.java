package org.malai.fsm;

import java.util.Set;
import org.mockito.internal.util.collections.Sets;

public class StubTransitionOK extends Transition<StubEvent> {
	protected boolean guard;

	protected StubTransitionOK(final OutputState<StubEvent> srcState, final InputState<StubEvent> tgtState) {
		this(srcState, tgtState, true);
	}

	protected StubTransitionOK(final OutputState<StubEvent> srcState, final InputState<StubEvent> tgtState, final boolean guard) {
		super(srcState, tgtState);
		this.guard = guard;
	}

	@Override
	protected boolean accept(final StubEvent event) {
		return true;
	}

	@Override
	protected boolean isGuardOK(final StubEvent event) {
		return guard;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Sets.newSet(StubEvent.class);
	}
}

class SubStubTransition1 extends StubTransitionOK {
	protected SubStubTransition1(final OutputState<StubEvent> srcState, final InputState<StubEvent> tgtState, final boolean guard) {
		super(srcState, tgtState, guard);
	}

	@Override
	public boolean accept(final StubEvent event) {
		return event instanceof StubSubEvent1;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Sets.newSet(StubSubEvent1.class);
	}
}

class SubStubTransition2 extends StubTransitionOK {
	protected SubStubTransition2(final OutputState<StubEvent> srcState, final InputState<StubEvent> tgtState, final boolean guard) {
		super(srcState, tgtState, guard);
	}

	@Override
	public boolean accept(final StubEvent event) {
		return event instanceof StubSubEvent2;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Sets.newSet(StubSubEvent2.class);
	}
}

class SubStubTransition3 extends StubTransitionOK {
	protected SubStubTransition3(final OutputState<StubEvent> srcState, final InputState<StubEvent> tgtState, final boolean guard) {
		super(srcState, tgtState, guard);
	}

	@Override
	public boolean accept(final StubEvent event) {
		return event instanceof StubSubEvent3;
	}

	@Override
	public Set<Object> getAcceptedEvents() {
		return Sets.newSet(StubSubEvent3.class);
	}
}
