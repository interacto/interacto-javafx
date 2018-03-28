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
package org.malai.javafx.command;

import javafx.stage.Stage;

/**
 * This command shows or hides a window.
 * @author Arnaud BLOUIN
 */
public class ShowStage extends WidgetCommand<Stage> {
	/** Defines if the component must be shown or hidden. */
	protected boolean visible;


	/**
	 * Creates the command.
	 */
	public ShowStage() {
		super();
	}

	@Override
	protected void doCmdBody() {
		if(visible) {
			widget.show();
		}else {
			widget.hide();
		}
	}


	/**
	 * @param visible Defines if the component must be shown or hidden.
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
