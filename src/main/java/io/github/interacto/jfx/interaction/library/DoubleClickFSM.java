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
import io.github.interacto.fsm.SubFSMTransition;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.fsm.TimeoutTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.MoveTransition;
import java.util.function.LongSupplier;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DoubleClickFSM extends JfxFSM<FSMDataHandler> {
	/** The time gap between the two spinner events. */
	private static long timeGap = 300;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap between the two spinner events.
	 * @param timeGapBetweenClicks The time gap between the two spinner events. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	protected final ClickFSM firstClickFSM;
	private final ClickFSM sndClick;
	private MouseButton checkButton;

	public DoubleClickFSM() {
		super();
		firstClickFSM = new ClickFSM();
		sndClick = new ClickFSM();
	}

	@Override
	protected void buildFSM(final FSMDataHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);
		firstClickFSM.buildFSM(null);
		sndClick.buildFSM(null);
		final TerminalState<Event> dbleclicked = new TerminalState<>(this, "dbleclicked");
		final CancellingState<Event> cancelled = new CancellingState<>(this, "cancelled");
		final StdState<Event> clicked = new StdState<>(this, "clicked");

		addState(clicked);
		addState(dbleclicked);
		addState(cancelled);
		startingState = dbleclicked;

		new SubFSMTransition<>(initState, clicked, firstClickFSM) {
			@Override
			protected void action(final Event event) {
				setCheckButton(firstClickFSM.getCheckButton());
			}
		};
		new MoveTransition(clicked, cancelled) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return super.isGuardOK(event) && (checkButton == null || event instanceof MouseEvent && ((MouseEvent) event).getButton() == checkButton);
			}
		};
		new TimeoutTransition<>(clicked, cancelled, SUPPLY_TIME_GAP);
		new SubFSMTransition<>(clicked, dbleclicked, sndClick);
	}

	protected void setCheckButton(final MouseButton buttonToCheck) {
		if(checkButton == null) {
			checkButton = buttonToCheck;
		}
		sndClick.setCheckButton(buttonToCheck);
	}

	protected MouseButton getCheckButton() {
		return checkButton;
	}

	@Override
	public void reinit() {
		super.reinit();
		checkButton = null;
	}
}
