package test.org.malai.action;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.action.Action.ActionStatus;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;

import test.org.malai.HelperTest;

public class TestActionRegistry extends TestCase {
	boolean visited;

	@Override
	@Before
	public void setUp() {
		ActionsRegistry.INSTANCE.removeAllHandlers();
		ActionsRegistry.INSTANCE.getActions().clear();
		ActionsRegistry.INSTANCE.setSizeMax(30);
		UndoCollector.INSTANCE.clear();
	}


	@SuppressWarnings("unchecked")
	public List<ActionHandler> getListHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
		final Action action1 = new ActionMock();
		final Action action2 = new ActionMock();
		ActionsRegistry.INSTANCE.setSizeMax(10);
		ActionsRegistry.INSTANCE.addAction(action1, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.addAction(action2, new ActionHandlerMock());
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
		final Action action = new ActionMock();
		ActionsRegistry.INSTANCE.abortAction(action);
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testAbortActionNotify() {
		final Action action = new ActionMock();
		visited = false;

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionAborted(final Action act) {
				visited = true;
			}
		});

		ActionsRegistry.INSTANCE.abortAction(action);
		assertTrue(visited);
	}


	@Test
	public void testAbortActionRemoved() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final Action action = new ActionMock();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.abortAction(action);
		final List<ActionHandler> handlers = getListHandler();
		assertTrue(handlers.isEmpty());
	}


	@Test
	public void testGetActionThatExists() {
		final Action action1 = new ActionMock();
		final Action action2 = new ActionMock();
		ActionsRegistry.INSTANCE.addAction(action1, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.addAction(action2, new ActionHandlerMock());
		assertEquals(action1, ActionsRegistry.INSTANCE.getAction(ActionMock.class));
	}


	@Test
	public void testGetActionThatDoesNotExist() {
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		assertNull(ActionsRegistry.INSTANCE.getAction(ActionMock2.class));
	}


	@Test
	public void testGetActionNull() {
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		assertNull(ActionsRegistry.INSTANCE.getAction(null));
	}


	@Test
	public void testRemoveAllHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock());
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock());
		final List<ActionHandler> handlers = getListHandler();
		ActionsRegistry.INSTANCE.removeAllHandlers();
		assertTrue(handlers.isEmpty());
	}


	@Test
	public void testRemoveHandler() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final ActionHandler handler = new ActionHandlerMock();
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
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock());
		assertEquals(1, handlers.size());
	}


	@Test
	public void testRemoveActionNull() {
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		ActionsRegistry.INSTANCE.removeAction(null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}


	@Test
	public void testRemoveActionNotNull() {
		final Action action = new ActionMock();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.removeAction(action);
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testOnActionExecuted() {
		visited = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock());
		ActionsRegistry.INSTANCE.onActionExecuted(null);
		ActionsRegistry.INSTANCE.removeAllHandlers();
		final Action a = new ActionMock();

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionExecuted(final Action act) {
				visited = true;
				assertEquals(a, act);
			}
		});

		ActionsRegistry.INSTANCE.onActionExecuted(a);

		assertTrue(visited);
	}



	@Test
	public void testOnActionDone() {
		visited = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock());
		ActionsRegistry.INSTANCE.onActionDone(null);
		ActionsRegistry.INSTANCE.removeAllHandlers();
		final Action a = new ActionMock();

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionDone(final Action act) {
				visited = true;
				assertEquals(a, act);
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
		ActionsRegistry.INSTANCE.cancelActions(null);
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
	}



	@Test
	public void testCancelsActionNotNullDoNotCancel() {
		final Action act = new ActionMock();
		ActionsRegistry.INSTANCE.addAction(act, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.cancelActions(new ActionMock2());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertNotSame(Action.ActionStatus.FLUSHED, act.getStatus());
	}



	@Test
	public void testCancelsActionNotNullDoesCancel() {
		visited = false;
		final Action act = new ActionMock2();

		ActionsRegistry.INSTANCE.addAction(act, new ActionHandlerMock());
		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionCancelled(final Action a) {
				visited = true;
				assertEquals(act, a);
			}
		});
		ActionsRegistry.INSTANCE.cancelActions(new ActionMock());
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
		assertEquals(Action.ActionStatus.FLUSHED, act.getStatus());
		assertTrue(visited);
	}



	@Test
	public void testAddActionCannotAddBecauseNullOrAlreadyAdded() {
		final Action action = new ActionMock();
		ActionsRegistry.INSTANCE.getActions().add(action);
		ActionsRegistry.INSTANCE.addAction(null, new ActionHandlerMock());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(new ActionMock2(), null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(null, null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());

		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerMock());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}




	@Test
	public void testAddActionCancelsAction() {
		visited = false;
		final Action action = new ActionMock2();
		ActionsRegistry.INSTANCE.getActions().add(action);

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionCancelled(final Action a) {
				visited = true;
				assertEquals(action, a);
			}
			@Override
			public void onActionAdded(final Action a) {//
			}
		});
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertTrue(visited);
	}


	@Test
	public void testAddActionNotifyHandlers() {
		visited = false;

		ActionsRegistry.INSTANCE.addHandler(new ActionHandlerMock() {
			@Override
			public void onActionAdded(final Action a) {
				visited = true;
			}
		});
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		assertTrue(visited);
	}


	@Test
	public void testAddActionRemovesActionWhenMaxCapacity() {
		final Action action = new ActionMock();
		final Action action2 = new ActionMock();
		ActionsRegistry.INSTANCE.setSizeMax(1);
		ActionsRegistry.INSTANCE.getActions().add(action2);
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerMock());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertEquals(action, ActionsRegistry.INSTANCE.getActions().get(0));
		assertEquals(Action.ActionStatus.FLUSHED, action2.getStatus());
	}


	@Test
	public void testAddActionMaxCapacityIs0() {
		ActionsRegistry.INSTANCE.setSizeMax(0);
		ActionsRegistry.INSTANCE.addAction(new ActionMock(), new ActionHandlerMock());
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
	}


	@Test
	public void testAddActionAddsUndoableCollector() {
		final Action action = new ActionUndoableMock();
		ActionsRegistry.INSTANCE.addAction(action, new ActionHandlerMock());
		assertEquals(action, UndoCollector.INSTANCE.getLastUndo());
	}





	private class ActionHandlerMock implements ActionHandler {
		public ActionHandlerMock() {
			super();
		}

		@Override
		public void onUndoableUndo(final Undoable undoable) {fail();}
		@Override
		public void onUndoableRedo(final Undoable undoable) {fail();}
		@Override
		public void onUndoableAdded(final Undoable undoable) {fail();}
		@Override
		public void onActionExecuted(final Action act) {fail();}
		@Override
		public void onActionDone(final Action act) {fail();}
		@Override
		public void onActionCancelled(final Action act) {fail();}
		@Override
		public void onActionAdded(final Action act) {fail();}
		@Override
		public void onActionAborted(final Action act) {fail();}

		@Override
		public void onUndoableCleared() {
			// TODO Auto-generated method stub

		}
	}


	private class ActionUndoableMock extends Action implements Undoable {
		public ActionUndoableMock() {
			super();
		}

		@Override
		public boolean isRegisterable() {
			return false;
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
			// TODO Auto-generated method stub
			return null;
		}
	}


	private class ActionMock extends Action {
		public ActionMock() {
			super();
		}

		@Override
		public boolean isRegisterable() {
			return false;
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


	private class ActionMock2 extends Action {
		public ActionMock2() {
			super();
		}

		@Override
		public boolean cancelledBy(final Action action) {
			return action instanceof ActionMock;
		}

		@Override
		public boolean isRegisterable() {
			return false;
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
