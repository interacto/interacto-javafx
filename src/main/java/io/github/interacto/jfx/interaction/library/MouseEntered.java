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

import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class MouseEntered extends PointInteraction<FullPointData, MouseEnteredFSM, Event> {
	private final MouseEnteredFSM.EnterFSMHandler handler;

	public MouseEntered() {
		super(new MouseEnteredFSM());

		handler = new MouseEnteredFSM.EnterFSMHandler() {
			@Override
			public void initToEnter(final MouseEvent event) {
				setPointData(event);
			}

			@Override
			public void reinitData() {
				MouseEntered.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	@Override
	public FullPointData getData() {
		return this;
	}
}
