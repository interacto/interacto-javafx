package org.malai.swing.interaction;

import javax.swing.JTabbedPane;

import org.malai.interaction.Transition;
import org.malai.interaction.WidgetTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition corresponds to a change in the selection of a tabbed pane.<br>
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
 * 12/09/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class TabSelectedTransition extends WidgetTransition<JTabbedPane> {
	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public TabSelectedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
