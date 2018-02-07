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

import javafx.scene.control.ColorPicker;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A transition based on the onAction event of colour pickers.
 * @author Arnaud BLOUIN
 */
public class JfxColorPickedTransition extends JFXWidgetTransition<ColorPicker> {
	public JfxColorPickedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
