/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction2.library;

import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.malai.javafx.interaction2.JfxInteraction;

public class DoubleClick extends JfxInteraction<DoubleClickFSM, Node> {
	private final Click firstClick;

	public DoubleClick() {
		super(new DoubleClickFSM());

		firstClick = new Click(fsm.firstClickFSM);
		fsm.buildFSM(this);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		firstClick.reinitData();
	}

	/**
	 * @return True: the alt key is pressed.
	 */
	public boolean isAltPressed() {
		return firstClick.isAltPressed();
	}

	/**
	 * @return True: the control key is pressed.
	 */

	public boolean isCtrlPressed() {
		return firstClick.isCtrlPressed();
	}

	/**
	 * @return True: the shift key is pressed.
	 */

	public boolean isShiftPressed() {
		return firstClick.isShiftPressed();
	}

	/**
	 * @return True: the meta key is pressed.
	 */

	public boolean isMetaPressed() {
		return firstClick.isMetaPressed();
	}

	/**
	 * @return The pressed local position.
	 */
	public Point3D getSrcLocalPoint() {
		return firstClick.getSrcLocalPoint();
	}

	/**
	 * @return The pressed scene position.
	 */
	public Point3D getSrcScenePoint() {
		return firstClick.getSrcScenePoint();
	}

	/**
	 * @return The button used for the pressure.
	 */
	public MouseButton getButton() {
		return firstClick.getButton();
	}

	/**
	 * @return The object picked at the pressed position.
	 */
	public Optional<Node> getSrcObject() {
		return firstClick.getSrcObject();
	}

	public ReadOnlyObjectProperty<Point3D> srcLocalPointProperty() {
		return firstClick.srcLocalPointProperty();
	}

	public ReadOnlyObjectProperty<Point3D> srcScenePointProperty() {
		return firstClick.srcScenePointProperty();
	}

	public ReadOnlyObjectProperty<Node> srcObjectProperty() {
		return firstClick.srcObjectProperty();
	}
}
