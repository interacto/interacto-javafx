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
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;

/**
 * Interaction data that contains a serie of points.
 */
public interface PointsData extends InteractionData {
	/**
	 * @return The series of points.
	 */
	List<PointData> getPointsData();

	/**
	 * @return The current position of the pointing device.
	 */
	Point3D getCurrentPosition();

	/**
	 * @return The last button used to produce the points.
	 */
	Optional<MouseButton> getLastButton();
}
