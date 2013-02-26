package org.malai.interaction;

/**
 * This interface is the base interface of object that want to manager events produced by, for example,
 * Swing, Android.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-02-21<br>
 * @author Arnaud BLOUIN
 * @since 0.2
*  @<T> The root type of the component to support. For instance with Swing the root component is Component, for Android it is View.
 */
public interface EventManager<T> {
	/**
	 * Detaches the EventManager to the listened component.
	 * @param comp The Component to detach.
	 */
	void detachForm(final T comp);


	/**
	 * Attaches the EventManager to the given component to listen.
	 * @param comp The Component to listen.
	 */
	void attachTo(final T comp);


	/**
	 * Adds a handler to the event manager.
	 * @param h The handler to add. Must not be null.
	 * @since 0.1
	 */
	void addHandlers(final EventHandler h);

	/**
	 * Removes a handler from the event manager.
	 * @param h The handler to remove. Must not be null.
	 * @since 0.1
	 */
	void removeHandler(final EventHandler h);
}
