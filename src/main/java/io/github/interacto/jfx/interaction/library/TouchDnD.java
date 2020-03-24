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
import javafx.scene.input.MouseButton;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;

/**
 * A touch interaction (that works as a DnD)
 */
public class TouchDnD extends JfxInteraction<TouchData, TouchDnDFSM> {
	private final TouchDnDFSM.TouchDnDFSMHandler handler;

	/**
	 * Creates the interaction
	 */
	public TouchDnD() {
		super(new TouchDnDFSM());

		handler = new TouchDnDFSM.TouchDnDFSMHandler() {
			@Override
			public void onTouch(final TouchEvent evt) {
				final TouchPoint pt = evt.getTouchPoint();
				((TouchDataImpl) data).setPointData(pt.getX(), pt.getY(), pt.getZ(), pt.getSceneX(), pt.getSceneY(),
					MouseButton.NONE, pt.getPickResult().getIntersectedNode());
				setTgtData(evt.getTouchPoint());
				((TouchDataImpl) data).setTouchID(pt.getId());
			}

			@Override
			public void onMove(final TouchEvent evt) {
				setTgtData(evt.getTouchPoint());
			}

			@Override
			public void onRelease(final TouchEvent evt) {
				setTgtData(evt.getTouchPoint());
			}

			@Override
			public void reinitData() {
				TouchDnD.this.reinitData();
			}

			private void setTgtData(final TouchPoint pt) {
				((TouchDataImpl) data).setTgtData(pt.getX(), pt.getY(), pt.getZ(), pt.getSceneX(), pt.getSceneY(),
					pt.getPickResult().getIntersectedNode());
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected TouchData createDataObject() {
		return new TouchDataImpl();
	}
}
