package test.org.malai.action;

import java.lang.reflect.Field;
import static org.junit.Assert.*;

import org.malai.action.library.InstrumentAction;
import org.malai.instrument.Instrument;

import test.org.malai.HelperTest;

public abstract class TestInstrumentAction<T extends InstrumentAction> extends TestAbstractAction<InstrumentAction> {
	@Override
	public void testCanDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.canDo());
		action.setInstrument(new InstrumentMock());
		assertTrue(action.canDo());
	}

	@Override
	public void testFlush() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.setInstrument(new InstrumentMock());
		action.flush();
		final Field field = HelperTest.getField(InstrumentAction.class, "instrument");
		assertNull(field.get(action));
	}

	public void testGetSetInstrument() {
		final Instrument ins = new InstrumentMock();
		action.setInstrument(ins);
		assertEquals(ins, action.getInstrument());
	}
}
