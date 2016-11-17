/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.action;

/**
 * An anonymous action that takes an anonymous function as a parameter corresponding
 * to the command to execute.
 * The goal of this action is to avoid the creation of an action class for a small action.
 */
public class AnonAction extends ActionImpl {
	Runnable exec;

	public AnonAction() {
		this(null);
	}

	public AnonAction(final Runnable function) {
		exec = function;
	}

	public void setExec(final Runnable exec) {
		this.exec = exec;
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	public boolean canDo() {
		return exec!=null;
	}

	@Override
	protected void doActionBody() {
		exec.run();
	}
}
