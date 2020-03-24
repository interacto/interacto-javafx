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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;

public class PointsDataImpl extends PointDataImpl implements PointsData {
	/** The current position of the pointing device. */
	protected Point3D currentPosition;
	protected final List<PointData> pointsData;

	public PointsDataImpl() {
		super();
		pointsData = new ArrayList<>();
	}

	@Override
	public List<PointData> getPointsData() {
		return Collections.unmodifiableList(pointsData);
	}

	@Override
	public Point3D getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(final Point3D currentPosition) {
		this.currentPosition = currentPosition;
	}

	@Override
	public Optional<MouseButton> getLastButton() {
		return pointsData.isEmpty() ? Optional.empty() : Optional.of(pointsData.get(pointsData.size() - 1).getButton());
	}

	public void addPoint(final PointData ptData) {
		pointsData.add(ptData);
	}

	@Override
	public void flush() {
		pointsData.clear();
		currentPosition = null;
	}
}
