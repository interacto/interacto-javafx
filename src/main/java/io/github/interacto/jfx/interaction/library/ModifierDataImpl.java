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
package io.github.interacto.jfx.interaction.library;

public abstract class ModifierDataImpl implements ModifierData {
	protected boolean altDown;

	protected boolean ctrlDown;

	protected boolean shiftDown;

	protected boolean metaDown;

	protected boolean shortcutDown;

	@Override
	public boolean isAltDown() {
		return altDown;
	}

	@Override
	public boolean isCtrlDown() {
		return ctrlDown;
	}

	@Override
	public boolean isShiftDown() {
		return shiftDown;
	}

	@Override
	public boolean isMetaDown() {
		return metaDown;
	}

	@Override
	public boolean isShortcutDown() {
		return shortcutDown;
	}

	@Override
	public void flush() {
		altDown = false;
		ctrlDown = false;
		shiftDown = false;
		metaDown = false;
		shortcutDown = false;
	}
}
