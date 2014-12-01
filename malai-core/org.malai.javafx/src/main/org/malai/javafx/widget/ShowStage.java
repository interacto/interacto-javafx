/*
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 */

package org.malai.javafx.widget;

import javafx.stage.Stage;

import org.malai.action.Action;

/**
 * An action for showing or hiding a stage.<br>
 * @author Arnaud BLOUIN
 * @date 2014-11-30
 */
public class ShowStage extends Action {
	protected Stage stage;
	protected boolean show;
	
	/**
	 * Creates the action.
	 */
	public ShowStage() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		if(show)
			stage.show();
		else
			stage.hide();
	}
	

	/**
	 * Sets the stage to show or hide.
	 * @param stage The stage to show or hide.
	 */
	public void setStage(final Stage newStage) {
		stage = newStage;
	}
	
	/**
	 * Defines if the stage must be shown or hidden.
	 * @param visible True: the stage will be shown.
	 */
	public void setShow(final boolean visible) {
		show = visible;
	}

	@Override
	public boolean canDo() {
		return stage!=null;
	}
}
