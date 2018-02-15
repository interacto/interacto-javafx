package org.malai.fsm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestTimeoutTransition {
	TimeoutTransition<StubEvent> evt;
	OutputState<StubEvent> src;
	InputState<StubEvent> tgt;
	FSM<StubEvent> fsm;

	@BeforeEach
	void setUp() {
		fsm = Mockito.mock(FSM.class);
		src = Mockito.mock(OutputState.class);
		tgt = Mockito.mock(InputState.class);
		Mockito.when(src.getFSM()).thenReturn(fsm);
		Mockito.when(tgt.getFSM()).thenReturn(fsm);
		evt = new TimeoutTransition<>(src, tgt, () -> 50L);
	}

	@Test
	void testConstructorKO() {
		assertThrows(IllegalArgumentException.class, () -> new TimeoutTransition<>(src, tgt, null));
	}

	@Test
	void testIsGuardOKAfterTimeout() throws InterruptedException {
		evt.startTimeout();
		Thread.sleep(100L);
		assertTrue(evt.isGuardOK(null));
	}

	@Test
	void testIsGuardKOBeforeTimeout() {
		evt.startTimeout();
		assertFalse(evt.isGuardOK(null));
	}

	@Test
	void testacceptOKAfterTimeout() throws InterruptedException {
		evt.startTimeout();
		Thread.sleep(100L);
		assertTrue(evt.accept(null));
	}

	@Test
	void testacceptKOBeforeTimeout() {
		evt.startTimeout();
		assertFalse(evt.accept(null));
	}

	@Test
	void testStopTimeout() throws InterruptedException {
		evt.startTimeout();
		evt.stopTimeout();
		Thread.sleep(100L);
		assertFalse(evt.isGuardOK(null));
	}

	@Test
	void testGetAcceptEventsEmpty() {
		assertTrue(evt.getAcceptedEvents().isEmpty());
	}

	@Test
	void testExecuteWithoutTimeout() throws CancelFSMException {
		assertFalse(evt.execute(null).isPresent());
	}

	@Test
	void testExecuteWithTimeout() throws CancelFSMException, InterruptedException {
		evt.startTimeout();
		Thread.sleep(100L);
		assertEquals(tgt, evt.execute(null).get());
	}

	@Test
	void testExecuteCallFSMTimeout() throws InterruptedException {
		evt.startTimeout();
		Thread.sleep(100L);
		Mockito.verify(fsm, Mockito.times(1)).onTimeout();
	}

	@Test
	void testExecuteCallsStatesMethods() throws InterruptedException, CancelFSMException {
		evt.startTimeout();
		Thread.sleep(100L);
		evt.execute(null);
		Mockito.verify(src, Mockito.times(1)).exit();
		Mockito.verify(tgt, Mockito.times(1)).enter();
	}
}