package org.malai.swing.interaction.library;

import java.util.ArrayList;
import java.util.List;

import org.malai.interaction.AbortingState;
import org.malai.interaction.IntermediaryState;
import org.malai.interaction.KeyPressureTransition;
import org.malai.interaction.KeyReleaseTransition;
import org.malai.interaction.TerminalState;
import org.malai.interaction.library.PointInteraction;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * This interaction permits to mouse press with key pressures (eg modifiers).<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013/07/20<br>
 * @author Arnaud BLOUIN
 * @since 1.1
 */
public class PressWithKeys extends PointInteraction {
	/** The list of the keys pressed. */
	protected List<Integer> keys;


	/**
	 * Creates the interaction.
	 */
	public PressWithKeys() {
		super();
		keys = new ArrayList<>();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();

		if(keys!=null)
			keys.clear();
	}


	@SuppressWarnings("unused")
	@Override
	protected void initStateMachine() {
		final IntermediaryState pressed = new IntermediaryState("pressed"); //$NON-NLS-1$
		final TerminalState end			= new TerminalState("ended"); //$NON-NLS-1$
		final AbortingState abort		= new AbortingState("abort"); //$NON-NLS-1$

		addState(pressed);
		addState(end);
		addState(abort);

		new PressWithKeysKeyPressedTransition(initState, pressed);
		new PressWithKeysKeyPressedTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==PressWithKeys.this.getLastHIDUsed();
			}
		};
		new KeyReleaseTransition(pressed, abort) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==PressWithKeys.this.getLastHIDUsed() && PressWithKeys.this.keys.contains(key) && PressWithKeys.this.keys.size()==1;
			}
		};
		new KeyReleaseTransition(pressed, pressed) {
			@Override
			public boolean isGuardRespected() {
				return this.hid==PressWithKeys.this.getLastHIDUsed() && PressWithKeys.this.keys.contains(key) && PressWithKeys.this.keys.size()>1;
			}

			@Override
			public void action() {
				PressWithKeys.this.keys.remove((Integer)this.key);
			}
		};
		new PointPressureTransition(initState, end);
		new PointPressureTransition(pressed, end);
	}



	/**
	 * Defines a transition modifying the keys attribute of the interaction.
	 */
	public class PressWithKeysKeyPressedTransition extends KeyPressureTransition {
		/**
		 * Creates the transition.
		 * @param inputState The source state of the transition.
		 * @param outputState The target state of the transition.
		 */
		public PressWithKeysKeyPressedTransition(final SourceableState inputState, final TargetableState outputState) {
			super(inputState, outputState);
		}

		@Override
		public void action() {
			PressWithKeys.this.keys.add(this.key);
			PressWithKeys.this.setLastHIDUsed(this.hid);
		}
	}


	/**
	 * @return the keys pressed.
	 * @since 0.2
	 */
	public List<Integer> getKeys() {
		return keys;
	}
}

