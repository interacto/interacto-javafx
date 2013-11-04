package test.org.malai.instrument;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;

import test.org.malai.interaction.InteractionMock;

public class TestLink {
	protected MockLink link;
	protected InstrumentMock instrument;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException {
		instrument = new InstrumentMock();
		link = new MockLink(instrument, false, ActionMock.class, InteractionMock.class);
	}



	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInstrumentNull() throws Exception {
		link = new MockLink(null, false, ActionMock.class, InteractionMock.class);
	}
}


class MockLink extends Link<ActionMock, InteractionMock, InstrumentMock>{
	public MockLink(final InstrumentMock ins, final boolean exec, final Class<ActionMock> clazzAction, final Class<InteractionMock> clazzInteraction)
			throws InstantiationException, IllegalAccessException {
		super(ins, exec, clazzAction, clazzInteraction);
	}
	@Override
	public void initAction() {
		//
	}
	@Override
	public boolean isConditionRespected() {
		return false;
	}
}


class ActionMock extends Action {
	public ActionMock() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		//
	}

	@Override
	public boolean canDo() {
		return false;
	}
}

class InstrumentMock extends Instrument {
	@Override
	protected void initialiseLinks() {
		//
	}
}
