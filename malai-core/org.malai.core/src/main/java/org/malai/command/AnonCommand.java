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

/**
 * An anonymous command that takes an anonymous function as a parameter corresponding to the command to execute.
 * The goal of this command is to avoid the creation of a command class for a small cmd.
 * @author Arnaud Blouin
 */
public class AnonCommand extends CommandImpl {
	private final Runnable exec;

	public AnonCommand(final Runnable function) {
		exec = function;
	}

	@Override
	public boolean canDo() {
		return exec != null;
	}

	@Override
	protected void doCmdBody() {
		exec.run();
	}
}
