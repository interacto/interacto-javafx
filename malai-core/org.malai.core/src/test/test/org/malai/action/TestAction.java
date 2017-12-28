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
	protected Action action;
	boolean visitOnActionExecuted;
	boolean visitOnActionDone;

	@Before
	public void setUp() {
		action = getActionCanDo();
		ActionsRegistry.INSTANCE.removeAllHandlers();
	}

	@Test
	public void testActionStatusAfterCreation() {
		assertEquals(Action.ActionStatus.CREATED, action.getStatus());
	}

	@Test
	public void testActionStatusAfterFlush() {
		action.flush();
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}

	@Test
	public void testActionCannotDoItWhenFlushed() {
		action.flush();
		assertFalse(action.doIt());
	}

	@Test
	public void testActionCannotDoItWhenDone() {
		action.done();
		assertFalse(action.doIt());
	}

	@Test
	public void testActionCannotDoItWhenCancelled() {
		action.cancel();
		assertFalse(action.doIt());
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
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionCancelled(final Action action) {fail();}

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
		action.done();
		assertTrue(action.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndCreated() {
		assertFalse(action.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndCancelled() {
		action.cancel();
		assertFalse(action.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndFlushed() {
		action.flush();
		assertFalse(action.hadEffect());
	}

	@Test
	public void testActionHadEffectWhenNotDoneAndExecuted() {
		final Action act = getActionCanDo();
		act.doIt();
		assertFalse(act.hadEffect());
	}

	@Test
	public void testActionNotUnregisterByByDefault() {
		assertFalse(action.unregisteredBy(null));
		assertFalse(action.unregisteredBy(getActionCanDo()));
	}

	@Test
	public void testActionNotDoneWhenFlushed() {
		action.flush();
		action.done();
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}

	@Test
	public void testActionNotDoneWhenCancelled() {
		action.cancel();
		action.done();
		assertEquals(Action.ActionStatus.CANCELLED, action.getStatus());
	}

	@Test
	public void testActionNotDoneWhenDone() {
		action.done();
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
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionCancelled(final Action action) {fail();}

			@Override
			public void onUndoableCleared() {
				// TODO Auto-generated method stub

			}
		});

		action.done();
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
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionCancelled(final Action action) {fail();}

			@Override
			public void onUndoableCleared() {
				// TODO Auto-generated method stub

			}
		});
		action.done();
		assertEquals(Action.ActionStatus.DONE, action.getStatus());
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
			public void onActionAdded(final Action action) {fail();}

			@Override
			public void onActionCancelled(final Action action) {fail();}

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
		assertNotNull(action.toString());
	}

	@Test
	public void testIsDoneWhenCreated() {
		assertFalse(action.isDone());
	}

	@Test
	public void testIsDoneWhenCancelled() {
		action.cancel();
		assertFalse(action.isDone());
	}

	@Test
	public void testIsDoneWhenFlushed() {
		action.flush();
		assertFalse(action.isDone());
	}


	@Test
	public void testIsDoneWhenDone() {
		action.done();
		assertTrue(action.isDone());
	}

	@Test
	public void testIsDoneWhenExecuted() {
		final Action a = getActionCanDo();
		a.doIt();
		assertFalse(action.isDone());
	}

	@Test
	public void testCancel() {
		assertNotSame(Action.ActionStatus.CANCELLED, action.getStatus());
		action.cancel();
		assertEquals(Action.ActionStatus.CANCELLED, action.getStatus());
	}
}
