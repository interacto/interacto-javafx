package org.malai.action;

import org.junit.jupiter.api.Test;
import org.malai.action.library.InstrumentAction;
import org.malai.instrument.Instrument;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TestInstrumentAction<T extends InstrumentAction> extends TestAbstractAction<InstrumentAction> {
	@Test
	@Override
	public void testCanDo() throws SecurityException, IllegalArgumentException {
		action.setInstrument(Mockito.mock(Instrument.class));
		assertTrue(action.canDo());
	}

	@Override
	@Test
	public void testFlush() {
		action.setInstrument(Mockito.mock(Instrument.class));
		action.flush();
		assertNull(action.getInstrument());
	}

	@Test
	public void testGetSetInstrument() {
		final Instrument<?> ins = Mockito.mock(Instrument.class);
		action.setInstrument(ins);
		assertEquals(ins, action.getInstrument());
	}
}
