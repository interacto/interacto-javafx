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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.FSM;
import org.malai.javafx.interaction.JfxInteraction;

public abstract class PointInteraction<D extends FullPointData, F extends FSM<Event>, T> extends JfxInteraction<D, F, T> implements FullPointData {
	/** The pressed local position. */
	protected final ObjectProperty<Point3D> srcLocalPoint;
	/** The pressed scene position. */
	protected final ObjectProperty<Point3D> srcScenePoint;
	protected final PointDataImpl pointData;


	/** The object picked at the pressed position. */
	protected final ObjectProperty<Node> srcObject;

	public PointInteraction(final F fsm) {
		super(fsm);
		srcLocalPoint = new SimpleObjectProperty<>();
		srcScenePoint = new SimpleObjectProperty<>();
		srcObject = new SimpleObjectProperty<>();
		pointData = new PointDataImpl();
	}

	@Override
	public void reinitData() {
		super.reinitData();
		srcLocalPoint.set(null);
		srcScenePoint.set(null);
		srcObject.set(null);
		pointData.reinitData();
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> srcLocalPointProperty() {
		return srcLocalPoint;
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> srcScenePointProperty() {
		return srcScenePoint;
	}

	@Override
	public ReadOnlyObjectProperty<Node> srcObjectProperty() {
		return srcObject;
	}

	protected void setPointData(final MouseEvent event) {
		srcLocalPoint.set(new Point3D(event.getX(), event.getY(), event.getZ()));
		srcScenePoint.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
		srcObject.set(event.getPickResult().getIntersectedNode());
		pointData.setPointData(event);
	}

	@Override
	public boolean isAltPressed() {
		return pointData.isAltPressed();
	}

	@Override
	public boolean isCtrlPressed() {
		return pointData.isCtrlPressed();
	}

	@Override
	public boolean isShiftPressed() {
		return pointData.isShiftPressed();
	}

	@Override
	public boolean isMetaPressed() {
		return pointData.isMetaPressed();
	}

	@Override
	public MouseButton getButton() {
		return pointData.getButton();
	}
}
