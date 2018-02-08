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
package org.malai.javafx.interaction2.library;

import java.util.HashSet;
import java.util.Set;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.malai.fsm.CancellingState;
import org.malai.fsm.InputState;
import org.malai.fsm.OutputState;
import org.malai.fsm.StdState;
import org.malai.fsm.SubFSMTransition;
import org.malai.fsm.TerminalState;
import org.malai.javafx.interaction2.FSMDataHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.KeyPressureTransition;
import org.malai.javafx.interaction2.KeyReleaseTransition;

public class KeysScrollFSM extends JfxFSM<KeysScrollFSM.KeysScrollFSMHandler> {
	protected final ScrollFSM scrollFSM;
	private final Set<KeyCode> currentCodes;

	public KeysScrollFSM() {
		super();
		scrollFSM = new ScrollFSM();
		currentCodes = new HashSet<>();
	}

	@Override
	protected void buildFSM(final KeysScrollFSMHandler dataHandler) {
		if(states.size() > 1) {
			return;
		}
		super.buildFSM(dataHandler);

		final StdState<Event> keyPressed = new StdState<>(this, "keyPressed");
		final CancellingState<Event> keyReleased = new CancellingState<>(this, "keyReleased");
		final TerminalState<Event> scrolled = new TerminalState<>(this, "scrolled");

		addState(keyPressed);
		addState(keyReleased);
		addState(scrolled);

		new SubFSMTransition<>(initState, scrolled, scrollFSM);
		new SubFSMTransition<>(keyPressed, scrolled, scrollFSM);
		new KeysScrollFSMKeyPressureTransition(initState, keyPressed);
		new KeysScrollFSMKeyPressureTransition(keyPressed, keyPressed);
		new KeyReleaseTransition(keyPressed, keyPressed) {
			@Override
			protected void action(final Event event) {
				if(event instanceof KeyEvent) {
					currentCodes.remove(((KeyEvent) event).getCode());
					if(dataHandler != null) {
						dataHandler.onKeyReleased((KeyEvent) event);
					}
				}
			}

			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof KeyEvent && currentCodes.contains(((KeyEvent) event).getCode()) && currentCodes.size() > 1;
			}
		};
		new KeyReleaseTransition(keyPressed, keyReleased) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return event instanceof KeyEvent && currentCodes.contains(((KeyEvent) event).getCode()) && currentCodes.size() == 1;
			}
		};
	}

	private final class KeysScrollFSMKeyPressureTransition extends KeyPressureTransition {
		KeysScrollFSMKeyPressureTransition(final OutputState<Event> srcState, final InputState<Event> tgtState) {
			super(srcState, tgtState);
		}

		@Override
		protected void action(final Event event) {
			if(event instanceof KeyEvent) {
				currentCodes.add(((KeyEvent) event).getCode());
				if(dataHandler != null) {
					dataHandler.onKeyPressed((KeyEvent) event);
				}
			}
		}
	}

	interface KeysScrollFSMHandler extends FSMDataHandler {
		void onKeyPressed(final KeyEvent event);

		void onKeyReleased(final KeyEvent event);
	}
}
