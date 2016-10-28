package test.org.malai.action;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.ActionImpl;
import org.malai.instrument.Interactor;
import org.malai.instrument.InstrumentImpl;

public abstract class TestAbstractAction<T extends ActionImpl> {
	protected T action;

	@Before
	public void setUp() {
		action = createAction();
	}

	protected abstract T createAction();

	@Test
	public abstract void testConstructor() throws Exception;

	@Test
	public abstract void testFlush() throws Exception;

	@Test
	public abstract void testDo() throws Exception;

	@Test
	public abstract void testCanDo() throws Exception;

	@Test
	public abstract void testIsRegisterable() throws Exception;

	@Test
	public abstract void testHadEffect() throws Exception;


	public class InstrumentMock extends InstrumentImpl<Interactor> {
		public InstrumentMock() {
			super();
		}
		@Override
		protected void initialiseInteractors() {
			//
		}
	}
}
