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
package io.interacto.jfx.interaction.library;

import io.interacto.interaction.InteractionData;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

public interface PointData extends InteractionData {
	/**
	 * @return True: the alt key is pressed.
	 */
	boolean isAltPressed();

	/**
	 * @return True: the control key is pressed.
	 */
	boolean isCtrlPressed();

	/**
	 * @return True: the shift key is pressed.
	 */
	boolean isShiftPressed();

	/**
	 * @return True: the meta key is pressed.
	 */
	boolean isMetaPressed();

	/**
	 * @return The pressed local position.
	 */
	Point3D getSrcLocalPoint();

	/**
	 * @return The pressed scene position.
	 */
	Point3D getSrcScenePoint();

	/**
	 * @return The button used for the pressure.
	 */
	MouseButton getButton();

	/**
	 * @return The object picked at the pressed position.
	 */
	Optional<Node> getSrcObject();
}
