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
import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

/**
 * Interaction data for a single pointing device at a single position.
 */
public interface PointData extends InteractionData, ModifierData {
	/**
	 * @return The pressed local position.
	 */
	default Point3D getSrcLocalPoint() {
		return srcLocalPointProperty().get();
	}

	/**
	 * @return The pressed scene position.
	 */
	default Point3D getSrcScenePoint() {
		return srcScenePointProperty().get();
	}

	/**
	 * @return The button used for the pressure.
	 */
	MouseButton getButton();

	/**
	 * @return The object picked at the pressed position.
	 */
	default Optional<Node> getSrcObject() {
		return Optional.ofNullable(srcObjectProperty().get());
	}

	/**
	 * @return An observable property for the source object.
	 */
	ReadOnlyObjectProperty<Node> srcObjectProperty();

	/**
	 * @return An observable property for the source local position.
	 */
	ReadOnlyObjectProperty<Point3D> srcLocalPointProperty();

	/**
	 * @return An observable property for the source scene position.
	 */
	ReadOnlyObjectProperty<Point3D> srcScenePointProperty();
}
