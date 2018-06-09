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
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.malai.interaction.InteractionData;

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
