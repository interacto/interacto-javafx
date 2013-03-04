package org.malai.wiimote.interaction;

import org.malai.interaction.Transition;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

import wiiusej.wiiusejevents.physicalevents.ButtonsEvent;


public class ButtonPressedTransition extends Transition {
	
	protected ButtonsEvent button;
	
	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 */
	public ButtonPressedTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The pressed button.
	 * @since 0.2
	 */
	public ButtonsEvent getButton() {
		return button;
	}


	/**
	 * Sets the pressed button.
	 * @param button The pressed button. Must not be null.
	 * @since 0.2
	 */
	public void setButton(final ButtonsEvent button) {
		if(button!=null)
			this.button = button;
	}
}
