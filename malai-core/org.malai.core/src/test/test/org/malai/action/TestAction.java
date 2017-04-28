package test.org.malai.action;

import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionImpl;
import org.malai.action.ActionsRegistry;
import org.malai.undo.Undoable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestAction {
	protected Action IAction;
	boolean visitOnActionExecuted;
	boolean visitOnActionDone;

	@Before
	public void setUp() {
		IAction = getActionCanDo();
		ActionsRegistry.INSTANCE.removeAllHandlers();
	}

	@Test
	public void testActionStatusAfterCreation() {
		assertEquals(Action.ActionStatus.CREATED, IAction.getStatus());
	}

	@Test
	public void testActionStatusAfterFlush() {
		IAction.flush();
		assertEquals(Action.ActionStatus.FLUSHED, IAction.getStatus());
	}

	@Test
	public void testActionCannotDoItWhenFlushed() {
		IAction.flush();
		assertFalse(IAction.doIt());
	}

	@Test
	public void testActionCannotDoItWhenDone() {
		IAction.done();
		assertFalse(IAction.doIt());
	}

	@Test
	public void testActionCannotDoItWhenAborted() {
		IAction.abort();
		assertFalse(IAction.doIt());
	}

	@Test
	public void testActionCannotDoItWhenCannotDoAndCreated() {
		final Action act = getActionCannotDo();
		assertFalse(act.doIt());
	}

	@Test
	public void testActionCannotDoItWhenCannotDoAndExecuted() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
		IllegalAccessException {
		final Action act = getActionCannotDo();
		act.doIt();
		final Field field = act.getClass().getSuperclass().getDeclaredField("status");//
		field.setAccessible(true);
		field.set(act, Action.ActionStatus.EXECUTED);
		assertFalse(act.doIt());
	}

	@Test
	public void testActionCanDoItWhenCanDo() {
		final Action act = getActionCanDo();
		assertTrue(act.doIt());
	}

	@Test
	public void testActionIsExecutedWhenDoIt() {
		final Action act = getActionCanDo();
		act.doIt();
		assertEquals(Action.ActionStatus.EXECUTED, act.getStatus());
	}

	@Test
	public void testNotifiedOnActionExecuted() {
		visitOnActionExecuted = false;

		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableRedo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableAdded(final Undoable undoable) {fail();}

			@Override
			public void onActionExecuted(final Action action) {
				visitOnActionExecuted = true;
			}

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
				// TODO Auto-generated method stub

			}
		});

		final Action act = getActionCanDo();
		act.doIt();
		assertTrue(visitOnActionExecuted);
	}

	public Action getActionCanDo() {
		return new ActionImpl() {
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
				return true;
			}
		};
	}

	public Action getActionCannotDo() {
		return new ActionImpl() {
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
		};
	}

	@Test
	public void testActionHadEffectWhenDone() {
		IAction.done();
		assertTrue(IAction.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndCreated() {
		assertFalse(IAction.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndAborted() {
		IAction.abort();
		assertFalse(IAction.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndFlushed() {
		IAction.flush();
		assertFalse(IAction.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndExecuted() {
		final Action act = getActionCanDo();
		act.doIt();
		assertFalse(act.hadEffect());
	}

	@Test
	public void testActionNotUnregisterByByDefault() {
		assertFalse(IAction.unregisteredBy(null));
		assertFalse(IAction.unregisteredBy(getActionCanDo()));
	}

	@Test
	public void testActionNotDoneWhenFlushed() {
		IAction.flush();
		IAction.done();
		assertEquals(Action.ActionStatus.FLUSHED, IAction.getStatus());
	}

	@Test
	public void testActionNotDoneWhenAborted() {
		IAction.abort();
		IAction.done();
		assertEquals(Action.ActionStatus.ABORTED, IAction.getStatus());
	}

	@Test
	public void testActionNotDoneWhenDone() {
		IAction.done();
		// Cannot visit ActionDone if already done.
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
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
				// TODO Auto-generated method stub

			}
		});

		IAction.done();
	}

	@Test
	public void testActionDoneWhenCreated() {
		visitOnActionDone = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableRedo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableAdded(final Undoable undoable) {fail();}

			@Override
			public void onActionExecuted(final Action action) {fail();}

			@Override
			public void onActionDone(final Action action) { visitOnActionDone = true; }

			@Override
			public void onActionCancelled(final Action action) {fail();}

			@Override
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionAborted(final Action action) {fail();}

			@Override
			public void onUndoableCleared() {
				// TODO Auto-generated method stub

			}
		});
		IAction.done();
		assertEquals(Action.ActionStatus.DONE, IAction.getStatus());
		assertTrue(visitOnActionDone);
	}


	@Test
	public void testActionDoneWhenExecuted() {
		final Action a = getActionCanDo();
		a.doIt();
		visitOnActionDone = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableRedo(final Undoable undoable) {fail();}

			@Override
			public void onUndoableAdded(final Undoable undoable) {fail();}

			@Override
			public void onActionExecuted(final Action action) {fail();}

			@Override
			public void onActionDone(final Action action) { visitOnActionDone = true; }

			@Override
			public void onActionCancelled(final Action action) {fail();}

			@Override
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionAborted(final Action action) {fail();}

			@Override
			public void onUndoableCleared() {
				// TODO Auto-generated method stub

			}
		});
		a.done();
		assertEquals(Action.ActionStatus.DONE, a.getStatus());
		assertTrue(visitOnActionDone);
	}


	@Test
	public void testToStringNotNull() {
		assertNotNull(IAction.toString());
	}

	@Test
	public void testIsDoneWhenCreated() {
		assertFalse(IAction.isDone());
	}

	@Test
	public void testIsDoneWhenAborted() {
		IAction.abort();
		assertFalse(IAction.isDone());
	}

	@Test
	public void testIsDoneWhenFlushed() {
		IAction.flush();
		assertFalse(IAction.isDone());
	}


	@Test
	public void testIsDoneWhenDone() {
		IAction.done();
		assertTrue(IAction.isDone());
	}

	@Test
	public void testIsDoneWhenExecuted() {
		final Action a = getActionCanDo();
		a.doIt();
		assertFalse(IAction.isDone());
	}

	@Test
	public void testAbort() {
		assertNotSame(Action.ActionStatus.ABORTED, IAction.getStatus());
		IAction.abort();
		assertEquals(Action.ActionStatus.ABORTED, IAction.getStatus());
	}
}
