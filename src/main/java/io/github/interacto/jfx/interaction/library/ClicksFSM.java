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
import io.github.interacto.fsm.StdState;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.fsm.TimeoutTransition;
import io.github.interacto.jfx.interaction.ClickTransition;
import io.github.interacto.jfx.interaction.JfxFSM;
import javafx.scene.input.MouseEvent;

/**
 * The FSM for the clicks interaction
 */
public class ClicksFSM extends JfxFSM<Clicks.ClicksFSMHandler> {
	private int countClicks;

	private final int nbClicks;

	/**
	 * Creates the Clicks FSM
	 */
	public ClicksFSM(final int nbClicks) {
		super();

		if(nbClicks <= 0) {
			throw new IllegalArgumentException("The number of clicks must be greater than 1");
		}

		if(nbClicks == 1) {
			throw new IllegalArgumentException("For a number of clicks that equals 1, use the Click interaction");
		}

		this.nbClicks = nbClicks;
		this.countClicks = 0;
	}

	@Override
	public void buildFSM(final Clicks.ClicksFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}

		super.buildFSM(dataHandler);

		final var clicked = new StdState<>(this, "clicked");
		final var ended = new TerminalState<>(this, "ended");
		final var timeouted = new CancellingState<>(this, "timeouted");
		this.addState(clicked);
		this.addState(ended);
		this.addState(timeouted);

		new ClickTransition(initState, clicked) {
			@Override
			protected void action(final MouseEvent event) {
				ClicksFSM.this.countClicks++;
				dataHandler.click(event);
			}
		};

		new ClickTransition(clicked, clicked) {
			@Override
			protected void action(final MouseEvent event) {
				ClicksFSM.this.countClicks++;
				dataHandler.click(event);
			}

			@Override
			protected boolean isGuardOK(final MouseEvent event) {
				return (ClicksFSM.this.countClicks + 1) < ClicksFSM.this.nbClicks;
			}
		};

		new ClickTransition(clicked, ended) {
			@Override
			protected void action(final MouseEvent event) {
				dataHandler.click(event);
			}

			@Override
			protected boolean isGuardOK(final MouseEvent event) {
				return (ClicksFSM.this.countClicks + 1) == ClicksFSM.this.nbClicks;
			}
		};

		new TimeoutTransition<>(clicked, timeouted, () -> 1000);
	}
}
