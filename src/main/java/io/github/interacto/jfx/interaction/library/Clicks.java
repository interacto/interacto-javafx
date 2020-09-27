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

import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxInteraction;
import javafx.scene.input.MouseEvent;

public class Clicks extends JfxInteraction<PointsData, ClicksFSM> {
	private final ClicksFSMHandler handler;

	/**
	 * Creates the clicks interaction
	 * @param numberClicks The number of clicks expected to end the interaction.
	 *					If this number is not reached after a timeout, the interaction is cancelled.
	 */
	public Clicks(final int numberClicks) {
		super(new ClicksFSM(numberClicks));

		this.handler = new ClicksFSMHandler() {
			@Override
			public void click(final MouseEvent evt) {
				final PointDataImpl pt = new PointDataImpl();
				pt.setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(), evt.getButton(), evt.getPickResult().getIntersectedNode());
				pt.setModifiersData(evt);
				((PointsDataImpl) data).addPoint(pt);
			}

			@Override
			public void reinitData() {
				Clicks.this.reinitData();
			}
		};

		this.getFsm().buildFSM(this.handler);
	}

	@Override
	protected PointsData createDataObject() {
		return new PointsDataImpl();
	}

	interface ClicksFSMHandler extends FSMDataHandler {
		void click(final MouseEvent evt);
	}
}
