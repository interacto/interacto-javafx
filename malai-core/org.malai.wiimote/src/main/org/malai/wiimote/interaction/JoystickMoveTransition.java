package org.malai.wiimote.interaction;
import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.JoystickEvent;

/**
 * Initiate at every move of the joystick. Returns information about
 * orientation and magnitude of the joystick
 * 
 * @see wiiusej.wiiusejevents.physicalevents.JoystickEvent
 * @author Maxime Lorant
 *
 */
public class JoystickMoveTransition extends Transition {
		
	protected JoystickEvent joystick;

	public JoystickMoveTransition(SourceableState inputState, TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return joystick data
	 * @since 0.2
	 */
	public JoystickEvent getJoystick() {
		return joystick;
	}


	/**
	 * Sets joystick movements data
	 * @param motion motion sensing data. Must not be null.
	 * @since 0.2
	 */
	public void setJoystick(final JoystickEvent joystick) {
		if(joystick!=null)
			this.joystick = joystick;
	}
}
