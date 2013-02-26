package org.malai.swing.instrument.library;

import java.awt.Component;

import org.malai.instrument.Instrument;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.widget.MButton;

/**
 * This link links a button interaction to an action that shows a JComponent.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-10-23<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 * @param <N> The type of the instrument that will contain this link.
 */
public class Button2ShowComponentLink<N extends Instrument> extends Interaction2ShowComponentLink<ButtonPressed, N> {
	/** The button used to shows the component. */
	protected MButton button;

	public Button2ShowComponentLink(final N ins, final Component component, final MButton button)
			throws InstantiationException, IllegalAccessException {
		super(ins, false, ButtonPressed.class, component);
		this.button = button;
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==button;
	}
}
