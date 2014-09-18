package org.malai.javafx.ui;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;

import org.malai.ui.UIComposer;

/**
 * A UI composer is a object that composes a user interface using instruments and presentations.<br>
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
 * @author Arnaud BLOUIN
 * @param <T> The type of widget produced by the composer.
 */
public abstract class JfxUIComposer<T extends Node> extends UIComposer<Node, T, ProgressBar> {
	/**
	 * Creates the composer.
	 */
	public JfxUIComposer() {
		super();
	}

	@Override
	public void setWidgetVisible(final Node widget, final boolean visible) {
		if(widget!=null)
			widget.setVisible(visible);
	}
}
