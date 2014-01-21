package org.malai.javafx.widget;

import javafx.scene.Node;
import javafx.scene.Parent;

import org.malai.picking.Picker;

/**
 * This singleton provides common methods for widgets.<br>
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
 * 11/02/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class JavafxWidgetUtilities {
	/** The singleton of the class. */
	public static final JavafxWidgetUtilities INSTANCE = new JavafxWidgetUtilities();

	/**
	 * Initialise the singleton.
	 * @since 0.2
	 */
	protected JavafxWidgetUtilities() {
		super();
	}


	/**
	 * Tests if the given point is into the pickable object.
	 * @param node The concerned widget.
	 * @param x The x-coordinate of the point to test.
	 * @param y The y-coordinate of the point to test.
	 * @return True if the given point is into the pickable object.
	 * @since 0.1
	 */
	public boolean contains(final Node node, final double x, final double y) {
		return node!=null && !node.isDisabled() && node.contains((int)x, (int)y);
	}


	/**
	 * @param node The concerned widget.
	 * @return The picker object that contains the pickable object.
	 * @since 0.2
	 */
	public Picker getPicker(final Node node) {
		Parent parent = node.getParent();
		Picker picker = null;

		while(picker==null && parent!=null)
			if(parent instanceof Picker)
				picker = (Picker) parent;
			else
				parent = parent.getParent();

		return picker;
	}
}
