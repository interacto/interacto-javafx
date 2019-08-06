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
package io.github.interacto.jfx.interaction.library;

import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

class PointDataImpl implements PointData {
	/** The button used for the pressure. */
	protected MouseButton button;

	protected boolean altPressed;

	protected boolean ctrlPressed;

	protected boolean shiftPressed;

	protected boolean metaPressed;

	/** The object picked at the pressed position. */
	protected Node srcObject;

	/** The pressed local position. */
	protected Point3D srcLocalPoint;
	/** The pressed scene position. */
	protected Point3D srcScenePoint;


	protected PointDataImpl() {
	}

	protected void reinitData() {
		button = null;
		altPressed = false;
		ctrlPressed = false;
		shiftPressed = false;
		metaPressed = false;
	}

	@Override
	public boolean isAltPressed() {
		return altPressed;
	}

	@Override
	public boolean isCtrlPressed() {
		return ctrlPressed;
	}

	@Override
	public boolean isShiftPressed() {
		return shiftPressed;
	}

	@Override
	public boolean isMetaPressed() {
		return metaPressed;
	}

	@Override
	public MouseButton getButton() {
		return button;
	}

	protected void setModifiersData(final MouseEvent event) {
		altPressed = event.isAltDown();
		shiftPressed = event.isShiftDown();
		ctrlPressed = event.isControlDown();
		metaPressed = event.isMetaDown();
	}

	protected void setPointData(final MouseEvent event) {
		srcLocalPoint = new Point3D(event.getX(), event.getY(), event.getZ());
		srcScenePoint = new Point3D(event.getSceneX(), event.getSceneY(), event.getZ());
		button = event.getButton();
		srcObject = event.getPickResult().getIntersectedNode();
		setModifiersData(event);
	}

	@Override
	public Point3D getSrcLocalPoint() {
		return srcLocalPoint;
	}

	@Override
	public Point3D getSrcScenePoint() {
		return srcScenePoint;
	}

	@Override
	public Optional<Node> getSrcObject() {
		return Optional.ofNullable(srcObject);
	}
}
