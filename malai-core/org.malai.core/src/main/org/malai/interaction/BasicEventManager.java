package org.malai.interaction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A basic event manager that gathers events produces by widgets and transfers them to handlers.<br>
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
 * 2013-02-26<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class BasicEventManager<T> implements EventManager<T> {
	/** The handlers that are notified when events occur. */
	protected List<EventHandler> handlers;

	/**
	 * Creates a event manager that gathers events and transfers them to handlers.
	 * @since 0.2
	 */
	public BasicEventManager() {
		super();
		handlers = new CopyOnWriteArrayList<EventHandler>();
	}


	@Override
	public void addHandlers(final EventHandler h) {
		if(h!=null)
			handlers.add(h);
	}


	@Override
	public void removeHandler(final EventHandler h) {
		if(h!=null)
			handlers.remove(h);
	}
}
