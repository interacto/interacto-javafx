package org.malai.wiimote.interaction.library;

import org.malai.interaction.KeyPressureTransition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;
import org.malai.wiimote.interaction.ButtonPressedTransition;

import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;

/**
 * This transition should be used to cancel an interaction using key ESCAPE.
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/18/2011<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class EscapeButtonPressureTransition extends ButtonPressedTransition {
	/**
	 * Creates the transition.
	 * @param inputState The source state.
	 * @param outputState The target state.
	 * @since 0.2
	 */
	public EscapeButtonPressureTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}

	@Override
	/**
	 * Returns true if the B button is just pressed on the Wiimote
	 */
	public boolean isGuardRespected() {
		if(button instanceof WiimoteButtonsEvent)
			return ((WiimoteButtonsEvent) button).isButtonBPressed();
		else
			return false;
	}
}