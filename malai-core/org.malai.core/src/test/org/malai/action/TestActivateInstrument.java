package org.malai.action;

import org.junit.jupiter.api.Test;
import org.malai.action.library.ActivateInstrument;
import org.malai.instrument.Instrument;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestActivateInstrument extends TestInstrumentAction<ActivateInstrument> {
	@Override
	protected ActivateInstrument createAction() {
		return new ActivateInstrument();
	}

	@Override
	@Test
	public void testDo() {
		final Instrument<?> ins = Mockito.mock(Instrument.class);
		action.setInstrument(ins);
		action.doIt();
		Mockito.verify(ins, Mockito.times(1)).setActivated(true);
	}

	@Override
	@Test
	public void testIsRegisterable() {
		assertEquals(Action.RegistrationPolicy.NONE, action.getRegistrationPolicy());
	}

	@Override
	@Test
	public void testHadEffect() {
		action.done();
		assertTrue(action.hadEffect());
	}

	@Test
	public void testHadNoEffectBeforeDone() {
		assertFalse(action.hadEffect());
	}
}
