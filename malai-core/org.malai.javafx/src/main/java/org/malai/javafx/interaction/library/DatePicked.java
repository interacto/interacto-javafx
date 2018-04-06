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
package org.malai.javafx.interaction.library;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import org.malai.javafx.interaction.JfxInteraction;

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
