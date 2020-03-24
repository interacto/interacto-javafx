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

public class DoubleClick extends JfxInteraction<PointData, DoubleClickFSM> {
	protected final Click firstClick;

	public DoubleClick() {
		this(new DoubleClickFSM());
	}

	protected DoubleClick(final DoubleClickFSM fsm) {
		super(fsm);
		firstClick = new Click(fsm.firstClickFSM);
		fsm.buildFSM(this);
	}

	@Override
	protected PointData createDataObject() {
		return new PointDataImpl();
	}

	@Override
	public void reinitData() {
		super.reinitData();
		firstClick.reinitData();
	}

	@Override
	public PointData getData() {
		return firstClick.getData();
	}
}
