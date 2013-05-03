package org.malai.wiimote.interaction.library;

import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.JoystickMoveTransition;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.JoystickEvent;


/**
 * Occurs when one or several buttons are pressed on the wiimote
 * 
 * @author Maxime Lorant
 *
 */
public class JoystickMove extends WiimoteInteraction {
	
	/** The pressed button. */
	protected JoystickEvent joystick;

	/**
	 * Creates the interaction.
	 */
	public JoystickMove() {
		super();
		initStateMachine();
	}


	@Override
	public void reinit() {
		super.reinit();
		joystick = null;
	}


	@Override
	protected void initStateMachine() {
		final TerminalState moved = new TerminalState("pressed"); //$NON-NLS-1$

		addState(moved);

		new JoystickMoveTransition(initState, moved) {
			@Override
			public void action() {
				super.action();
				JoystickMove.this.joystick = this.joystick;
			}
		};
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public JoystickEvent getJoystick() {
		return joystick;
	}
}
