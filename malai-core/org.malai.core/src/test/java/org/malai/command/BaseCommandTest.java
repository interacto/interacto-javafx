package org.malai.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class BaseCommandTest<T extends CommandImpl> {
	protected T cmd;

	@BeforeEach
	public void setUp() {
		cmd = createCommand();
	}

	protected abstract T createCommand();

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
		assertFalse(cmd.canDo());
	}
}
