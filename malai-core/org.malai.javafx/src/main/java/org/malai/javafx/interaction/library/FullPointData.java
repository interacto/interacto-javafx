/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

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
