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
import io.github.interacto.fsm.SubFSMTransition;
import io.github.interacto.fsm.TerminalState;
import io.github.interacto.jfx.interaction.EscapeKeyPressureTransition;
import io.github.interacto.jfx.interaction.FSMDataHandler;
import io.github.interacto.jfx.interaction.JfxFSM;
import io.github.interacto.jfx.interaction.MoveTransition;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DragLockFSM extends JfxFSM<DragLockFSM.DragLockFSMHandler> {
	private static class DragLockMoveTransition extends MoveTransition {
		final DragLockFSMHandler dataHandler;

		DragLockMoveTransition(final OutputState<Event> srcState, final InputState<Event> tgtState, final DragLockFSMHandler dataHandler) {
			super(srcState, tgtState);
			this.dataHandler = dataHandler;
		}

		@Override
		protected void action(final Event event) {
			if(dataHandler != null && event instanceof MouseEvent) {
				dataHandler.onMove((MouseEvent) event);
			}
		}
	}

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
		if(states.size() > 1) {
			return;
		}
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

		new SubFSMTransition<>(initState, locked, firstDbleClick) {
			@Override
			protected void action(final Event event) {
				checkButton = firstDbleClick.getCheckButton();
				sndDbleClick.setCheckButton(checkButton);
				cancelDbleClick.setCheckButton(checkButton);
				if(dataHandler != null) {
					dataHandler.onLock();
				}
			}
		};
		new SubFSMTransition<>(locked, cancelled, cancelDbleClick);
		new DragLockMoveTransition(locked, moved, dataHandler);
		new DragLockMoveTransition(moved, moved, dataHandler);
		new EscapeKeyPressureTransition(locked, cancelled);
		new EscapeKeyPressureTransition(moved, cancelled);
		new SubFSMTransition<>(moved, dropped, sndDbleClick) {
			@Override
			protected void action(final Event event) {
				if(dataHandler != null) {
					dataHandler.onUnlock();
				}
			}
		};
	}

	@Override
	public void log(final boolean log) {
		super.log(log);
		firstDbleClick.log(log);
		sndDbleClick.log(log);
	}

	@Override
	public void reinit() {
		super.reinit();
		firstDbleClick.reinit();
		sndDbleClick.reinit();
		checkButton = null;
	}

	@Override
	public void fullReinit() {
		super.fullReinit();
		firstDbleClick.fullReinit();
		sndDbleClick.fullReinit();
	}

	interface DragLockFSMHandler extends FSMDataHandler {
		void onLock();
		void onMove(final MouseEvent event);
		void onUnlock();
	}
}
