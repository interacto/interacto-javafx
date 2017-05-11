package test.org.malai.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.action.Action.ActionStatus;
import org.malai.action.ActionHandler;
import org.malai.action.ActionImpl;
import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;
import test.org.malai.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestActionRegistry {
	boolean visited;

	@Before
	public void setUp() {
		ActionsRegistry.INSTANCE.removeAllHandlers();
		ActionsRegistry.INSTANCE.getActions().clear();
		ActionsRegistry.INSTANCE.setSizeMax(30);
		UndoCollector.INSTANCE.clear();
	}


	@SuppressWarnings("unchecked")
	public List<ActionHandler> getListHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
		IllegalAccessException {
		final Field field = HelperTest.getField(ActionsRegistry.INSTANCE.getClass(), "handlers");
		return (List<ActionHandler>) field.get(ActionsRegistry.INSTANCE);
	}


	@Test
	public void testGetSetSizeMax() {
		ActionsRegistry.INSTANCE.setSizeMax(55);
		assertEquals(55, ActionsRegistry.INSTANCE.getSizeMax());
		ActionsRegistry.INSTANCE.setSizeMax(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, ActionsRegistry.INSTANCE.getSizeMax());
		ActionsRegistry.INSTANCE.setSizeMax(0);
		assertEquals(0, ActionsRegistry.INSTANCE.getSizeMax());
		ActionsRegistry.INSTANCE.setSizeMax(-1);
		assertEquals(0, ActionsRegistry.INSTANCE.getSizeMax());
		ActionsRegistry.INSTANCE.setSizeMax(Integer.MIN_VALUE);
		assertEquals(0, ActionsRegistry.INSTANCE.getSizeMax());
	}


	@Test
	public void testSetSizeMaxRemovesAction() {
		final List<Action> handlers = ActionsRegistry.INSTANCE.getActions();
		final Action action1 = new ActionImplStub();
		final Action action2 = new ActionImplStub();
		ActionsRegistry.INSTANCE.setSizeMax(10);
		ActionsRegistry.INSTANCE.addAction(action1, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.addAction(action2, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.setSizeMax(1);
		assertEquals(ActionStatus.FLUSHED, action1.getStatus());
		assertEquals(ActionStatus.CREATED, action2.getStatus());
		assertEquals(1, handlers.size());
		assertEquals(action2, handlers.get(0));

		ActionsRegistry.INSTANCE.setSizeMax(0);
		assertEquals(ActionStatus.FLUSHED, action2.getStatus());
		assertEquals(0, handlers.size());
	}


	@Test
	public void testAbortActionNull() {
		ActionsRegistry.INSTANCE.abortAction(null);
	}


	@Test
	public void testAbortActionFlush() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.abortAction(action);
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testAbortActionNotify() {
		final Action action = new ActionImplStub();
		visited = false;

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionAborted(final Action action) {
				visited = true;
			}
		});

		ActionsRegistry.INSTANCE.abortAction(action);
		assertTrue(visited);
	}


	@Test
	public void testAbortActionRemoved() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.abortAction(action);
		final List<ActionHandler> handlers = getListHandler();
		assertTrue(handlers.isEmpty());
	}


	@Test
	public void testRemoveAllHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub());
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub());
		final List<ActionHandler> handlers = getListHandler();
		ActionsRegistry.INSTANCE.removeAllHandlers();
		assertTrue(handlers.isEmpty());
	}


	@Test
	public void testRemoveHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final ActionHandler handler = new ActionHandlerStub();
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.removeHandler(null);
		final List<ActionHandler> handlers = getListHandler();
		assertEquals(1, handlers.size());
		ActionsRegistry.INSTANCE.removeHandler(handler);
		assertTrue(handlers.isEmpty());
	}


	@Test
	public void testAddHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ActionsRegistry.INSTANCE.addHandler(null);
		final List<ActionHandler> handlers = getListHandler();
		assertTrue(handlers.isEmpty());
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub());
		assertEquals(1, handlers.size());
	}


	@Test
	public void testRemoveActionNull() {
		ActionsRegistry.INSTANCE.addAction(new ActionImplStub(), new ActionHandlerStub());
		ActionsRegistry.INSTANCE.removeAction(null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}


	@Test
	public void testRemoveActionNotNull() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.removeAction(action);
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testOnActionExecuted() {
		visited = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub());
		ActionsRegistry.INSTANCE.onActionExecuted(null);
		ActionsRegistry.INSTANCE.removeAllHandlers();
		final Action a = new ActionImplStub();

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionExecuted(final Action action) {
				visited = true;
				assertEquals(a, action);
			}
		});

		ActionsRegistry.INSTANCE.onActionExecuted(a);

		assertTrue(visited);
	}


	@Test
	public void testOnActionDone() {
		visited = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub());
		ActionsRegistry.INSTANCE.onActionDone(null);
		ActionsRegistry.INSTANCE.removeAllHandlers();
		final Action a = new ActionImplStub();

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionDone(final Action action) {
				visited = true;
				assertEquals(a, action);
			}
		});

		ActionsRegistry.INSTANCE.onActionDone(a);

		assertTrue(visited);
	}


	@Test
	public void testGetActionsNotNull() {
		assertNotNull(ActionsRegistry.INSTANCE.getActions());
	}


	@Test
	public void testCancelsActionNull() {
		ActionsRegistry.INSTANCE.unregisterActions(null);
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
	}


	@Test
	public void testCancelsActionNotNullDoNotCancel() {
		final Action act = new ActionImplStub();
		ActionsRegistry.INSTANCE.addAction(act, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.unregisterActions(new ActionImplStub2());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertNotSame(Action.ActionStatus.FLUSHED, act.getStatus());
	}


	@Test
	public void testCancelsActionNotNullDoesCancel() {
		visited = false;
		final Action act = new ActionImplStub2();

		ActionsRegistry.INSTANCE.addAction(act, new ActionHandlerStub());
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionCancelled(final Action action) {
				visited = true;
				assertEquals(act, action);
			}
		});
		ActionsRegistry.INSTANCE.unregisterActions(new ActionImplStub());
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
		assertEquals(Action.ActionStatus.FLUSHED, act.getStatus());
		assertTrue(visited);
	}


	@Test
	public void testAddActionCannotAddBecauseNullOrAlreadyAdded() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.getActions().add(action);
		ActionsRegistry.INSTANCE.addAction(null, new ActionHandlerStub());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(new ActionImplStub2(), null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(null, null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerStub());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}


	@Test
	public void testAddActionCancelsAction() {
		visited = false;
		final Action action = new ActionImplStub2();
		ActionsRegistry.INSTANCE.getActions().add(action);

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionCancelled(final Action action) {
				visited = true;
				assertEquals(action, action);
			}

			@Override
			public void onActionAdded(final Action action) {//
			}
		});
		ActionsRegistry.INSTANCE.addAction(new ActionImplStub(), new ActionHandlerStub());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertTrue(visited);
	}


	@Test
	public void testAddActionNotifyHandlers() {
		visited = false;

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerStub() {
			@Override
			public void onActionAdded(final Action action) {
				visited = true;
			}
		});
		ActionsRegistry.INSTANCE.addAction(new ActionImplStub(), new ActionHandlerStub());
		assertTrue(visited);
	}


	@Test
	public void testAddActionRemovesActionWhenMaxCapacity() {
		final Action action = new ActionImplStub();
		final Action action2 = new ActionImplStub();
		ActionsRegistry.INSTANCE.setSizeMax(1);
		ActionsRegistry.INSTANCE.getActions().add(action2);
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerStub());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertEquals(action, ActionsRegistry.INSTANCE.getActions().get(0));
		assertEquals(Action.ActionStatus.FLUSHED, action2.getStatus());
	}


	@Test
	public void testAddActionMaxCapacityIs0() {
		ActionsRegistry.INSTANCE.setSizeMax(0);
		ActionsRegistry.INSTANCE.addAction(new ActionImplStub(), new ActionHandlerStub());
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
	}


	@Test
	public void testAddActionAddsUndoableCollector() {
		final Action action = new ActionImplUndoableStub();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerStub());
		assertEquals(action, UndoCollector.INSTANCE.getLastUndo());
	}


	@Test
	public void testRegistryConcurrentAccess() {
		final List<Action> addedActions = new ArrayList<>();
		final ActionHandlerStub stub = new ActionHandlerStub();

		IntStream.range(0, 100000).parallel().forEach(i -> {
			if(i % 2 == 0) {
				final Action action = new ActionImplStub();
				synchronized(addedActions) {
					addedActions.add(action);
				}
				ActionsRegistry.INSTANCE.addAction(action, stub);
			}else {
				Action action = null;
				synchronized(addedActions) {
					if(!addedActions.isEmpty()) {
						action = addedActions.remove(new Random().nextInt(addedActions.size()));
					}
				}
				ActionsRegistry.INSTANCE.removeAction(action);
			}
		});
	}


	private class ActionHandlerStub implements ActionHandler {
		public ActionHandlerStub() {
			super();
		}

		@Override
		public void onUndoableUndo(final Undoable undoable) {fail();}

		@Override
		public void onUndoableRedo(final Undoable undoable) {fail();}

		@Override
		public void onUndoableAdded(final Undoable undoable) {fail();}

		@Override
		public void onActionExecuted(final Action action) {fail();}

		@Override
		public void onActionDone(final Action action) {fail();}

		@Override
		public void onActionCancelled(final Action action) {fail();}

		@Override
		public void onActionAdded(final Action action) {fail();}

		@Override
		public void onActionAborted(final Action action) {fail();}

		@Override
		public void onUndoableCleared() {
		}
	}


	private class ActionImplUndoableStub extends ActionImpl implements Undoable {
		public ActionImplUndoableStub() {
			super();
		}

		@Override
		protected void doActionBody() {
			//
		}

		@Override
		public boolean canDo() {
			return false;
		}

		@Override
		public void undo() {
			//
		}

		@Override
		public void redo() {
			//
		}

		@Override
		public String getUndoName() {
			return null;
		}
	}


	private class ActionImplStub extends ActionImpl {
		public ActionImplStub() {
			super();
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


	private class ActionImplStub2 extends ActionImpl {
		public ActionImplStub2() {
			super();
		}

		@Override
		public boolean unregisteredBy(final Action action) {
			return action instanceof ActionImplStub;
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
}
