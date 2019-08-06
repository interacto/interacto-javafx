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
package io.github.interacto.jfx.command;

import io.github.interacto.command.Command;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

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
