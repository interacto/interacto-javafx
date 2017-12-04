package test.org.malai.undo;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.malai.undo.EmptyUndoHandler;
import org.malai.undo.UndoCollector;
import org.malai.undo.UndoHandler;
import org.malai.undo.Undoable;
import test.org.malai.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUndoCollector {
	boolean ok;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		ok = false;
		UndoCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.setSizeMax(10);
		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>) field.get(UndoCollector.INSTANCE);
		list.clear();
	}

	@Test
	public void testRedo_withUndoDone_withGlobalUndoable() {
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableRedo(final Undoable undoable) { ok = true; }
		};

		ok = false;
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		assertTrue(ok);
	}

	@Test
	public void testRedo_withUndoDone_withUndoable() {
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableRedo(final Undoable undoable) { ok = true; }
		};

		ok = false;
		UndoCollector.INSTANCE.add(new MockUndoable(), handler);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		assertTrue(ok);
	}


	@Test
	public void testRedo_whenRedoEmpty() {
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableRedo(final Undoable undoable) { ok = false; }
		};

		ok = true;
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.redo();
		assertTrue(ok);
	}


	@Test
	public void testUndoCall_undo() {
		final MockUndoable undoable = new MockUndoable() {
			@Override
			public void undo() { ok = true; }
		};

		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		assertTrue(ok);
	}


	@Test
	public void testUndo_whenUndoEmpty() {
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) { ok = false; }
		};

		ok = true;
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.undo();
		assertTrue(ok);
	}


	@Test
	public void testRedoCall_redo() {
		final MockUndoable undoable = new MockUndoable() {
			@Override
			public void redo() { ok = true; }
		};
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableUndo(final Undoable u) {
				assertEquals(undoable, u);
			}
		};

		UndoCollector.INSTANCE.add(undoable, handler);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		assertTrue(ok);
		assertEquals(undoable, UndoCollector.INSTANCE.getLastUndo().get());
		UndoCollector.INSTANCE.undo();
	}


	@Test
	public void testAddUndoableFollowedByUndo_withUndoHandler() {
		UndoCollector.INSTANCE.setSizeMax(5);
		ok = false;
		UndoCollector.INSTANCE.add(new MockUndoable(), new UndoHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) {
				ok = true;
			}

			@Override
			public void onUndoableRedo(final Undoable undoable) {
				throw new IllegalArgumentException();
			}

			@Override
			public void onUndoableCleared() {
				throw new IllegalArgumentException();
			}

			@Override
			public void onUndoableAdded(final Undoable undoable) {
				throw new IllegalArgumentException();
			}
		});

		UndoCollector.INSTANCE.undo();
		assertTrue(ok);
	}


	@Test
	public void testAddUndoableFollowedByUndo_withDefaultndoHandler() {
		UndoCollector.INSTANCE.setSizeMax(5);
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) { ok = true; }
		};
		ok = false;
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);

		UndoCollector.INSTANCE.undo();
		assertTrue(ok);
	}


	@Test
	public void testAddCall_onUndoableAdded() {
		UndoCollector.INSTANCE.addHandler(new EmptyUndoHandler() {
			@Override
			public void onUndoableAdded(final Undoable undoable) { ok = true; }
		});
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		assertTrue(ok);
	}


	@Test
	public void testAddUndoable_with0SizeUndoable() {
		UndoCollector.INSTANCE.setSizeMax(0);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		assertTrue(UndoCollector.INSTANCE.getUndo().isEmpty());
		assertTrue(UndoCollector.INSTANCE.getRedo().isEmpty());
	}

	@Test
	public void testAddUndoable_withNullUndoable() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(null, null);
		assertTrue(UndoCollector.INSTANCE.getUndo().isEmpty());
		assertTrue(UndoCollector.INSTANCE.getRedo().isEmpty());
	}

	@Test
	public void testAddUndoable_withLimitedUndoSize() {
		final Undoable undoable1 = new MockUndoable();
		final Undoable undoable2 = new MockUndoable();
		UndoCollector.INSTANCE.setSizeMax(1);
		UndoCollector.INSTANCE.add(undoable1, null);
		UndoCollector.INSTANCE.add(undoable2, null);
		assertEquals(1, UndoCollector.INSTANCE.getUndo().size());
		assertEquals(undoable2, UndoCollector.INSTANCE.getUndo().getFirst());
	}


	@Test
	public void testGetRedos() {
		assertNotNull(UndoCollector.INSTANCE.getRedo());
	}

	@Test
	public void testGetUndos() {
		assertNotNull(UndoCollector.INSTANCE.getUndo());
	}


	@Test
	public void testSizeMaxMutatorsUndoableRemoved() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		assertTrue(UndoCollector.INSTANCE.getLastUndo().isPresent());
		UndoCollector.INSTANCE.setSizeMax(0);
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}

	@Test
	public void testSizeMaxMutatorsSize() {
		UndoCollector.INSTANCE.setSizeMax(21);
		assertEquals(21, UndoCollector.INSTANCE.getSizeMax());
		UndoCollector.INSTANCE.setSizeMax(32);
		assertEquals(32, UndoCollector.INSTANCE.getSizeMax());
		UndoCollector.INSTANCE.setSizeMax(9);
		assertEquals(9, UndoCollector.INSTANCE.getSizeMax());
		UndoCollector.INSTANCE.setSizeMax(0);
		assertEquals(0, UndoCollector.INSTANCE.getSizeMax());
		UndoCollector.INSTANCE.setSizeMax(5);
		assertEquals(5, UndoCollector.INSTANCE.getSizeMax());
		UndoCollector.INSTANCE.setSizeMax(-1);
		assertEquals(5, UndoCollector.INSTANCE.getSizeMax());
	}


	@Test
	public void testGetLastRedo() {
		final Undoable undoable = new MockUndoable();
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
		UndoCollector.INSTANCE.add(undoable, null);
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
		UndoCollector.INSTANCE.undo();
		assertEquals(undoable, UndoCollector.INSTANCE.getLastRedo().get());
	}


	@Test
	public void testGetLastUndo() {
		final Undoable undoable = new MockUndoable();
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
		UndoCollector.INSTANCE.add(undoable, null);
		assertEquals(undoable, UndoCollector.INSTANCE.getLastUndo().get());
	}


	@Test
	public void testGetLastUndoMessage() {
		assertFalse(UndoCollector.INSTANCE.getLastUndoMessage().isPresent());
		UndoCollector.INSTANCE.add(new MockUndoable("undoredomsg"), null);
		assertEquals("undoredomsg", UndoCollector.INSTANCE.getLastUndoMessage().get());
	}


	@Test
	public void testGetLastRedoMessage() {
		assertFalse(UndoCollector.INSTANCE.getLastRedoMessage().isPresent());
		UndoCollector.INSTANCE.add(new MockUndoable("undoredomsg"), null);
		assertFalse(UndoCollector.INSTANCE.getLastRedoMessage().isPresent());
		UndoCollector.INSTANCE.undo();
		assertEquals("undoredomsg", UndoCollector.INSTANCE.getLastRedoMessage().get());
	}


	@Test
	public void testClear() throws SecurityException, IllegalArgumentException {
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		UndoCollector.INSTANCE.add(new MockUndoable(), null);
		UndoCollector.INSTANCE.undo();

		UndoCollector.INSTANCE.clear();
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}


	@Test
	public void testClearLaunch_UndoableCleaned() {
		final UndoHandler handler = new EmptyUndoHandler() {
			@Override
			public void onUndoableCleared() { ok = true; }
		};
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.clear();
		assertTrue(ok);
	}


	@Test
	public void testClearLaunchedHandlersCleaned() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final UndoHandler handler1 = new EmptyUndoHandler() {
			@Override
			public void onUndoableCleared() { ok = true; }
		};
		final UndoHandler handler2 = new EmptyUndoHandler() {
			@Override
			public void onUndoableCleared() { ok = true; }
		};

		UndoCollector.INSTANCE.add(new MockUndoable(), handler1);
		UndoCollector.INSTANCE.add(new MockUndoable(), handler2);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.clear();
		assertTrue(((Deque<?>) HelperTest.getField(UndoCollector.class, "undoHandlers").get(UndoCollector.INSTANCE)).isEmpty());
		assertTrue(((Deque<?>) HelperTest.getField(UndoCollector.class, "redoHandlers").get(UndoCollector.INSTANCE)).isEmpty());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testAddHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final UndoHandler handler1 = new EmptyUndoHandler();
		final UndoHandler handler2 = new EmptyUndoHandler();

		UndoCollector.INSTANCE.addHandler(null);
		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>) field.get(UndoCollector.INSTANCE);
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
		final UndoHandler handler1 = new EmptyUndoHandler();
		final UndoHandler handler2 = new EmptyUndoHandler();

		UndoCollector.INSTANCE.removeHandler(null);
		final Field field = HelperTest.getField(UndoCollector.class, "handlers");
		final List<UndoHandler> list = (List<UndoHandler>) field.get(UndoCollector.INSTANCE);
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
		protected String undoMsg;

		public MockUndoable() {
			this("");
		}

		public MockUndoable(final String undoMsg) {
			super();
			this.undoMsg = undoMsg;
		}

		@Override
		public void undo() {//
		}

		@Override
		public void redo() {//
		}

		@Override
		public String getUndoName() {
			return undoMsg;
		}
	}
}
