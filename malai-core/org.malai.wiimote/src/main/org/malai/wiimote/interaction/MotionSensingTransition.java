package org.malai.wiimote.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;

/**
 * Occur every 10ms, when the motion sensing is enabled. Provide
 * motion sensing information (orientation, acceleration and GForce...)
 * 
 * @see wiiusej.wiiusejevents.physicalevents.MotionSensingTransition
 * @author Maxime Lorant
 *
 */
public class MotionSensingTransition extends Transition {
	
	protected MotionSensingEvent motion;

	public MotionSensingTransition(SourceableState inputState, TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return Motion sensing data
	 * @since 0.2
	 */
	public MotionSensingEvent getMotion() {
		return motion;
	}


	/**
	 * Sets motion sensing data
	 * @param motion motion sensing data. Must not be null.
	 * @since 0.2
	 */
	public void setMotion(final MotionSensingEvent motion) {
		if(motion!=null)
			this.motion = motion;
	}
	
}
