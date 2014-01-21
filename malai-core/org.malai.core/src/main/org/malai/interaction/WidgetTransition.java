package org.malai.interaction;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition must be used to use a widget within an interaction.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * @author Arnaud BLOUIN
 */
public abstract class WidgetTransition<T> extends Transition {
	/** The pressed button. */
	protected T widget;

	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public WidgetTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return The widget used.
	 */
	public T getWidget() {
		return widget;
	}

	/**
	 * Sets the widget.
	 * @param widget The widget to set. Must not be null.
	 */
	public void setWidget(final T widget) {
		if(widget!=null)
			this.widget = widget;
	}
}
