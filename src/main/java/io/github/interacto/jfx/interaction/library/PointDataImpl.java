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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Single point interaction data implementation with write accesses.
 */
class PointDataImpl implements PointData {
	/** The button used for the pressure. */
	protected MouseButton button;

	protected boolean altPressed;

	protected boolean ctrlPressed;

	protected boolean shiftPressed;

	protected boolean metaPressed;

	/** The object picked at the pressed position. */
	protected final ObjectProperty<Node> srcObject;

	/** The pressed local position. */
	protected final ObjectProperty<Point3D> srcLocalPoint;
	/** The pressed scene position. */
	protected final ObjectProperty<Point3D> srcScenePoint;

	/**
	 * Creates the interaction data with no value.
	 */
	protected PointDataImpl() {
		super();
		srcObject = new SimpleObjectProperty<>();
		srcLocalPoint = new SimpleObjectProperty<>();
		srcScenePoint = new SimpleObjectProperty<>();
	}

	@Override
	public void flush() {
		button = null;
		altPressed = false;
		ctrlPressed = false;
		shiftPressed = false;
		metaPressed = false;
		srcObject.set(null);
		srcLocalPoint.set(null);
		srcScenePoint.set(null);
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

	/**
	 * Sets the possible modifiers of this point data used in the mouse event.
	 * @param event The mouse event to use to set this interaction data.
	 */
	protected void setModifiersData(final MouseEvent event) {
		altPressed = event.isAltDown();
		shiftPressed = event.isShiftDown();
		ctrlPressed = event.isControlDown();
		metaPressed = event.isMetaDown();
	}

	/**
	 * Sets the point data (the coordinates, the mouse button and the targeted node).
	 * Key modifiers are not set here.
	 * @param x The x local position
	 * @param y The y local position
	 * @param z The z local position
	 * @param sx The x scene position
	 * @param sy The y scene position
	 * @param b The mouse button
	 * @param obj The targeted object
	 */
	protected void setPointData(final double x, final double y, final double z, final double sx, final double sy,
		final MouseButton b, final Node obj) {
		srcLocalPoint.set(new Point3D(x, y, z));
		srcScenePoint.set(new Point3D(sx, sy, z));
		button = b;
		srcObject.set(obj);
	}

	@Override
	public ObjectProperty<Node> srcObjectProperty() {
		return srcObject;
	}

	@Override
	public ObjectProperty<Point3D> srcLocalPointProperty() {
		return srcLocalPoint;
	}

	@Override
	public ObjectProperty<Point3D> srcScenePointProperty() {
		return srcScenePoint;
	}
}
