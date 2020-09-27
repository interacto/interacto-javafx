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

import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.ClickTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The FSM for click interactions
 */
public class ClickFSM extends JfxFSM<ClickFSM.ClickFSMHandler> {
	private MouseButton checkButton;

	/**
	 * Creates the FSM
	 */
	public ClickFSM() {
		super();
	}

	@Override
	protected void buildFSM(final ClickFSM.ClickFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		final TerminalState<Event> clicked = new TerminalState<>(this, "clicked");
		addState(clicked);
		new ClickTransition(initState, clicked) {
			@Override
			public void action(final MouseEvent event) {
				setCheckButton(event.getButton());

				if(dataHandler != null) {
					dataHandler.initToClicked(event);
				}
			}

			@Override
			protected boolean isGuardOK(final MouseEvent event) {
				return super.isGuardOK(event) && checkButton == null || event.getButton() == checkButton;
			}
		};
	}

	protected MouseButton getCheckButton() {
		return checkButton;
	}

	protected void setCheckButton(final MouseButton buttonToCheck) {
		if(checkButton == null) {
			checkButton = buttonToCheck;
		}
	}

	@Override
	public void reinit() {
		super.reinit();
		checkButton = null;
	}

	interface ClickFSMHandler extends FSMDataHandler {
		void initToClicked(final MouseEvent event);
	}
}
