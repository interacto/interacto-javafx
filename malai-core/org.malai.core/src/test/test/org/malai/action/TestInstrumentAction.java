package test.org.malai.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;
import org.malai.action.library.InstrumentAction;
import test.org.malai.HelperTest;

public abstract class TestInstrumentAction<T extends InstrumentAction> extends TestAbstractAction<InstrumentAction> {
	@Test
	@Override
	public void testCanDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.canDo());
		action.setInstrument(new InstrumentMock());
		assertTrue(action.canDo());
	}

	@Test
	@Override
	public void testFlush() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.setInstrument(new InstrumentMock());
		action.flush();
		final Field field = HelperTest.getField(InstrumentAction.class, "instrument");
		assertNull(field.get(action));
	}

	@Test public void testGetSetInstrument() {
		final InstrumentMock ins = new InstrumentMock();
		action.setInstrument(ins);
		assertEquals(ins, action.getInstrument());
	}
}
