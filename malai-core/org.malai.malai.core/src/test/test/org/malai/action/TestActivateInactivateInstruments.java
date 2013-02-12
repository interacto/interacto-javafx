package test.org.malai.action;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.malai.action.Action;
import org.malai.action.library.ActivateInactivateInstruments;
import org.malai.instrument.Instrument;
import org.malai.instrument.WidgetInstrument;
import org.malai.ui.UIComposer;
import org.malai.widget.MProgressBar;

import test.org.malai.HelperTest;

public class TestActivateInactivateInstruments extends TestAbstractAction<ActivateInactivateInstruments> {

	@Override
	protected ActivateInactivateInstruments createAction() {
		return new ActivateInactivateInstruments();
	}


	@Test
	public void testSetActivateFirst() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "activateFirst");
		action.setActivateFirst(false);
		assertFalse(act.getBoolean(action));
		action.setActivateFirst(true);
		assertTrue(act.getBoolean(action));
	}



	@Test
	public void testSetHideWidgets() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "hideWidgets");
		action.setHideWidgets(true);
		assertTrue(act.getBoolean(action));
		action.setHideWidgets(false);
		assertFalse(act.getBoolean(action));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddInstrumentToInactivateTwoTimes() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Instrument ins1 = new InstrumentMock();
		Instrument ins2 = new InstrumentMock();
		action.addInstrumentToInactivate(ins1);
		action.addInstrumentToInactivate(ins2);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate");
		assertNotNull(act.get(action));
		assertEquals(ins1, ((List<Instrument>)act.get(action)).get(0));
		assertEquals(ins2, ((List<Instrument>)act.get(action)).get(1));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddInstrumentToActivateTwoTimes() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Instrument ins1 = new InstrumentMock();
		Instrument ins2 = new InstrumentMock();
		action.addInstrumentToActivate(ins1);
		action.addInstrumentToActivate(ins2);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insActivate");
		assertNotNull(act.get(action));
		assertEquals(ins1, ((List<Instrument>)act.get(action)).get(0));
		assertEquals(ins2, ((List<Instrument>)act.get(action)).get(1));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddInstrumentToActivateNotNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Instrument ins = new InstrumentMock();
		action.addInstrumentToActivate(ins);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insActivate");
		assertNotNull(act.get(action));
		assertEquals(ins, ((List<Instrument>)act.get(action)).get(0));
		assertNull(HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate").get(action));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddInstrumentToInactivateNotNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Instrument ins = new InstrumentMock();
		action.addInstrumentToInactivate(ins);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate");
		assertNotNull(act.get(action));
		assertEquals(ins, ((List<Instrument>)act.get(action)).get(0));
		assertNull(HelperTest.getField(ActivateInactivateInstruments.class, "insActivate").get(action));
	}


	@Test
	public void testAddInstrumentToActivateNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.addInstrumentToActivate(null);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insActivate");
		assertNull(act.get(action));
	}

	@Test
	public void testAddInstrumentToInactivateNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.addInstrumentToInactivate(null);
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate");
		assertNull(act.get(action));
	}


	@Override
	public void testConstructor() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Action act = new ActivateInactivateInstruments();
		Field activeFirst = HelperTest.getField(ActivateInactivateInstruments.class, "activateFirst");
		Field hide = HelperTest.getField(ActivateInactivateInstruments.class, "hideWidgets");

		assertFalse(hide.getBoolean(act));
		assertTrue(activeFirst.getBoolean(act));
	}


	@Override
	public void testFlush() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.addInstrumentToActivate(new InstrumentMock());
		action.addInstrumentToInactivate(new InstrumentMock());
		action.flush();
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insActivate");
		Field inact = HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate");

		assertNull(act.get(action));
		assertNull(inact.get(action));
	}


	@Test
	public void testFlushNull() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		action.flush();
		Field act = HelperTest.getField(ActivateInactivateInstruments.class, "insActivate");
		Field inact = HelperTest.getField(ActivateInactivateInstruments.class, "insInactivate");

		assertNull(act.get(action));
		assertNull(inact.get(action));
	}


	boolean activated;
	boolean unactivated;

	@Override
	public void testDo() {
		activated = false;
		unactivated = true;
		Instrument ins1 = new InstrumentMock();
		ins1.setActivated(false);
		Instrument ins2 = new InstrumentMock();
		ins2.setActivated(true);

		action.addInstrumentToActivate(ins1);
		action.addInstrumentToInactivate(ins2);
		action.doIt();

		assertTrue(ins1.isActivated());
		assertFalse(ins2.isActivated());
	}


	boolean foo;

	@Test
	public void testDoActivateFirst() {
		action.setActivateFirst(true);
		foo = false;
		action.addInstrumentToActivate(new InstrumentMock() {
			@Override
			public void setActivated(boolean activated) {
				foo = true;
			}
		});
		action.addInstrumentToInactivate(new InstrumentMock() {
			@Override
			public void setActivated(boolean activated) {
				assertTrue(foo);
			}
		});
		action.doIt();
	}


	@Test
	public void testDoActivateLast() {
		action.setActivateFirst(false);
		foo = false;
		action.addInstrumentToActivate(new InstrumentMock() {
			@Override
			public void setActivated(boolean activated) {
				assertTrue(foo);
			}
		});
		action.addInstrumentToInactivate(new InstrumentMock() {
			@Override
			public void setActivated(boolean activated) {
				foo = true;
			}
		});
		action.doIt();
	}



	@Test
	public void testDoActivateWhenNothingToActivate() {
		action.addInstrumentToInactivate(new InstrumentMock());
		action.doIt();
	}



	@Test
	public void testDoActivateWhenNothingToInactivate() {
		action.addInstrumentToActivate(new InstrumentMock());
		action.doIt();
	}



	@Test
	public void testDoInactivateWhenHideWidgetsButNoWidgetInstrument() {
		action.addInstrumentToInactivate(new InstrumentMock());
		action.setHideWidgets(true);
		action.doIt();
	}



	@Test
	public void testDoActivateWhenHideWidgetsAndHasWidgetInstrument() {
		action.addInstrumentToInactivate(new WidgetInstrument(new UIComposer<Component>() {
			@Override
			public void compose(MProgressBar progressBar) {
				//
			}
		}) {
			@Override
			protected void initialiseLinks() {
				//
			}
			@Override
			protected void initialiseWidgets() {
				//
			}
			@Override
			public void setActivated(boolean activated, boolean hide) {
				assertTrue(hide);
				assertFalse(activated);
			}

		});
		action.setHideWidgets(true);
		action.doIt();
	}



	@Test
	public void testDoActivateWhenDoNotHideWidgetsAndHasWidgetInstrument() {
		action.addInstrumentToInactivate(new WidgetInstrument(new UIComposer<Component>() {
			@Override
			public void compose(MProgressBar progressBar) {
				//
			}
		}) {
			@Override
			protected void initialiseLinks() {
				//
			}
			@Override
			protected void initialiseWidgets() {
				//
			}
			@Override
			public void setActivated(boolean activated, boolean hide) {
				assertFalse(hide);
				assertFalse(activated);
			}

		});
		action.setHideWidgets(false);
		action.doIt();
	}


	@Override
	public void testCanDo() {
		action.addInstrumentToActivate(new InstrumentMock());
		action.addInstrumentToInactivate(new InstrumentMock());
		assertTrue(action.canDo());
	}


	@Test
	public void testCanDoWithOnlyActivate() {
		action.addInstrumentToActivate(new InstrumentMock());
		assertTrue(action.canDo());
	}


	@Test
	public void testCanDoWithOnlyInactivate() {
		action.addInstrumentToInactivate(new InstrumentMock());
		assertTrue(action.canDo());
	}


	@Test
	public void testCanDoWithBothNull() {
		assertFalse(action.canDo());
	}


	@Override
	public void testIsRegisterable() {
		assertFalse(action.isRegisterable());
	}

	@Override
	public void testHadEffect() {
		assertEquals(action.hadEffect(), action.isDone());
		action.done();
		assertEquals(action.hadEffect(), action.isDone());
	}
}
