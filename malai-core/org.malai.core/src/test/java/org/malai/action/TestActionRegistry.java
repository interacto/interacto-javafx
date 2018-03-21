package org.malai.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.action.Action.ActionStatus;
import org.malai.undo.UndoCollector;
import org.malai.undo.Undoable;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestActionRegistry {
	@BeforeEach
	public void setUp() {
		ActionsRegistry.INSTANCE.removeAllHandlers();
		ActionsRegistry.INSTANCE.getActions().clear();
		ActionsRegistry.INSTANCE.setSizeMax(30);
		UndoCollector.INSTANCE.clear();
	}

	@Test
	public void testGetSetSizeMaxOK() {
		ActionsRegistry.INSTANCE.setSizeMax(55);
		assertEquals(55, ActionsRegistry.INSTANCE.getSizeMax());
	}

	@Test
	public void testGetSetSizeMaxNeg() {
		ActionsRegistry.INSTANCE.setSizeMax(55);
		ActionsRegistry.INSTANCE.setSizeMax(-1);
		assertEquals(55, ActionsRegistry.INSTANCE.getSizeMax());
	}

	@Test
	public void testGetSetSizeMaxZero() {
		ActionsRegistry.INSTANCE.setSizeMax(0);
		assertEquals(0, ActionsRegistry.INSTANCE.getSizeMax());
	}

	@Test
	public void testSetSizeMaxRemovesAction() {
		final List<Action> handlers = ActionsRegistry.INSTANCE.getActions();
		final Action action1 = new ActionImplStub();
		final Action action2 = new ActionImplStub();
		ActionsRegistry.INSTANCE.setSizeMax(10);
		ActionsRegistry.INSTANCE.addAction(action1, Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.addAction(action2, Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.setSizeMax(1);

		assertEquals(ActionStatus.FLUSHED, action1.getStatus());
		assertEquals(ActionStatus.CREATED, action2.getStatus());
		assertEquals(1, handlers.size());
		assertEquals(action2, handlers.get(0));
	}


	@Test
	public void testCancelActionNull() {
		ActionsRegistry.INSTANCE.cancelAction(null);
	}


	@Test
	public void testCancelActionFlush() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.cancelAction(action);
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testCancelActionNotify() {
		final Action action = Mockito.mock(Action.class);
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.cancelAction(action);
		Mockito.verify(handler, Mockito.times(1)).onActionCancelled(action);
	}


	@Test
	public void testCancelActionRemoved() {
		final Action action = Mockito.mock(Action.class);
		ActionsRegistry.INSTANCE.addAction(action, Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.cancelAction(action);
		assertTrue(ActionsRegistry.INSTANCE.getHandlers().isEmpty());
	}


	@Test
	public void testRemoveAllHandler() {
		ActionsRegistry.INSTANCE.addHandler(Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.addHandler(Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.removeAllHandlers();
		assertTrue(ActionsRegistry.INSTANCE.getHandlers().isEmpty());
	}


	@Test
	public void testRemoveHandlerKO() {
		ActionsRegistry.INSTANCE.addHandler(Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.removeHandler(null);
		assertEquals(1, ActionsRegistry.INSTANCE.getHandlers().size());
	}

	@Test
	public void testRemoveHandlerOK() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.removeHandler(handler);
		assertTrue(ActionsRegistry.INSTANCE.getHandlers().isEmpty());
	}


	@Test
	public void testAddHandlerKO() {
		ActionsRegistry.INSTANCE.addHandler(null);
		assertTrue(ActionsRegistry.INSTANCE.getHandlers().isEmpty());
	}

	@Test
	public void testAddHandlerOK() {
		ActionsRegistry.INSTANCE.addHandler(Mockito.mock(ActionHandler.class));
		assertEquals(1, ActionsRegistry.INSTANCE.getHandlers().size());
	}


	@Test
	public void testRemoveActionNull() {
		ActionsRegistry.INSTANCE.addAction(Mockito.mock(Action.class), Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.removeAction(null);
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}


	@Test
	public void testRemoveActionNotNull() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.addAction(action, Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.removeAction(action);
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}


	@Test
	public void testOnActionExecuted() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		final Action action = Mockito.mock(Action.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.onActionExecuted(action);
		Mockito.verify(handler, Mockito.times(1)).onActionExecuted(action);
	}


	@Test
	public void testOnActionDone() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		final Action action = Mockito.mock(Action.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.onActionDone(action);
		Mockito.verify(handler, Mockito.times(1)).onActionDone(action);
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
		ActionsRegistry.INSTANCE.addAction(act, Mockito.mock(ActionHandler.class));
		ActionsRegistry.INSTANCE.unregisterActions(new ActionImplStub2());
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertNotSame(Action.ActionStatus.FLUSHED, act.getStatus());
	}

	@Test
	public void testAddActionCannotAddBecauseNull() {
		final Action action = new ActionImplStub();
		ActionsRegistry.INSTANCE.getActions().add(action);
		ActionsRegistry.INSTANCE.addAction(null, Mockito.mock(ActionHandler.class));
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}

	@Test
	public void testAddActionCannotAddBecauseExist() {
		final Action action = new org.malai.action.ActionImplStub();
		ActionsRegistry.INSTANCE.getActions().add(action);
		ActionsRegistry.INSTANCE.addAction(action, Mockito.mock(ActionHandler.class));
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
	}


	@Test
	public void testAddActionNotifyHandlers() {
		final Action action = Mockito.mock(Action.class);
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		ActionsRegistry.INSTANCE.addAction(action, handler);
		Mockito.verify(handler, Mockito.times(1)).onActionAdded(action);
	}


	@Test
	public void testAddActionRemovesActionWhenMaxCapacity() {
		final Action action = Mockito.mock(Action.class);
		final Action action2 = new ActionImplStub();
		ActionsRegistry.INSTANCE.setSizeMax(1);
		ActionsRegistry.INSTANCE.getActions().add(action2);
		ActionsRegistry.INSTANCE.addAction(action, Mockito.mock(ActionHandler.class));
		assertEquals(1, ActionsRegistry.INSTANCE.getActions().size());
		assertEquals(action, ActionsRegistry.INSTANCE.getActions().get(0));
		assertEquals(Action.ActionStatus.FLUSHED, action2.getStatus());
	}


	@Test
	public void testAddActionMaxCapacityIs0() {
		ActionsRegistry.INSTANCE.setSizeMax(0);
		ActionsRegistry.INSTANCE.addAction(Mockito.mock(Action.class), Mockito.mock(ActionHandler.class));
		assertTrue(ActionsRegistry.INSTANCE.getActions().isEmpty());
	}


	@Test
	public void testAddActionAddsUndoableCollector() {
		final Action action = new ActionImplUndoableStub();
		ActionsRegistry.INSTANCE.addAction(action, Mockito.mock(ActionHandler.class));
		assertEquals(action, UndoCollector.INSTANCE.getLastUndo().get());
	}


	@Test
	public void testRegistryConcurrentAccess() {
		final List<Action> addedActions = new ArrayList<>();
		final ActionHandler stub = Mockito.mock(ActionHandler.class);

		IntStream.range(0, 100000).parallel().forEach(i -> {
			if(i % 2 == 0) {
				final Action action = Mockito.mock(Action.class);
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


	private static class ActionImplUndoableStub extends ActionImpl implements Undoable {
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


	private static class ActionImplStub extends ActionImpl {
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


	private static class ActionImplStub2 extends ActionImpl {
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
