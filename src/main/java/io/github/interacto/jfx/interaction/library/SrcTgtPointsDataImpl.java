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

import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;

public class SrcTgtPointsDataImpl extends PointDataImpl implements SrcTgtPointsData {
	/** The ending local point of the dnd. */
	protected final ObjectProperty<Point3D> tgtLocalPt;
	/** The ending scene point of the dnd. */
	protected final ObjectProperty<Point3D> tgtScenePt;
	/** The object picked at the beginning of the dnd. */
	protected final ObjectProperty<Node> tgtObject;

	public SrcTgtPointsDataImpl() {
		super();
		tgtLocalPt = new SimpleObjectProperty<>();
		tgtScenePt = new SimpleObjectProperty<>();
		tgtObject = new SimpleObjectProperty<>();
	}

	@Override
	public Optional<Node> getTgtObject() {
		return Optional.ofNullable(tgtObject.get());
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> tgtLocalPointProperty() {
		return tgtLocalPt;
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> tgtScenePointProperty() {
		return tgtScenePt;
	}

	@Override
	public ReadOnlyObjectProperty<Node> tgtObjectProperty() {
		return tgtObject;
	}

	@Override
	public Point3D getTgtLocalPoint() {
		return tgtLocalPt.get();
	}

	@Override
	public Point3D getTgtScenePoint() {
		return tgtScenePt.get();
	}

	protected void setTgtData(final double x, final double y, final double z, final double sx, final double sy, final Node obj) {
		tgtLocalPt.set(new Point3D(x, y, z));
		tgtScenePt.set(new Point3D(sx, sy, z));
		tgtObject.set(obj);
	}

	@Override
	public void flush() {
		super.flush();
		tgtLocalPt.set(null);
		tgtObject.set(null);
		tgtScenePt.set(null);
	}
}
