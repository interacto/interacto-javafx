package org.malai.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCommand {
	public static Command getCmdCanDo() {
		return new CommandImpl() {
			@Override
			protected void doCmdBody() {
				//
			}

			@Override
			public boolean canDo() {
				return true;
			}
		};
	}

	Command cmd;

	@BeforeEach
	void setUp() {
		cmd = getCmdCanDo();
		CommandsRegistry.INSTANCE.removeAllHandlers();
	}

	@Test
	void testCommandStatusAfterCreation() {
		assertEquals(Command.CmdStatus.CREATED, cmd.getStatus());
	}

	@Test
	void testCommandStatusAfterFlush() {
		cmd.flush();
		assertEquals(Command.CmdStatus.FLUSHED, cmd.getStatus());
	}

	@Test
	void testCommandCannotDoItWhenFlushed() {
		cmd.flush();
		assertFalse(cmd.doIt());
	}

	@Test
	void testCommandCannotDoItWhenDone() {
		cmd.done();
		assertFalse(cmd.doIt());
	}

	@Test
	void testCommandCannotDoItWhenCancelled() {
		cmd.cancel();
		assertFalse(cmd.doIt());
	}

	@Test
	void testCommandCannotDoItWhenCannotDoAndCreated() {
		final Command cmd = new CommandImplStub();
		assertFalse(cmd.doIt());
	}

	@Test
	void testCommandCanDoItWhenCanDo() {
		final Command cmd = getCmdCanDo();
		assertTrue(cmd.doIt());
	}

	@Test
	void testCommandIsExecutedWhenDoIt() {
		final Command cmd = getCmdCanDo();
		cmd.doIt();
		assertEquals(Command.CmdStatus.EXECUTED, cmd.getStatus());
	}

	@Test
	void testNotifiedOnCommandExecuted() {
		CmdHandler handler = Mockito.mock(CmdHandler.class);
		CommandsRegistry.INSTANCE.addHandler(handler);
		final Command cmd = getCmdCanDo();
		cmd.doIt();
		Mockito.verify(handler, Mockito.times(1)).onCmdExecuted(cmd);
	}

	@Test
	void testCommandHadEffectWhenDone() {
		cmd.done();
		assertTrue(cmd.hadEffect());
	}

	@Test
	void testCommandHadEffectWhenNotDoneAndCreated() {
		assertFalse(cmd.hadEffect());
	}

	@Test
	void testCommandHadEffectWhenNotDoneAndCancelled() {
		cmd.cancel();
		assertFalse(cmd.hadEffect());
	}

	@Test
	void testCommandHadEffectWhenNotDoneAndFlushed() {
		cmd.flush();
		assertFalse(cmd.hadEffect());
	}

	@Test
	void testCommandHadEffectWhenNotDoneAndExecuted() {
		final Command cmd = getCmdCanDo();
		cmd.doIt();
		assertFalse(cmd.hadEffect());
	}

	@Test
	void testCommandNotUnregisterByByDefault() {
		assertFalse(cmd.unregisteredBy(null));
		assertFalse(cmd.unregisteredBy(getCmdCanDo()));
	}

	@Test
	void testCommandNotDoneWhenFlushed() {
		cmd.flush();
		cmd.done();
		assertEquals(Command.CmdStatus.FLUSHED, cmd.getStatus());
	}

	@Test
	void testCommandNotDoneWhenCancelled() {
		cmd.cancel();
		cmd.done();
		assertEquals(Command.CmdStatus.CANCELLED, cmd.getStatus());
	}

	@Test
	void testHadEffectKOByDefault() {
		assertFalse(cmd.hadEffect());
	}

	@Test
	void testCommandNotDoneWhenDone() {
		final CmdHandler handler = Mockito.mock(CmdHandler.class);
		cmd.done();
		// Cannot visit CommandDone if already done.
		CommandsRegistry.INSTANCE.addHandler(handler);
		cmd.done();
		Mockito.verify(handler, Mockito.never()).onCmdDone(cmd);
	}

	@Test
	void testCommandDoneWhenCreated() {
		final CmdHandler handler = Mockito.mock(CmdHandler.class);
		CommandsRegistry.INSTANCE.addHandler(handler);
		cmd.done();
		assertEquals(Command.CmdStatus.DONE, cmd.getStatus());
		Mockito.verify(handler, Mockito.times(1)).onCmdDone(cmd);
	}


	@Test
	void testCommandDoneWhenExecuted() {
		final CmdHandler handler = Mockito.mock(CmdHandler.class);
		final Command cmd = getCmdCanDo();
		cmd.doIt();
		CommandsRegistry.INSTANCE.addHandler(handler);
		cmd.done();
		assertEquals(Command.CmdStatus.DONE, cmd.getStatus());
		Mockito.verify(handler, Mockito.times(1)).onCmdDone(cmd);
	}


	@Test
	void testToStringNotNull() {
		assertNotNull(cmd.toString());
	}

	@Test
	void testIsDoneWhenCreated() {
		assertFalse(cmd.isDone());
	}

	@Test
	void testIsDoneWhenCancelled() {
		cmd.cancel();
		assertFalse(cmd.isDone());
	}

	@Test
	void testIsDoneWhenFlushed() {
		cmd.flush();
		assertFalse(cmd.isDone());
	}


	@Test
	void testIsDoneWhenDone() {
		cmd.done();
		assertTrue(cmd.isDone());
	}

	@Test
	void testIsDoneWhenExecuted() {
		final Command cmd = getCmdCanDo();
		cmd.doIt();
		assertFalse(this.cmd.isDone());
	}

	@Test
	void testNotCancelAtStart() {
		assertNotSame(Command.CmdStatus.CANCELLED, cmd.getStatus());
	}

	@Test
	void testCancel() {
		cmd.cancel();
		assertEquals(Command.CmdStatus.CANCELLED, cmd.getStatus());
	}
}
