package org.malai.fsm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestStateImpl {
	StateImpl<StubEvent> state;
	FSM<StubEvent> fsm;

	@BeforeEach
	void setUp() {
		fsm = new FSM<>();
		state = new StdState<>(fsm, "s1");
	}

	@Test
	void testFSM() {
		assertEquals(fsm, state.fsm);
	}

	@Test
	void testName() {
		assertEquals("s1", state.getName());
	}

	@Test
	void testToStringNotNull() {
		assertNotNull(state.toString());
	}
}
