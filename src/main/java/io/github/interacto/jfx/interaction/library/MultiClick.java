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

import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;

/**
 * The multi-click user interaction.
 * A user can click at several positions with the primary button
 * of a mouse. Ends by clicking with a different mouse button.
 */
public class MultiClick extends JfxInteraction<PointsData, MultiClickFSM> {
	private final MultiClickFSM.MultiClickFSMHandler handler;

	/**
	 * Creates the user interaction
	 * @param minPts The minimal number of clicks for ending normally the user interaction.
	 */
	public MultiClick(final int minPts) {
		super(new MultiClickFSM(minPts));

		handler = new MultiClickFSM.MultiClickFSMHandler() {
			@Override
			public void onClick(final MouseEvent evt) {
				final PointDataImpl ptData = new PointDataImpl();
				ptData.setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(),
					evt.getButton(), evt.getPickResult().getIntersectedNode());
				((PointsDataImpl) data).addPoint(ptData);
				((PointsDataImpl) data).setCurrentPosition(new Point3D(evt.getX(), evt.getY(), evt.getZ()));
			}

			@Override
			public void onMove(final MouseEvent event) {
				((PointsDataImpl) data).setCurrentPosition(new Point3D(event.getX(), event.getY(), event.getZ()));
			}

			@Override
			public void reinitData() {
				MultiClick.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	public MultiClick() {
		this(2);
	}

	@Override
	protected PointsData createDataObject() {
		return new PointsDataImpl();
	}
}
