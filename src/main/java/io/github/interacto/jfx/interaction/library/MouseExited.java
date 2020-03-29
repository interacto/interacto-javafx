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
import javafx.scene.input.MouseEvent;

/**
 * A user interaction just composed of a mouse exit transition.
 */
public class MouseExited extends JfxInteraction<PointData, MouseExitedFSM> {
	private final MouseExitedFSM.ExitFSMHandler handler;

	/**
	 * Creates the user interaction.
	 */
	public MouseExited() {
		super(new MouseExitedFSM());

		handler = new MouseExitedFSM.ExitFSMHandler() {
			@Override
			public void initToExit(final MouseEvent evt) {
				((PointDataImpl) data).setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(),
					evt.getButton(), evt.getPickResult().getIntersectedNode());
			}

			@Override
			public void reinitData() {
				MouseExited.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected PointData createDataObject() {
		return new PointDataImpl();
	}
}
