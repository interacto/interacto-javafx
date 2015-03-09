/*
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 */
package org.malai.javafx.action.library;

import javafx.scene.Node;

/**
 * This action shows or hides a JComponent.<br>
 * @author Arnaud BLOUIN
 * @since 2.0
 */
public class ShowNode extends WidgetAction<Node> {
	/** Defines if the component must be shown or hidden. */
	protected boolean visible;


	/**
	 * Creates the action.
	 */
	public ShowNode() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		widget.setVisible(visible);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && widget.isVisible()!=visible;
	}


	/**
	 * @param visible Defines if the component must be shown or hidden.
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
