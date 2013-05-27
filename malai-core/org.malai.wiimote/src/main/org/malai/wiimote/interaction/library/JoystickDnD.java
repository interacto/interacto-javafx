package org.malai.wiimote.interaction.library;

import org.malai.interaction.IntermediaryState;
import org.malai.wiimote.interaction.ButtonPressedTransition;
import org.malai.wiimote.interaction.JoystickMoveTransition;

import wiiusej.wiiusejevents.physicalevents.JoystickEvent;

/**
 * Allows to make a drag and drop by pressing a single button and 
 * move with the joystick. 
 * @author Maxime Lorant
 *
 */
public class JoystickDnD extends ButtonHeld {

	/** The joystick activity */
	protected JoystickEvent joystick;
	
	@Override
	protected void initStateMachine() {
		
		super.initStateMachine();
		
		final IntermediaryState move = new IntermediaryState("move"); //$NON-NLS-1$

		addState(move);
		
		new JoystickMoveTransition(pressed, move) {
			@Override
			public void action() {
				super.action();
				JoystickDnD.this.joystick = this.joystick;
			}
		};
		
		new JoystickMoveTransition(move, move);
		
		new ButtonPressedTransition(move, released) {	
			@Override
			public boolean isGuardRespected() {
				int released = this.button.getButtonsJustReleased();
				return super.isGuardRespected() && JoystickDnD.this.buttonPressed==released;
			}
		};
	}
}
