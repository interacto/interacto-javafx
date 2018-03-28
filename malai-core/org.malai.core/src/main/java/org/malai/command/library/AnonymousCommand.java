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
package org.malai.command.library;

import org.malai.command.CommandImpl;

/**
 * An anonymous class that permits to create a command without defining a specific ActionImpl class.
 * A runnable, corresponding to the body of the command has to be provided to the cmd.
 * @author Arnaud Blouin
 */
public class AnonymousCommand extends CommandImpl {
	/** The runnable executed when the command is executed. */
	protected Runnable cmdBody;

	/** Create the command. */
	public AnonymousCommand() {
		super();
	}

	@Override
	protected void doCmdBody() {
		cmdBody.run();
	}

	@Override
	public boolean canDo() {
		return cmdBody != null;
	}

	/**
	 * Sets the runnable of the command.
	 * @param body The runnable executed when the command is executed.
	 */
	public void setCmdBody(final Runnable body) {
		cmdBody = body;
	}

	/**
	 * @return The runnable of the command.
	 */
	public Runnable getCmdBody() {
		return cmdBody;
	}
}
