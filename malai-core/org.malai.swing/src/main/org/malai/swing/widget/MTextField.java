package org.malai.swing.widget;

import javax.swing.JTextField;

import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.swing.interaction.SwingEventManager;


/**
 * This widgets is based on a JTextField. It allows to be used in the Malai framework for picking.<br>
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
 * 06/05/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MTextField extends JTextField implements Pickable {
	private static final long	serialVersionUID	= 1L;

	/**
	 * {@link JTextField#JTextField()}
	 */
	public MTextField() {
		this(false);
	}


	/**
	 * Creates the text field.
	 * @param eventOnEachModification If true: each modification
	 * of the underlying document will launch an event (DocumentEvent)
	 * that can be used by a link based on the interaction TextChanged.
	 * If false, the user has to type on the back space key to create
	 * an event.
	 */
	public MTextField(final boolean eventOnEachModification) {
		super();

		if(eventOnEachModification)
			getDocument().putProperty(SwingEventManager.OWNING_PROPERTY, this);
	}



	@Override
	public boolean contains(final double x, final double y) {
		return WidgetUtilities.INSTANCE.contains(this, x, y);
	}


	@Override
	public Picker getPicker() {
		return WidgetUtilities.INSTANCE.getPicker(this);
	}
}
