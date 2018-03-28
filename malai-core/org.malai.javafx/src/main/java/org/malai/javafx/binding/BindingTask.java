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
package org.malai.javafx.binding;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import org.malai.command.Command;
import org.malai.javafx.command.ProgressableCmd;

/**
 * A future task to execute a command async.
 * @author Arnaud Blouin
 */
public class BindingTask extends Task<Boolean> {
	private final Command cmd;
	private final ChangeListener<Number> updateProgress;
	private final ChangeListener<String> updateTextProgress;

	public BindingTask(final Command cmd) {
		this.cmd = cmd;
		updateProgress = (observable, oldValue, newValue) -> updateProgress(newValue.doubleValue(), 100d);
		updateTextProgress = (observable, oldValue, newValue) -> updateMessage(newValue);
	}

	@Override
	protected Boolean call() {
		final DoubleProperty progProp = cmd instanceof ProgressableCmd ? ((ProgressableCmd) cmd).progress() : null;
		final StringProperty textProp = cmd instanceof ProgressableCmd ? ((ProgressableCmd) cmd).textProgress() : null;

		if(progProp != null) {
			progProp.addListener(updateProgress);
		}

		if(textProp != null) {
			textProp.addListener(updateTextProgress);
		}

		final boolean ok = cmd.doIt();

		if(progProp != null) {
			progProp.removeListener(updateProgress);
		}

		if(textProp != null) {
			textProp.removeListener(updateTextProgress);
		}

		return ok;
	}
}
