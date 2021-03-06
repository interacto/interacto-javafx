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

import io.github.interacto.interaction.InteractionData;

public interface ModifierData extends InteractionData {
	boolean isShortcutDown();

	/**
	 * @return True: the alt key is pressed.
	 */
	boolean isAltDown();

	/**
	 * @return True: the control key is pressed.
	 */
	boolean isCtrlDown();

	/**
	 * @return True: the shift key is pressed.
	 */
	boolean isShiftDown();

	/**
	 * @return True: the meta key is pressed.
	 */
	boolean isMetaDown();
}
