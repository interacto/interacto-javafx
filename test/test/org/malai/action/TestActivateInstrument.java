package test.org.malai.action;

import org.malai.action.Action;
import org.malai.action.library.ActivateInstrument;
import org.malai.instrument.Instrument;

public class TestActivateInstrument extends TestInstrumentAction<ActivateInstrument> {

	@Override
	protected ActivateInstrument createAction() {
		return new ActivateInstrument();
	}

	@SuppressWarnings("unused")
	@Override
	public void testConstructor() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Action act = new ActivateInstrument();
	}


	@Override
	public void testDo() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Instrument ins = new InstrumentMock();
		ins.setActivated(false);
		action.setInstrument(ins);
		action.doIt();
		assertTrue(ins.isActivated());
	}

	@Override
	public void testIsRegisterable() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		assertFalse(action.isRegisterable());
	}

	@Override
	public void testHadEffect() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		assertFalse(action.hadEffect());
		action.done();
		assertTrue(action.hadEffect());
	}
}
