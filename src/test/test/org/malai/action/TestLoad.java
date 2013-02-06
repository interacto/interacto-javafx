package test.org.malai.action;

import java.io.File;
import java.lang.reflect.Field;

import org.malai.action.library.IOAction;
import org.malai.action.library.Load;
import org.malai.ui.UI;
import org.malai.widget.MProgressBar;

import test.org.malai.HelperTest;

public class TestLoad extends TestIOAction<Load<UI, Object>> {

	@Override
	protected Load<UI, Object> createAction() {
		return new Load<UI, Object>();
	}

	@SuppressWarnings("unused")
	@Override
	public void testConstructor() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		new Load<UI, Object>();
	}

	boolean visit1Ok;
	boolean visit2Ok;

	@SuppressWarnings("serial")
	@Override
	public void testDo() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		if(HelperTest.isX11Set()) {
			final Field field = HelperTest.getField(IOAction.class, "ok");
			visit1Ok = false;
			visit2Ok = false;
			action.setFile(new File("/foo"));
			action.setOpenSaveManager(new ISOpenSaverMock() {
				@Override
				public boolean open(final String path, final UI ui, final MProgressBar progressBar, final Object statusBar) {
					visit1Ok = true;
					assertEquals("/foo", path);
					return true;
				}
			});
			action.setUi(new UIMock() {
				@Override
				public void setModified(final boolean modified) {
					visit2Ok = true;
				}
			});

			action.doIt();
			assertTrue(visit1Ok);
			assertTrue(visit2Ok);
			assertTrue(field.getBoolean(action));
		}
	}


	@SuppressWarnings("serial")
	public void testDoNotOpened() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		if(HelperTest.isX11Set()) {
			final Field field = HelperTest.getField(IOAction.class, "ok");
			visit1Ok = false;
			visit2Ok = false;
			action.setFile(new File("/foo"));
			action.setOpenSaveManager(new ISOpenSaverMock() {
				@Override
				public boolean open(final String path, final UI ui, final MProgressBar progressBar, final Object statusBar) {
					visit1Ok = true;
					assertEquals("/foo", path);
					return false;
				}
			});
			action.setUi(new UIMock() {
				@Override
				public void setModified(final boolean modified) {
					visit2Ok = true;
				}
			});

			action.doIt();
			assertTrue(visit1Ok);
			assertTrue(visit2Ok);
			assertFalse(field.getBoolean(action));
		}
	}
}
