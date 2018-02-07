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
package org.malai.javafx.interaction;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import org.malai.interaction.WidgetTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A transition based on a JFX widget.
 * @param <T> The type of the widget.
 * @author Arnaud Blouin
 */
public abstract class JFXWidgetTransition<T> extends WidgetTransition<T> {
	public JFXWidgetTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	@Override
	public EventType<ActionEvent> getEventType() {
		return ActionEvent.ACTION;
	}
}
