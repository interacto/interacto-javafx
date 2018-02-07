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
package org.malai.javafx.action;

import javafx.scene.Node;

/**
 * This action shows or hides a JComponent.
 * @author Arnaud BLOUIN
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
	protected void doActionBody() {
		widget.setVisible(visible);
	}

	@Override
	public boolean canDo() {
		return super.canDo() && widget.isVisible() != visible;
	}


	/**
	 * @param visible Defines if the component must be shown or hidden.
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
