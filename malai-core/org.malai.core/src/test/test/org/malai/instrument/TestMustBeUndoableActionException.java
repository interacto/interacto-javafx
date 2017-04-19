package test.org.malai.instrument;

import org.junit.Test;
import org.malai.instrument.MustBeUndoableActionException;

import static org.junit.Assert.assertNotNull;

public class TestMustBeUndoableActionException {

	@Test
	public void testMustBeUndoableActionException() {
		@SuppressWarnings("unused") MustBeUndoableActionException ex = new MustBeUndoableActionException(null);
		ex = new MustBeUndoableActionException(Class.class);
	}

	@Test
	public void testToString() {
		MustBeUndoableActionException ex = new MustBeUndoableActionException(null);
		assertNotNull(ex.toString());
		ex = new MustBeUndoableActionException(Class.class);
		assertNotNull(ex.toString());
	}

}
