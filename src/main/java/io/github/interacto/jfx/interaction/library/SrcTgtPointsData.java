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

import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;

public interface SrcTgtPointsData extends PointData {
	/**
	 * @return The object picked at the target location.
	 */
	Optional<Node> getTgtObject();

	ReadOnlyObjectProperty<Point3D> tgtLocalPointProperty();

	ReadOnlyObjectProperty<Point3D> tgtScenePointProperty();

	ReadOnlyObjectProperty<Node> tgtObjectProperty();

	/**
	 * @return The target position.
	 */
	Point3D getTgtLocalPoint();

	/**
	 * @return The target scene position.
	 */
	Point3D getTgtScenePoint();
}
