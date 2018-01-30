package org.malai.instrument;

import org.junit.jupiter.api.Test;
import org.malai.binding.MustBeUndoableActionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMustBeUndoableActionException {
	@Test
	public void testMustBeUndoableActionExceptionOnNull() {
		new MustBeUndoableActionException(null);
	}

	@Test
	public void testMustBeUndoableActionExceptionOnClass() {
		new MustBeUndoableActionException(Class.class);
	}

	@Test
	public void testToStringNotNullOnNull() {
		MustBeUndoableActionException ex = new MustBeUndoableActionException(null);
		assertNotNull(ex.toString());
	}

	@Test
	public void testToStringNotNullOnClass() {
		MustBeUndoableActionException ex = new MustBeUndoableActionException(Class.class);
		assertNotNull(ex.toString());
	}
}
