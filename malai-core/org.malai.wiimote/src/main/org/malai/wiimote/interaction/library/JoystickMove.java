package org.malai.wiimote.interaction.library;

import org.malai.interaction.TerminalState;
import org.malai.wiimote.interaction.JoystickMoveTransition;
import org.malai.wiimote.interaction.WiimoteInteraction;

import wiiusej.wiiusejevents.physicalevents.JoystickEvent;


/**
 * Track every moves make with the nunchuk joystick (if this last
 * is connected)
 * 
 * @author Maxime Lorant
 *
 */
public class JoystickMove extends WiimoteInteraction {
	
	/** The joystick activity */
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
	 * @return Joystick information
	 * @since 0.2
	 */
	public JoystickEvent getJoystick() {
		return joystick;
	}
}
