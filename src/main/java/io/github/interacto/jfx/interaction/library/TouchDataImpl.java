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

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;

public class TouchDataImpl extends SrcTgtPointsDataImpl implements TouchData {
	int touchID;

	public TouchDataImpl() {
		this(-1, null, null, null, null, null, null);
	}

	public TouchDataImpl(final int touchID, final Point3D tgtLocalPt, final Point3D tgtScenePt,
		final Node tgtObj, final Point3D srcLocalPt, final Point3D srcScenePt, final Node srcObj) {
		super();
		this.touchID = touchID;
		this.tgtLocalPt.set(tgtLocalPt);
		this.tgtScenePt.set(tgtScenePt);
		this.tgtObject.set(tgtObj);
		this.srcLocalPoint.set(srcLocalPt);
		this.srcScenePoint.set(srcScenePt);
		this.srcObject.set(srcObj);
	}

	public void setTouchID(final int touchID) {
		this.touchID = touchID;
	}

	@Override
	public int getTouchId() {
		return touchID;
	}

	@Override
	public void flush() {
		super.flush();
		touchID = -1;
	}

	@Override
	public MouseButton getButton() {
		return MouseButton.NONE;
	}

	@Override
	public boolean isAltPressed() {
		return false;
	}

	@Override
	public boolean isCtrlPressed() {
		return false;
	}

	@Override
	public boolean isShiftPressed() {
		return false;
	}

	@Override
	public boolean isMetaPressed() {
		return false;
	}
}
