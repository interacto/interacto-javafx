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

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import org.malai.javafx.interaction2.JfxInteraction;

/**
 * A user interaction for date pickers.
 * @author Arnaud BLOUIN
 */
public class DatePicked extends JfxInteraction<DatePickedFSM, DatePicker> {
	/**
	 * Creates the interaction.
	 */
	public DatePicked() {
		super(new DatePickedFSM());
		fsm.buildFSM(this);
	}

	@Override
	public void processDatePickerData(final Object datepicker) {
		if(datepicker instanceof DatePicker) {
			widget = (DatePicker) datepicker;
		}
	}

	@Override
	protected void onNewNodeRegistered(final Node node) {
		if(node instanceof DatePicker) {
			registerActionHandler(node);
		}
	}

	@Override
	protected void onNodeUnregistered(final Node node) {
		if(node instanceof DatePicker) {
			unregisterActionHandler(node);
		}
	}
}
