package org.malai.wiimote.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.IREvent;

/**
 * Occur every 10ms, when the IR (InfraRed) tracking is enabled. Provide
 * IR tracking, about the wiimote position relative to the sensor bar.
 * 
 * @see wiiusej.wiiusejevents.physicalevents.IREvent
 * @author Maxime Lorant
 */
public class IRTransition extends Transition {
	
	/** IR (= infra-red) event received */
	protected IREvent ir;

	public IRTransition(SourceableState inputState, TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return IR data
	 * @since 0.2
	 */
	public IREvent getIR() {
		return ir;
	}

	/**
	 * Sets IR data
	 * @param ir IR data. Must not be null.
	 * @since 0.2
	 */
	public void setIR(final IREvent ir) {
		if(ir!=null)
			this.ir = ir;
	}
	
}

