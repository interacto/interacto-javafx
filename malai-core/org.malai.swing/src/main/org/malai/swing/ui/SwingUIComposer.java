package org.malai.swing.ui;

import java.awt.Component;

import org.malai.swing.widget.MProgressBar;
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
 * 10/31/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 * @param <T> The type of widget produced by the composer.
 */
public abstract class SwingUIComposer<T extends Component> extends UIComposer<Component, T, MProgressBar> {
	/**
	 * Creates the composer.
	 * @since 0.2
	 */
	public SwingUIComposer() {
		super();
	}

	@Override
	public void setWidgetVisible(final Component widget, final boolean visible) {
		if(widget!=null)
			widget.setVisible(visible);
	}
}
