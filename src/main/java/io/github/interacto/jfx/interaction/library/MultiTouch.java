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
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;

public class MultiTouch extends JfxInteraction<MultiTouchData, MultiTouchFSM> {
	private final TouchDnDFSM.TouchDnDFSMHandler handler;

	public MultiTouch(final int nbTouches) {
		super(new MultiTouchFSM(nbTouches));

		handler = new TouchDnDFSM.TouchDnDFSMHandler() {
			@Override
			public void onTouch(final TouchEvent event) {
				final TouchPoint pt = event.getTouchPoint();
				final Point3D pLocal = new Point3D(pt.getX(), pt.getY(), pt.getZ());
				final Point3D pScene = new Point3D(pt.getSceneX(), pt.getSceneY(), pt.getZ());
				((MultiTouchDataImpl) data).addTouchData(new TouchDataImpl(pt.getId(), pLocal, pScene,
					pt.getPickResult().getIntersectedNode(), pLocal, pScene, pt.getPickResult().getIntersectedNode()));
			}

			@Override
			public void onMove(final TouchEvent event) {
				((MultiTouchDataImpl) data).setTouch(event.getTouchPoint());
			}

			@Override
			public void onRelease(final TouchEvent event) {
				((MultiTouchDataImpl) data).setTouch(event.getTouchPoint());
			}

			@Override
			public void reinitData() {
				MultiTouch.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	public boolean isRunning() {
		return isActivated() && fsm.isStarted();
	}

	@Override
	protected MultiTouchData createDataObject() {
		return new MultiTouchDataImpl();
	}
}
