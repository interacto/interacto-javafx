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
package org.malai.ui;

/**
 * A UI composer is a object that composes a user interface using instruments and presentations.
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <T> The type of widget produced by the composer.
 * @param <R> The type of root widget class of the GUI toolkit.
 * @param <S> The type of the progress bar in the GUI toolkit.
 */
public abstract class UIComposer<R, T, S> {
	/** The widget composed by the composer. */
	protected T widget;


	/**
	 * Creates the composer.
	 * @since 0.2
	 */
	public UIComposer() {
		super();
	}


	/**
	 * This method composes the user interface using instruments, presentations and widgets of the interactive system.
	 * @param progressBar The progress bar that can be used to show the progress of the UI composition. Can be null.
	 */
	public abstract void compose(final S progressBar);


	/**
	 * Changes the visibility of the given widget and may launch a process of recomposition/adaptation of the UI if needed.
	 * @param widget The widget to change its visibility.
	 * @param visible True: the widget will be visible.
	 * @since 0.2
	 */
	public abstract void setWidgetVisible(final R widget, final boolean visible);


	/**
	 * @return The widget composed by the composer.
	 * @since 0.2
	 */
	public T getWidget() {
		return widget;
	}
}
