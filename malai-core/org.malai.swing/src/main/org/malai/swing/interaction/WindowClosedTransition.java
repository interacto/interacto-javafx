package org.malai.swing.interaction;

import org.malai.interaction.WidgetTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import java.awt.*;

/**
 * This transition corresponds to a press on the decorative close button of a frame.<br>
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
 * 05/31/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class WindowClosedTransition extends WidgetTransition<Window> {
	/**
	 * Creates the transition.
	 */
	public WindowClosedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
