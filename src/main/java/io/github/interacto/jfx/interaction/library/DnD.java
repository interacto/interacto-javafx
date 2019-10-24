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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DnD extends PointInteraction<SrcTgtPointsData, DnDFSM, Node> implements SrcTgtPointsData {
	private final DnDFSM.DnDFSMHandler handler;
	/** The ending local point of the dnd. */
	protected final ObjectProperty<Point3D> tgtLocalPt;
	/** The ending scene point of the dnd. */
	protected final ObjectProperty<Point3D> tgtScenePt;
	/** The object picked at the beginning of the dnd. */
	protected final ObjectProperty<Node> tgtObject;

	public DnD(final boolean updateSrcOnUpdate, final boolean cancellable) {
		super(new DnDFSM(cancellable));

		tgtLocalPt = new SimpleObjectProperty<>();
		tgtScenePt = new SimpleObjectProperty<>();
		tgtObject = new SimpleObjectProperty<>();

		handler = new DnDFSM.DnDFSMHandler() {
			@Override
			public void onPress(final MouseEvent event) {
				setPointData(event);
				setTgtData(event);
			}

			@Override
			public void onDrag(final MouseEvent event) {
				if(updateSrcOnUpdate) {
					srcLocalPoint.set(tgtLocalPt.get());
					srcScenePoint.set(tgtScenePt.get());
					srcObject.set(tgtObject.get());
				}

				setTgtData(event);
			}

			@Override
			public void onRelease(final MouseEvent event) {
				setTgtData(event);
			}

			@Override
			public void reinitData() {
				DnD.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	public DnD() {
		this(false, false);
	}

	protected void setTgtData(final MouseEvent event) {
		tgtLocalPt.set(new Point3D(event.getX(), event.getY(), event.getZ()));
		tgtScenePt.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
		tgtObject.set(event.getPickResult().getIntersectedNode());
		pointData.setModifiersData(event);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		tgtLocalPt.setValue(null);
		tgtScenePt.setValue(null);
		tgtObject.set(null);
	}

	@Override
	public SrcTgtPointsData getData() {
		return this;
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
}
