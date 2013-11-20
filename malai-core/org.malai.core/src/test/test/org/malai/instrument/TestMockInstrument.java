package test.org.malai.instrument;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.malai.instrument.Instrument;

import test.org.malai.action.ActionMock;
import test.org.malai.instrument.TestMockInstrument.MockInstrument;
import test.org.malai.interaction.InteractionMock;


public class TestMockInstrument extends TestInstrument<MockInstrument> {

	@Before
	public void setUp() throws Exception {
		instrument = new MockInstrument();
	}


	@Test
	public void testHasLink() throws InstantiationException, IllegalAccessException {
		assertFalse(instrument.hasLinks());
		instrument.getLinks().add(new MockLink(instrument, false, ActionMock.class, InteractionMock.class));
		assertTrue(instrument.hasLinks());
	}


	public static class MockInstrument extends Instrument {
		@Override
		protected void initialiseLinks() {
			//
		}
	}
}
