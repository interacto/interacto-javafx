/*
 * Interacto
 * Copyright (C) 2019 Arnaud Blouin
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.interacto.jfx.command;

import io.interacto.command.CommandImpl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A base implementation of ProgressableAction.
 * @author Arnaud Blouin
 */
public abstract class ProgressableCmdImpl extends CommandImpl implements ProgressableCmd {
	protected final DoubleProperty progress;
	protected final StringProperty textProgress;

	public ProgressableCmdImpl() {
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
