package org.malai.wiimote.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.NunchukEvent;

/**
 * 
 * @see wiiusej.wiiusejevents.physicalevents.NunchukEvent
 * @author Maxime Lorant
 */
public class NunchukTransition extends Transition {
	
	/** Nunchuk event received */
	protected NunchukEvent nunchuk;

	public NunchukTransition(SourceableState inputState, TargetableState outputState) {
		super(inputState, outputState);
	}

	/**
	 * @return Nunchul event data
	 * @since 0.2
	 */
	public NunchukEvent getNunchukEvent() {
		return nunchuk;
	}

	/**
	 * Sets Nunchuk event data
	 * @param ir IR data. Must not be null.
	 * @since 0.2
	 */
	public void setNunchukEvent(final NunchukEvent nunchuk) {
		if(nunchuk!=null)
			this.nunchuk = nunchuk;
	}
	
}

