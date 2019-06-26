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
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;

public interface PointsData extends InteractionData {
	List<PointData> getPointsData();

	/**
	 * @return The current position of the pointing device.
	 */
	Point3D getCurrentPosition();

	Optional<MouseButton> getLastButton();
}
