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

import io.github.interacto.fsm.CancellingState;
import io.github.interacto.fsm.InputState;
import io.github.interacto.fsm.OutputState;
import io.github.interacto.fsm.StdState;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.ClickTransition;
import io.github.interacto.jfx.interaction.EscapeKeyPressureTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.MoveTransition;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MultiClickFSM extends JfxFSM<MultiClickFSM.MultiClickFSMHandler> {
	/** The minimum number of points that the interaction must gather. */
	protected int minPoints;
	private int counterPts;

	public MultiClickFSM(final int minPoints) {
		super();
		this.minPoints = 2;
		setMinPoints(minPoints);
		counterPts = 0;
	}

	@Override
	protected void buildFSM(final MultiClickFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);

		final StdState<Event> clicked = new StdState<>(this, "clicked");
		final TerminalState<Event> ended = new TerminalState<>(this, "ended");
		final CancellingState<Event> cancelled = new CancellingState<>(this, "cancelled");

		addState(clicked);
		addState(ended);
		addState(cancelled);

		new MultiClickClickTransition(initState, clicked);
		new MoveTransition(clicked, clicked) {
			@Override
			protected void action(final MouseEvent event) {
				if(dataHandler != null) {
					dataHandler.onMove(event);
				}
			}
		};
		new MultiClickClickTransition(clicked, clicked);
		new EscapeKeyPressureTransition(clicked, cancelled);
		new ClickTransition(clicked, ended) {
			@Override
			protected boolean isGuardOK(final MouseEvent event) {
				return (counterPts + 1) >= minPoints && event.getButton() != MouseButton.PRIMARY;
			}

			@Override
			protected void action(final MouseEvent event) {
				if(dataHandler != null) {
					dataHandler.onClick(event);
				}
			}
		};
	}

	@Override
	public void reinit() {
		super.reinit();
		counterPts = 0;
	}

	/**
	 * @return The minimum number of points that the interaction must gather.
	 */
	public int getMinPoints() {
		return minPoints;
	}

	/**
	 * @param minPoints The minimum number of points that the interaction must gather. Must be greater than 0.
	 */
	public void setMinPoints(final int minPoints) {
		if(minPoints > 0) {
			this.minPoints = minPoints;
		}
	}

	class MultiClickClickTransition extends ClickTransition {
		MultiClickClickTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected void action(final MouseEvent event) {
			counterPts++;
			if(dataHandler != null) {
				dataHandler.onClick(event);
			}
		}

		@Override
		protected boolean isGuardOK(final MouseEvent event) {
			return super.isGuardOK(event) && event.getButton() == MouseButton.PRIMARY;
		}
	}

	interface MultiClickFSMHandler extends FSMDataHandler {
		void onClick(final MouseEvent event);

		void onMove(final MouseEvent event);
	}
}
