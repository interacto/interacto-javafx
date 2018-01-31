package org.malai.fsm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTransition {
	Transition<StubEvent> tr;
	StdState<StubEvent> state1;
	StdState<StubEvent> state2;

	@BeforeEach
	void setUp() {
		FSM<StubEvent> fsm = new FSM<>();
		state1 = new StdState<>(fsm, "s1");
		state2 = new StdState<>(fsm, "s2");
	}

	@Nested
	class TestBadConstructorCall {
		@Test
		void testNullSrc() {
			assertThrows(IllegalArgumentException.class, () -> new StubTransitionOK(null, state2));
		}

		@Test
		void testNullTgt() {
			assertThrows(IllegalArgumentException.class, () -> new StubTransitionOK(state1, null));
		}
	}

	@Nested
	class TestConstructorOK {
		@BeforeEach
		void setUp() {
			tr = new StubTransitionOK(state1, state2);
		}

		@Test
		void testGoodSrc() {
			assertEquals(state1, tr.src);
		}

		@Test
		void testGoodTgt() {
			assertEquals(state2, tr.tgt);
		}

		@Test
		void testSrcStateTransitionAdded() {
			assertEquals(1, state1.transitions.size());
			assertEquals(tr, state1.transitions.get(0));
		}
	}
}
