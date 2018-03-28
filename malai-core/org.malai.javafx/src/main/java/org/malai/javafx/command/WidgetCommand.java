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
package org.malai.javafx.command;

import org.malai.command.CommandImpl;

/**
 * Defines an abstract command that concerns a widget.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetCommand<T> extends CommandImpl {
	/** The component concerned by the command. */
	protected T widget;

	/**
	 * Creates the command.
	 */
	public WidgetCommand() {
		super();
	}


	@Override
	public boolean canDo() {
		return widget != null;
	}


	/**
	 * @param n The component concerned by the command.
	 * @since 0.2
	 */
	public void setWidget(final T n) {
		this.widget = n;
	}


	@Override
	public void flush() {
		super.flush();
		widget = null;
	}
}
