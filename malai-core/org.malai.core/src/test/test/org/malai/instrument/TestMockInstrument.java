package test.org.malai.instrument;

import org.junit.Before;
import org.junit.Test;
import org.malai.instrument.InstrumentImpl;
import org.malai.instrument.InteractorImpl;
import test.org.malai.action.ActionImplMock;
import test.org.malai.instrument.TestMockInstrument.MockInstrument;
import test.org.malai.interaction.InteractionMock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestMockInstrument extends TestInstrument<MockInstrument> {

	@Before
	public void setUp() throws Exception {
		instrument = new MockInstrument();
	}


	@Test
	public void testHasLink() throws InstantiationException, IllegalAccessException {
		assertFalse(instrument.hasInteractors());
		instrument.getInteractors().add(new MockInteractor(instrument, false, ActionImplMock.class, InteractionMock.class));
		assertTrue(instrument.hasInteractors());
	}


	public static class MockInstrument extends InstrumentImpl<InteractorImpl<?, ?, MockInstrument>> {
		@Override
		protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {
			//
		}
	}
}
