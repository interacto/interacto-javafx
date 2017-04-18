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
package org.malai.javafx.action.library;

import org.malai.action.ActionImpl;

/**
 * Defines an abstract action that concerns a widget.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetAction<T> extends ActionImpl {
	/** The component concerned by the action. */
	protected T widget;

	/**
	 * Creates the action.
	 */
	public WidgetAction() {
		super();
	}


	@Override
	public boolean canDo() {
		return widget!=null;
	}


	/**
	 * @param n The component concerned by the action.
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
