/*
 * Interacto
 * Copyright (C) 2020 Arnaud Blouin
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
import javafx.scene.Cursor;
import javafx.scene.Node;

public class ChangeCursor extends CommandImpl {
	private final Cursor cursor;
	private final Node node;

	public ChangeCursor(final Cursor cursor, final Node node) {
		super();
		this.cursor = cursor;
		this.node = node;
	}

	@Override
	protected void doCmdBody() {
		node.setCursor(cursor);
	}

	@Override
	public boolean canDo() {
		return cursor != null && node != null;
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.NONE;
	}
}
