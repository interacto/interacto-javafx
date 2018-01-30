package org.malai.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class TestAbstractAction<T extends ActionImpl> {
	protected T action;

	@BeforeEach
	public void setUp() {
		action = createAction();
	}

	protected abstract T createAction();

	@Test
	public abstract void testFlush() throws Exception;

	@Test
	public abstract void testDo() throws Exception;

	@Test
	public abstract void testCanDo() throws Exception;

	@Test
	public abstract void testIsRegisterable() throws Exception;

	@Test
	public abstract void testHadEffect() throws Exception;

	@Test
	public void testCanDoKOByDefault() {
		assertFalse(action.canDo());
	}
}
