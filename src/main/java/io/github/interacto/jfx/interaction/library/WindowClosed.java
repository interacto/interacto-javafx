/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.interacto.jfx.interaction.library;

import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * A user interaction for windows to be closed.
 * @author Arnaud BLOUIN
 */
public class WindowClosed extends JfxInteraction<WidgetData<Window>, WindowClosedFSM> {
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
					((WidgetDataImpl<Window>) data).setWidget((Window) event.getSource());
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
	public WidgetData<Window> getData() {
		return super.getData();
	}

	@Override
	protected WidgetDataImpl<Window> createDataObject() {
		return new WidgetDataImpl<>();
	}

	@Override
	protected void onWindowUnregistered(final Window window) {
		if(window != null) {
			window.removeEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, winClose);
		}
	}
}
