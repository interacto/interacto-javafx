package test.org.malai.undo;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.malai.undo.UndoCollector;
import org.malai.undo.UndoHandler;
import org.malai.undo.Undoable;

import test.org.malai.HelperTest;

public class TestUndoCollector extends TestCase {
	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		UndoCollector.INSTANCE.clear();

		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>)field.get(UndoCollector.INSTANCE);
		list.clear();
	}


	@Test
	public void testClear() throws SecurityException, IllegalArgumentException {
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		UndoCollector.INSTANCE.undo();

		UndoCollector.INSTANCE.clear();
		assertNull(UndoCollector.INSTANCE.getLastRedo());
		assertNull(UndoCollector.INSTANCE.getLastUndo());
	}



	@SuppressWarnings("unchecked")
	@Test
	public void testAddHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final UndoHandler handler1 = new MockUndoHandler();
		final UndoHandler handler2 = new MockUndoHandler();

		UndoCollector.INSTANCE.addHandler(null);
		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>)field.get(UndoCollector.INSTANCE);
		assertTrue(list.isEmpty());

		UndoCollector.INSTANCE.addHandler(handler1);
		assertEquals(handler1, list.get(0));

		UndoCollector.INSTANCE.addHandler(handler2);
		assertEquals(handler1, list.get(0));
		assertEquals(handler2, list.get(1));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testremoveHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final UndoHandler handler1 = new MockUndoHandler();
		final UndoHandler handler2 = new MockUndoHandler();

		UndoCollector.INSTANCE.removeHandler(null);
		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>)field.get(UndoCollector.INSTANCE);
		assertTrue(list.isEmpty());

		UndoCollector.INSTANCE.addHandler(handler1);
		assertEquals(handler1, list.get(0));

		UndoCollector.INSTANCE.removeHandler(null);
		assertEquals(handler1, list.get(0));

		UndoCollector.INSTANCE.removeHandler(handler1);
		assertTrue(list.isEmpty());

		UndoCollector.INSTANCE.addHandler(handler1);
		UndoCollector.INSTANCE.addHandler(handler2);

		UndoCollector.INSTANCE.removeHandler(null);
		assertEquals(handler1, list.get(0));
		assertEquals(handler2, list.get(1));

		UndoCollector.INSTANCE.removeHandler(handler1);
		assertEquals(handler2, list.get(0));

		UndoCollector.INSTANCE.removeHandler(handler2);
		assertTrue(list.isEmpty());

		UndoCollector.INSTANCE.removeHandler(handler2);
		assertTrue(list.isEmpty());
	}



	public class MockUndoable implements Undoable {
		@Override
		public void undo() {//
		}

		@Override
		public void redo() {//
		}

		@Override
		public String getUndoName() {
			return null;
		}
	}


	public class MockUndoHandler implements UndoHandler {
		public MockUndoHandler() {
		}

		@Override
		public void onUndoableAdded(final Undoable undoable) {//
		}

		@Override
		public void onUndoableUndo(final Undoable undoable) {//
		}

		@Override
		public void onUndoableRedo(final Undoable undoable) {//
		}
	}
}
