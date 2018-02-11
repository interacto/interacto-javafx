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
import org.malai.fsm.SubFSMTransition;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction.EscapeKeyPressureTransition;
import org.malai.javafx.interaction.FSMDataHandler;
import org.malai.javafx.interaction.JfxFSM;
import org.malai.javafx.interaction.MoveTransition;

public class DragLockFSM extends JfxFSM<DragLockFSM.DragLockFSMHandler> {
	protected final DoubleClickFSM firstDbleClick;
	protected final DoubleClickFSM sndDbleClick;
	protected MouseButton checkButton;

	public DragLockFSM() {
		super();
		firstDbleClick = new DoubleClickFSM();
		sndDbleClick = new DoubleClickFSM();
	}

	@Override
	protected void buildFSM(final DragLockFSMHandler dataHandler) {
		if(states.size() > 1) return;
		super.buildFSM(dataHandler);
		final DoubleClickFSM cancelDbleClick = new DoubleClickFSM();
		firstDbleClick.buildFSM(null);
		sndDbleClick.buildFSM(null);
		cancelDbleClick.buildFSM(null);
		final TerminalState<Event> dropped = new TerminalState<>(this, "dropped");
		final CancellingState<Event> cancelled = new CancellingState<>(this, "cancelled");
		final StdState<Event> locked = new StdState<>(this, "locked");
		final StdState<Event> moved = new StdState<>(this, "moved");

		addState(dropped);
		addState(cancelled);
		addState(locked);
		addState(moved);

		new SubFSMTransition<Event>(initState, locked, firstDbleClick) {
			@Override
			protected void action(final Event event) {
				checkButton = firstDbleClick.getCheckButton();
				sndDbleClick.setCheckButton(checkButton);
				cancelDbleClick.setCheckButton(checkButton);
			}
		};
		new SubFSMTransition<>(locked, cancelled, cancelDbleClick);
		new DragLockMoveTransition(locked, moved);
		new EscapeKeyPressureTransition(locked, cancelled);
		new EscapeKeyPressureTransition(moved, cancelled);
		new SubFSMTransition<>(moved, dropped, sndDbleClick);
	}

	class DragLockMoveTransition extends MoveTransition {
		public DragLockMoveTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected boolean isGuardOK(final Event event) {
			return super.isGuardOK(event) && (checkButton == null || event instanceof MouseEvent && ((MouseEvent) event).getButton() == checkButton);
		}

		@Override
		protected void action(final Event event) {
			if(dataHandler != null && event instanceof MouseEvent) {
				dataHandler.onMove((MouseEvent) event);
			}
		}
	}

	interface DragLockFSMHandler extends FSMDataHandler {
		void onMove(final MouseEvent event);
	}
}
