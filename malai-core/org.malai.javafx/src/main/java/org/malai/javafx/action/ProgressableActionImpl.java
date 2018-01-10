/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.action;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.malai.action.ActionImpl;

/**
 * A base implementation of ProgressableAction.
 * @author Arnaud Blouin
 */
public abstract class ProgressableActionImpl extends ActionImpl implements ProgressableAction {
	protected final DoubleProperty progress;
	protected final StringProperty textProgress;

	public ProgressableActionImpl() {
		super();
		progress = new SimpleDoubleProperty(0d);
		textProgress = new SimpleStringProperty("");
	}

	@Override
	public StringProperty textProgress() {
		return textProgress;
	}

	@Override
	public DoubleProperty progress() {
		return progress;
	}
}
