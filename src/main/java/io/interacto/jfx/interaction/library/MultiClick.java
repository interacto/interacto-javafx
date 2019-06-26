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
package io.interacto.jfx.interaction.library;

import io.interacto.jfx.interaction.JfxInteraction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MultiClick extends JfxInteraction<PointsData, MultiClickFSM, Event> implements PointsData {
	private final MultiClickFSM.MultiClickFSMHandler handler;
	/** The current position of the pointing device. */
	protected Point3D currentPosition;
	protected final List<PointData> pointsData;

	public MultiClick(final int minPts) {
		super(new MultiClickFSM(minPts));
		pointsData = new ArrayList<>();

		handler = new MultiClickFSM.MultiClickFSMHandler() {
			@Override
			public void onClick(final MouseEvent event) {
				final PointDataImpl data = new PointDataImpl();
				data.setPointData(event);
				pointsData.add(data);
				currentPosition = new Point3D(event.getX(), event.getY(), event.getZ());
			}

			@Override
			public void onMove(final MouseEvent event) {
				currentPosition = new Point3D(event.getX(), event.getY(), event.getZ());
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
	public MultiClickFSM getFsm() {
		return super.getFsm();
	}

	@Override
	public List<PointData> getPointsData() {
		return Collections.unmodifiableList(pointsData);
	}

	@Override
	public Point3D getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public Optional<MouseButton> getLastButton() {
		return pointsData.isEmpty() ? Optional.empty() : Optional.of(pointsData.get(pointsData.size() - 1).getButton());
	}

	@Override
	public void reinitData() {
		super.reinitData();
		pointsData.clear();
		currentPosition = null;
	}

	@Override
	public PointsData getData() {
		return this;
	}
}
