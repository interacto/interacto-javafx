/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
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
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;

/**
 * A user interaction for date pickers.
 * @author Arnaud BLOUIN
 */
public class DatePicked extends JfxInteraction<WidgetData<DatePicker>, DatePickedFSM, DatePicker> {
	private final DatePickedFSM.DatePickedFSMHandler handler;

	/**
	 * Creates the interaction.
	 */
	public DatePicked() {
		super(new DatePickedFSM());

		handler = new DatePickedFSM.DatePickedFSMHandler() {
			@Override
			public void initToPickedHandler(final ActionEvent event) {
				if(event.getSource() instanceof DatePicker) {
					widget = (DatePicker) event.getSource();
				}
			}

			@Override
			public void reinitData() {
				DatePicked.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof DatePicker) {
			registerActionHandler(node);
		}
	}

	@Override
	public WidgetData<DatePicker> getData() {
		return this;
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof DatePicker) {
			unregisterActionHandler(node);
		}
	}
}
