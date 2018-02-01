package org.malai.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAction {
	public static Action getActionCanDo() {
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

	Action action;

	@BeforeEach
	void setUp() {
		action = getActionCanDo();
		ActionsRegistry.INSTANCE.removeAllHandlers();
	}

	@Test
	void testActionStatusAfterCreation() {
		assertEquals(Action.ActionStatus.CREATED, action.getStatus());
	}

	@Test
	void testActionStatusAfterFlush() {
		action.flush();
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}

	@Test
	void testActionCannotDoItWhenFlushed() {
		action.flush();
		assertFalse(action.doIt());
	}

	@Test
	void testActionCannotDoItWhenDone() {
		action.done();
		assertFalse(action.doIt());
	}

	@Test
	void testActionCannotDoItWhenCancelled() {
		action.cancel();
		assertFalse(action.doIt());
	}

	@Test
	void testActionCannotDoItWhenCannotDoAndCreated() {
		final Action act = new ActionImplStub();
		assertFalse(act.doIt());
	}

	@Test
	void testActionCanDoItWhenCanDo() {
		final Action act = getActionCanDo();
		assertTrue(act.doIt());
	}

	@Test
	void testActionIsExecutedWhenDoIt() {
		final Action act = getActionCanDo();
		act.doIt();
		assertEquals(Action.ActionStatus.EXECUTED, act.getStatus());
	}

	@Test
	void testNotifiedOnActionExecuted() {
		ActionHandler handler = Mockito.mock(ActionHandler.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		final Action act = getActionCanDo();
		act.doIt();
		Mockito.verify(handler, Mockito.times(1)).onActionExecuted(act);
	}

	@Test
	void testActionHadEffectWhenDone() {
		action.done();
		assertTrue(action.hadEffect());
	}

	@Test
	void testActionHadEffectWhenNotDoneAndCreated() {
		assertFalse(action.hadEffect());
	}

	@Test
	void testActionHadEffectWhenNotDoneAndCancelled() {
		action.cancel();
		assertFalse(action.hadEffect());
	}

	@Test
	void testActionHadEffectWhenNotDoneAndFlushed() {
		action.flush();
		assertFalse(action.hadEffect());
	}

	@Test
	void testActionHadEffectWhenNotDoneAndExecuted() {
		final Action act = getActionCanDo();
		act.doIt();
		assertFalse(act.hadEffect());
	}

	@Test
	void testActionNotUnregisterByByDefault() {
		assertFalse(action.unregisteredBy(null));
		assertFalse(action.unregisteredBy(getActionCanDo()));
	}

	@Test
	void testActionNotDoneWhenFlushed() {
		action.flush();
		action.done();
		assertEquals(Action.ActionStatus.FLUSHED, action.getStatus());
	}

	@Test
	void testActionNotDoneWhenCancelled() {
		action.cancel();
		action.done();
		assertEquals(Action.ActionStatus.CANCELLED, action.getStatus());
	}

	@Test
	void testHadEffectKOByDefault() {
		assertFalse(action.hadEffect());
	}

	@Test
	void testActionNotDoneWhenDone() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		action.done();
		// Cannot visit ActionDone if already done.
		ActionsRegistry.INSTANCE.addHandler(handler);
		action.done();
		Mockito.verify(handler, Mockito.never()).onActionDone(action);
	}

	@Test
	void testActionDoneWhenCreated() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		ActionsRegistry.INSTANCE.addHandler(handler);
		action.done();
		assertEquals(Action.ActionStatus.DONE, action.getStatus());
		Mockito.verify(handler, Mockito.times(1)).onActionDone(action);
	}


	@Test
	void testActionDoneWhenExecuted() {
		final ActionHandler handler = Mockito.mock(ActionHandler.class);
		final Action a = getActionCanDo();
		a.doIt();
		ActionsRegistry.INSTANCE.addHandler(handler);
		a.done();
		assertEquals(Action.ActionStatus.DONE, a.getStatus());
		Mockito.verify(handler, Mockito.times(1)).onActionDone(a);
	}


	@Test
	void testToStringNotNull() {
		assertNotNull(action.toString());
	}

	@Test
	void testIsDoneWhenCreated() {
		assertFalse(action.isDone());
	}

	@Test
	void testIsDoneWhenCancelled() {
		action.cancel();
		assertFalse(action.isDone());
	}

	@Test
	void testIsDoneWhenFlushed() {
		action.flush();
		assertFalse(action.isDone());
	}


	@Test
	void testIsDoneWhenDone() {
		action.done();
		assertTrue(action.isDone());
	}

	@Test
	void testIsDoneWhenExecuted() {
		final Action a = getActionCanDo();
		a.doIt();
		assertFalse(action.isDone());
	}

	@Test
	void testNotCancelAtStart() {
		assertNotSame(Action.ActionStatus.CANCELLED, action.getStatus());
	}

	@Test
	void testCancel() {
		action.cancel();
		assertEquals(Action.ActionStatus.CANCELLED, action.getStatus());
	}
}
