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

public class Click extends JfxInteraction<PointData, ClickFSM> {
	private final ClickFSM.ClickFSMHandler handler;

	protected Click(final ClickFSM fsm) {
		super(fsm);

		handler = new ClickFSM.ClickFSMHandler() {
			@Override
			public void initToClicked(final MouseEvent evt) {
				((PointDataImpl) data).setPointData(evt.getX(), evt.getY(), evt.getZ(), evt.getSceneX(), evt.getSceneY(),
					evt.getButton(), evt.getPickResult().getIntersectedNode());
				((PointDataImpl) data).setModifiersData(evt);
			}

			@Override
			public void reinitData() {
				Click.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	protected PointData createDataObject() {
		return new PointDataImpl();
	}

	public Click() {
		this(new ClickFSM());
	}
}
