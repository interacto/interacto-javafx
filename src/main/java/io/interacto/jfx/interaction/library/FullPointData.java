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

import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;

public interface FullPointData extends PointData {
	ReadOnlyObjectProperty<Point3D> srcLocalPointProperty();

	ReadOnlyObjectProperty<Point3D> srcScenePointProperty();

	ReadOnlyObjectProperty<Node> srcObjectProperty();

	@Override
	default Point3D getSrcLocalPoint() {
		return srcLocalPointProperty().get();
	}

	@Override
	default Point3D getSrcScenePoint() {
		return srcScenePointProperty().get();
	}

	@Override
	default Optional<Node> getSrcObject() {
		return Optional.ofNullable(srcObjectProperty().get());
	}
}
