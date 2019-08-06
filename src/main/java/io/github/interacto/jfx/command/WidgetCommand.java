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

import io.github.interacto.command.CommandImpl;

/**
 * Defines an abstract command that concerns a widget.
 * @author Arnaud BLOUIN
 */
public abstract class WidgetCommand<T> extends CommandImpl {
	/** The component concerned by the command. */
	protected T widget;

	/**
	 * Creates the command.
	 */
	public WidgetCommand() {
		super();
	}


	@Override
	public boolean canDo() {
		return widget != null;
	}


	/**
	 * @param n The component concerned by the command.
	 * @since 0.2
	 */
	public void setWidget(final T n) {
		this.widget = n;
	}


	@Override
	public void flush() {
		super.flush();
		widget = null;
	}
}
