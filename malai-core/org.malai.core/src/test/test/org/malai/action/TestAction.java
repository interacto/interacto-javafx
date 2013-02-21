package test.org.malai.action;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.undo.Undoable;

public class TestAction extends TestCase {
	protected Action action;
	
	@Override
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
	public void testActionCannotDoItWhenAborted() {
		action.abort();
		assertFalse(action.doIt());
	}
	
	@Test
	public void testActionCannotDoItWhenCannotDoAndCreated() {
		Action act = getActionCannotDo();
		assertFalse(act.doIt());
	}
	
	@Test
	public void testActionCannotDoItWhenCannotDoAndExecuted() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Action act = getActionCannotDo();
		act.doIt();
		Field field = act.getClass().getSuperclass().getDeclaredField("status");//
		field.setAccessible(true);
		field.set(act, Action.ActionStatus.EXECUTED);
		assertFalse(act.doIt());
	}
	
	@Test
	public void testActionCanDoItWhenCanDo() {
		Action act = getActionCanDo();
		assertTrue(act.doIt());
	}
	
	@Test
	public void testActionIsExecutedWhenDoIt() {
		Action act = getActionCanDo();
		act.doIt();
		assertEquals(Action.ActionStatus.EXECUTED, act.getStatus());
	}
	
	
	boolean visitOnActionExecuted;
	
	@Test
	public void testNotifiedOnActionExecuted() {
		visitOnActionExecuted = false;
		
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(Undoable undoable) {fail();}
			@Override
			public void onUndoableRedo(Undoable undoable) {fail();}
			@Override
			public void onUndoableAdded(Undoable undoable) {fail();}
			@Override
			public void onActionExecuted(Action act) {
				visitOnActionExecuted = true;
			}
			@Override
			public void onActionDone(Action act) {fail();}
			@Override
			public void onActionCancelled(Action act) {fail();}
			@Override
			public void onActionAdded(Action act) {fail();}
			@Override
			public void onActionAborted(Action act) {fail();}
		});
		
		Action act = getActionCanDo();
		act.doIt();
		assertTrue(visitOnActionExecuted);
	}
	
	
	public Action getActionCanDo() {
		return new Action() {
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
		return new Action() {
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
		action.done();
		assertTrue(action.hadEffect());
	}
	
	
	@Test
	public void testActionHadEffectWhenNotDoneAndCreated() {
		assertFalse(action.hadEffect());
	}
	
	
	@Test
	public void testActionHadEffectWhenNotDoneAndAborted() {
		action.abort();
		assertFalse(action.hadEffect());
	}
	
	
	
	@Test
	public void testActionHadEffectWhenNotDoneAndFlushed() {
		action.flush();
		assertFalse(action.hadEffect());
	}
	
	
	@Test
	public void testActionHadEffectWhenNotDoneAndExecuted() {
		Action act = getActionCanDo();
		act.doIt();
		assertFalse(act.hadEffect());
	}
	
	
	@Test
	public void testActionNotCancelledByByDefault() {
		assertFalse(action.cancelledBy(null));
		assertFalse(action.cancelledBy(getActionCanDo()));
	}
	
	
	@Test
	public void testActionNotDoneWhenFlushed() {
		action.flush();
		action.done();
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}
	
	
	@Test
	public void testActionNotDoneWhenAborted() {
		action.abort();
		action.done();
		assertEquals(Action.ActionStatus.ABORTED, action.getStatus());
	}
	
	
	@Test
	public void testActionNotDoneWhenDone() {
		action.done();
		// Cannot visit ActionDone if already done.
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(Undoable undoable) {fail();}
			@Override
			public void onUndoableRedo(Undoable undoable) {fail();}
			@Override
			public void onUndoableAdded(Undoable undoable) {fail();}
			@Override
			public void onActionExecuted(Action act) {fail();}
			@Override
			public void onActionDone(Action act) {fail();}
			@Override
			public void onActionCancelled(Action act) {fail();}
			@Override
			public void onActionAdded(Action act) {fail();}
			@Override
			public void onActionAborted(Action act) {fail();}
		});
		
		action.done();
	}
	
	
	boolean visitOnActionDone;
	
	@Test
	public void testActionDoneWhenCreated() {
		visitOnActionDone = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(Undoable undoable) {fail();}
			@Override
			public void onUndoableRedo(Undoable undoable) {fail();}
			@Override
			public void onUndoableAdded(Undoable undoable) {fail();}
			@Override
			public void onActionExecuted(Action act) {fail();}
			@Override
			public void onActionDone(Action act) { visitOnActionDone = true; }
			@Override
			public void onActionCancelled(Action act) {fail();}
			@Override
			public void onActionAdded(Action act) {fail();}
			@Override
			public void onActionAborted(Action act) {fail();}
		});
		action.done();
		assertEquals(Action.ActionStatus.DONE, action.getStatus());
		assertTrue(visitOnActionDone);
	}
	
	
	
	@Test
	public void testActionDoneWhenExecuted() {
		Action a = getActionCanDo();
		a.doIt();
		visitOnActionDone = false;
		ActionsRegistry.INSTANCE.addHandler(new ActionHandler() {
			@Override
			public void onUndoableUndo(Undoable undoable) {fail();}
			@Override
			public void onUndoableRedo(Undoable undoable) {fail();}
			@Override
			public void onUndoableAdded(Undoable undoable) {fail();}
			@Override
			public void onActionExecuted(Action act) {fail();}
			@Override
			public void onActionDone(Action act) { visitOnActionDone = true; }
			@Override
			public void onActionCancelled(Action act) {fail();}
			@Override
			public void onActionAdded(Action act) {fail();}
			@Override
			public void onActionAborted(Action act) {fail();}
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
	public void testIsDoneWhenAborted() {
		action.abort();
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
		Action a = getActionCanDo();
		a.doIt();
		assertFalse(action.isDone());
	}
	
	@Test
	public void testAbort() {
		assertNotSame(Action.ActionStatus.ABORTED, action.getStatus());
		action.abort();
		assertEquals(Action.ActionStatus.ABORTED, action.getStatus());
	}
}
