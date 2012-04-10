package test.org.malai.action;

import java.io.File;
import java.lang.reflect.Field;

import org.malai.action.library.IOAction;
import org.malai.instrument.Instrument;
import org.malai.ui.ISOpenSaver;
import org.malai.ui.UI;
import org.malai.widget.MProgressBar;

import test.org.malai.HelperTest;

public abstract class TestIOAction<T extends IOAction> extends TestAbstractAction<IOAction> {
	public void testSetUI() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		UI ui = new UIMock();
		action.setUi(ui);
		Field field = HelperTest.getField(IOAction.class, "ui");
		assertNotNull(field.get(action));
		action.setUi(null);
		assertNull(field.get(action));
	}

	public void testSetOpenSaveManager() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ISOpenSaver os = new ISOpenSaverMock();
		action.setOpenSaveManager(os);
		Field field = HelperTest.getField(IOAction.class, "openSaveManager");
		assertNotNull(field.get(action));
		action.setOpenSaveManager(null);
		assertNull(field.get(action));
	}

	public void testSetFile() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		File file = new File("/foo");
		action.setFile(file);
		Field field = HelperTest.getField(IOAction.class, "file");
		assertNotNull(field.get(action));
		action.setFile(null);
		assertNull(field.get(action));
	}

	@Override
	public void testFlush() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		action.setFile(new File("/foo"));
		action.setOpenSaveManager(new ISOpenSaverMock());
		action.setUi(new UIMock());
		action.flush();
		Field field = HelperTest.getField(IOAction.class, "file");
		assertNull(field.get(action));
		field = HelperTest.getField(IOAction.class, "openSaveManager");
		assertNull(field.get(action));
		field = HelperTest.getField(IOAction.class, "ui");
		assertNull(field.get(action));
	}

	@Override
	public void testCanDo() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		assertFalse(action.canDo());
		action.setFile(new File("/foo"));
		assertFalse(action.canDo());
		action.setOpenSaveManager(new ISOpenSaverMock());
		assertFalse(action.canDo());
		action.setUi(new UIMock());
		assertTrue(action.canDo());
		action.setFile(null);
		assertFalse(action.canDo());
		action.setOpenSaveManager(null);
		assertFalse(action.canDo());
		action.setFile(new File("/foo"));
		assertFalse(action.canDo());
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
		Field field = HelperTest.getField(IOAction.class, "ok");
		assertFalse(action.hadEffect());
		field.set(action, true);
		assertFalse(action.hadEffect());
		field.set(action, false);
		action.done();
		assertFalse(action.hadEffect());
		field.set(action, true);
		assertTrue(action.hadEffect());
	}



	public class ISOpenSaverMock implements ISOpenSaver {
		@Override
		public boolean save(String path, UI ui, final MProgressBar progressBar, final Object statusBar) {
			return false;
		}

		@Override
		public boolean open(String path, UI ui, final MProgressBar progressBar, final Object statusBar) {
			return false;
		}
	}


	public class UIMock extends UI {
		private static final long serialVersionUID = 1L;

		@Override
		public Instrument[] getInstruments() {
			return new Instrument[] {new InstrumentMock()};
		}

		@Override
		public void initialisePresentations() {//
		}
	}
}
