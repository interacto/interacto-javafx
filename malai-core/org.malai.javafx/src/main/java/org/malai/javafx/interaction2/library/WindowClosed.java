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
package org.malai.javafx.interaction2.library;

import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for windows to be closed.
 * @author Arnaud BLOUIN
 */
public class WindowClosed extends JfxInteraction<WindowClosedFSM, Window> {
	private final EventHandler<WindowEvent> winClose;
	private final WindowClosedFSM.WindowClosedHandler handler;

	/**
	 * Creates the interaction.
	 */
	public WindowClosed() {
		super(new WindowClosedFSM());

		handler = new WindowClosedFSM.WindowClosedHandler() {
			@Override
			public void initToClosedHandler(final WindowEvent event) {
				if(event.getSource() instanceof Window) {
					widget = (Window) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				WindowClosed.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
		winClose = evt -> processEvent(evt);
	}

	@Override
	protected void onNewWindowRegistered(final Window window) {
		if(window != null) {
			window.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, winClose);
		}
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		if(window != null) {
			window.removeEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, winClose);
		}
	}
}
