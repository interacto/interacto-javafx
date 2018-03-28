/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.command;

import java.util.Collections;
import java.util.List;

/**
 * Base implementation of the Command interface.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class CommandImpl implements Command {
	/** The state of the command. */
	protected CmdStatus status;

	/**
	 * The default constructor.
	 * Initialises the current status to created.
	 */
	public CommandImpl() {
		super();
		status = CmdStatus.CREATED;
	}


	@Override
	public void flush() {
		status = CmdStatus.FLUSHED;
	}

	/**
	 * Commands may need to create a memento before their first execution.
	 * This is the goal of the operation that should be overriden.
	 * This operator is called a single time before the first execution of the command.
	 */
	protected void createMemento() {
		// To Override.
	}

	@Override
	public boolean doIt() {
		final boolean ok;

		if((status == CmdStatus.CREATED || status == CmdStatus.EXECUTED) && canDo()) {
			if(status == CmdStatus.CREATED) {
				createMemento();
			}

			ok = true;
			doCmdBody();
			status = CmdStatus.EXECUTED;
			CommandsRegistry.INSTANCE.onCmdExecuted(this);
		}else {
			ok = false;
		}

		return ok;
	}


	/**
	 * This method contains the statements to execute the command.
	 * This method is automatically called by DoIt and must not be called explicitly.
	 * @since 0.1
	 */
	protected abstract void doCmdBody();

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}

	@Override
	public boolean hadEffect() {
		return isDone();
	}


	@Override
	public boolean unregisteredBy(final Command cmd) {
		return false;
	}


	@Override
	public void done() {
		if(status == CmdStatus.CREATED || status == CmdStatus.EXECUTED) {
			status = CmdStatus.DONE;
			CommandsRegistry.INSTANCE.onCmdDone(this);
		}
	}


	@Override
	public boolean isDone() {
		return status == CmdStatus.DONE;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}


	@Override
	public void cancel() {
		status = CmdStatus.CANCELLED;
	}


	@Override
	public CmdStatus getStatus() {
		return status;
	}


	@Override
	public List<Command> followingCmds() {
		return Collections.emptyList();
	}
}
