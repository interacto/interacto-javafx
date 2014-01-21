package org.malai.swing.interaction;

import javax.swing.JMenuItem;

import org.malai.interaction.Transition;
import org.malai.interaction.WidgetTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This transition is mapped to a menu item that has been pressed.<br>
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
 * 05/21/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class MenuItemTransition extends WidgetTransition<JMenuItem> {
	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 * @since 0.2
	 */
	public MenuItemTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
