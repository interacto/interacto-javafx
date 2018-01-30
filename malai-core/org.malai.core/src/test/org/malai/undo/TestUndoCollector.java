package org.malai.undo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUndoCollector {
	UndoHandler handler;
	Undoable undoable;

	@BeforeEach
	public void setUp() {
		UndoCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.setSizeMax(10);
		UndoCollector.INSTANCE.clearHandlers();
		handler = Mockito.mock(UndoHandler.class);
		undoable = Mockito.mock(Undoable.class);
	}

	@Test
	void testRedowithUndoDonewithGlobalUndoable() {
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		Mockito.verify(handler, Mockito.times(1)).onUndoableRedo(undoable);
	}

	@Test
	void testRedowithUndoDonewithUndoable() {
		UndoCollector.INSTANCE.add(undoable, handler);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		Mockito.verify(handler, Mockito.times(1)).onUndoableRedo(undoable);
	}


	@Test
	void testRedowhenRedoEmpty() {
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.redo();
		Mockito.verify(handler, Mockito.never()).onUndoableRedo(ArgumentMatchers.any());
	}


	@Test
	void testUndoCallundo() {
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		Mockito.verify(undoable, Mockito.times(1)).undo();
	}


	@Test
	void testUndowhenUndoEmpty() {
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.undo();
		Mockito.verify(handler, Mockito.never()).onUndoableUndo(ArgumentMatchers.any());
	}


	@Test
	void testRedoCallredo() {
		UndoCollector.INSTANCE.add(undoable, handler);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		Mockito.verify(undoable, Mockito.times(1)).redo();
		Mockito.verify(handler, Mockito.times(1)).onUndoableRedo(undoable);
		assertEquals(undoable, UndoCollector.INSTANCE.getLastUndo().get());
	}


	@Test
	void testAddUndoableFollowedByUndowithUndoHandler() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(undoable, handler);
		UndoCollector.INSTANCE.undo();
		Mockito.verify(handler, Mockito.times(1)).onUndoableUndo(undoable);
		Mockito.verify(handler, Mockito.never()).onUndoableRedo(undoable);
		Mockito.verify(handler, Mockito.never()).onUndoableAdded(undoable);
		Mockito.verify(handler, Mockito.never()).onUndoableCleared();
	}


	@Test
	void testAddUndoableFollowedByUndowithDefaultndoHandler() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		Mockito.verify(handler, Mockito.times(1)).onUndoableUndo(undoable);
	}


	@Test
	void testAddCallonUndoableAdded() {
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.add(undoable, null);
		Mockito.verify(handler, Mockito.times(1)).onUndoableAdded(undoable);
	}


	@Test
	void testAddUndoablewith0SizeUndoable() {
		UndoCollector.INSTANCE.setSizeMax(0);
		UndoCollector.INSTANCE.add(undoable, null);
		assertTrue(UndoCollector.INSTANCE.getUndo().isEmpty());
		assertTrue(UndoCollector.INSTANCE.getRedo().isEmpty());
	}

	@Test
	void testAddUndoablewithNullUndoable() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(null, null);
		assertTrue(UndoCollector.INSTANCE.getUndo().isEmpty());
		assertTrue(UndoCollector.INSTANCE.getRedo().isEmpty());
	}

	@Test
	void testAddUndoablewithLimitedUndoSize() {
		final Undoable undoable2 = Mockito.mock(Undoable.class);
		UndoCollector.INSTANCE.setSizeMax(1);
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.add(undoable2, null);
		assertEquals(1, UndoCollector.INSTANCE.getUndo().size());
		assertEquals(undoable2, UndoCollector.INSTANCE.getUndo().getFirst());
	}


	@Test
	void testGetRedos() {
		assertNotNull(UndoCollector.INSTANCE.getRedo());
	}

	@Test
	void testGetUndos() {
		assertNotNull(UndoCollector.INSTANCE.getUndo());
	}


	@Test
	void testSizeMaxMutatorsUndoableRemoved() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(undoable, null);
		assertTrue(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}

	@Test
	void testSizeMaxMutatorsUndoableRemovedWhen0() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.setSizeMax(0);
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}

	@Test
	void testSizeMaxMutatorsSizeOK() {
		UndoCollector.INSTANCE.setSizeMax(21);
		assertEquals(21, UndoCollector.INSTANCE.getSizeMax());
	}

	@Test
	void testSizeMaxMutatorsSizeKO() {
		UndoCollector.INSTANCE.setSizeMax(5);
		UndoCollector.INSTANCE.setSizeMax(-1);
		assertEquals(5, UndoCollector.INSTANCE.getSizeMax());
	}

	@Test
	void testGetLastRedoNothingStart() {
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
	}

	@Test
	void testGetLastRedoNothingOnNewUndoable() {
		UndoCollector.INSTANCE.add(undoable, null);
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
	}

	@Test
	void testGetLastRedoOKOnRedo() {
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		assertEquals(undoable, UndoCollector.INSTANCE.getLastRedo().get());
	}

	@Test
	void testGetLastUndoNothingAtStart() {
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}

	@Test
	void testGetLastUndoOKOnAdd() {
		UndoCollector.INSTANCE.add(undoable, null);
		assertEquals(undoable, UndoCollector.INSTANCE.getLastUndo().get());
	}

	@Test
	void testGetLastUndoMessageNothingOnStart() {
		assertFalse(UndoCollector.INSTANCE.getLastUndoMessage().isPresent());
	}

	@Test
	void testGetLastUndoMessageOK() {
		Mockito.when(undoable.getUndoName()).thenReturn("undoredomsg");
		UndoCollector.INSTANCE.add(undoable, null);
		assertEquals("undoredomsg", UndoCollector.INSTANCE.getLastUndoMessage().get());
	}

	@Test
	void testGetLastRedoMessageNothingOnStart() {
		assertFalse(UndoCollector.INSTANCE.getLastRedoMessage().isPresent());
	}

	@Test
	void testGetLastRedoMessageOK() {
		Mockito.when(undoable.getUndoName()).thenReturn("undoredomsg");
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.undo();
		assertEquals("undoredomsg", UndoCollector.INSTANCE.getLastRedoMessage().get());
	}


	@Test
	void testClear() {
		UndoCollector.INSTANCE.add(undoable, null);
		UndoCollector.INSTANCE.add(Mockito.mock(Undoable.class), null);
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.clear();
		assertFalse(UndoCollector.INSTANCE.getLastRedo().isPresent());
		assertFalse(UndoCollector.INSTANCE.getLastUndo().isPresent());
	}


	@Test
	void testClearLaunchUndoableCleaned() {
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.clear();
		Mockito.verify(handler, Mockito.times(1)).onUndoableCleared();
	}

	@Test
	void testClearLaunchedHandlersCleaned() {
		UndoCollector.INSTANCE.add(undoable, handler);
		UndoCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.undo();
		UndoCollector.INSTANCE.redo();
		Mockito.verify(handler, Mockito.never()).onUndoableRedo(undoable);
		Mockito.verify(handler, Mockito.never()).onUndoableUndo(undoable);
	}

	@Test
	void testAddHandlerKO() {
		UndoCollector.INSTANCE.addHandler(null);
		assertTrue(UndoCollector.INSTANCE.getHandlers().isEmpty());
	}

	@Test
	void testAddHandlerOK() {
		final UndoHandler handler2 = Mockito.mock(UndoHandler.class);
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.addHandler(handler2);
		assertEquals(handler, UndoCollector.INSTANCE.getHandlers().get(0));
		assertEquals(handler2, UndoCollector.INSTANCE.getHandlers().get(1));
	}

	@Test
	void testremoveHandlerEmpty() {
		UndoCollector.INSTANCE.removeHandler(null);
		assertTrue(UndoCollector.INSTANCE.getHandlers().isEmpty());
	}

	@Test
	void testremoveHandlerOK() {
		final UndoHandler handler2 = Mockito.mock(UndoHandler.class);
		UndoCollector.INSTANCE.addHandler(handler);
		UndoCollector.INSTANCE.addHandler(handler2);
		UndoCollector.INSTANCE.removeHandler(handler);
		assertEquals(1, UndoCollector.INSTANCE.getHandlers().size());
		assertEquals(handler2, UndoCollector.INSTANCE.getHandlers().get(0));
	}
}
