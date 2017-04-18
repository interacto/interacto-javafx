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
package org.malai.action.library;

import org.malai.action.ActionImpl;

/**
 * An anonymous class that permits to create an action without defining a specific ActionImpl class.
 * A runnable, corresponding to the body of the action has to be provided to the action.
 * @author Arnaud Blouin
 */
public class AnonymousAction extends ActionImpl {
	/** The runnable executed when the action is executed. */
	protected Runnable actionBody;

	/** Create the action. */
	public AnonymousAction() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		actionBody.run();
	}

	@Override
	public boolean canDo() {
		return actionBody!=null;
	}

	/**
	 * Sets the runnable of the action.
	 * @param body The runnable executed when the action is executed.
	 */
	public void setActionBody(final Runnable body) {
		actionBody = body;
	}

	/**
	 * @return The runnable of the action.
	 */
	public Runnable getActionBody() {
		return actionBody;
	}
}
