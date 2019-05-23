/*
 * This file is part of Malai.
 * Copyright (c) 2009-2018 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction.library;

import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.CancellingState;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction.ClickTransition;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.FSMDataHandler;
import org.malai.javafx.interaction.JfxFSM;
import org.malai.javafx.interaction.MoveTransition;

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
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof MouseEvent) {
					dataHandler.onMove((MouseEvent) event);
				}
			}
		};
		new MultiClickClickTransition(clicked, clicked);
		new EscapeKeyPressureTransition(clicked, cancelled);
		new ClickTransition(clicked, ended) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return (counterPts + 1) >= minPoints && event instanceof MouseEvent && ((MouseEvent) event).getButton() != MouseButton.PRIMARY;
			}

			@Override
			protected void action(final Event event) {
				if(dataHandler != null && event instanceof MouseEvent) {
					dataHandler.onClick((MouseEvent) event);
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
		protected void action(final Event event) {
			counterPts++;
			if(dataHandler != null && event instanceof MouseEvent) {
				dataHandler.onClick((MouseEvent) event);
			}
		}

		@Override
		protected boolean isGuardOK(final Event event) {
			return super.isGuardOK(event) && event instanceof MouseEvent && ((MouseEvent) event).getButton() == MouseButton.PRIMARY;
		}
	}

	interface MultiClickFSMHandler extends FSMDataHandler {
		void onClick(final MouseEvent event);

		void onMove(final MouseEvent event);
	}
}
