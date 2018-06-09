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

import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import org.malai.interaction.InteractionData;

public interface PointsData extends InteractionData {
	List<PointData> getPointsData();

	/**
	 * @return The current position of the pointing device.
	 */
	Point3D getCurrentPosition();

	Optional<MouseButton> getLastButton();
}
