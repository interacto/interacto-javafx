package org.malai.instrument;

import org.junit.jupiter.api.Test;
import org.malai.binding.MustBeUndoableCmdException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMustBeUndoableCmdException {
	@Test
	public void testMustBeUndoableActionExceptionOnNull() {
		new MustBeUndoableCmdException(null);
	}

	@Test
	public void testMustBeUndoableActionExceptionOnClass() {
		new MustBeUndoableCmdException(Class.class);
	}

	@Test
	public void testToStringNotNullOnNull() {
		MustBeUndoableCmdException ex = new MustBeUndoableCmdException(null);
		assertNotNull(ex.toString());
	}

	@Test
	public void testToStringNotNullOnClass() {
		MustBeUndoableCmdException ex = new MustBeUndoableCmdException(Class.class);
		assertNotNull(ex.toString());
	}
}
