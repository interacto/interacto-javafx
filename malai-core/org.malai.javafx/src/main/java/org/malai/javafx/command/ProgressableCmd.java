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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import org.malai.command.Command;

/**
 * Commands can notify about their execution progression.
 * @author Arnaud Blouin
 */
public interface ProgressableCmd extends Command {
	/**
	 * @return The progress rate [0;100].
	 */
	DoubleProperty progress();

	/**
	 * @return The text to explain the execution all along it.
	 */
	StringProperty textProgress();
}
