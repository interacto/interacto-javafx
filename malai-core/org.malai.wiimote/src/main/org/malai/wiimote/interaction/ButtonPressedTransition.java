package org.malai.wiimote.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;

/**
 * Occur when a button is pressed on the Wiimote or an expansion
 * 
 * @see wiiusej.wiiusejevents.physicalevents.ButtonsEvent
 * @author Maxime Lorant
 * @since 0.2
 */
public class ButtonPressedTransition extends Transition {
	
	/** Button event received */
	protected ButtonsEvent button;
	
	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ButtonPressedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The pressed button.
	 */
	public ButtonsEvent getButton() {
		return button;
	}


	/**
	 * Sets the pressed button.
	 * @param button The pressed button. Must not be null.
	 */
	public void setButton(final ButtonsEvent button) {
		if(button!=null)
			this.button = button;
	}
}
